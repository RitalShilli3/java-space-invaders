
/**
 * 
 * @author 
 */
public class Alien extends Sprite implements Cloneable {

    private Bomb bomb;
   // private final String alien = "/img/alien.png";

    /*
     * Constructor
     */
    public Alien(int x, int y) {
        this.x = x;
        this.y = y;

        bomb = new Bomb(x, y);
        
        // // Use of SpriteManager singleton instead of new ImageIcon
        // setImage(SpriteManager.getInstance().getSprite(this.getClass().getResource(alien).getPath()));
    }

    public void act(int direction) {
        this.x += direction;
    }
       public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public Alien clone() {
        Alien alienClone = new Alien(this.x, this.y);
        alienClone.setImage(this.getImage());
        return alienClone;

    }

    /*
     * Getters & Setters
     */
    
	public Bomb getBomb() {
		return bomb;
	}

}