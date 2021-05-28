package cn.nukkit.utils;

import cn.nukkit.Location;
import cn.nukkit.Item;
import cn.nukkit.block.Block;

public class BlockPattern {

    private PatternBlock[] blocks;

    public BlockPattern(PatternBlock... blocks) {
        this.blocks = blocks;
    }

    public boolean matches(Location location, boolean clear, int xz, int y) {
        for (Alignment alignment : Alignment.values()) {
            Location[] matches = this.matches(location, xz, y, alignment);
            if (matches != null) {
                if (clear) {
                    for (Location match : matches) {
                        location.getLevel().setBlock(Block.AIR);
                    }
                }
                return true;
            }
        }
        return false;
    }

    public Location[] matches(Location location, int xz, int y, Alignment alignment) {
        int i = 0;
        Location[] loc = new Location[blocks.length];
        for (PatternBlock block : blocks) {
            int dxz = block.getXZ() - xz;
            int dy = block.getY() - y;
            Location relative = location.clone().add(dxz * alignment.x, -dy, dxz * alignment.z);
            if (relative.getLevel().getBlock(relative).getId() != block.getId() || ((relative.getLevel().getBlock(relative).getDamage() != block.getDamage()) && block.getDamage() != -1)) {
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

        private Block block;
        private int meta;
        private int xz;
        private int y;

        public PatternBlock(Block block, int meta, int xz, int y) {
            this.block = block;
            this.meta = meta;
            this.xz = xz;
            this.y = y;
        }

        public Block getBlock() {
            return this.block;
        }

        public int getDamage() {
            return this.meta;
        }

        public int getXZ() {
            return this.xz;
        }

        public int getY() {
            return this.y;
        }

        public boolean matches(Block block) {
            return block.getId() == this.block.getId() && block.getDamage() == this.meta;
        }
    }
}
