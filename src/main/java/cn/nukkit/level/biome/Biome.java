package cn.nukkit.level.biome;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.block.BlockID;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.generator.noise.vanilla.f.NoiseGeneratorPerlinF;
import cn.nukkit.level.generator.populator.type.Populator;
import cn.nukkit.math.NukkitRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public abstract class Biome implements BlockID {

    public static final int MAX_BIOMES = 256;
    public static final Biome[] biomes = new Biome[MAX_BIOMES];
    public static final List<Biome> unorderedBiomes = new ArrayList<>();
    
    private static final NoiseGeneratorPerlinF temperatureNoise = new NoiseGeneratorPerlinF(new NukkitRandom(12345678), 1); // TODO: Get correct temperature noise seed
    
    private final ArrayList<Populator> populators = new ArrayList<>();
    private int id;
    private float baseHeight = 0.1f;
    private float heightVariation = 0.3f;
    private float temperature = 0.5f;

    protected static void register(int id, Biome biome) {
        biome.setId(id);
        biomes[id] = biome;
        unorderedBiomes.add(biome);
    }

    public static Biome getBiome(int id) {
        Biome biome = biomes[id];
        return biome != null ? biome : EnumBiome.OCEAN.biome;
    }

    /**
     * Get Biome by name.
     *
     * @param name Name of biome. Name could contain symbol "_" instead of space
     * @return Biome. Null - when biome was not found
     */
    public static Biome getBiome(String name) {
        for (Biome biome : biomes) {
            if (biome != null) {
                if (biome.getName().equalsIgnoreCase(name.replace("_", " "))) return biome;
            }
        }
        return null;
    }

    public void clearPopulators() {
        this.populators.clear();
    }

    public void addPopulator(Populator populator) {
        this.populators.add(populator);
    }

    public void populateChunk(ChunkManager level, int chunkX, int chunkZ, NukkitRandom random) {
        FullChunk chunk = level.getChunk(chunkX, chunkZ);
        for (Populator populator : populators) {
            populator.populate(level, chunkX, chunkZ, random, chunk);
        }
    }

    public ArrayList<Populator> getPopulators() {
        return populators;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract String getName();

    public void setBaseHeight(float baseHeight) {
        this.baseHeight = baseHeight;
    }

    public void setHeightVariation(float heightVariation)   {
        this.heightVariation = heightVariation;
    }

    public float getBaseHeight() {
        return baseHeight;
    }

    public float getHeightVariation() {
        return heightVariation;
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public boolean equals(Object obj) {
        return hashCode() == obj.hashCode();
    }

    //whether or not water should freeze into ice on generation
    public boolean isFreezing() {
        return false;
    }

    /**
     * Whether or not overhangs should generate in this biome (places where solid blocks generate over air)
     *
     * This should probably be used with a custom max elevation or things can look stupid
     *
     * @return overhang
     */
    public boolean doesOverhang()   {
        return false;
    }

    /**
     * How much offset should be added to the min/max heights at this position
     *
     * @param x x
     * @param z z
     * @return height offset
     */
    public int getHeightOffset(int x, int z)    {
        return 0;
    }

    public boolean canRain() {
        return true;
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public boolean isDry() {
        return false;
    }
    
    /**
     * Get base temperature of the biome
     */
    @PowerNukkitOnly
    @Since("FUTURE")
    public float getTemperature() {
        return this.temperature;
    }
    
    /**
     * Get temperature at specific position of the biome
     */
    @PowerNukkitOnly
    @Since("FUTURE")
    public float getTemperatureAt(double x, double y, double z) {
        // Above sea level (Y > 64), temperature drop 0.0016 per a block above
        if (y > 64) {
            // Apply temperature noise
            float tNoise = this.getTemperatureNoise().getValue((float) x * 0.125f, (float) z * 0.125f); // TODO: Get correct noise value
            return this.getTemperature() - (0.0016f * (((float) y - 64f) + tNoise));
        } else {
            return this.getTemperature();
        }
    }
    
    /**
     * Set base temperature of the biome
     */
    @PowerNukkitOnly
    @Since("FUTURE")
    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }
    
    /**
     * Check if snow can generate at specific position
     * TODO: Support block light checking
     */
    @PowerNukkitOnly
    @Since("FUTURE")
    public boolean canSnow(double x, double y, double z) {
        if (this.getTemperatureAt(x, y, z) < 0.15f) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Check if ice can generate at specific position
     * TODO: Support block light checking
     */
    @PowerNukkitOnly
    @Since("FUTURE")
    public boolean canFreeze(double x, double y, double z) {
        if (this.getTemperatureAt(x, y, z) < 0.15f) {
            return true;
        }
        
        return false;
    }
    
    @PowerNukkitOnly
    @Since("FUTURE")
    public NoiseGeneratorPerlinF getTemperatureNoise() {
        return temperatureNoise;
    }
}
