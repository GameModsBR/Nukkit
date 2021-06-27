package cn.nukkit.entity.projectile;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.block.BlockWater;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
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
        } else {
            for (Block block : this.getCollisionBlocks()) {
                if (block instanceof BlockWater) {
                    Vector3 hitVec = this.floor();
                    for (int y = -1; y <= 1; y++) {
                        for (int x = -1; x <= 1; x++) {
                            for (int z = -1; z <= 1; z++) {
                                Vector3 changeBlock = hitVec.add(x, y, z);
                                if (this.level.getBlock(changeBlock) instanceof BlockWater) {
                                    this.level.setBlock(changeBlock, Block.get(BlockID.ICE), true);
                                }
                            }
                        }
                    }
                }
                this.kill();
                hasUpdate = true;
            }
        }
        
        this.timing.stopTiming();
        
        return hasUpdate;
    }
}
