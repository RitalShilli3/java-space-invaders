import java.awt.Image;

/**
 * Flyweight class representing shared intrinsic state of aliens
 */
public class AlienType {
    private final String typeName;
    private final int maxHp;
    private final String imagePath;
    private Image image;

    public AlienType(String typeName, int maxHp, String imagePath) {
        this.typeName = typeName;
        this.maxHp = maxHp;
        this.imagePath = imagePath;
        // Image will be loaded on-demand via SpriteManager
        this.image = null;
    }

    // Getters
    public String getTypeName() {
        return typeName;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Image getImage() {
        if (image == null) {
            try {
                java.net.URL imageUrl = getClass().getResource(imagePath);
                if (imageUrl != null) {
                    image = SpriteManager.getInstance().getSprite(imageUrl.getPath());
                    System.out.println("AlienType image loaded via URL: " + imagePath);
                } else {
                    System.out.println("FAILED to find AlienType image: " + imagePath);
                }
            } catch (Exception e) {
                System.out.println("Error loading AlienType image: " + e.getMessage());
            }
        }
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AlienType alienType = (AlienType) o;
        return typeName.equals(alienType.typeName);
    }

    @Override
    public int hashCode() {
        return typeName.hashCode();
    }
}