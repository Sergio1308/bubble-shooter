import java.awt.*;

public class Player {

    private double x;
    private double y;
    private int r;

    private double dx;
    private double dy;

    private int speed;

    private int health;
    public static boolean healthy;

    private Color color1;

    private int powerLevel;
    private int power;
    private int[] requiredPower = {
            1, 2, 3, 4, 5
    };

    public static boolean up;
    public static boolean down;
    public static boolean left;
    public static boolean right;

    private float angle = 90;
    public static boolean isShotgun = false;
    public int bulletsAmount = 5;
    private float minAngle = 0;
    private float currentAngle = minAngle;
    private float density = angle / bulletsAmount;

    public static boolean isFiring;
    private long firingTimer;
    private long firingDelay;

    private int score;

    public Player () {
        x = (double) GamePanel.WIDTH / 2;
        y = 500;

        r = 8;

        speed = 10;

        dx = 0;
        dy = 0;

        health = 3;
        healthy = false;

        color1 = Color.WHITE;

        up  = false;
        down = false;
        left = false;
        right = false;

        isFiring = false;
        firingTimer = System.nanoTime();
        firingDelay = 200;

        score = 0;
    }

    public double getX(){
        return x;
    }

    public double getY() {
        return y;
    }

    public int getR() {
        return r;
    }

    public int getScore() { return score; }

    public int getHealth() { return health; }

    public void gainLife() {
        health++;
    }

    public void hit() {
        health--;
        if (health <= 0) {
            healthy = true;
            GamePanel.state = GamePanel.STATES.GAMEOVER;
        }
    }
    public void addScore(int i) { score += i; }

    public void increasePower(int i) {
        if (power + i < requiredPower.length) {
            power += i;
            if (power >= requiredPower[powerLevel]) {
                power -= requiredPower[powerLevel];
                powerLevel++;
            }
        }
    }

    public int getPowerLevel() { return powerLevel; }
    public int getPower() { return power; }
    public int getRequiredPower() { return requiredPower[powerLevel]; }


    public void update() {
        if (up && y > r) {
            dy = -speed;
        }
        if (down && y < GamePanel.HEIGHT - r) {
            dy = speed;
        }
        if (left && x > r) {
            dx = -speed;
        }
        if (right && x < GamePanel.WIDTH - r) {
            dx += speed;
        }
        if (up && left || up && right || down && left || down && right) {
            dy = dy * Math.sin(Math.sin(Math.PI/4));
            dx = dx * Math.cos(Math.sin(Math.PI/4));
        }

        y += dy;
        x += dx;

        dy = 0; // stop moving

        dx = 0;

        // firing
        if (isFiring) {
            long elapsed = (System.nanoTime() - firingTimer) / 1000000;
            if (elapsed > firingDelay) {
                firingTimer = System.nanoTime();
                // extra option
                if(isShotgun) {
                    currentAngle = 360;

                    int x1 = 500;
                    while (x1 > -500) {
                        GamePanel.bullets.add(new Bullet(currentAngle, x + x1, y));
                        x1 -= 100;
                    }
                }
                else if (powerLevel < 2) {
                    GamePanel.bullets.add(new Bullet(currentAngle, x, y));
                }
                else if (powerLevel < 4) {
                    currentAngle = 45;
                    GamePanel.bullets.add(new Bullet(currentAngle, x + 15, y));
                    GamePanel.bullets.add(new Bullet(currentAngle, x - 15, y));
                }
                else {
                    currentAngle = 90;
                    GamePanel.bullets.add(new Bullet(currentAngle, x, y));
                    GamePanel.bullets.add(new Bullet(currentAngle, x + 55, y));
                    GamePanel.bullets.add(new Bullet(currentAngle, x - 55, y));
                }
            }
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(color1);
        g.fillOval((int) (x - r), (int) (y - r), 2 * r, 2 * r);
        g.setStroke(new BasicStroke(3)); // line thickness
        g.setColor(color1.darker());
        g.drawOval((int) (x - r), (int) (y - r), 2 * r, 2 * r);
        g.setStroke(new BasicStroke(1)); // line thickness
    }
    public void drawHealth(Graphics2D g) {
        for (int i = 0; i < GamePanel.player.getHealth(); i++) {
            g.setColor(color1);
            g.fillOval(15 + (25 * i), 20, GamePanel.player.getR() * 2, GamePanel.player.getR() * 2);
            g.setStroke(new BasicStroke(3));
            g.setColor(color1.darker());
            g.fillOval(15 + (25 * i), 20, GamePanel.player.getR() * 2, GamePanel.player.getR() * 2);
            g.setStroke(new BasicStroke(1));
        }
    }
}
