import java.awt.Image;

/**
 * Base decorator for Alien instances. Delegates calls to the wrapped Alien
 * instance. Subclasses can override behavior, for example damage handling
 * while keeping the Alien API.
 */
public abstract class AlienDecorator extends Alien {

    protected Alien delegate;

    public AlienDecorator(Alien delegate) {
        // initialize the decorator with delegate's coordinates
        super(delegate.getX(), delegate.getY());
        this.delegate = delegate;
    }

    @Override
    public void act(int direction) {
        delegate.act(direction);
    }

    @Override
    public void setPosition(int x, int y) {
        delegate.setPosition(x, y);
    }

    @Override
    public int getX() {
        return delegate.getX();
    }

    @Override
    public int getY() {
        return delegate.getY();
    }

    @Override
    public Image getImage() {
        return delegate.getImage();
    }

    @Override
    public void setImage(Image img) {
        delegate.setImage(img);
    }

    @Override
    public Bomb getBomb() {
        return delegate.getBomb();
    }

    @Override
    public boolean isVisible() {
        return delegate.isVisible();
    }

    @Override
    public void die() {
        delegate.die();
    }

    @Override
    public void setDying(boolean d) {
        delegate.setDying(d);
    }

    @Override
    public boolean isDying() {
        return delegate.isDying();
    }

    @Override
    public void setX(int x) {
        delegate.setX(x);
    }

    @Override
    public void setY(int y) {
        delegate.setY(y);
    }

    @Override
    public Alien clone() {
        Alien cloned = delegate.clone();
        return wrapClone(cloned);
    }

    /**
     * Wraps a cloned delegate in the specific decorator type.
     */
    protected abstract Alien wrapClone(Alien clonedDelegate);

    @Override
    public boolean damage() {
        return delegate.damage();
    }

}
