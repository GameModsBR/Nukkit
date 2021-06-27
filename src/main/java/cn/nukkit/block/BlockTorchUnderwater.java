package cn.nukkit.block;

/**
 * @author good777LUCKY
 */
public class BlockTorchUnderwater extends BlockTorch {

    public BlockTorchUnderwater() {
        // Does Nothing
    }
    
    public BlockTorchUnderwater(int meta) {
        super(meta);
    }
    
    @Override
    public String getName() {
        return "Underwater Torch";
    }
    
    @Override
    public int getId() {
        return UNDERWATER_TORCH;
    }
    
    @Override
    public int getWaterloggingLevel() {
        return 1;
    }
    
    @Override
    public boolean canBeFlowedInto() {
        return false;
    }
}
