import java.awt.Graphics;

// Implements AlienComponent for composite pattern
public class Alien extends Sprite implements Cloneable, AlienComponent {

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

    /**
     * Apply damage to this alien. Default behavior: mark as dying and return true
     * to indicate the alien was destroyed. Decorators (or subclasses) can
     * override this to implement HP, armor, or other behaviors.
     * @return true if the alien died, false otherwise
     */
    public boolean damage() {
        this.setDying(true);
        return true;
    }

    /*
     * Getters & Setters
     */
    
	public Bomb getBomb() {
		return bomb;
	}

    // AlienComponent interface methods
    @Override
    public void draw(Graphics g) {
        if (isVisible()) {
            g.drawImage(getImage(), getX(), getY(), null);
        }
    }

    @Override
    public void move(int direction) {
        this.x += direction;
    }

    @Override
    public boolean isDestroyed() {
        return !isVisible();
    }

}