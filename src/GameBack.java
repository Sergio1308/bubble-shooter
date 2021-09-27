import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameBack {
    // Fields
    private Color color;

    // Constructor
    public GameBack() {
        color = Color.BLUE;
    }

    // Functions
    public void update() {

    }
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillRect(0,0, GamePanel.WIDTH, GamePanel.HEIGHT);
        Image im = null;
        try {
            im = ImageIO.read(new File("image\\bubbleBack.jpg"));
        } catch (IOException e) {

        }
        g.drawImage(im, 0, 0, 600, 600, null);
    }
}

