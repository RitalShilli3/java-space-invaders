import java.awt.Image;

/**
 * 
 * @author
 */
public class GameOver extends Sprite implements Commons {

	private final String gameOver = "/img/gameover.png";
	private int width;

	/*
	 * Constructor
	 */
	public GameOver() {
		
		// Use of SpriteManager instead of new ImageIcon
        Image img = SpriteManager.getInstance().getSprite(
            this.getClass().getResource(gameOver).getPath()
        );

        setWidth(img.getWidth(null));
        setImage(img);
        setX(0);
        setY(0);
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
