package cn.nukkit.level.generator.populator.impl;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.blockstate.BlockState;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.generator.object.ore.OreV2;
import cn.nukkit.level.generator.populator.type.Populator;
import cn.nukkit.math.MathHelper;
import cn.nukkit.math.NukkitMath;
import cn.nukkit.math.NukkitRandom;

import lombok.Data;

/**
 * @author GoodLucky777
 */
@PowerNukkitOnly
@Since("FUTURE")
@Data
public class PopulatorOreV2 extends Populator {

    private final OreV2 ore;
    private final int iterations;
    private final int startX;
    private final int endX;
    private final int startY;
    private final int endY;
    private final int startZ;
    private final int endZ;
    
    @Override
    public void populate(ChunkManager level, int chunkX, int chunkZ, NukkitRandom random, FullChunk chunk) {
        int x = (chunkX << 4) + startX;
        int y = 0 + startY;
        int z = (chunkZ << 4) + startZ;
        
        for (int i = 0; i < iterations; i++) {
            this.generateOre(level, random, ore, x + random.nextBoundedInt(endX), y + random.nextBoundedInt(endY), z + random.nextBoundedInt(endZ));
        }
    }
    
    public void generateOre(ChunkManager level, NukkitRandom random, OreV2 ore, int x, int y, int z) {
        float piScaled = random.nextFloat() * (float) Math.PI;
        double scaleMaxX = (float) (x + 8) + MathHelper.sin(piScaled) * (float) ore.getCount() / 8.0F;
        double scaleMinX = (float) (x + 8) - MathHelper.sin(piScaled) * (float) ore.getCount() / 8.0F;
        double scaleMaxZ = (float) (z + 8) + MathHelper.cos(piScaled) * (float) ore.getCount() / 8.0F;
        double scaleMinZ = (float) (z + 8) - MathHelper.cos(piScaled) * (float) ore.getCount() / 8.0F;
        double scaleMaxY = y + random.nextBoundedInt(3) - 2;
        double scaleMinY = y + random.nextBoundedInt(3) - 2;
        
        for (int i = 0; i < ore.getCount(); ++i) {
            float sizeIncr = (float) i / (float) ore.getCount();
            double scaleX = scaleMaxX + (scaleMinX - scaleMaxX) * (double) sizeIncr;
            double scaleY = scaleMaxY + (scaleMinY - scaleMaxY) * (double) sizeIncr;
            double scaleZ = scaleMaxZ + (scaleMinZ - scaleMaxZ) * (double) sizeIncr;
            double randSizeOffset = random.nextDouble() * (double) ore.getCount() / 16.0D;
            double randVec1 = (double) (MathHelper.sin((float) Math.PI * sizeIncr) + 1.0F) * randSizeOffset + 1.0D;
            double randVec2 = (double) (MathHelper.sin((float) Math.PI * sizeIncr) + 1.0F) * randSizeOffset + 1.0D;
            int minX = MathHelper.floor(scaleX - randVec1 / 2.0D);
            int minY = MathHelper.floor(scaleY - randVec2 / 2.0D);
            int minZ = MathHelper.floor(scaleZ - randVec1 / 2.0D);
            int maxX = MathHelper.floor(scaleX + randVec1 / 2.0D);
            int maxY = MathHelper.floor(scaleY + randVec2 / 2.0D);
            int maxZ = MathHelper.floor(scaleZ + randVec1 / 2.0D);
            
            for (int xSeg = minX; xSeg <= maxX; ++xSeg) {
                double xVal = ((double) xSeg + 0.5D - scaleX) / (randVec1 / 2.0D);
                
                if (xVal * xVal < 1.0D) {
                    for (int ySeg = minY; ySeg <= maxY; ++ySeg) {
                        double yVal = ((double) ySeg + 0.5D - scaleY) / (randVec2 / 2.0D);
                        
                        if (xVal * xVal + yVal * yVal < 1.0D) {
                            for (int zSeg = minZ; zSeg <= maxZ; ++zSeg) {
                                double zVal = ((double) zSeg + 0.5D - scaleZ) / (randVec1 / 2.0D);
                                
                                if (xVal * xVal + yVal * yVal + zVal * zVal < 1.0D) {
                                    for (OreV2.ReplaceRule replaceRule : ore.getReplaceRules()) {
                                        for (BlockState mayReplace : replaceRule.getMayReplace()) {
                                            if (level.getBlockStateAt(xSeg, ySeg, zSeg).equals(mayReplace)) {
                                                level.setBlockStateAt(xSeg, ySeg, zSeg, replaceRule.getPlacesBlock());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
