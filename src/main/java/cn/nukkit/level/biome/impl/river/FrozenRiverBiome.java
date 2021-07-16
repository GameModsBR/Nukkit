package cn.nukkit.level.biome.impl.river;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.level.generator.populator.impl.WaterIcePopulator;

/**
 * @author DaPorkchop_ (Nukkit Project)
 */
public class FrozenRiverBiome extends RiverBiome {
    public FrozenRiverBiome() {
        super();

        WaterIcePopulator ice = new WaterIcePopulator();
        this.addPopulator(ice);
        
        this.setTemperature(0.0f);
    }

    @Override
    public String getName() {
        return "Frozen River";
    }

    @Override
    public boolean isFreezing() {
        return true;
    }

    @Override
    public boolean canRain() {
        return false;
    }
}
