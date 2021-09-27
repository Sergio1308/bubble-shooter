import java.awt.*;

public class Bullet {
    // Fields
    private double x;
    private double y;
    //private double bulletdx;
    //private double bulletdy;

    private double distX;
    private double distY;
    //private double dist;
    private int r;

//    private double dx;
//    private double dy;
//    private double rad;
    private double speed;

    private Color color;

    // Constructor
    public Bullet (double angle, double x, double y) {
        this.x = GamePanel.player.getX();
        this.y = GamePanel.player.getY();
        r = 2;

//        rad = Math.toRadians(angle);
        speed = 20;
//        dx = Math.cos(rad) * speed;
//        dy = Math.sin(rad) * speed;

        distX = GamePanel.mouseX - x; // разница по х от мышки до пули
        distY = y - GamePanel.mouseY;
        //dist = Math.sqrt(distX * distX + distY * distY);

        //bulletdx = distX/dist * speed;
        //bulletdy = distY/dist * speed;

        color = Color.WHITE;
    }

    //  Functions
    public double getX() { return x; }
    public double getY() { return y; }
    public int getR() { return r; }

    public boolean remove() {
        if (y < 0 || y > GamePanel.HEIGHT || x < 0 || x > GamePanel.WIDTH) {
            return true;
        }
        return false;
    }

    public boolean update() {
//        y -= speed;
       // y += Math.sin(x) * 4 + bulletdy;
       // x += Math.sin(y) * 4 + bulletdx;

        //y += bulletdy;
        //x += bulletdx;
        y = y - speed * distY/(Math.sqrt(distX*distX + distY*distY));
        x = x + speed * distX/(Math.sqrt(distX*distX + distY*distY));

//        x += dx;
//        y += dy;

        if (x < -r || x > GamePanel.WIDTH + r ||
                y < -r || y > GamePanel.HEIGHT + r) {
            return true;
        } return false;
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillOval((int) x, (int) y, r, 2 * r);
    }
}
