
/**
 * 
 * @author 
 */
public class Bomb extends Sprite {

	private final String bomb = "/img/bomb.png";
	private boolean destroyed;

	/*
	 * Constructor
	 */
	public Bomb(int x, int y) {

		setDestroyed(true);
		this.x = x;
		this.y = y;

		 // Use of SpriteManager instead of new ImageIcon
        setImage(SpriteManager.getInstance().getSprite(this.getClass().getResource(bomb).getPath()));
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

	public boolean isDestroyed() {
		return destroyed;
	}
}
