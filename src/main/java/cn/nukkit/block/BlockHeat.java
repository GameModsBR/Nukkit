package cn.nukkit.block;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;
import cn.nukkit.utils.BlockColor;

/**
 * @author good777LUCKY
 */
public class BlockHeat extends BlockSolid {

    public BlockHeat() {
        // Does Nothing
    }
    
    @Override
    public int getId() {
        return CHEMICAL_HEAT;
    }
    
    @Override
    public double getHardness() {
        return 2.5;
    }
    
    @Override
    public double getResistance() {
        return 10; // TODO: Correct Resistance
    }
    
    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }
    
    @Override
    public String getName() {
        return "Heat Block";
    }
    
    @Override
    public Item[] getDrops(Item item) {
        if (item.isPickaxe()) {
            return new Item[]{
                toItem()
            };
        } else {
            return new Item[0];
        }
    }
    
    @Override
    public boolean canHarvestWithHand() {
        return false;
    }
    
    @Override
    public BlockColor getColor() {
        return BlockColor.STONE_BLOCK_COLOR;
    }
    
    // TODO: Melt Ice and Snow without light
}
