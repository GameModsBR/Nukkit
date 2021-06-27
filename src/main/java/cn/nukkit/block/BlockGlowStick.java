package cn.nukkit.block;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.math.BlockFace;

/**
 * @author good777LUCKY
 */
public class BlockGlowStick extends BlockTransparentMeta {

    public BlockGlowStick() {
        this(0);
    }
    
    public BlockGlowStick(int meta) {
        super(meta);
    }
    
    @Override
    public int getId() {
        return GLOW_STICK;
    }
    
    @Override
    public String getName() {
        return "Glow Stick";
    }
    
    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {
        // Can't Place
        return false;
    }
}
