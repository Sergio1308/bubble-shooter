import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Menu {

    // Fields
    private Color color1;
    private String s;

    private long waveTimer;
    private long waveDelay;
    private long waveTimerDiff;

    private String waveText;

    // Constructor
    public Menu() {
        color1 = Color.white;

        waveTimer = 50;
        waveDelay = 1300;
        waveTimerDiff = 50;
        waveText = " Press Space to play! ";

    }

    // Functions
    public void update() {
        if (GamePanel.leftMouse) {
            GamePanel.state = GamePanel.STATES.PLAY;
        }
    }


    public void draw(Graphics2D g) {

        // image background
        Image im = null;
        try {
            im = ImageIO.read(new File("image\\bubbleMenu.jpg"));
        } catch (IOException e) {

        }
        g.drawImage(im, 0, 0, 600,600, null);

        // flashing inscription
        double divider = waveDelay / 180;
        double alpha = waveTimerDiff / divider;
        alpha = 255 * Math.sin(Math.toRadians(alpha));

        if (alpha < 0) alpha = 0;
        if (alpha > 255) alpha = 255;

        g.setFont(new Font("Consolas", Font.PLAIN, 40));
        g.setColor(new Color(255, 255, 255, (int)alpha));

        String s = waveText;
        long length = (int)g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, GamePanel.WIDTH / 2 - (int) (length / 2), GamePanel.HEIGHT - (length / 4));

        if (waveTimer == 0) {
            waveTimer = System.nanoTime();
        }
        if (waveTimer > 0) {
            waveTimerDiff += (System.nanoTime() - waveTimer) / 1000000;
            waveTimer = System.nanoTime();
        }
        if (waveTimerDiff > waveDelay) {
            waveTimer = 0;
            waveTimerDiff = 0;
        }
    }
}
