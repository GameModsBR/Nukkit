package cn.nukkit.utils;

import cn.nukkit.level.Location;
import cn.nukkit.item.Item;
import cn.nukkit.block.Block;

public class BlockPattern {

    private PatternBlock[] patternBlocks;

    public BlockPattern(PatternBlock... patternBlocks) {
        this.patternBlocks = patternBlocks;
    }

    public boolean matches(Location location, boolean clear, int xz, int y) {
        for (Alignment alignment : Alignment.values()) {
            Location[] matches = this.matches(location, xz, y, alignment);
            if (matches != null) {
                if (clear) {
                    for (Location match : matches) {
                        match.getLevel().setBlock(match, Block.get(Block.AIR));
                    }
                }
                return true;
            }
        }
        return false;
    }

    public Location[] matches(Location location, int xz, int y, Alignment alignment) {
        int i = 0;
        Location[] loc = new Location[patternBlocks.length];
        for (PatternBlock block : patternBlocks) {
            int dxz = block.xz - xz,
                  dy = block.y - y;
            Location relative = location.clone().add(dxz * alignment.x, -dy, dxz * alignment.z);
            if ((relative.getLevelBlock().getId() != block.getId() || relative.getLevelBlock().getDamage() != block.getDamage()) && block.getDamage() != -1) {
                return null;
            }
            loc[i++] = relative;
        }
        return loc;
    }

    private enum Alignment {
        X(1, 0),
        Z(0, 1);

        private final int x;
        private final int z;

        Alignment(int x, int z) {
            this.x = x;
            this.z = z;
        }
    }

    public static class PatternBlock {

        private final Block block;
        private final int meta;
        private final int xz;
        private final int y;

        public PatternBlock(Block block, int meta, int xz, int y) {
            this.block = block;
            this.meta = meta;
            this.xz = xz;
            this.y = y;
        }
        
        public int getId() {
        	return this.block.getId();
        }
        
        public int getDamage() {
        	return this.meta;
        }

        public boolean matches(Block block) {
            return block.getId() == this.getId() && block.getDamage() == this.getDamage();
        }
    }
}
