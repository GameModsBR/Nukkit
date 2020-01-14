package cn.nukkit.block;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.item.ItemTool;
import cn.nukkit.math.BlockFace;
import cn.nukkit.utils.BlockColor;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public class BlockWood extends BlockSolidMeta {
    public static final int OAK = 0;
    public static final int SPRUCE = 1;
    public static final int BIRCH = 2;
    public static final int JUNGLE = 3;


    public BlockWood() {
        this(0);
    }

    public BlockWood(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return WOOD;
    }

    @Override
    public double getHardness() {
        return 2;
    }

    @Override
    public double getResistance() {
        return 10;
    }

    @Override
    public String getName() {
        String[] names = new String[]{
                "Oak Wood",
                "Spruce Wood",
                "Birch Wood",
                "Jungle Wood"
        };

        return names[this.getDamage() & 0x03];
    }

    @Override
    public int getBurnChance() {
        return 5;
    }

    @Override
    public int getBurnAbility() {
        return 10;
    }

    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {
        short[] faces = new short[]{
                0,
                0,
                0b1000,
                0b1000,
                0b0100,
                0b0100
        };

        this.setDamage(((this.getDamage() & 0x03) | faces[face.getIndex()]));
        this.getLevel().setBlock(block, this, true, true);

        return true;
    }
    
    @Override
    public boolean canBeActivated() {
        return true;
    }
    
    protected int getStrippedId() {
        int[] strippedIds = new int[] {
                STRIPPED_OAK_LOG,
                STRIPPED_SPRUCE_LOG,
                STRIPPED_BIRCH_LOG,
                STRIPPED_JUNGLE_LOG
        };
        return strippedIds[getDamage() & 0x03];
    }
    
    protected int getStrippedDamage() {
        return getDamage() >> 2;
    }
    
    @Override
    public boolean onActivate(Item item, Player player) {
        if (item.isAxe()) {
            Block strippedBlock = Block.get(getStrippedId(), getStrippedDamage());
            item.useOn(this);
            this.level.setBlock(this, strippedBlock, true, true);
            return true;
        }
        return false;
    }
    
    @Override
    public Item toItem() {
        if ((getDamage() & 0b1100) == 0b1100) {
            return new ItemBlock(new BlockWoodBark(), this.getDamage() & 0x3);
        } else {
            return new ItemBlock(this, this.getDamage() & 0x03);
        }
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_AXE;
    }

    @Override
    public BlockColor getColor() {
        switch (getDamage() & 0x07) {
            default:
            case OAK:
                return BlockColor.WOOD_BLOCK_COLOR;
            case SPRUCE:
                return BlockColor.SPRUCE_BLOCK_COLOR;
            case BIRCH:
                return BlockColor.SAND_BLOCK_COLOR;
            case JUNGLE:
                return BlockColor.DIRT_BLOCK_COLOR;
        }
    }
}
