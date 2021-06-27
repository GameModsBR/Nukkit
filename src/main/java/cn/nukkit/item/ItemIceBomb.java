package cn.nukkit.item;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.projectile.EntityIceBomb;

/**
 * @author good777LUCKY
 */
public class ItemIceBomb extends ProjectileItem {

    public ItemIceBomb() {
        this(0, 1);
    }
    
    public ItemIceBomb(Integer meta) {
        this(meta, 1);
    }
    
    public ItemIceBomb(Integer meta, int count) {
        super(ICE_BOMB, 0, count, "Ice Bomb");
    }
    
    @Override
    public int getMaxStackSize() {
        return 16;
    }
    
    @Override
    public String getProjectileEntityType() {
        return "IceBomb";
    }
    
    @Override
    public float getThrowForce() {
        return 1.5f;
    }
    
    @Override
    protected Entity correctProjectile(Player player, Entity projectile) {
        if (projectile instanceof EntityIceBomb) {
            if (player.getServer().getTick() - player.getLastIceBombThrowingTick() < 10) {
                projectile.kill();
                return null;
            }
            return projectile;
        }
        return null;
    }
}
