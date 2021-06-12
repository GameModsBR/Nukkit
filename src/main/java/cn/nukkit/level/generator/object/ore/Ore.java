package cn.nukkit.level.generator.object.ore;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.blockstate.BlockState;

import java.util.List;

import lombok.Data;

/**
 * @author GoodLucky777
 */
@PowerNukkitOnly
@Since("FUTURE")
@Data
public class Ore {

    private final int count;
    private final List<ReplaceRule> replaceRules;
    
    @Data
    public class ReplaceRule {
    
        private final BlockState placesBlock;
        private final List<BlockState> mayReplace;
    }
}
