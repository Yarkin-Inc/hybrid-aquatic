package dev.hybridlabs.aquatic.entity.fish

import dev.hybridlabs.aquatic.effect.HAMobEffects
import dev.hybridlabs.hapi.tag.HAPIEntityTags
import dev.hybridlabs.aquatic.tag.HAItemTags
import dev.hybridlabs.hapi.entity.ai.MobTargetConfiguration
import dev.hybridlabs.hapi.entity.ai.goal.aquatic.WaterAnimalEatItemGoal
import dev.hybridlabs.hapi.entity.ai.goal.aquatic.boids.BoidGoal
import dev.hybridlabs.hapi.entity.ai.goal.aquatic.boids.StayInWaterGoal
import dev.hybridlabs.hapi.entity.base.aquatic.BaseSchoolingFishEntity
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.TimeUtil
import net.minecraft.util.valueproviders.IntProvider
import net.minecraft.world.Difficulty
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.*
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.phys.AABB
import net.minecraft.world.entity.EntityReference

class PiranhaEntity(type: EntityType<out PiranhaEntity>, world: Level) :
    BaseSchoolingFishEntity(type, world),
    NeutralMob {

    override fun getTargetConfig() = TARGET_CONFIG

    private var angerTime = NeutralMob.NO_ANGER_END_TIME
    private var persistentAngerTarget: EntityReference<LivingEntity>? = null
    private var ticksUntilNextAlert = 0

    override fun registerGoals() {
        super.registerGoals()
        goalSelector.addGoal(5, BoidGoal(this, 0.25f, 0.5f, 8 / 20f, 1 / 20f))
        goalSelector.addGoal(3, StayInWaterGoal(this))
        goalSelector.addGoal(2, WaterAnimalEatItemGoal(this))
        targetSelector.addGoal(1, HurtByTargetGoal(this).setAlertOthers())
        targetSelector.addGoal(2, NearestAttackableTargetGoal(this, Player::class.java, 10, true, false) { candidate, server -> this.isAngryAt(candidate, server) })
        targetSelector.addGoal(2, NearestAttackableTargetGoal(this, LivingEntity::class.java, 10, true, true) { candidate, _ -> candidate.hasEffect(HAMobEffects.BLEEDING.asHolder()) && candidate !is PiranhaEntity })
        targetSelector.addGoal(3, ResetUniversalAngerTargetGoal(this, true))
    }

    override fun getMaxSpawnClusterSize(): Int {
        return 6
    }

    override fun isFood(stack: ItemStack): Boolean {
        return stack.`is`(HAItemTags.SMALL_FISH)
    }

    override fun addAdditionalSaveData(compound: net.minecraft.world.level.storage.ValueOutput) {
        super.addAdditionalSaveData(compound)
        this.addPersistentAngerSaveData(compound)
    }

    override fun readAdditionalSaveData(compound: net.minecraft.world.level.storage.ValueInput) {
        super.readAdditionalSaveData(compound)
        this.readPersistentAngerSaveData(this.level(), compound)
    }

    override fun customServerAiStep(level: net.minecraft.server.level.ServerLevel) {
        this.updatePersistentAnger(this.level() as ServerLevel, true)
        if (this.target != null) {
            this.maybeAlertOthers()
        }

        if (this.isAngry) {
            this.lastHurtByPlayerMemoryTime = 100
        }

        super.customServerAiStep(level)
    }

    override fun canCollideWith(entity: Entity): Boolean {
        if (entity is PiranhaEntity) {
            return false
        }
        return super.canCollideWith(entity)
    }

    override fun doPush(entity: Entity) {
        if (entity is PiranhaEntity) {
            return
        }
        super.doPush(entity)
    }

    override fun doHurtTarget(level: net.minecraft.server.level.ServerLevel, target: Entity): Boolean {
        if (super.doHurtTarget(level, target)) {
            if (target is LivingEntity) {
                var i = 0
                if (level().difficulty == Difficulty.NORMAL) {
                    i = 7
                } else if (level().difficulty == Difficulty.HARD) {
                    i = 15
                }

                if (i > 0) {
                    target.addEffect(MobEffectInstance(HAMobEffects.BLEEDING.asHolder(), i * 20, 0), this)
                }
            }

            return true
        } else {
            return false
        }
    }

    //#region Angerable Implementation Details
    override fun setPersistentAngerEndTime(time: Long) {
        this.angerTime = time
    }

    override fun getPersistentAngerEndTime(): Long {
        return this.angerTime
    }

    override fun getPersistentAngerTarget(): EntityReference<LivingEntity>? {
        return this.persistentAngerTarget
    }

    override fun setPersistentAngerTarget(target: EntityReference<LivingEntity>?) {
        this.persistentAngerTarget = target
    }

    override fun startPersistentAngerTimer() {
        this.setTimeToRemainAngry(PERSISTENT_ANGER_TIME.sample(this.random).toLong())
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun maybeAlertOthers() {
        if (this.ticksUntilNextAlert > 0) {
            --this.ticksUntilNextAlert
        } else {
            if (this.target?.let { this.sensing.hasLineOfSight(it) } == true) {
                this.alertOthers()
            }

            this.ticksUntilNextAlert = ALERT_INTERVAL.sample(this.random)
        }
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun alertOthers() {
        val d0 = this.getAttributeValue(Attributes.FOLLOW_RANGE)
        val aabb = AABB.unitCubeFromLowerCorner(this.position()).inflate(d0, 10.0, d0)
        this.level()
            .getEntitiesOfClass(PiranhaEntity::class.java, aabb, EntitySelector.NO_SPECTATORS)
            .stream().filter { entity: PiranhaEntity? -> entity !== this }
            .filter { entity: PiranhaEntity? -> entity!!.target == null }
            .filter { entity: PiranhaEntity? -> !entity!!.isAlliedTo(this.target) }
            .forEach { entity: PiranhaEntity? -> entity!!.target = this.target }
    }

    override fun setTarget(livingEntity: LivingEntity?) {
        if (this.target == null && livingEntity != null) {
            this.ticksUntilNextAlert = ALERT_INTERVAL.sample(this.random)
        }

        if (livingEntity is Player) {
            this.setLastHurtByPlayer(livingEntity, 100)
        }

        super.setTarget(livingEntity)
    }
    //#endregion

    companion object {
        private val TARGET_CONFIG = MobTargetConfiguration.create(
            listOf(
                HAPIEntityTags.SMALL_CREATURES
            ),
            listOf(
                HAPIEntityTags.MEDIUM_CREATURES,
                HAPIEntityTags.LARGE_CREATURES,
                HAPIEntityTags.ALL_SHARKS
            ),
        )

        val PERSISTENT_ANGER_TIME: IntProvider = TimeUtil.rangeOfSeconds(12, 24)
        val ALERT_INTERVAL: IntProvider = TimeUtil.rangeOfSeconds(2, 6)

        fun createMobAttributes(): AttributeSupplier.Builder {
            return createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 4.0)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.ATTACK_DAMAGE, 2.0)
                .add(Attributes.ATTACK_KNOCKBACK, 0.0)
                .add(Attributes.ATTACK_SPEED, 1.0)
                .add(Attributes.FOLLOW_RANGE, 8.0)
        }
    }
}