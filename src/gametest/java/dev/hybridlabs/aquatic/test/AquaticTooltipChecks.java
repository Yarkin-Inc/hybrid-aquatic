package dev.hybridlabs.aquatic.test;

import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.resources.language.ClientLanguage;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.locale.Language;
import net.minecraft.resources.Identifier;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Items;
import net.minecraft.core.component.DataComponents;

import java.util.ArrayList;
import java.util.List;

final class AquaticTooltipChecks {
    static void preview(ClientGameTestContext context) {
        var original = new Language[1];
        context.runOnClient(client -> {
            original[0] = Language.getInstance();
            Language.inject(ClientLanguage.loadFrom(client.getResourceManager(), List.of("en_us", "ru_ru"), false));
        });
        try {
            for (String id : List.of("great_white_shark_plushie", "message_in_a_bottle")) {
                context.runOnClient(client -> {
                    var stack = new ItemStack(BuiltInRegistries.ITEM.getValue(Identifier.fromNamespaceAndPath("hybrid_aquatic", id)));
                    client.gui.setScreen(new Screen(Component.literal("Hybrid Aquatic tooltip check")) {
                        @Override public void extractRenderState(GuiGraphicsExtractor graphics, int mx, int my, float delta) {
                            graphics.fill(0, 0, width, height, 0xFF24465B);
                            graphics.item(stack, width / 4 - 24, height / 2);
                            graphics.setTooltipForNextFrame(client.font, stack, width / 4, height / 2);
                        }
                        @Override public boolean isPauseScreen() { return false; }
                    });
                });
                context.waitTicks(4);
                context.takeScreenshot("aquatic-tooltip-ru-" + id);
            }
            context.runOnClient(client -> {
                var advancements = client.getConnection().getAdvancements();
                int count = 0;
                for (var node : advancements.tree().nodes()) {
                    if (!node.holder().id().getNamespace().equals("hybrid_aquatic")) continue;
                    var display = node.advancement().display().orElseThrow();
                    for (var text : List.of(display.title(), display.description())) {
                        if (!text.getString().equals("Hybrid Aquatic") && text.getString().codePoints().noneMatch(c -> c >= 0x0400 && c <= 0x04ff)) {
                            throw new AssertionError("Untranslated Russian advancement: " + node.holder().id() + " / " + text.getString());
                        }
                    }
                    display.background().ifPresent(background -> {
                        if (client.getResourceManager().getResource(background.texturePath()).isEmpty()) {
                            throw new AssertionError("Missing advancement background: " + background.texturePath());
                        }
                    });
                    count++;
                }
                if (count != 32) throw new AssertionError("Expected 32 HA advancements, found " + count);
                var root = advancements.get(Identifier.fromNamespaceAndPath("hybrid_aquatic", "root"));
                if (root == null) throw new AssertionError("Missing HA advancement tab");
                client.gui.setScreen(new AdvancementsScreen(advancements));
                advancements.setSelectedTab(root, false);
                System.out.println("HA advancement check: " + count + " Russian titles/descriptions and background OK");
            });
            context.waitTicks(4);
            context.takeScreenshot("aquatic-advancements-ru");
        } finally {
            context.runOnClient(client -> {
                client.gui.setScreen(null);
                Language.inject(original[0]);
            });
        }
    }

    static void verify(Minecraft client) {
        Language original = Language.getInstance();
        var missing = new ArrayList<String>();
        try {
            for (String locale : List.of("en_us", "ru_ru")) {
                Language language = ClientLanguage.loadFrom(client.getResourceManager(),
                        locale.equals("en_us") ? List.of(locale) : List.of("en_us", locale), false);
                Language.inject(language);
                int count = 0;
                for (var item : BuiltInRegistries.ITEM) {
                    var id = BuiltInRegistries.ITEM.getKey(item);
                    if (!id.getNamespace().equals("hybrid_aquatic")) continue;
                    var stack = new ItemStack(item);
                    checkTranslations(stack.getHoverName(), language, locale + " " + id, missing);
                    for (var flag : List.of(TooltipFlag.NORMAL, TooltipFlag.ADVANCED)) {
                        var lines = stack.getTooltipLines(Item.TooltipContext.of(client.level), client.player, flag);
                        for (var line : lines) checkTranslations(line, language, locale + " " + id, missing);
                        if (lines.size() < 2 || !lines.get(1).getString().equals("Hybrid Aquatic")
                                || lines.stream().filter(line -> line.getString().equals("Hybrid Aquatic")).count() != 1) {
                            throw new AssertionError("Missing or duplicate mod subtitle: " + id + " / " + lines);
                        }
                        if (!lines.getFirst().getString().equals(stack.getHoverName().getString())) {
                            throw new AssertionError("Item title changed: " + id);
                        }
                    }
                    stack.set(DataComponents.CUSTOM_NAME, Component.literal("Моё название"));
                    if (!stack.getTooltipLines(Item.TooltipContext.of(client.level), client.player, TooltipFlag.NORMAL)
                            .getFirst().getString().equals("Моё название")) throw new AssertionError("Custom name lost: " + id);
                    count++;
                }
                var vanilla = new ItemStack(Items.STONE).getTooltipLines(Item.TooltipContext.of(client.level), client.player, TooltipFlag.NORMAL);
                if (vanilla.stream().anyMatch(line -> line.getString().equals("Hybrid Aquatic"))) throw new AssertionError("Vanilla item was labelled HA");
                System.out.println("HA tooltip translation check: " + locale + " / " + count + " items");
            }
        } finally {
            Language.inject(original);
        }
        if (!missing.isEmpty()) throw new AssertionError("Untranslated HA tooltip components: " + missing);
    }

    private static void checkTranslations(Component text, Language language, String item, List<String> missing) {
        if (text.getContents() instanceof TranslatableContents content) {
            if (!language.has(content.getKey()) && (content.getKey().contains("hybrid_aquatic") || content.getFallback() == null)) missing.add(item + " -> " + content.getKey());
            for (Object arg : content.getArgs()) {
                if (arg instanceof Component child) checkTranslations(child, language, item, missing);
            }
        }
        for (var child : text.getSiblings()) checkTranslations(child, language, item, missing);
    }
}
