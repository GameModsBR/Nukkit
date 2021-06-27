package cn.nukkit.block;

import cn.nukkit.item.Item;

/**
 * @author good777LUCKY
 */
public class BlockGlassPaneHardened extends BlockGlassPane {

    public BlockGlassPaneHardened() {
        // Does Nothing
    }

    @Override
    public int getId() {
        return HARD_GLASS_PANE;
    }
    
    @Override
    public double getHardness() {
        return 10;
    }
    
    @Override
    public double getResistance() {
        return 9; // TODO: Correct Resistance
    }
    
    @Override
    public String getName() {
        return "Hardened Glass Pane";
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
