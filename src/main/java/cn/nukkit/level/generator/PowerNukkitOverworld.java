package cn.nukkit.level.generator;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.block.BlockID;
import cn.nukkit.block.BlockStone;
import cn.nukkit.blockstate.BlockState;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.biome.Biome;
import cn.nukkit.level.generator.object.ore.OreType;
import cn.nukkit.level.generator.object.ore.OreV2;
import cn.nukkit.level.generator.populator.impl.PopulatorCaves;
import cn.nukkit.level.generator.populator.impl.PopulatorOreV2;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;

import com.google.common.collect.ImmutableList;

import java.util.Collections;
import java.util.Map;

/**
 * @author GoodLucky777
 */
// TODO: Better generator
@PowerNukkitOnly
@Since("FUTURE")
public class PowerNukkitOverworld extends Normal {

    private static final BlockState STATE_STONE = BlockState.of(BlockID.STONE);
    private static final BlockState STATE_DEEPSLATE = BlockState.of(BlockID.DEEPSLATE);
    
    static {
        seaHeight = 63;
    }
    
    public PowerNukkitOverworld() {
        this(Collections.emptyMap());
    }
    
    public PowerNukkitOverworld(Map<String, Object> options) {
        // Just used for future update.
        super(options);
    }
    
    @Override
    public ChunkManager getChunkManager() {
        return super.getChunkManager();
    }
    
    @Override
    public int getId() {
        return TYPE_INFINITE;
    }
    
    @Override
    public String getName() {
        return "PowerNukkitOverworld";
    }
    
    @Override
    public Map<String, Object> getSettings() {
        return Collections.emptyMap();
    }
    
    @Override
    public Vector3 getSpawn() {
        return new Vector3(0.5, 256, 0.5);
    }
    
    @Override
    public void init(ChunkManager level, NukkitRandom random) {
        super.init(level, random);
        
        this.populators = ImmutableList.of(
                new PopulatorOreV2(new OreV2(33, ImmutableList.of(new OreV2.ReplaceRule(STATE_DEEPSLATE, ImmutableList.of(STATE_STONE)))), 10, 0, 16, 0, 16, 0, 16), // Deepslate
                new PopulatorOreV2(new OreV2(33, ImmutableList.of(new OreV2.ReplaceRule(BlockState.of(BlockID.TUFF), ImmutableList.of(STATE_STONE)))), 2, 0, 16, 0, 16, 0, 16), // Tuff
                new PopulatorOreV2(new OreV2(17, ImmutableList.of(new OreV2.ReplaceRule(BlockState.of(BlockID.COAL_ORE), ImmutableList.of(STATE_STONE)), new OreV2.ReplaceRule(BlockState.of(BlockID.DEEPSLATE_COAL_ORE), ImmutableList.of(STATE_DEEPSLATE)))), 20, 0, 16, 0, 128, 0, 16), // Coal Ore
                new PopulatorOreV2(new OreV2(9, ImmutableList.of(new OreV2.ReplaceRule(BlockState.of(BlockID.IRON_ORE), ImmutableList.of(STATE_STONE)), new OreV2.ReplaceRule(BlockState.of(BlockID.DEEPSLATE_IRON_ORE), ImmutableList.of(STATE_DEEPSLATE)))), 20, 0, 16, 0, 64, 0, 16), // Iron Ore
                new PopulatorOreV2(new OreV2(10, ImmutableList.of(new OreV2.ReplaceRule(BlockState.of(BlockID.COPPER_ORE), ImmutableList.of(STATE_STONE)), new OreV2.ReplaceRule(BlockState.of(BlockID.DEEPSLATE_COPPER_ORE), ImmutableList.of(STATE_DEEPSLATE)))), 10, 0, 16, 0, 64, 0, 16), // Copper Ore
                new PopulatorOreV2(new OreV2(8, ImmutableList.of(new OreV2.ReplaceRule(BlockState.of(BlockID.REDSTONE_ORE), ImmutableList.of(STATE_STONE)), new OreV2.ReplaceRule(BlockState.of(BlockID.DEEPSLATE_REDSTONE_ORE), ImmutableList.of(STATE_DEEPSLATE)))), 8, 0, 16, 0, 16, 0, 16), // Redstone ore
                new PopulatorOreV2(new OreV2(7, ImmutableList.of(new OreV2.ReplaceRule(BlockState.of(BlockID.LAPIS_ORE), ImmutableList.of(STATE_STONE)), new OreV2.ReplaceRule(BlockState.of(BlockID.DEEPSLATE_LAPIS_ORE), ImmutableList.of(STATE_DEEPSLATE)))), 1, 0, 16, 0, 16, 0, 16), // Lapis Ore
                new PopulatorOreV2(new OreV2(9, ImmutableList.of(new OreV2.ReplaceRule(BlockState.of(BlockID.GOLD_ORE), ImmutableList.of(STATE_STONE)), new OreV2.ReplaceRule(BlockState.of(BlockID.DEEPSLATE_GOLD_ORE), ImmutableList.of(STATE_DEEPSLATE)))), 2, 0, 16, 0, 32, 0, 16), // Gold Ore
                new PopulatorOreV2(new OreV2(8, ImmutableList.of(new OreV2.ReplaceRule(BlockState.of(BlockID.DIAMOND_ORE), ImmutableList.of(STATE_STONE)), new OreV2.ReplaceRule(BlockState.of(BlockID.DEEPSLATE_DIAMOND_ORE), ImmutableList.of(STATE_DEEPSLATE)))), 1, 0, 16, 0, 16, 0, 16), // Diamond Ore
                new PopulatorOreV2(new OreV2(33, ImmutableList.of(new OreV2.ReplaceRule(BlockState.of(BlockID.DIRT), ImmutableList.of(STATE_STONE)))), 10, 0, 16, 0, 128, 0, 16), // Dirt
                new PopulatorOreV2(new OreV2(33, ImmutableList.of(new OreV2.ReplaceRule(BlockState.of(BlockID.GRAVEL), ImmutableList.of(STATE_STONE)))), 8, 0, 16, 0, 128, 0, 16), // Gravel
                new PopulatorOreV2(new OreV2(33, ImmutableList.of(new OreV2.ReplaceRule(BlockState.of(BlockID.STONE, BlockStone.GRANITE), ImmutableList.of(STATE_STONE)))), 10, 0, 16, 0, 80, 0, 16), // Granite
                new PopulatorOreV2(new OreV2(33, ImmutableList.of(new OreV2.ReplaceRule(BlockState.of(BlockID.STONE, BlockStone.DIORITE), ImmutableList.of(STATE_STONE)))), 10, 0, 16, 0, 80, 0, 16), // Diorite
                new PopulatorOreV2(new OreV2(33, ImmutableList.of(new OreV2.ReplaceRule(BlockState.of(BlockID.STONE, BlockStone.ANDESITE), ImmutableList.of(STATE_STONE)))), 10, 0, 16, 0, 80, 0, 16), // Andesite
                
                new PopulatorCaves()//,
                //new PopulatorRavines()
        );
    }
    
    @Override
    public void generateChunk(final int chunkX, final int chunkZ) {
        super.generateChunk(chunkX, chunkZ);
    }
    
    public Biome pickBiome(int x, int z) {
        return super.pickBiome(x, z);
    }
    
    @Override
    public void populateChunk(int chunkX, int chunkZ) {
        super.populateChunk(chunkX, chunkZ);
    }
}
