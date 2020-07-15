package cn.nukkit.block;

import cn.nukkit.api.PowerNukkitDifference;
import cn.nukkit.blockproperty.BlockProperties;
import cn.nukkit.blockproperty.value.StoneSlab4Type;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;
import cn.nukkit.utils.BlockColor;

import javax.annotation.Nonnull;

@PowerNukkitDifference(info = "Extends BlockDoubleSlabBase instead of BlockDoubleSlabStone only in PowerNukkit")
public class BlockDoubleSlabStone4 extends BlockDoubleSlabBase {
    public static final int MOSSY_STONE_BRICKS = 0;
    public static final int SMOOTH_QUARTZ = 1;
    public static final int STONE = 2;
    public static final int CUT_SANDSTONE = 3;
    public static final int CUT_RED_SANDSTONE = 4;

    public BlockDoubleSlabStone4() {
        this(0);
    }

    public BlockDoubleSlabStone4(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return DOUBLE_STONE_SLAB4;
    }

    @Nonnull
    @Override
    public BlockProperties getProperties() {
        return BlockSlabStone4.PROPERTIES;
    }

    public StoneSlab4Type getSlabType() {
        return getPropertyValue(StoneSlab4Type.PROPERTY);
    }

    public void setSlabType(StoneSlab4Type type) {
        setPropertyValue(StoneSlab4Type.PROPERTY, type);
    }

    @Override
    public String getSlabName() {
        return getSlabType().getEnglishName();
    }

    @Override
    public double getResistance() {
        return getToolType() > ItemTool.TIER_WOODEN ? 30 : 15;
    }

    @Override
    public double getHardness() {
        return 2;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    public Item toItem() {
        return getCurrentState().forItem().withBlockId(BlockID.STONE_SLAB4).asItemBlock();
    }

    @Override
    public int getToolTier() {
        return ItemTool.TIER_WOODEN;
    }

    @Override
    public Item[] getDrops(Item item) {
        if (item.isPickaxe() && item.getTier() >= getToolTier()) {
            Item slab = toItem();
            slab.setCount(2);
            return new Item[]{ slab };
        } else {
            return new Item[0];
        }
    }

    @Override
    public BlockColor getColor() {
        return getSlabType().getColor();
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }
}
