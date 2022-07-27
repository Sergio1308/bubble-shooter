import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable{

    public static int WIDTH = 1024;
    public static int HEIGHT = 768;

    public static int mouseX;
    public static int mouseY;
    public static boolean leftMouse;
    public static boolean space = false;

    private Thread thread = new Thread(this);

    private BufferedImage image;
    private Graphics2D g;

    private int FPS;
    private double millisPerFrame;
    private long timerFPS;
    private int  sleepTime;

    public enum STATES {
        MENU,
        PLAY,
        GAMEOVER
    }

    public static STATES state = STATES.MENU;

    public static GameBack background;
    public static Player player;
    public static ArrayList<Bullet> bullets;
    public static ArrayList<Enemy> enemies;
    public static ArrayList<Explosion> explosions;
    public static ArrayList<PowerUp> powerUps;

    public static Wave wave;
    public static Menu menu;
    public static GameOver gameover;
    public static Player health;

    public GamePanel() {
        super();

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus(); // activate focus

        addKeyListener(new Listeners());
        addMouseMotionListener(new Listeners());
        addMouseListener(new Listeners());
    }
    // Functions
    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        FPS = 30;
        millisPerFrame = 1000.0 / FPS;
        sleepTime = 0;

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //improved graphics
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        leftMouse = false;
        background = new GameBack();
        player = new Player();

        bullets = new ArrayList<Bullet>();
        enemies = new ArrayList<Enemy>();
        explosions = new ArrayList<Explosion>();
        powerUps = new ArrayList<PowerUp>();

        wave = new Wave();
        menu = new Menu();
        gameover = new GameOver();
        health = new Player();


        Toolkit kit = Toolkit.getDefaultToolkit();
        BufferedImage buffered = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g3 = (Graphics2D) buffered.getGraphics();
        g3.setColor(new Color(255, 255, 255));
        g3.drawOval(0, 0, 4, 4);
        g3.drawLine(2, 0, 2, 4);
        g3.drawLine(0, 2, 4, 2);
        Cursor myCursor = kit.createCustomCursor(buffered, new Point(3, 3), "myCursor");
        g3.dispose(); // del

        while (true) {
            this.setCursor(myCursor);
            timerFPS = System.nanoTime();
            if (state.equals(STATES.MENU)) {
                background.update();
                background.draw(g);
                menu.update();
                menu.draw(g);
                gameDraw();
            }
            if (state.equals(STATES.PLAY)) {
                gameUpdate();
                gameRender();
                gameDraw();
            }
            if (state.equals(STATES.GAMEOVER)) {
                this.setCursor(Cursor.getDefaultCursor());
                background.update();
                background.draw(g);
                gameover.draw(g);
                gameDraw();
            }

            timerFPS = (System.nanoTime() - timerFPS) / 1000000;
            if (millisPerFrame > timerFPS) {
                sleepTime = (int)(millisPerFrame - timerFPS);
            } else sleepTime = 1;
            try {
                thread.sleep(sleepTime); // current game FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timerFPS = 0;
            sleepTime = 1;
        }
    }
    // updating data of all objects
    public void gameUpdate() {
        // background upd
        background.update();

        // player upd
        player.update();

        // Bullets update
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).update();
            boolean remove = bullets.get(i).remove();
            if (remove) {
                bullets.remove(i);
                i--;
            }
        }
        // Enemies upd
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();
        }

        // bullets-enemies collide
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            double ex = e.getX();
            double ey = e.getY();
            for (int j = 0; j < bullets.size(); j++) {
                Bullet b = bullets.get(j);
                double bx = b.getX();
                double by = b.getY();
                double dx = ex - bx;
                double dy = ey - by;
                double dist = Math.sqrt(dx * dx + dy * dy);
                if ((int) dist <= e.getR() + b.getR()) {
                    e.hit();
                    bullets.remove(j);
                    j--;
                    explosions.add(new Explosion(e.getX(), e.getY(), e.getR(), e.getR() + 20));
                    boolean remove = e.remove();

                    if (remove) {
                        // chance for powerup
                        double rand = Math.random();
                        if (rand < 0.015) powerUps.add(new PowerUp(1, e.getX(), e.getY()));
                        else if (rand < 0.120) powerUps.add(new PowerUp(2, e.getX(), e.getY()));
                        else if (rand < 0.020) powerUps.add(new PowerUp(3, e.getX(), e.getY()));

                        enemies.remove(i);
                        player.addScore(e.getType() + e.getRank());
                        i--;
                        e.explode();
                        return;
                    }
                }
            }
        }
        // PowerUp upd
        for (int i = 0; i < powerUps.size(); i++) {
            boolean remove = powerUps.get(i).update();
            if (remove) {
                powerUps.remove(i);
                i--;
            }
        }
        // player-enemy collides
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            double ex = e.getX(); // get coordinates
            double ey = e.getY();

            double px = player.getX();
            double py = player.getY();
            double dx = ex - px;
            double dy = ey - py;
            double dist = Math.sqrt(dx * dx + dy * dy); // get distance
            if ((int)dist <= e.getR() + player.getR()) {
                e.hit();
                player.hit();

                enemies.remove(i);
                i--;
                return;
            }
        }
        // player-powerup collision
        double px = player.getX();
        double py = player.getY();
        int pr = player.getR();
        for (int i = 0; i < powerUps.size(); i++) {
            PowerUp p = powerUps.get(i);
            double x = p.getX();
            double y = p.getY();
            double r = p.getR();
            double dx = px - x;
            double dy = py - y;
            double dist = Math.sqrt(dx * dx + dy * dy);

            // collected powerup
            if (dist < pr + r) {
                int type = p.getType();

                if (type == 1 && player.getHealth() < 3) {
                    player.gainLife();
                }
                if (type == 2) {
                    player.increasePower(1);
                }
                if (type == 3) {
                    player.increasePower(2);
                }

                powerUps.remove(i);
                i --;
                player.addScore(10);
            }
        }
        wave.update();

        // Explosion update
        for (int i = 0; i < explosions.size(); i++) {
            boolean remove = explosions.get(i).update();
            if (remove) {
                explosions.remove(i);
                i--;
            }
        }

    }
    // updating graphic elements
    public void gameRender() {
        // background draw
        background.draw(g);

        // player draw
        player.draw(g);

        // bullets draw
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
        // enemies draw
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
        // draw powerups
        for (PowerUp powerUp : powerUps) {
            powerUp.draw(g);
        }
        // wave draw
        if (wave.showWave()) {
            wave.draw(g);
        }
        // explosion draw
        for (Explosion explosion : explosions) {
            explosion.draw(g);
        }
        // draw player lives
        health.drawHealth(g);

        //draw player score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.PLAIN, 25));
        g.drawString("Score " + player.getScore(), WIDTH - 140, 30);

        // draw player power
        g.setColor(Color.YELLOW);
        g.fillRect(17, 44, player.getPower() * 13, 12);
        g.setColor(Color.YELLOW.darker());
        g.setStroke(new BasicStroke(2));
        for (int i = 0; i < player.getRequiredPower(); i++) {
            g.drawRect(17 + 13 * i, 44, 12, 12);
        }
        g.setStroke(new BasicStroke(1));
    }

    private void gameDraw() {
        Graphics g2 = this.getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose(); // cleaning
    }
}
