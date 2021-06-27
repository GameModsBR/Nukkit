package cn.nukkit.item;

/**
 * @author good777LUCKY
 */
public class ItemSuperFertilizer extends Item {

    public ItemSuperFertilizer() {
        this(0, 1);
    }
    
    public ItemSuperFertilizer(Integer meta) {
        this(meta, 1);
    }
    
    public ItemSuperFertilizer(Integer meta, int count) {
        super(RAPID_FERTILIZER, meta, count, "Super Fertilizer");
    }
}
