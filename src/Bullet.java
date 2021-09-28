import java.awt.*;

public class Bullet {
    
    // Fields
    private double x;
    private double y;

    private double distX;
    private double distY;
    private int r;

    private double speed;

    private Color color;

    // Constructor
    public Bullet (double angle, double x, double y) {
        this.x = GamePanel.player.getX();
        this.y = GamePanel.player.getY();
        r = 2;
        speed = 20;

        distX = GamePanel.mouseX - x;  // subtracting the distance from the crosshair to the bullet
        distY = y - GamePanel.mouseY;

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
        y = y - speed * distY/(Math.sqrt(distX*distX + distY*distY));
        x = x + speed * distX/(Math.sqrt(distX*distX + distY*distY));

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
