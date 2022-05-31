package data.scripts.weapons;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.combat.listeners.ApplyDamageResultAPI;
import com.fs.starfarer.api.loading.DamagingExplosionSpec;
import data.scripts.util.MagicRender;
import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;

public class vic_astronomiconOnHit2 implements OnHitEffectPlugin {

    static final Color CORE_EXPLOSION_COLOR = new Color(0, 222, 255, 255);
    static final Color CORE_GLOW_COLOR = new Color(213, 240, 241, 150);
    static final Color EXPLOSION_COLOR = new Color(176, 243, 255, 10);
    static final Color FLASH_GLOW_COLOR = new Color(215, 241, 238, 200);
    static final Color GLOW_COLOR = new Color(172, 255, 230, 50);
    static final Vector2f ZERO = new Vector2f();

    static final int NUM_PARTICLES = 50;

    static final DamagingExplosionSpec explosion = new DamagingExplosionSpec(0.05f,
            350,
            200f,
            250,
            125f,
            CollisionClass.PROJECTILE_FF,
            CollisionClass.PROJECTILE_FIGHTER,
            0,
            0,
            0,
            0,
            new Color(33, 255, 122, 255),
            new Color(255, 150, 35, 255)
    );

    @Override
    public void onHit(DamagingProjectileAPI projectile, CombatEntityAPI target, Vector2f point, boolean shieldHit, ApplyDamageResultAPI damageResult, CombatEngineAPI engine) {
        if (target instanceof MissileAPI) return;

        explosion(point, engine, projectile);
    }

    static void explosion(Vector2f point, CombatEngineAPI engine, DamagingProjectileAPI projectile){
        // Blast visuals
        float CoreExplosionRadius = 70f;
        float CoreExplosionDuration = 1f;
        float ExplosionRadius = 200f;
        float ExplosionDuration = 1f;
        float CoreGlowRadius = 300f;
        float CoreGlowDuration = 1f;
        float GlowRadius = 400f;
        float GlowDuration = 1f;
        float FlashGlowRadius = 500f;
        float FlashGlowDuration = 0.05f;

        engine.spawnExplosion(point, ZERO, CORE_EXPLOSION_COLOR, CoreExplosionRadius, CoreExplosionDuration);
        engine.spawnExplosion(point, ZERO, EXPLOSION_COLOR, ExplosionRadius, ExplosionDuration);
        engine.addHitParticle(point, ZERO, CoreGlowRadius, 1f, CoreGlowDuration, CORE_GLOW_COLOR);
        engine.addSmoothParticle(point, ZERO, GlowRadius, 1f, GlowDuration, GLOW_COLOR);
        engine.addHitParticle(point, ZERO, FlashGlowRadius, 1f, FlashGlowDuration, FLASH_GLOW_COLOR);

        for (int x = 0; x < NUM_PARTICLES; x++) {
            engine.addHitParticle(point,
                    MathUtils.getPointOnCircumference(null, MathUtils.getRandomNumberInRange(50f, 150f), (float) Math.random() * 360f),
                    MathUtils.getRandomNumberInRange(4, 8), 1f, MathUtils.getRandomNumberInRange(0.4f, 0.9f), CORE_EXPLOSION_COLOR);
        }

        explosion.setDamageType(DamageType.FRAGMENTATION);
        explosion.setShowGraphic(false);
        explosion.setMinDamage(projectile.getDamageAmount() * 0.5f * 0.5f);
        explosion.setMaxDamage(projectile.getDamageAmount() * 0.5f);
        engine.spawnDamagingExplosion(explosion, projectile.getSource(), point);

        Global.getSoundPlayer().playSound("vic_astronomicon_hit",1f + MathUtils.getRandomNumberInRange(-0.1f, 0.1f),10f,point,ZERO);

        MagicRender.battlespace(
                Global.getSettings().getSprite("fx", "vic_laidlawExplosion"),
                point,
                new Vector2f(),
                new Vector2f(96, 96),
                new Vector2f(480, 480),
                //angle,
                360 * (float) Math.random(),
                0,
                new Color(255, 255, 255, 255),
                true,
                0,
                0.1f,
                0.3f
        );

        MagicRender.battlespace(
                Global.getSettings().getSprite("fx", "vic_laidlawExplosion"),
                point,
                new Vector2f(),
                new Vector2f(128, 128),
                new Vector2f(500, 500),
                //angle,
                360 * (float) Math.random(),
                0,
                new Color(0, 255, 156, 255),
                true,
                0.2f,
                0.0f,
                0.4f
        );

        MagicRender.battlespace(
                Global.getSettings().getSprite("fx", "vic_laidlawExplosion"),
                point,
                new Vector2f(),
                new Vector2f(250, 250),
                new Vector2f(150, 150),
                //angle,
                360 * (float) Math.random(),
                0,
                new Color(149, 35, 35, 200),
                true,
                0.35f,
                0.0f,
                1f
        );

        MagicRender.battlespace(
                Global.getSettings().getSprite("fx", "vic_laidlawExplosion"),
                point,
                new Vector2f(),
                new Vector2f(200, 200),
                new Vector2f(150, 150),
                //angle,
                360 * (float) Math.random(),
                0,
                new Color(0, 255, 194, 100),
                true,
                0.35f,
                0.0f,
                1f
        );
    }
}

