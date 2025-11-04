import java.awt.Image;

/**
 * A stronger alien decorator. Adds hit points and overrides damage handling.
 */
public class StrongAlien extends AlienDecorator {

    private int hp; // health points

    public StrongAlien(Alien delegate, int hp) {
        super(delegate);
        this.hp = hp;
    }

    @Override
    public boolean damage() {
        hp--;
        if (hp <= 0) {
            // mark delegate as dying
            delegate.setDying(true);
            return true;
        }
        // still alive
        return false;
    }

    @Override
    protected Alien wrapClone(Alien clonedDelegate) {
        return new StrongAlien(clonedDelegate, this.hp);
    }


    // OPTIONAL: Override getImage to use strong alien image
    @Override
    public Image getImage() {
        // Use strong alien image from AlienType, fallback to delegate image
        Image strongImage = getAlienType().getImage();
        return strongImage != null ? strongImage : delegate.getImage();
    }
}
