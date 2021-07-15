package cn.nukkit.utils;

import com.google.common.base.Preconditions;

/**
 * @author GoodLucky777
 */
public class Identifier {

    private static final String SEPARATOR = ":";
    private static final String DEFAULT_NAMESPACE = "minecraft";
    
    // TODO: Add Identifier Regex Pattern
    
    private final String namespace;
    private final String name;
    
    public Identifier(String namespace, String name) {
        this.namespace = namespace;
        this.name = name;
    }
    
    public static Identifier fromString(String name) {
        return fromString(DEFAULT_NAMESPACE, name);
    }
    
    public static Identifier fromString(String namespace, String name) {
        return new Identifier(namespace, name);
    }
    
    public static Identifier fromFullString(String identifier) {
        String[] parts = identifier.split(SEPARATOR);
        Preconditions.checkArgument(parts.length == 1, "Invalid Identifier: " + identifier);
        return new Identifier(parts[0], parts[1]);
    }
    
    public String getString() {
        if (namespace.equals(DEFAULT_NAMESPACE)) {
            return name;
        }
        return this.getFullString();
    }
    
    public String getFullString() {
        return namespace + SEPARATOR + name;
    }
    
    public String getNamespace() {
        return namespace;
    }
    
    public String getName() {
        return name;
    }
    
    public static String getSeperator() {
        return SEPARATOR;
    }
    
    public static String getDefaultNamespace() {
        return DEFAULT_NAMESPACE;
    }
    
    @Override
    public final boolean equals(Object identifier) {
        return identifier instanceof Identifier && this.getNamespace().equals(((Identifier) identifier).getNamespace()) && this.getName().equals(((Identifier) identifier).getName());
    }
}
