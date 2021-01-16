package cn.nukkit.item;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.projectile.EntityEnderEye;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3f;

public class ItemEnderEye extends ProjectileItem {

    public ItemEnderEye() {
        this(0, 1);
    }

    public ItemEnderEye(Integer meta) {
        this(meta, 1);
    }

    public ItemEnderEye(Integer meta, int count) {
        super(ENDER_EYE, meta, count, "Ender Eye");
    }

    @Override
    public String getProjectileEntityType() {
        return "EnderEye";
    }

    @Override
    public float getThrowForce() {
        return 1.5f;
    }

    @Override
    protected Entity correctProjectile(Player player, Entity projectile) {
        if (projectile instanceof EntityEnderEye) {
            if (player.getServer().getTick() - player.getLastEnderPearlThrowingTick() < 20) {
                projectile.kill();

                return null;
            }

            Position strongholdPosition = this.getStrongholdPosition();

            if (strongholdPosition == null) {
                projectile.kill();

                return null;
            }

            Vector3f vector = strongholdPosition
                    .subtract(player.getPosition())
                    .asVector3f()
                    .normalize()
                    .multiply(1f);

            vector.y = 0.55f;

            projectile.setMotion(vector.asVector3().divide(this.getThrowForce()));

            return projectile;
        }

        return null;
    }

    private Position getStrongholdPosition() {
        return null;
    }
}
