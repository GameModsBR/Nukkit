package cn.nukkit.block;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.item.Item;
import cn.nukkit.math.BlockFace;
import cn.nukkit.utils.BlockColor;

/**
 * @author good777LUCKY
 */
public class BlockCamera extends BlockSolidMeta {
// TODO: Add Block State
    public BlockCamera() {
        this(0);
    }
    
    public BlockCamera(int meta) {
        super(meta);
    }
    
    @Override
    public int getId() {
        return CAMERA;
    }
    
    @Override
    public double getHardness() {
        return 0;
    }
    
    @Override
    public double getResistance() {
        return 0;
    }
    
    @Override
    public String getName() {
        return "Camera";
    }
    
    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {
        //TODO: Add Block face?
        return super.place(item, block, target, face, fx, fy, fz, player);
    }
    
    @Override
    public BlockColor getColor() {
        return BlockColor.WOOD_BLOCK_COLOR;
    }
}
