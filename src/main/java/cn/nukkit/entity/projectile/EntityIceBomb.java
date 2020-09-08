package cn.nukkit.entity.projectile;

import cn.nukkit.entity.Entity;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

/**
 * @author good777LUCKY
 */
public class EntityIceBomb extends EntityProjectile {

    public static final int NETWORK_ID = 106;
    
    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }
    
    @Override
    public float getWidth() {
        return 0.25f;
    }
    
    @Override
    public float getLength() {
        return 0.25f;
    }
    
    @Override
    public float getHeight() {
        return 0.25f;
    }
    
    @Override
    protected float getGravity() {
        return 0.025f;
    }
    
    @Override
    protected float getDrag() {
        return 0.01f;
    }
    
    public EntityIceBomb(FullChunk chunk, CompoundTag nbt) {
        this(chunk, nbt, null);
    }
    
    public EntityIceBomb(FullChunk chunk, CompoundTag nbt, Entity shootingEntity) {
        super(chunk, nbt, shootingEntity);
    }
    
    @Override
    public boolean onUpdate(int currentTick) {
        if (this.closed) {
            return false;
        }
        
        this.timing.startTiming();
        
        boolean hasUpdate = super.onUpdate(currentTick);
        
        if (this.age > 1200) {
            this.kill();
            hasUpdate = true;
        }
        
        if (this.isCollided) {
            this.kill();
            // TODO: Add Functionality
            hasUpdate = true;
        }
        
        this.timing.stopTiming();
        
        return hasUpdate;
    }
}
