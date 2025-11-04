import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Flyweight Factory with Singleton pattern
 * Creates and manages shared AlienType instances
 */
public class AlienTypeFactory {
    // Singleton instance
    private static volatile AlienTypeFactory instance;

    // Cache of created AlienType objects (Flyweight storage)
    private final Map<String, AlienType> alienTypes;

    private AlienTypeFactory() {
        alienTypes = new ConcurrentHashMap<>();
        // Pre-populate with known types for better performance
        initializeDefaultTypes();
    }

    // Singleton double-checked locking for thread safety
    public static AlienTypeFactory getInstance() {
        if (instance == null) {
            synchronized (AlienTypeFactory.class) {
                if (instance == null) {
                    instance = new AlienTypeFactory();
                }
            }
        }
        return instance;
    }

    private void initializeDefaultTypes() {
        // Pre-create the types we know we'll need
        alienTypes.put("weak", new AlienType("weak", 1, "/img/alien.png"));
        alienTypes.put("strong", new AlienType("strong", 3, "/img/strong_alien.png"));
    }

    /**
     * Main Flyweight method - returns shared AlienType instance
     * 
     * @param typeName  unique identifier for the type
     * @param maxHp     hit points for this type
     * @param imagePath image resource path
     * @return shared AlienType instance
     */
    public AlienType getType(String typeName, int maxHp, String imagePath) {
        // Return existing type if present
        if (alienTypes.containsKey(typeName)) {
            return alienTypes.get(typeName);
        }

        // Create new type, add to cache, and return
        AlienType newType = new AlienType(typeName, maxHp, imagePath);
        alienTypes.put(typeName, newType);
        return newType;
    }

    /**
     * Overload for getting pre-defined types by name only
     */
    public AlienType getType(String typeName) {
        if (!alienTypes.containsKey(typeName)) {
            throw new IllegalArgumentException("Unknown alien type: " + typeName);
        }
        return alienTypes.get(typeName);
    }

    /**
     * Utility method to check if a type exists
     */
    public boolean hasType(String typeName) {
        return alienTypes.containsKey(typeName);
    }

    /**
     * Get all available types (for debugging or UI)
     */
    public Map<String, AlienType> getAllTypes() {
        return new ConcurrentHashMap<>(alienTypes);
    }
}