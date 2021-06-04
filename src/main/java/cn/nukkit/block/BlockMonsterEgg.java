package cn.nukkit.block;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.blockproperty.ArrayBlockProperty;
import cn.nukkit.blockproperty.BlockProperties;
import cn.nukkit.blockproperty.BlockProperty;
import cn.nukkit.blockproperty.value.MonsterEggStoneType;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.mob.EntitySilverfish;
import cn.nukkit.event.entity.CreatureSpawnEvent;
import cn.nukkit.item.Item;

import javax.annotation.Nonnull;

public class BlockMonsterEgg extends BlockSolidMeta {

    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    public static final BlockProperty<MonsterEggStoneType> MONSTER_EGG_STONE_TYPE = new ArrayBlockProperty<>(
        "monster_egg_stone_type", true, MonsterEggStoneType.class
    );
    
    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    public static final BlockProperties PROPERTIES = new BlockProperties(MONSTER_EGG_STONE_TYPE);
    
    @Deprecated
    public static final int STONE = 0;
    
    @Deprecated
    public static final int COBBLESTONE = 1;
    
    @Deprecated
    public static final int STONE_BRICK = 2;
    
    @Deprecated
    public static final int MOSSY_BRICK = 3;
    
    @Deprecated
    public static final int CRACKED_BRICK = 4;
    
    @Deprecated
    public static final int CHISELED_BRICK = 5;
    
    @Deprecated
    private static final String[] NAMES = new String[]{
            "Stone",
            "Cobblestone",
            "Stone Brick",
            "Mossy Stone Brick",
            "Cracked Stone Brick",
            "Chiseled Stone Brick"
    };
    
    public BlockMonsterEgg() {
        this(0);
    }

    public BlockMonsterEgg(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return MONSTER_EGG;
    }
    
    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    @Nonnull
    @Override
    public BlockProperties getProperties() {
        return PROPERTIES;
    }
    
    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public MonsterEggStoneType getMonsterEggStoneType() {
        return getPropertyValue(MONSTER_EGG_STONE_TYPE);
    }
    
    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public void setMonsterEggStoneType(MonsterEggStoneType type) {
        setPropertyValue(MONSTER_EGG_STONE_TYPE, type);
    }
    
    @Override
    public double getHardness() {
        return 0.75;
    }

    @Override
    public double getResistance() {
        return 0.75;
    }

    @Override
    public String getName() {
        return getMonsterEggStoneType().getEnglishName();
    }

    @Override
    public Item[] getDrops(Item item) {
        return Item.EMPTY_ARRAY;
    }
    
    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    @Override
    public boolean onBreak(Item item) {
        // TODO: Check the player is Survival or Adventure
        CreatureSpawnEvent creatureSpawnEvent = new CreatureSpawnEvent(EntitySilverfish.NETWORK_ID, this, CreatureSpawnEvent.SpawnReason.SILVERFISH_BLOCK);
        this.level.getServer().getPluginManager().callEvent(creatureSpawnEvent);
        if (!creatureSpawnEvent.isCancelled()) {
            EntitySilverfish silverfish = (EntitySilverfish) Entity.createEntity(creatureSpawnEvent.getEntityNetworkId(), creatureSpawnEvent.getPosition());
            if (silverfish != null) {
                silverfish.spawnToAll();
            }
        }
        return super.onBreak(item);
    }
}
