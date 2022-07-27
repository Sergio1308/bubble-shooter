import java.awt.*;

/**
 * type #1 +1 life
 * type #2 +1 power
 * type #3 +2 power
 */
public class PowerUp {

    private double x;
    private double y;
    private int r;

    private int type;
    private Color color1;

    public PowerUp(int type, double x, double y) {
        this.type = type;
        this.x = x;
        this.y = y;

        if (type == 1) {
            color1 = Color.PINK;
            r = 3;
        }
        if (type == 2) {
            color1 = Color.YELLOW;
            r = 3;
        }
        if (type == 3) {
            color1 = Color.YELLOW;
            r = 5;
        }
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getR() { return r; }

    public int getType() { return type; }

    public boolean update() {
        y += 2;
        return y > GamePanel.HEIGHT + r;
    }
    public void draw(Graphics2D g) {
        g.setColor(color1);
        g.fillRect((int) (x - r), (int) (y - r), 2 * r, 2 * r);
        g.setStroke(new BasicStroke(3));
        g.setColor(color1.darker());
        g.fillRect((int) (x - r), (int) (y - r), 2 * r, 2 * r);
        g.setStroke(new BasicStroke(1));
    }
}
