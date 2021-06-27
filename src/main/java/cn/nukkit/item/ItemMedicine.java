package cn.nukkit.item;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerItemConsumeEvent;
import cn.nukkit.math.Vector3;
import cn.nukkit.potion.Effect;

/**
 * @author good777LUCKY
 */
public class ItemMedicine extends Item {

    public static final int EYE_DROPS = 0;
    public static final int TONIC = 1;
    public static final int ANTIDOTE = 2;
    public static final int ELIXIR = 3;
    
    public ItemMedicine() {
        this(0, 1);
    }
    
    public ItemMedicine(Integer meta) {
        this(meta, 1);
    }
    
    public ItemMedicine(Integer meta, int count) {
        super(MEDICINE, meta, count, "Medicine");
    }
    
    protected static String getName(int meta) {
        switch (meta) {
            case 0:
                return "Eye Drops";
            case 1:
                return "Tonic";
            case 2:
                return "Antidote";
            case 3:
                return "Elixir";
            default:
                return "Medicine";
        }
    }
    
    @Override
    public int getMaxStackSize() {
        return 1;
    }
    
    @Override
    public boolean onClickAir(Player player, Vector3 directionVector) {
        switch (this.getDamage()) {
            case EYE_DROPS:
                return player.hasEffect(Effect.BLINDNESS);
            case TONIC:
                return player.hasEffect(Effect.NAUSEA);
            case ANTIDOTE:
                return player.hasEffect(Effect.POISON);
            case ELIXIR:
                return player.hasEffect(Effect.WEAKNESS);
        }
        return false;
    }
    
    @Override
    public boolean onUse(Player player, int ticksUsed) {
        PlayerItemConsumeEvent consumeEvent = new PlayerItemConsumeEvent(player, this);
        player.getServer().getPluginManager().callEvent(consumeEvent);
        if (consumeEvent.isCancelled()) {
            return false;
        }
        
        if (!player.isCreative()) {
            --this.count;
            player.getInventory().setItemInHand(this);
            player.getInventory().addItem(new ItemGlassBottle());
        }
        
        switch (this.getDamage()) {
            case EYE_DROPS:
                player.removeEffect(Effect.BLINDNESS);
                break;
            case TONIC:
                player.removeEffect(Effect.NAUSEA);
                break;
            case ANTIDOTE:
                player.removeEffect(Effect.POISON);
                break;
            case ELIXIR:
                player.removeEffect(Effect.WEAKNESS);
                break;
            default:
                return true;
        }
        
        return true;
    }
}
