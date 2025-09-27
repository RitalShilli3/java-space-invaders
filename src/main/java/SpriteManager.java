import java.awt.Image;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.ImageIcon;

/**
 * SpriteManager - Singleton that loads and caches Image objects.
 *
 * Usage:
 *   Image alien = SpriteManager.getInstance().getSprite("resources/sprites/alien.png");
 *
 **/
public final class SpriteManager {


    private static volatile SpriteManager instance;


    // thread-safe cache for loaded images
    private final Map<String, Image> cache;


    private SpriteManager() {
        cache = new ConcurrentHashMap<>();
    }

    // global access to the singleton instance
    public static SpriteManager getInstance() {
        if (instance == null) {
            synchronized (SpriteManager.class) { // double checked locking is used to insure that only one instance is created
                if (instance == null) {
                    instance = new SpriteManager();
                }
            }
        }
        return instance;
    }

    /**
     * Returns the Image for the given path. If not loaded, loads it and caches it.
     * @param path path to image file
     * @return loaded Image
     */
    public Image getSprite(String path) {
        // Use cache if present
        Image img = cache.get(path);
        if (img == null) {
            // load and put in cache
            Image loaded = new ImageIcon(path).getImage();
            Image prev = cache.putIfAbsent(path, loaded);
            img = (prev == null) ? loaded : prev;
        }
        return img;
    }

}
