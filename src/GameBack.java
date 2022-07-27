import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameBack {
    private Color color;

    public GameBack() {
        color = Color.BLUE;
    }

    public void update() { }
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillRect(0,0, GamePanel.WIDTH, GamePanel.HEIGHT);
        Image im = null;
        try {
            im = ImageIO.read(new File("image\\bubbleBack.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(im, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
    }
}

