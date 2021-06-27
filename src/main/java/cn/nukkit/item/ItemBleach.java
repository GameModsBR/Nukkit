package cn.nukkit.item;

/**
 * @author good777LUCKY
 */
public class ItemBleach extends Item {

    public ItemBleach() {
        this(0, 1);
    }
    
    public ItemBleach(Integer meta) {
        this(meta, 1);
    }
    
    public ItemBleach(Integer meta, int count) {
        super(BLEACH, meta, count, "Bleach");
    }
}
