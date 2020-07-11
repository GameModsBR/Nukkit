package cn.nukkit.blockstate;

import cn.nukkit.api.DeprecationDetails;
import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.block.Block;
import cn.nukkit.blockproperty.BlockProperties;
import cn.nukkit.blockproperty.BlockProperty;
import cn.nukkit.level.Level;
import cn.nukkit.utils.HumanStringComparator;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.math.BigInteger;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@ParametersAreNonnullByDefault
public interface IBlockState {
    int getBlockId();

    @Nonnull
    Number getDataStorage();

    @Nonnull
    BlockProperties getProperties();

    @Deprecated
    @DeprecationDetails(reason = "Can't store all data, exists for backward compatibility reasons", since = "1.4.0.0-PN", replaceWith = "getDataStorage()")
    int getLegacyDamage();

    @Deprecated
    @DeprecationDetails(reason = "Can't store all data, exists for backward compatibility reasons", since = "1.4.0.0-PN", replaceWith = "getDataStorage()")
    int getBigDamage();

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    @Nonnull
    BigInteger getHugeDamage();

    @Nonnull
    Object getPropertyValue(String propertyName);

    int getIntValue(String propertyName);

    boolean getBooleanValue(String propertyName);

    @Nonnull
    String getPersistenceValue(String propertyName);

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    default String getPersistenceName() {
        return BlockStateRegistry.getPersistenceName(getBlockId());
    }
    
    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    default String getStateId() {
        BlockProperties properties = getProperties();
        Map<String, String> propertyMap = new TreeMap<>(HumanStringComparator.getInstance());
        properties.getNames().forEach(name-> propertyMap.put(properties.getBlockProperty(name).getPersistenceName(), getPersistenceValue(name)));

        StringBuilder stateId = new StringBuilder(getPersistenceName());
        propertyMap.forEach((name, value) -> stateId.append(';').append(name).append('=').append(value));
        return stateId.toString();
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    default String getLegacyStateId() {
        return getPersistenceName()+";nukkit-legacy="+getDataStorage();
    }

    @Nonnull
    BlockState getCurrentState();

    @Nonnull
    default Block getBlock() {
        Block block = Block.get(getBlockId());
        block.setDataStorage(getDataStorage());
        return block;
    }

    @Nonnull
    default Block getBlock(Level level, int x, int y, int z) {
        return getBlock(level, x, y, z, 0);
    }

    @Nonnull
    default Block getBlock(Level level, int x, int y, int z, int layer) {
        Block block = Block.get(getBlockId());
        block.setDataStorage(getDataStorage());
        block.level = level;
        block.x = x;
        block.y = y;
        block.z = z;
        block.layer = layer;
        return block;
    }

    default int getRuntimeId() {
        return BlockStateRegistry.getRuntimeId(getCurrentState());
    }

    @Deprecated
    @DeprecationDetails(reason = "Can't store all data, exists for backward compatibility reasons", since = "1.4.0.0-PN", replaceWith = "the BlockState itself")
    default int getFullId() {
        return (getBlockId() << Block.DATA_BITS) | (getLegacyDamage() & Block.DATA_MASK);
    }

    @Deprecated
    @DeprecationDetails(reason = "Can't store all data, exists for backward compatibility reasons", since = "1.4.0.0-PN", replaceWith = "the BlockState itself")
    default long getBigId() {
        return ((long)getBlockId() << 32) | (getBigDamage() & BlockStateRegistry.BIG_META_MASK);
    }

    @SuppressWarnings("rawtypes")
    @Nonnull
    default BlockProperty getProperty(String propertyName) {
        return getProperties().getBlockProperty(propertyName);
    }

    @Nonnull
    default <T extends BlockProperty<?>> T getCheckedProperty(String propertyName, Class<T> tClass) {
        return getProperties().getBlockProperty(propertyName, tClass);
    }

    @Nonnull
    default Set<String> getPropertyNames() {
        return getProperties().getNames();
    }

    @Nonnull
    default <T> T getCheckedPropertyValue(String propertyName, Class<T> tClass) {
        return tClass.cast(getPropertyValue(propertyName));
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    default <T> T getUncheckedPropertyValue(String propertyName) {
        return (T) getPropertyValue(propertyName);
    }

    default int getBitSize() {
        return getProperties().getBitSize();
    }
}
