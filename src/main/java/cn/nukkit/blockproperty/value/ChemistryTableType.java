package cn.nukkit.blockproperty.value;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ChemistryTableType {

    COMPOUND_CREATOR("Compound Creator"),
    
    MATERIAL_REDUCER("Material Reducer"),
    
    ELEMENT_CONSTRUCTOR("Element Constructor"),
    
    LAB_TABLE("Lab Table");
    
    private final String englishName;
    
    public String getEnglishName() {
        return englishName;
    }
}
