import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class AlienGroup implements AlienComponent {

    private List<AlienComponent> children = new ArrayList<>();

    public void add(AlienComponent component) {
        children.add(component);
    }

    public void remove(AlienComponent component) {
        children.remove(component);
    }

    public List<AlienComponent> getChildren() {
        return children;
    }

    @Override
    public void draw(Graphics g) {
        for (AlienComponent child : children) {
            child.draw(g);
        }
    }

    @Override
    public void move(int direction) {
        for (AlienComponent child : children) {
            child.move(direction);
        }
    }

    @Override
    public boolean isDestroyed() {
        for (AlienComponent child : children) {
            if (!child.isDestroyed()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Flatten the composite and return a list of actual Alien leaves.
     * This preserves existing code that expects concrete Alien objects.
     */
    public List<Alien> getAllAliens() {
        List<Alien> result = new ArrayList<>();
        collectAliens(this, result);
        return result;
    }

    // Recursive helper to traverse composite structure
    private void collectAliens(AlienComponent node, List<Alien> out) {
        if (node instanceof Alien) {
            out.add((Alien) node);
            return;
        }
        if (node instanceof AlienGroup) {
            for (AlienComponent child : ((AlienGroup) node).getChildren()) {
                collectAliens(child, out);
            }
        }
    }
}
