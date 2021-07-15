package cn.nukkit.entity.mob;

import cn.nukkit.event.entity.EntityDamageEvent;
//import cn.nukkit.inventory.InventoryHolder;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

/**
 * @author GoodLucky777
 */
public class EntityAgent extends EntityMob /*implements InventoryHolder*/ {

    // TODO: tick world, inventory (size 27, type container), save inventory, not rename able with nametag, persistent
    public final static int NETWORK_ID = 56;
    
    //protected Inventory inventory;
    
    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }
    
    public EntityAgent(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }
    
    @Override
    protected void initEntity() {
        super.initEntity();
        
        this.setMaxHealth(Integer.MAX_VALUE);
        this.setHealth(20); // TODO: Check health and max health
        
        this.setNameTagVisible(true);
        this.setNameTagAlwaysVisible(true);
        
        this.fireProof = true;
    }
    
    @Override
    public float getWidth() {
        return 0.6f;
    }
    
    @Override
    public float getHeight() {
        return 0.93f;
    }
    
    @Override
    public String getName() {
        return "Agent";
    }
    
    @Override
    public boolean attack(EntityDamageEvent source) {
        return false;
    }
    
    /*@Override
    public Inventory getInventory() {
        return inventory;
    }*/
}
