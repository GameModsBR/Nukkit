package cn.nukkit.entity.item;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityDamageEvent.DamageCause;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;

/**
 * @author good777LUCKY
 */
public class EntityBalloon extends Entity {

    public static final int NETWORK_ID = 107;
    
    protected long balloonAttached;
    protected float balloonMaxHeight;
    protected boolean balloonShouldDrop;
    
    public EntityBalloon(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }
    
    @Override
    public void initEntity() {
        super.initEntity();
        
        this.setMaxHealth(1);
        this.setHealth(1);
        
        this.setDataFlag(DATA_FLAGS, DATA_FLAG_GRAVITY, false);
        if (this.namedTag.contains("Color")) {
            this.dataProperties.putByte(DATA_COLOR, this.namedTag.getByte("Color"));
        } else {
            this.dataProperties.putByte(DATA_COLOR, 0);
        }
        if (this.namedTag.contains("balloon_attached")) {
            this.balloonAttached = this.namedTag.getLong("balloon_attached");
            this.dataProperties.putLong(DATA_BALLOON_ATTACHED_ENTITY, this.balloonAttached); // TODO: Or DATA_LEAD_HOLDER_EID?
            this.setDataFlag(DATA_FLAGS, DATA_FLAG_LEASHED, true);
        } else {
            this.balloonAttached = -1L; // Not Attached
        }
        if (this.namedTag.contains("balloon_max_height")) {
            this.balloonMaxHeight = this.namedTag.getFloat("balloon_max_height");
        } else {
            this.balloonMaxHeight = 256f;
        }
        if (this.namedTag.contains("balloon_should_drop")) {
            this.balloonShouldDrop = this.namedTag.getBoolean("balloon_should_drop");
        } else {
            balloonShouldDrop = false;
        }
    }
    
    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }
    
    @Override
    public String getName() {
        return "Balloon";
    }
    
    @Override
    public float getWidth() {
        return 0.4f;
    }
    
    @Override
    public float getHeight() {
        return 0.4f;
    }
    
    @Override
    protected float getGravity() {
        return -1.6f; // Should be Lift Force, balloon doesn't have gravity
    }
    
    @Override
    protected float getDrag() {
        return 0.02f; // TODO: Correct value
    }
    
    @Override
    public boolean attack(EntityDamageEvent source) {
        DamageCause cause = source.getCause();
        // TODO: Add Functionality
        if (source instanceof EntityDamageByEntityEvent) {
            Entity damager = ((EntityDamageByEntityEvent) source).getDamager();
            
            double deltaX = this.x - damager.x;
            double deltaZ = this.z - damager.z;
            this.knockBack(damager, source.getDamage(), deltaX, deltaZ, 0.1);
        }
        return true;
    }
    
    @Override
    public void saveNBT() {
        super.saveNBT();
        
        this.namedTag.putByte("Color", this.dataProperties.getByte(DATA_COLOR));
        this.namedTag.putLong("balloon_attached", balloonAttached);
        this.namedTag.putFloat("balloon_max_height", balloonMaxHeight);
        this.namedTag.putBoolean("balloon_should_drop", balloonShouldDrop);
    }
    
    @Override
    public boolean onUpdate(int currentTick) {
        if (this.closed) {
            return false;
        }
        
        int tickDiff = currentTick - this.lastUpdate;
        if (tickDiff <= 0 && !this.justCreated) {
            return true;
        }
        
        this.lastUpdate = currentTick;
        
        this.timing.startTiming();
        
        boolean hasUpdate = this.entityBaseTick(tickDiff);
        
        if (this.isAlive()) {
            if (this.y >= balloonMaxHeight) {
                this.close();
            }
            
            motionY -= getGravity() * 0.1f;
            
            move(motionX, motionY, motionZ);
            
            float friction = 1 - getDrag();
            
            // No fiction for X and Z
            motionY *= friction;
            
            updateMovement();
            // TODO: Add Functionality
        }
        
        this.timing.stopTiming();
        
        return hasUpdate || !this.onGround || Math.abs(this.motionX) > 0.00001 || Math.abs(this.motionY) > 0.00001 || Math.abs(this.motionZ) > 0.00001;
    }
    
    public void knockBack(Entity attacker, double damage, double x, double z, double base) {
        double f = Math.sqrt(x * x + z * z);
        if (f <= 0) {
            return;
        }
        
        f = 1 / f;
        
        Vector3 motion = new Vector3(this.motionX, this.motionY, this.motionZ);
        
        motion.x /= 2d;
        motion.z /= 2d;
        motion.x += x * f * base;
        motion.z += z * f * base;
        
        this.setMotion(motion);
    }
    
    @Override
    public void close() {
        super.close();
        
        if (this.balloonAttached != -1L) {
            this.level.getEntity(this.balloonAttached).close();
        }
    }
}
