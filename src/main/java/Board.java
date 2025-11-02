import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;

/**
 * 
 * @author
 */
public class Board extends JPanel implements Runnable, Commons {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Dimension d;
	private AlienGroup alienGroup; // root of the Composite structure
	private Player player;
	private Shot shot;
	private GameOver gameend;
	private Won vunnet;

	private int alienX = 150;
	private int alienY = 25;
	private int direction = -1;
	private int deaths = 0;

	private boolean ingame = true;
	private boolean havewon = true;
	private final String expl = "/img/explosion.png";
	private final String alienpix = "/img/alien.png";
	private String message = "Seu planeta nos pertence agora...";

	private Thread animator;
	// optional injected prototype for creating aliens (Prototype pattern)
	private Alien alienPrototype;

	/*
	 * Constructor
	 */
	public Board() {
		this(null);
	}

	/**
	 * Create a Board optionally using an injected Alien prototype.
	 * If prototype is null the board will create its own prototype.
	 */
	public Board(Alien alienPrototype) {
		this.alienPrototype = alienPrototype;
		addKeyListener(new TAdapter());
		setFocusable(true);
		d = new Dimension(BOARD_WIDTH, BOARD_HEIGTH);
		setBackground(Color.black);

		gameInit();
		setDoubleBuffered(true);
	}

	public void addNotify() {
		super.addNotify();
		gameInit();
	}

	public void gameInit() {
		// using the prototype pattern to create aliens
		Alien proto = this.alienPrototype;
		if (proto == null) {
			proto = new Alien(0, 0);

			// Use of SpriteManager singleton instead of new ImageIcon
			proto.setImage(SpriteManager.getInstance().getSprite(proto.getClass().getResource(alienpix).getPath()));
		} else {
			// ensure prototype has an image configured (fallback to default)
			if (proto.getImage() == null) {
				proto.setImage(SpriteManager.getInstance().getSprite(proto.getClass().getResource(alienpix).getPath()));
			}
		}

		alienGroup = new AlienGroup();

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 6; j++) {
				Alien alien = proto.clone();
				alien.setPosition(alienX + 18 * j, alienY + 18 * i);
				alienGroup.add(alien); // 👈 add each alien to the group instead of list
			}
		}

		player = new Player();
		shot = new Shot();

		if (animator == null || !ingame) {
			animator = new Thread(this);
			animator.start();
		}
	}

	// Updated to use Composite pattern
	public void drawAliens(Graphics g) {
		alienGroup.draw(g);
	}

	public void drawPlayer(Graphics g) {
		if (player.isVisible()) {
			g.drawImage(player.getImage(), player.getX(), player.getY(), this);
		}

		if (player.isDying()) {
			player.die();
			havewon = false;
			ingame = false;
		}
	}

	public void drawGameEnd(Graphics g) {
		g.drawImage(gameend.getImage(), 0, 0, this);
	}

	public void drawShot(Graphics g) {
		if (shot.isVisible())
			g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
	}

	public void drawBombing(Graphics g) {
		// }
		if (alienGroup == null)
			return;

		for (Alien a : alienGroup.getAllAliens()) {
			Bomb b = a.getBomb();
			if (!b.isDestroyed()) {
				g.drawImage(b.getImage(), b.getX(), b.getY(), this);
			}
		}
	}

	public void paint(Graphics g) {
		super.paint(g);

		g.setColor(Color.black);
		g.fillRect(0, 0, d.width, d.height);
		g.setColor(Color.green);

		if (ingame) {

			g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
			drawAliens(g);
			drawPlayer(g);
			drawShot(g);
			drawBombing(g);
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void gameOver() {
		Graphics g = this.getGraphics();

		gameend = new GameOver();
		vunnet = new Won();

		// g.setColor(Color.black);
		g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGTH);
		if (havewon == true) {
			g.drawImage(vunnet.getImage(), 0, 0, this);
		} else {
			g.drawImage(gameend.getImage(), 0, 0, this);
		}
		g.setColor(new Color(0, 32, 48));
		g.fillRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);
		g.setColor(Color.white);
		g.drawRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);

		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = this.getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2,
				BOARD_WIDTH / 2);
	}

	public void animationCycle() {
		if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {
			ingame = false;
			message = "Parab�ns! Voc� salvou a gal�xia!";
		}

		// player

		player.act();

		// shot
		if (shot.isVisible()) {

			// Updated to use Composite pattern
			if (alienGroup != null) {
				List<Alien> aliens = alienGroup.getAllAliens();
				int shotX = shot.getX();
				int shotY = shot.getY();

				for (Alien alien : aliens) {
					int alienX = alien.getX();
					int alienY = alien.getY();

					if (alien.isVisible() && shot.isVisible()) {
						if (shotX >= (alienX) && shotX <= (alienX + ALIEN_WIDTH)
								&& shotY >= (alienY)
								&& shotY <= (alienY + ALIEN_HEIGHT)) {
							Image explImg = SpriteManager.getInstance().getSprite(
									getClass().getResource(expl).getPath());
							alien.setImage(explImg);
							alien.setDying(true);
							deaths++;
							shot.die();
						}
					}
				}
			}

			int y = shot.getY();
			y -= 8;
			if (y < 0)
				shot.die();
			else
				shot.setY(y);
		}

		// aliens
		// Updated to use Composite pattern
		if (alienGroup != null) {
			List<Alien> aliens = alienGroup.getAllAliens();
			for (Alien a1 : aliens) {
				int x = a1.getX();

				if (x >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {
					direction = -1;
					for (Alien a2 : aliens) {
						a2.setY(a2.getY() + GO_DOWN);
					}
					break; // already handled the drop for this tick
				}

				if (x <= BORDER_LEFT && direction != 1) {
					direction = 1;
					for (Alien a : aliens) {
						a.setY(a.getY() + GO_DOWN);
					}
					break;
				}
			}
		}
		// Updated to use Composite pattern
		if (alienGroup != null) {
			List<Alien> aliens = alienGroup.getAllAliens();

			for (Alien alien : aliens) {
				if (alien.isVisible()) {
					int y = alien.getY();
					if (y > GROUND - ALIEN_HEIGHT) {
						havewon = false;
						ingame = false;
						message = "Aliens est�o invadindo a gal�xia!";
					}
					alien.act(direction); // keep existing act() behavior
				}
			}
		}

		// bombs
		if (alienGroup != null) {
			List<Alien> aliens = alienGroup.getAllAliens();
			Random generator = new Random();

			for (Alien a : aliens) {
				int shotRand = generator.nextInt(15);
				Bomb b = a.getBomb();
				if (shotRand == CHANCE && a.isVisible() && b.isDestroyed()) {
					b.setDestroyed(false);
					b.setX(a.getX());
					b.setY(a.getY());
				}

				int bombX = b.getX();
				int bombY = b.getY();
				int playerX = player.getX();
				int playerY = player.getY();

				if (player.isVisible() && !b.isDestroyed()) {
					if (bombX >= (playerX) && bombX <= (playerX + PLAYER_WIDTH)
							&& bombY >= (playerY)
							&& bombY <= (playerY + PLAYER_HEIGHT)) {

						Image explImg = SpriteManager.getInstance().getSprite(
								getClass().getResource(expl).getPath());
						player.setImage(explImg);
						player.setDying(true);
						b.setDestroyed(true);
					}
				}

				if (!b.isDestroyed()) {
					b.setY(b.getY() + 1);
					if (b.getY() >= GROUND - BOMB_HEIGHT) {
						b.setDestroyed(true);
					}
				}
			}
		}

		if (alienGroup != null) {

			List<Alien> aliensToProcess = alienGroup.getAllAliens();
			for (Alien alien : aliensToProcess) {
				if (alien.isDying()) {
					alien.die(); 
				}
			}

			Iterator<AlienComponent> it = alienGroup.getChildren().iterator();
			while (it.hasNext()) {
				AlienComponent component = it.next();
				if (component.isDestroyed()) {
					it.remove(); 
				}
			}
		}
	}

	public void run() {
		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();

		while (ingame) {
			repaint();
			animationCycle();

			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = DELAY - timeDiff;

			if (sleep < 0)
				sleep = 1;
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				System.out.println("interrupted");
			}
			beforeTime = System.currentTimeMillis();
		}
		gameOver();
	}

	private class TAdapter extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			player.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {

			player.keyPressed(e);

			int x = player.getX();
			int y = player.getY();

			if (ingame) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_SPACE) {

					if (!shot.isVisible())
						shot = new Shot(x, y);
				}
			}
		}
	}
}