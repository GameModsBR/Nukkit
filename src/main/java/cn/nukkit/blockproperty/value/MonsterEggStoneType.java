package cn.nukkit.blockproperty.value;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;

import lombok.RequiredArgsConstructor;

/**
 * @author good777LUCKY
 */
@Since("1.4.0.0-PN")
@PowerNukkitOnly
@RequiredArgsConstructor
public enum MonsterEggStoneType {

    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    STONE("Infested Stone"),
    
    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    COBBLESTONE("Infested Cobblestone"),
    
    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    STONE_BRICK("Infested Stone Brick"),
    
    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    MOSSY_STONE_BRICK("Infested Mossy Stone Brick"),
    
    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    CRACKED_STONE_BRICK("Infested Cracked Stone Brick"),
    
    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    CHISELED_STONE_BRICK("Infested Chiseled Stone Brick");
    
    private final String englishName;
    
    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    public String getEnglishName() {
        return englishName;
    }
}
