package cn.nukkit.item;

/**
 * @author good777LUCKY
 */
public class ItemCompound extends Item {

    public static final int SALT = 0;
    public static final int SODIUM_OXIDE = 1;
    public static final int LYE = 2;
    public static final int MAGNESIUM_NITRATE = 3;
    public static final int IRON_SULFIDE = 4;
    public static final int LITHIUM_HYDRIDE = 5;
    public static final int SODIUM_HYDRIDE = 6;
    public static final int CALCIUM_BROMIDE = 7;
    public static final int MAGNESIUM_OXIDE = 8;
    public static final int SODIUM_ACETATE = 9;
    public static final int LUMINOL = 10;
    public static final int CHARCOAL = 11;
    public static final int SUGER = 12;
    public static final int ALUMINIUM_OXIDE = 13;
    public static final int BORON_TRIOXIDE = 14;
    public static final int SOAP = 15;
    public static final int POLYETHYLENE = 16;
    public static final int GARBAGE = 17;
    public static final int MAGNESIUM_SALTS = 18;
    public static final int SULFATE = 19;
    public static final int BARIUM_SULFATE = 20;
    public static final int POTASSIUM_CHLORIDE = 21;
    public static final int MERCURIC_CHLORIDE = 22;
    public static final int CERIUM_CHLORIDE = 23;
    public static final int TUGSTEN_CHLORIDE = 24;
    public static final int CALCIUM_CHLORIDE = 25;
    public static final int WATER = 26;
    public static final int GLUE = 27;
    public static final int HYPOCHLORITE = 28;
    public static final int CRUDE_OIL = 29;
    public static final int LATEX = 30;
    public static final int POTASSIUM_IODIDE = 31;
    public static final int SODIUM_FLUORIDE = 32;
    public static final int BENZENE = 33;
    public static final int INK = 34;
    public static final int HYDROGEN_PEROXIDE = 35;
    public static final int AMMONIA = 36;
    public static final int SODIUM_HYPOCHLORITE = 37;
    
    public ItemCompound() {
        this(0, 1);
    }
    
    public ItemCompound(Integer meta) {
        this(meta, 1);
    }
    
    public ItemCompound(Integer meta, int count) {
        super(COMPOUND, meta, count, "Compound");
    }
    
    protected static String getName(int meta) {
        switch (meta) {
            case 0:
                return "Salt";
            case 1:
                return "Sodium Oxide";
            case 2:
                return "Lye";
            case 3:
                return "Magnesium Nitrate";
            case 4:
                return "Iron Sulfide";
            case 5:
                return "Lithium Hydride";
            case 6:
                return "Sodium Hydride";
            case 7:
                return "Calcium Bromide";
            case 8:
                return "Magnesium Oxide";
            case 9:
                return "Sodium Acetate";
            case 10:
                return "Luminol";
            case 11:
                return "Charcoal"; // Invalid Natural Compound
            case 12:
                return "Suger"; // Invalid Natural Compound
            case 13:
                return "Aluminium Oxide";
            case 14:
                return "Boron Trioxide";
            case 15:
                return "Soap";
            case 16:
                return "Polyethylene";
            case 17:
                return "Garbage";
            case 18:
                return "Magnesium Salts"; // ?
            case 19:
                return "Sulfate";
            case 20:
                return "Barium Sulfate";
            case 21:
                return "Potassium Chloride";
            case 22:
                return "Mercuric Chloride";
            case 23:
                return "Cerium Chloride";
            case 24:
                return "Tungsten Chloride";
            case 25:
                return "Calcium Chloride";
            case 26:
                return "Water";
            case 27:
                return "Glue";
            case 28:
                return "Hypochlorite"; // ?
            case 29:
                return "Crude Oil";
            case 30:
                return "Latex";
            case 31:
                return "Potassium Iodide";
            case 32:
                return "Sodium Fluoride";
            case 33:
                return "Benzene";
            case 34:
                return "Ink"; // Invalid Natural Compound
            case 35:
                return "Hydrogen Peroxide";
            case 36:
                return "Ammonia";
            case 37:
                return "Sodium Hypochlorite";
            default:
                return "Compound";
        }
    }
}
