import java.awt.*;

public class Enemy {
    // Fields
    private double x;
    private double y;
    private int r;

    private double speed;
    private double dx; // shift
    private double dy;
    private double rad;

    private double health;


    private int type;
    private int rank;

    private boolean ready;

    private Color color;

    // Constructor
    public Enemy(int type, int rank) {
        this.type = type;
        this.rank = rank;

        // default enemy
        if (type == 1) {
            color = Color.GREEN;
            if (rank == 1) {
                speed = 2;
                r = 7;
                health = 1;
            }
        }
        // stronger, faster default
        if (type == 2) {
            color = Color.RED;
            if (rank == 1) {
                speed = 3;
                r = 7;
                health = 2;
            }
            if (rank == 2) {
                speed = 2;
                r = 12;
                health = 2;
            }
            if (rank == 3) {
                speed = 1.5;
                r = 22;
                health = 3;
            }
            if (rank == 4) {
                speed = 1.5;
                r = 32;
                health = 4;
            }
        }
        // slow, but hard to kill
        if (type == 3) {
            color = Color.YELLOW;
            if (rank == 1) {
                speed = 1.5;
                r = 7;
                health = 5;
            }
        }

        x = Math.random() * GamePanel.WIDTH;
        y = 0;
        double angle = Math.toRadians(Math.random() * 360);
        //dx = Math.sin(angle) * speed;
        dy = Math.cos(angle) * speed;
        dx = Math.sin(angle) * speed;

        ready = false;

//        switch(type) {
//            case(1): color = Color.GREEN;
//                switch(rank) {
//                    case(1):
//                        x = Math.random() * GamePanel.WIDTH;
//                        y = 0;
//                        r = 7;
//                        speed = 2;
//                        health = 1;
//                        double angle = Math.toRadians(Math.random() * 360);
//                        dx = Math.sin(angle) * speed;
//                        dy = Math.cos(angle) * speed;
//                    case(2):
//                        r = 12;
//                        speed = 2;
//                        health = 2;
//                    case(3):
//                        speed = 1.5;
//                        r = 20;
//                        health = 3;
//                    case(4):
//                        speed = 1.5;
//                        r = 30;
//                        health = 4;
                //}
            //case(2): color = Color.RED;
        //}
    }

    // Functions
    public double getX() { return x; }
    public double getY() { return y; }
    public int getR() { return r; }

    public int getType() { return type; }
    public int getRank() { return rank; }

    public boolean remove() {
        if (health <= 0) {
            return true;
        }
        return false;
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
