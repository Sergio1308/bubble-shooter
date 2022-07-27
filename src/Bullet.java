import java.awt.*;

public class Bullet {

    private double x;
    private double y;
    private double distX;
    private double distY;
    private double speed;
    private int r;

    private Color color;

    public Bullet (double angle, double x, double y) {
        this.x = GamePanel.player.getX();
        this.y = GamePanel.player.getY();
        r = 3;

        speed = 25;

        distX = GamePanel.mouseX - x;
        distY = y - GamePanel.mouseY;

        color = Color.WHITE;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public int getR() { return r; }

    public boolean remove() {
        return y < 0 || y > GamePanel.HEIGHT || x < 0 || x > GamePanel.WIDTH;
    }

    public boolean update() {

        y = y - speed * distY/(Math.sqrt(distX*distX + distY*distY));
        x = x + speed * distX/(Math.sqrt(distX*distX + distY*distY));

        return x < -r || x > GamePanel.WIDTH + r ||
                y < -r || y > GamePanel.HEIGHT + r;
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillOval((int) x, (int) y, r, 2 * r);
    }
}
