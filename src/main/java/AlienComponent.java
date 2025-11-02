import java.awt.Graphics;

public interface AlienComponent {
    void draw(Graphics g);

    void move(int direction);

    boolean isDestroyed();
}