package cn.nukkit.block;

import cn.nukkit.Player;
import cn.nukkit.blockproperty.ArrayBlockProperty;
import cn.nukkit.blockproperty.BlockProperties;
import cn.nukkit.blockproperty.BlockProperty;
import cn.nukkit.blockproperty.value.ChemistryTableType;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;
import cn.nukkit.math.BlockFace;
import cn.nukkit.utils.BlockColor;
import cn.nukkit.utils.Faceable;

import static cn.nukkit.blockproperty.CommonBlockProperties.DIRECTION;

/**
 * @author good777LUCKY
 */
public class BlockChemistryTable extends BlockSolidMeta implements Faceable {
    public static final BlockProperty<ChemistryTableType> CHEMISTRY_TABLE_TYPE = new ArrayBlockProperty<>(
        "chemistry_table_type", true, ChemistryTableType.class
    );
    public static final BlockProperties PROPERTIES = new BlockProperties(
        CHEMISTRY_TABLE_TYPE,
        DIRECTION
    );
    
    public BlockChemistryTable() {
        this(0);
    }
    
    public BlockChemistryTable(int meta) {
        super(meta);
    }
    
    @Override
    public int getId() {
        return CHEMISTRY_TABLE;
    }
    
    @Override
    public BlockProperties getProperties() {
        return PROPERTIES;
    }
    
    @Override
    public double getHardness() {
        return 2.5;
    }
    
    @Override
    public double getResistance() {
        return 2;
    }
    
    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }
    
    @Override
    public String getName() {
        return getChemistryTableType().getEnglishName();
    }
    
    @Override
    public boolean canBeActivated() {
        return true;
    }
    
    @Override
    public boolean onActivate(Item item, Player player) {
        //TODO: Add UI
        return true;
    }
    
    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {
        if (player == null) {
            setBlockFace(BlockFace.SOUTH);
        } else {
            setBlockFace(player.getDirection().getOpposite());
        }
        return true;
    }
    
    public ChemistryTableType getChemistryTableType() {
        return getPropertyValue(CHEMISTRY_TABLE_TYPE);
    }
    
    public void setChemistryTableType(ChemistryTableType type) {
        setPropertyValue(CHEMISTRY_TABLE_TYPE, type);
    }
    
    @Override
    public BlockFace getBlockFace() {
        return getPropertyValue(DIRECTION);
    }
    
    @Override
    public void setBlockFace(BlockFace face) {
        setPropertyValue(DIRECTION, face);
    }
    
    @Override
    public BlockColor getColor() {
        return BlockColor.WHITE_BLOCK_COLOR;
    }
}
