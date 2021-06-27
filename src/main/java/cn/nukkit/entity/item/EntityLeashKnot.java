package cn.nukkit.entity.item;

import cn.nukkit.entity.Entity;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityDamageEvent.DamageCause;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.LevelSoundEventPacket;

public class EntityLeashKnot extends Entity {

    public static final int NETWORK_ID = 88;
    
    public EntityLeashKnot(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }
    
    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }
    
    @Override
    public float getWidth() {
        return 0.4f; // TODO: Correct value
    }
    
    @Override
    public float getHeight() {
        return 0.8f; // TODO: Correct value
    }
    
    @Override
    public void initEntity() {
        super.initEntity();
        this.level.addLevelSoundEvent(this, LevelSoundEventPacket.SOUND_LEASHKNOT_PLACE, -1, NETWORK_ID);
    }
    
    @Override
    public boolean attack(EntityDamageEvent source) {
        DamageCause cause = source.getCause(); // TODO
        
        this.level.addLevelSoundEvent(this, LevelSoundEventPacket.SOUND_LEASHKNOT_BREAK, -1, NETWORK_ID);
        
        this.close();
        return true;
    }
}
