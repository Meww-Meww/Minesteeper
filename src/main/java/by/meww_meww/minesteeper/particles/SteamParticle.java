package by.meww_meww.minesteeper.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class SteamParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected SteamParticle(ClientLevel level, double xOrigin, double yOrigin, double zOrigin, double xDistance, double yDistance, double zDistance, float p_171914_, SpriteSet sprites) {
        super(level, xOrigin, yOrigin, zOrigin, 0.0D, 0.0D, 0.0D);
        this.friction = 0.96F;
        this.gravity = -0.1F;
        this.speedUpWhenYMotionIsBlocked = true;
        this.sprites = sprites;
        this.xd *= 0.1F;
        this.yd *= 0.1F;
        this.zd *= 0.1F;
        this.xd += xDistance;
        this.yd += yDistance;
        this.zd += zDistance;
        this.rCol = 1;
        this.gCol = 1;
        this.bCol = 1;
        this.quadSize *= 0.75F * p_171914_;
        this.lifetime = (int)(10D * p_171914_);
        this.setSpriteFromAge(sprites);
        this.hasPhysics = true;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet p_107696_) {
            this.sprites = p_107696_;
        }

        public Particle createParticle(SimpleParticleType p_107707_, ClientLevel p_107708_, double p_107709_, double p_107710_, double p_107711_, double p_107712_, double p_107713_, double p_107714_) {
            return new SteamParticle(p_107708_, p_107709_, p_107710_, p_107711_, p_107712_, p_107713_, p_107714_, 1.0F, this.sprites);
        }
    }
}
