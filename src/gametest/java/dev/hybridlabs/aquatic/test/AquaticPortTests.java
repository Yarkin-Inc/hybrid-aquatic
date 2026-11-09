package dev.hybridlabs.aquatic.test;

import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.util.ProblemReporter;

public final class AquaticPortTests {
    @GameTest public void allEntitiesCreateAndSaveLoad(GameTestHelper helper) {
        int count = 0;
        for (var type : BuiltInRegistries.ENTITY_TYPE) {
            var id = BuiltInRegistries.ENTITY_TYPE.getKey(type);
            if (!id.getNamespace().equals("hybrid_aquatic")) continue;
            var entity = type.create(helper.getLevel(), EntitySpawnReason.COMMAND);
            helper.assertTrue(entity != null, "Create " + id);
            if (entity instanceof Mob mob) helper.assertTrue(mob.getMaxHealth() > 0, "Attributes for " + id);
            var output = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, helper.getLevel().registryAccess());
            entity.save(output);
            var restored = type.create(helper.getLevel(), EntitySpawnReason.LOAD);
            restored.load(TagValueInput.create(ProblemReporter.DISCARDING, helper.getLevel().registryAccess(), output.buildResult()));
            helper.assertTrue(restored.getType() == type, "Restore " + id);
            count++;
        }
        helper.assertTrue(count >= 90, "Expected aquatic entity registry, got " + count);
        helper.succeed();
    }
    @GameTest public void allMobAiTicksInWater(GameTestHelper helper) {
        var level = helper.getLevel();
        var origin = helper.absolutePos(new net.minecraft.core.BlockPos(1, 2, 1));
        for (var pos : net.minecraft.core.BlockPos.betweenClosed(origin.offset(-3, -1, -3), origin.offset(3, 3, 3)))
            level.setBlock(pos, net.minecraft.world.level.block.Blocks.WATER.defaultBlockState(), 2);
        int count = 0;
        for (var type : BuiltInRegistries.ENTITY_TYPE) {
            var id = BuiltInRegistries.ENTITY_TYPE.getKey(type);
            if (!id.getNamespace().equals("hybrid_aquatic")) continue;
            var entity = type.create(level, EntitySpawnReason.COMMAND);
            if (!(entity instanceof Mob mob)) continue;
            mob.snapTo(origin.getX() + 0.5, origin.getY(), origin.getZ() + 0.5, 0, 0);
            mob.setPersistenceRequired();
            try {
                for (int tick = 0; tick < 40; tick++) mob.tick();
            } catch (Exception exception) {
                throw new AssertionError("AI tick failed for " + id, exception);
            } finally {
                mob.discard();
            }
            count++;
        }
        helper.assertTrue(count >= 90, "Aquatic mobs exercised with active AI: " + count);
        helper.succeed();
    }

    @GameTest public void lureReturnsAfterCastingAndRetrieving(GameTestHelper helper) {
        var player = helper.makeMockPlayer(net.minecraft.world.level.GameType.SURVIVAL);
        var rod = new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.FISHING_ROD);
        var lure = new net.minecraft.world.item.ItemStack(dev.hybridlabs.aquatic.item.HAItems.INSTANCE.getBARBED_HOOK().get());
        player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, rod);
        player.setItemInHand(net.minecraft.world.InteractionHand.OFF_HAND, lure);
        rod.use(helper.getLevel(), player, net.minecraft.world.InteractionHand.MAIN_HAND);
        helper.assertTrue(player.fishing != null, "Casting creates a bobber");
        var bobber = player.fishing;
        helper.assertTrue(player.getOffhandItem().isEmpty(), "Lure transfers to bobber");
        helper.assertTrue(!((dev.hybridlabs.aquatic.access.CustomFishingBobberEntityData)bobber).getLureItem().isEmpty(), "Bobber contains lure");
        bobber.retrieve(rod);
        helper.assertTrue(player.getOffhandItem().is(dev.hybridlabs.aquatic.item.HAItems.INSTANCE.getBARBED_HOOK().get()), "Retrieving returns the lure");
        helper.succeed();
    }

    @GameTest public void argonautInventoryAndBottleVariantPersist(GameTestHelper helper) {
        var level = helper.getLevel();
        var type = dev.hybridlabs.aquatic.entity.HAEntityTypes.INSTANCE.getARGONAUT().get();
        var vehicle = type.create(level, EntitySpawnReason.COMMAND);
        vehicle.setItem(0, new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.COAL, 12));
        vehicle.setItem(3, new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.DIAMOND, 5));
        var output = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, level.registryAccess());
        vehicle.save(output);
        var restored = type.create(level, EntitySpawnReason.LOAD);
        restored.load(TagValueInput.create(ProblemReporter.DISCARDING, level.registryAccess(), output.buildResult()));
        helper.assertTrue(restored.getItem(0).getCount() == 12 && restored.getItem(3).getCount() == 5, "Vehicle fuel and cargo persist");
        var state = dev.hybridlabs.aquatic.block.HABlocks.INSTANCE.getMESSAGE_IN_A_BOTTLE().get().defaultBlockState();
        var pos = helper.absolutePos(new net.minecraft.core.BlockPos(1, 2, 1));
        level.setBlock(pos, state, 3);
        var bottle = (dev.hybridlabs.aquatic.block.entity.MessageInABottleBlockEntity)level.getBlockEntity(pos);
        var variant = dev.hybridlabs.aquatic.block.MessageInABottleBlock.Variant.values()[1];
        bottle.setVariant(variant);
        var tool = new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.DIAMOND_PICKAXE);
        tool.enchant(level.registryAccess().lookupOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT)
                .getOrThrow(net.minecraft.world.item.enchantment.Enchantments.SILK_TOUCH), 1);
        var drops = net.minecraft.world.level.block.Block.getDrops(state, level, pos, bottle, null, tool);
        helper.assertTrue(drops.size() == 1, "Silk touch drops one bottle");
        var data = drops.getFirst().get(net.minecraft.core.component.DataComponents.BLOCK_ENTITY_DATA);
        helper.assertTrue(data != null && data.copyTagWithoutId().getStringOr("variant", "").equals(variant.getId()), "Silk touch preserves bottle variant");
        helper.succeed();
    }

}
