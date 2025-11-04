import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

// Implements AlienComponent for composite pattern
public class Alien extends Sprite implements Cloneable, AlienComponent {

    private Bomb bomb;
    private AlienType alienType; // Flyweight reference
    private int currentHp; // Track remaining health

    /*
     * Constructor
     */
    public Alien(int x, int y, AlienType alienType) {
        this.x = x;
        this.y = y;
        this.alienType = alienType;
        this.currentHp = alienType.getMaxHp(); // Initialize HP from type

        bomb = new Bomb(x, y);
        setVisible(true); 
    }

    @Override
    public Image getImage() {
        // If no image set yet, get it from AlienType
        if (super.getImage() == null) {
            setImage(alienType.getImage());
        }
        return super.getImage();
    }

    /**
     * Initialize image after construction to avoid decorator issues
     */
    public void initializeImage() {
        setImage(alienType.getImage());
    }

    public void act(int direction) {
        this.x += direction;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Alien clone() {
        Alien alienClone = new Alien(this.x, this.y, this.alienType);
        alienClone.setImage(this.getImage());
        alienClone.currentHp = this.alienType.getMaxHp(); // Reset HP on clone
        return alienClone;

    }

    /**
     * Apply damage to this alien. Default behavior: mark as dying and return true
     * to indicate the alien was destroyed. Decorators (or subclasses) can
     * override this to implement HP, armor, or other behaviors.
     * 
     * Updated damage() - uses AlienType's maxHp for base behavio
     * 
     * @return true if the alien died, false otherwise
     */
    public boolean damage() {
        // this.setDying(true);
        // return true;
        currentHp--;
        if (currentHp <= 0) {
            this.setDying(true);
            return true; // Alien died
        }
        return false; // Alien still alive
    }

    /*
     * Getters & Setters
     */

    public Bomb getBomb() {
        return bomb;
    }

    public AlienType getAlienType() {
        return alienType;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    // AlienComponent interface methods
    @Override
    public void draw(Graphics g) {

        // Debug info
        System.out.println("Alien.draw() - visible: " + isVisible() + ", x: " + getX() + ", y: " + getY() + ", image: "
                + (getImage() != null));
      
        if (isVisible()) {
            // Try to draw the image (might not show yet)
            if (getImage() != null) {
                g.drawImage(getImage(), getX(), getY(), null);
            }

            // Draw colored rectangle based on alien type
            if (alienType.getTypeName().equals("weak")) {
                g.setColor(Color.RED); // Level 1 = Red
            } else {
                g.setColor(Color.GREEN); // Level 2 = Green
            }
            g.drawRect(getX(), getY(), 5, 5);
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