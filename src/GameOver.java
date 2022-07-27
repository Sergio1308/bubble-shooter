import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameOver {

    private int buttonWidth;
    private int buttonHeight;
    private Color color1;
    private String s;

    public static Player player;

    public GameOver() {
        buttonWidth = 120;
        buttonHeight = 60;

        color1 = Color.white;
        s = "Game Over!";

        player = new Player();
    }

    public void draw(Graphics2D g) {
        Image im = null;
        try {
            im = ImageIO.read(new File("image\\bubbleOver.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(im, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);

        // game over text
        g.setColor(color1);
        g.setFont(new Font("Broadway", Font.BOLD, 40));
        long length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, (int)(GamePanel.WIDTH / 2 - length / 2),
                (int)(GamePanel.HEIGHT / 2));

        // your score text
        g.setFont(new Font("Broadway", Font.BOLD, 40));
        g.setColor(Color.WHITE);
        g.drawString("Your Score: " + GamePanel.player.getScore(),
                (int)(GamePanel.WIDTH / 2 - length / 2),
                (int)(GamePanel.HEIGHT / 2 + 50));
    }
}
