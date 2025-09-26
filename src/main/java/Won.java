import java.awt.Image;

/**
 * 
 * @author 
 */
public class Won extends Sprite implements Commons{
    private final String won = "/img/won.jpg";
    private int width;

    /*
     * Constructor
     */
    public Won() {

        // Load image from SpriteManager singleton
        Image img = SpriteManager.getInstance().getSprite(
            this.getClass().getResource(won).getPath()
        );

        setImage(img);
        setX(0);
        setY(0);

        if (img != null) {
            width = img.getWidth(null);
        }
    }
    
    /*
     * Getters & Setters
     */
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
