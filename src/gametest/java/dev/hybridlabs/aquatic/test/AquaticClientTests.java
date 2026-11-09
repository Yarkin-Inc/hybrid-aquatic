package dev.hybridlabs.aquatic.test;

import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import java.util.ArrayList;

public final class AquaticClientTests implements FabricClientGameTest {
    @Override public void runTest(ClientGameTestContext context) {
        try (var world = context.worldBuilder().create()) {
            context.runOnClient(AquaticTooltipChecks::verify);
            world.getServer().runCommand("advancement grant @a from hybrid_aquatic:root");
            context.waitTicks(10);
            AquaticTooltipChecks.preview(context);
            var entities = new ArrayList<Entity>();
            var items = new ArrayList<ItemStack>();
            context.runOnClient(client -> {
                for (var type : BuiltInRegistries.ENTITY_TYPE) {
                    if (!BuiltInRegistries.ENTITY_TYPE.getKey(type).getNamespace().equals("hybrid_aquatic")) continue;
                    var entity = type.create(client.level, EntitySpawnReason.COMMAND);
                    entity.setId(-100 - entities.size());
                    entities.add(entity);
                }
                for (var item : BuiltInRegistries.ITEM) {
                    if (BuiltInRegistries.ITEM.getKey(item).getNamespace().equals("hybrid_aquatic")) items.add(new ItemStack(item));
                }
            });
            for (int page = 0; page * 12 < entities.size(); page++) {
                final int first = page * 12;
                context.runOnClient(client -> client.gui.setScreen(new Screen(Component.literal("Hybrid Aquatic entity render test")) {
                    @Override public void extractRenderState(GuiGraphicsExtractor graphics, int mx, int my, float delta) {
                        graphics.fill(0, 0, width, height, 0xFF24465B);
                        int w = width / 4, h = height / 3;
                        for (int i = first; i < Math.min(first + 12, entities.size()); i++) {
                            var entity = entities.get(i);
                            var renderer = client.getEntityRenderDispatcher().getRenderer(entity);
                            var state = renderer.createRenderState(entity, delta);
                            state.shadowPieces.clear(); state.nameTag = null;
                            int col = (i-first) % 4, row = (i-first) / 4;
                            float scale = Math.min(25f, 60f / Math.max(entity.getBbWidth(), entity.getBbHeight()));
                            graphics.entity(state, scale, new Vector3f(), new Quaternionf().rotationZ((float)Math.PI).rotateY((float)Math.PI / 2), null,
                                    col*w, row*h, (col+1)*w, (row+1)*h);
                        }
                    }
                    @Override public boolean isPauseScreen() { return false; }
                }));
                context.waitTicks(4);
                context.takeScreenshot("aquatic-entities-" + page);
            }
            for (int page = 0; page * 120 < items.size(); page++) {
            final int itemStart = page * 120;
            context.runOnClient(client -> client.gui.setScreen(new Screen(Component.literal("Hybrid Aquatic item render test")) {
                @Override public void extractRenderState(GuiGraphicsExtractor graphics, int mx, int my, float delta) {
                    graphics.fill(0, 0, width, height, 0xFF24465B);
                    int cols = width / 18;
                    for (int i = itemStart; i < Math.min(itemStart + 120, items.size()); i++) graphics.item(items.get(i), ((i-itemStart) % cols)*18, ((i-itemStart) / cols)*18);
                }
                @Override public boolean isPauseScreen() { return false; }
            }));
            context.waitTicks(10);
            context.takeScreenshot("aquatic-items-" + page);
            }
            // Exercise armor and plushie head renderers against a real player render state.
            for (var stack : items) {
                String name = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
                var equipment = stack.get(net.minecraft.core.component.DataComponents.EQUIPPABLE);
                if (equipment == null && !name.endsWith("_plushie")) continue;
                context.runOnClient(client -> {
                    for (var slot : net.minecraft.world.entity.EquipmentSlot.values()) client.player.setItemSlot(slot, ItemStack.EMPTY);
                    client.player.setItemSlot(equipment == null ? net.minecraft.world.entity.EquipmentSlot.HEAD : equipment.slot(), stack);
                    client.gui.setScreen(new Screen(Component.literal("Aquatic equipment")) {
                        @Override public void extractRenderState(GuiGraphicsExtractor graphics, int mx, int my, float delta) {
                            graphics.fill(0, 0, width, height, 0xFF24465B);
                            var renderer = client.getEntityRenderDispatcher().getRenderer(client.player);
                            var state = renderer.createRenderState(client.player, delta);
                            state.shadowPieces.clear(); state.nameTag = null;
                            graphics.entity(state, 40f, new Vector3f(), new Quaternionf().rotationZ((float)Math.PI).rotateY(0.3f), null, 0, 0, width, height);
                        }
                        @Override public boolean isPauseScreen() { return false; }
                    });
                });
                context.waitTicks(2);
                if (name.equals("diving_helmet") || name.equals("basking_shark_plushie")) context.takeScreenshot("aquatic-equipped-" + name);
            }
            context.runOnClient(client -> client.gui.setScreen(null));
            world.getServer().runCommand("fill -2 4 -2 10 4 4 stone");
            world.getServer().runCommand("fill -2 5 -2 10 7 4 water");
            String[] animatedBlocks = {"anemone", "giant_green_anemone", "strawberry_anemone", "message_in_a_bottle", "buoy", "bell_buoy", "basking_shark_plushie"};
            for (int i = 0; i < animatedBlocks.length; i++)
                world.getServer().runCommand("setblock " + i + (i < 3 || i == 6 ? " 5 0 hybrid_aquatic:" : " 8 0 hybrid_aquatic:") + animatedBlocks[i] + (i < 3 ? "[waterlogged=true]" : ""));
            world.getServer().runCommand("gamemode spectator @a");
            world.getServer().runCommand("tp @a 3 10 7 180 25");
            context.waitTicks(20);
            context.takeScreenshot("aquatic-animated-blocks");

        }
    }
}
