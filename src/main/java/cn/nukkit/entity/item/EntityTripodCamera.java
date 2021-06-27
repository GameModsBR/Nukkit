package cn.nukkit.entity.item;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityInteractable;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityDamageEvent.DamageCause;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.CameraPacket;
import cn.nukkit.network.protocol.EntityEventPacket;

/**
 * @author good777LUCKY
 */
public class EntityTripodCamera extends Entity implements EntityInteractable {

    public static final int NETWORK_ID = 62;
    
    protected Player target;
    protected int fuse;
    
    public EntityTripodCamera(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }
    
    @Override
    public void initEntity() {
        super.initEntity();
        
        this.setMaxHealth(4);
        this.setHealth(4);
    }
    
    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }
    
    @Override
    public String getName() {
        return "Tripod Camera";
    }
    
    @Override
    public float getWidth() {
        return 0.75f;
    }
    
    @Override
    public float getHeight() {
        return 1.8f;
    }
    
    @Override
    protected float getGravity() {
        return 0.08f;
    }
    
    @Override
    protected float getDrag() {
        return 0.02f;
    }
    
    @Override
    public boolean attack(EntityDamageEvent source) {
        DamageCause cause = source.getCause(); // TODO
        
        EntityEventPacket pk = new EntityEventPacket();
        pk.eid = this.getId();
        pk.event = 61; // DEATH_SMOKE_CLOUD
        Server.broadcastPacket(this.getViewers().values(), pk);
        
        this.close();
        return true;
    }
    
    @Override
    public boolean canDoInteraction() {
        return true;
    }
    
    @Override
    public String getInteractButtonText() {
        return "action.interact.takepicture";
    }
    
    @Override
    public boolean onInteract(Player player, Item item, Vector3 clickedPos) {
        if (this.target == null) {
            this.target = player;
            this.fuse = 99;
            
            CameraPacket pk = new CameraPacket();
            pk.cameraUniqueId = this.getId();
            pk.playerUniqueId = player.getId();
            Server.broadcastPacket(this.getViewers().values(), pk);
        }
        return true;
    }
    
    @Override
    public boolean onUpdate(int currentTick) {
        if (this.closed) {
            return false;
        }
        
        int tickDiff = currentTick - lastUpdate;
        boolean hasUpdated = super.onUpdate(currentTick);
        
        if (closed || tickDiff <= 0 && !justCreated) {
            return hasUpdated;
        }
        
        this.timing.startTiming();
        lastUpdate = currentTick;
        boolean hasUpdate = entityBaseTick(tickDiff);
        
        if (isAlive()) {
            if (!onGround) {
                motionY -= getGravity();
                move(motionX, motionY, motionZ);
                float friction = 1 - getDrag();
                motionX *= friction;
                motionY *= 1 - getDrag();
                motionZ *= friction;
            }
            
            if (this.target != null) {
                this.yaw = Math.toDegrees(Math.atan2(this.target.z - this.z, this.target.x - this.x)) - 90;
                this.fuse -= tickDiff;
                if (this.fuse <= 0) {
                    this.close();
                }
            }
            
            updateMovement();
            hasUpdate = true;
        }
        
        this.timing.stopTiming();
        
        return hasUpdate || !onGround || Math.abs(motionX) > 0.00001 || Math.abs(motionY) > 0.00001 || Math.abs(motionZ) > 0.00001;
    }
}
