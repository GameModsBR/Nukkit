package cn.nukkit.block;

import cn.nukkit.item.Item;

/**
 * @author good777LUCKY
 */
public class BlockGlassHardened extends BlockGlass {

    public BlockGlassHardened() {
        // Does Nothing
    }

    @Override
    public int getId() {
        return HARD_GLASS;
    }
    
    @Override
    public double getHardness() {
        return 10;
    }
    
    @Override
    public double getResistance() {
        return 9;
    }
    
    @Override
    public String getName() {
        return "Hardened Glass";
    }
    
    @Override
    public Item[] getDrops(Item item) {
        return new Item[]{
            toItem()
        };
    }
    
    @Override
    public boolean canSilkTouch() {
        return false;
    }
}
