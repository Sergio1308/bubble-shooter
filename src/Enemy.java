import java.awt.*;

public class Enemy {

    private double x;
    private double y;
    private int r;

    private double speed;
    private double dx;
    private double dy;
    private double rad;

    private double health;


    private int type;
    private int rank;

    private boolean ready;

    private Color color;

    public Enemy(int type, int rank) {
        this.type = type;
        this.rank = rank;

        switch (type) {
            // default enemy
            case 1:
                color = Color.GREEN;
                if (rank == 1) {
                    speed = 2;
                    r = 7;
                    health = 1;
                }
                break;
            // stronger than default, ability to divide into smaller parts after killing
            case 2:
                color = Color.RED;
                switch (rank) {
                    case 1:
                        speed = 3;
                        r = 7;
                        health = 2;
                        break;
                    case 2:
                        speed = 2;
                        r = 12;
                        health = 2;
                        break;
                    case 3:
                        speed = 1.5;
                        r = 22;
                        health = 3;
                        break;
                    case 4:
                        speed = 1.5;
                        r = 32;
                        health = 4;
                        break;
                }
                break;
            // slow, but hard to kill
            case 3:
                color = Color.YELLOW;
                if (rank == 1) {
                    speed = 1.5;
                    r = 7;
                    health = 5;
                }
                break;
            // fast and lively
            case 4:
                color = Color.PINK;
                if (rank == 1) {
                    speed = 5.5;
                    r = 5;
                    health = 2;
                }
                else if (rank == 2) {
                    speed = 6;
                    r = 7;
                    health = 3;
                }
                break;
        }

        x = Math.random() * GamePanel.WIDTH;
        y = 0;
        double angle = Math.toRadians(Math.random() * 360);
        dy = Math.cos(angle) * speed;
        dx = Math.sin(angle) * speed;

        ready = false;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public int getR() { return r; }

    public int getType() { return type; }
    public int getRank() { return rank; }

    public boolean remove() {
        return health <= 0;
    }

    public void hit() { health--; }

    public void explode() {
        if (rank > 1) {
            int amount = 0;
            if (type == 2) {
                amount = 3;
            }
            for (int i = 0; i < amount; i++) {
                Enemy e = new Enemy(getType(), getRank() - 1);
                e.x = this.x;
                e.y = this.y;
                double angle = 0;
                if (!ready) {
                    angle = Math.random() * 130 + 20;
                }
                else {
                    angle = Math.random() * 360;
                }
                e.rad = Math.toRadians(angle);

                GamePanel.enemies.add(e);
            }
        }
    }

    public void update() {
        y += dy;
        x += dx;

        if (x < 0 && dx < 0) dx = -dx;
        if (x > GamePanel.WIDTH && dx > 0) dx = - dx;
        if (y < 0 && dy < 0) dy = -dy;
        if (y > GamePanel.HEIGHT && dy > 0) dy = -dy;
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillOval((int ) (x - r), (int) (y - r), 2 * r, 2 * r);
        g.setStroke(new BasicStroke(3));
        g.setColor(color.darker());
        g.drawOval((int ) (x - r), (int) (y - r), 2 * r, 2 * r);
        g.setStroke(new BasicStroke(2));
    }
}
