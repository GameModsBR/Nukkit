package cn.nukkit.entity.projectile;

import cn.nukkit.entity.Entity;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;

import java.util.concurrent.ThreadLocalRandom;

public class EntityEnderEye extends EntityProjectile {

    public static final int NETWORK_ID = 70;

    public EntityEnderEye(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    public EntityEnderEye(FullChunk chunk, CompoundTag nbt, Entity shootingEntity) {
        super(chunk, nbt, shootingEntity);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public boolean onUpdate(int currentTick) {
        boolean hasUpdated = super.onUpdate(currentTick);

        if (!this.isAlive()) return hasUpdated;

        if (this.age == 20 * 3) {
            if (ThreadLocalRandom.current().nextFloat() > 0.2) {
                this.level.dropItem(this, Item.get(ItemID.ENDER_EYE));
            }

            this.kill();

            hasUpdated = true;
        } else if (this.age == 20 * 2) {
            this.setMotion(new Vector3(0, 0.2, 0));

            hasUpdated = true;
        }

        return hasUpdated;
    }
}
