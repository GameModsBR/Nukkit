package cn.nukkit.block;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.blockproperty.BlockProperties;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.Location;
import cn.nukkit.event.entity.CreatureSpawnEvent;
import cn.nukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.item.ItemID;
import cn.nukkit.item.ItemTool;
import cn.nukkit.math.BlockFace;
import cn.nukkit.utils.BlockColor;
import cn.nukkit.utils.BlockPattern;
import cn.nukkit.utils.BlockPattern.PatternBlock;
import cn.nukkit.utils.Faceable;

import static cn.nukkit.blockproperty.CommonBlockProperties.DIRECTION;

import javax.annotation.Nonnull;

/**
 * @author xtypr
 * @since 2015/12/8
 */
public class BlockPumpkin extends BlockSolidMeta implements Faceable {
	
	private static final BlockPattern IRONGOLEM_PATTERN = new BlockPattern(
        new BlockPattern.PatternBlock(PUMPKIN, -1, 1, 0),
        new BlockPattern.PatternBlock(IRON_BLOCK, 0, 0, 1),
        new BlockPattern.PatternBlock(IRON_BLOCK, 0, 1, 1),
        new BlockPattern.PatternBlock(IRON_BLOCK, 0, 2, 1),
        new BlockPattern.PatternBlock(IRON_BLOCK, 0, 1, 2)
    );

    private static final BlockPattern SNOWMAN_PATTERN = new BlockPattern(
        new BlockPattern.PatternBlock(PUMPKIN, -1, 0, 0),
        new BlockPattern.PatternBlock(SNOW_BLOCK, 0, 0, 1),
        new BlockPattern.PatternBlock(SNOW_BLOCK, 0, 0, 2)
    );

    public static final BlockProperties PROPERTIES = new BlockProperties(
        DIRECTION
    );
    
    public BlockPumpkin() {
        this(0);
    }

    public BlockPumpkin(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Pumpkin";
    }

    @Override
    public int getId() {
        return PUMPKIN;
    }
    
    @Nonnull
    @Override
    public BlockProperties getProperties() {
        return PROPERTIES;
    }
    
    @Override
    public double getHardness() {
        return 1;
    }

    @Override
    public double getResistance() {
        return 1;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_AXE;
    }

    @Override
    public Item toItem() {
        return new ItemBlock(this, 0);
    }
    
    @Override
    public boolean canBeActivated() {
        return true;
    }
    
    @Override
    public boolean onActivate(@Nonnull Item item, Player player) {
        if (item.isShears()) {
            BlockCarvedPumpkin carvedPumpkin = new BlockCarvedPumpkin();
            // TODO: Use the activated block face not the player direction
            if (player == null) {
                carvedPumpkin.setBlockFace(BlockFace.SOUTH);
            } else {
                carvedPumpkin.setBlockFace(player.getDirection().getOpposite());
            }
            item.useOn(this);
            this.level.setBlock(this, carvedPumpkin, true, true);
            this.getLevel().dropItem(add(0.5, 0.5, 0.5), Item.get(ItemID.PUMPKIN_SEEDS)); // TODO: Get correct drop item position
            return true;
        }
        return false;
    }
    
    @Override
    public boolean place(@Nonnull Item item, @Nonnull Block block, @Nonnull Block target, @Nonnull BlockFace face, double fx, double fy, double fz, Player player) {
        if (player == null) {
            setBlockFace(BlockFace.SOUTH);
        } else {
            setBlockFace(player.getDirection().getOpposite());
        }
        this.level.setBlock(block, this, true, true);
        Location location = block.getLocation();
        if (!spawnIronGolem(location.clone())) {
            spawnSnowman(location.clone());
        }
        return true;
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.ORANGE_BLOCK_COLOR;
    }

    @Override
    public boolean breaksWhenMoved() {
        return true;
    }

    @Override
    public boolean sticksToPiston() {
        return false;
    }
    
    @Override
    public BlockFace getBlockFace() {
        return getPropertyValue(DIRECTION);
    }
    
    @Override
    public void setBlockFace(BlockFace face) {
        setPropertyValue(DIRECTION, face);
    }
    
    private boolean spawnIronGolem(Location location) {
    	Entity entity = Entity.createEntity("Iron Golem", location.clone().subtract(-0.5, 2, -0.5));
    	CreatureSpawnEvent ev = new CreatureSpawnEvent(entity.getNetworkId(), location.clone().subtract(-0.5, 2, -0.5), SpawnReason.BUILD_IRONGOLEM);
        Server.getInstance().getPluginManager().callEvent(ev);

        if (ev.isCancelled()) {
            return false;
        }
        if (IRONGOLEM_PATTERN.matches(location, true, 1, 0)) {
            entity.spawnToAll();
            return true;
        }
        return false;
    }

    private void spawnSnowman(Location location) {
        Entity entity = Entity.createEntity("Snow Golem", location.clone().subtract(-0.5, 2, -0.5));
        CreatureSpawnEvent ev = new CreatureSpawnEvent(entity.getNetworkId(), location.clone().subtract(-0.5, 2, -0.5), SpawnReason.BUILD_IRONGOLEM);
        Server.getInstance().getPluginManager().callEvent(ev);

        if (ev.isCancelled()) {
            return;
        }
        if (SNOWMAN_PATTERN.matches(location, true, 0, 0)) {
            entity.spawnToAll();
        }
    }
}
