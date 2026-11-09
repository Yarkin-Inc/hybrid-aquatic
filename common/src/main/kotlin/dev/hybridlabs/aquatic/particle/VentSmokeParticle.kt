package dev.hybridlabs.aquatic.particle

import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.*
import net.minecraft.core.particles.SimpleParticleType
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.util.RandomSource

class VentSmokeParticle(
    world: ClientLevel,
    x: Double,
    y: Double,
    z: Double,
    xSpeed: Double,
    ySpeed: Double,
    zSpeed: Double, initialSprite: TextureAtlasSprite) : SingleQuadParticle(world, x, y, z, initialSprite) {

    init {
        this.scale(3.0f)
        this.setSize(0.25f, 0.25f)
        this.lifetime = this.random.nextInt(50) + 280

        this.gravity = 3.0E-6f
        this.xd = xSpeed
        this.yd = ySpeed + (this.random.nextFloat() / 500.0f).toDouble()
        this.zd = zSpeed
    }

    override fun tick() {
        this.xo = this.x
        this.yo = this.y
        this.zo = this.z
        if (this.age++ < this.lifetime && !(this.alpha <= 0.0f)) {
            this.xd += (this.random.nextFloat() / 5000.0f * (if (this.random.nextBoolean()) 1 else -1).toFloat()).toDouble()
            this.zd += (this.random.nextFloat() / 5000.0f * (if (this.random.nextBoolean()) 1 else -1).toFloat()).toDouble()
            this.yd -= this.gravity.toDouble()
            this.move(this.xd, this.yd, this.zd)
            if (this.age >= this.lifetime - 60 && this.alpha > 0.01f) {
                this.alpha -= 0.015f
            }
        } else {
            this.remove()
        }
    }

    override fun getLayer(): SingleQuadParticle.Layer {
        return SingleQuadParticle.Layer.OPAQUE
    }

    companion object {
        class Provider(private val sprites: SpriteSet) : ParticleProvider<SimpleParticleType> {
            override fun createParticle(
                type: SimpleParticleType,
                level: ClientLevel,
                x: Double,
                y: Double,
                z: Double,
                xSpeed: Double,
                ySpeed: Double,
                zSpeed: Double, random: RandomSource): SingleQuadParticle {
                val ventSmokeParticle = VentSmokeParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites.get(random))

                return ventSmokeParticle
            }
        }
    }
}