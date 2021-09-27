import java.awt.*;

public class Wave {
    // Fields
    private int waveNumber;
    private int waveMultiplier;

    private long waveTimer;
    private long waveDelay;
    private long waveTimerDiff;

    private boolean waveStart;

    private String waveText;

    // Constructor
    public Wave() {
        waveNumber = 1;
        waveMultiplier = 5;

        waveTimer = 0;
        waveDelay = 2000;
        waveTimerDiff = 0;
        waveStart = true;

        waveText = " <<<  W A V E  ";
    }

    // Functions
    public void createEnemies() {
        GamePanel.enemies.clear();
        Enemy e;

        if (waveNumber == 1) {
            for (int i = 0; i < 4; i++) {
                GamePanel.enemies.add(new Enemy(1, 1));
            }
        }
        if (waveNumber == 2) {
            for (int i = 0; i < 4; i++) {
                GamePanel.enemies.add(new Enemy(1, 1));
            }
            GamePanel.enemies.add(new Enemy(2, 1));
            GamePanel.enemies.add(new Enemy(2, 1));
            GamePanel.enemies.add(new Enemy(1,1));
        }
        if (waveNumber == 3) {
            GamePanel.enemies.add(new Enemy(2, 3));
            GamePanel.enemies.add(new Enemy(2, 3));
            GamePanel.enemies.add(new Enemy(2, 4));
            GamePanel.enemies.add(new Enemy(1,1));
        }
        if (waveNumber > 3) {
            for (int i = 0; i < 10; i++) {
                GamePanel.enemies.add(new Enemy(2, 4));
                GamePanel.enemies.add(new Enemy(2, 3));
                GamePanel.enemies.add(new Enemy(1,1));
                GamePanel.enemies.add(new Enemy(3,1));
            }
        }
        if (waveNumber == 10) {
            for (int i = 0; i < 2; i++) {
                GamePanel.enemies.add(new Enemy(1, 1));
            }
        }
//        int enemyCount = waveNumber * waveMultiplier;
//        if (waveNumber < 4) {
//            while (enemyCount > 0) {
//                int type = 1;
//                int rank = 1;
//                GamePanel.enemies.add(new Enemy(type, rank));
//                enemyCount -= type * rank;
//            }
//        }
//        if (waveNumber > 4) {
//            while (enemyCount > 0) {
//                int type = 1;
//                int rank = 3;
//                GamePanel.enemies.add(new Enemy(type, rank));
//                enemyCount -= type * rank;
//            }
//        }
//        waveNumber++;
    }

    public void update() {
        if (GamePanel.enemies.size() == 0 && waveTimer == 0) {
            waveTimer = System.nanoTime();
            waveNumber++;
            waveStart = false;
        }
        if (waveStart && GamePanel.enemies.size() == 0) {
            createEnemies();
        }
        if (waveTimer > 0) {
            waveTimerDiff += (System.nanoTime() - waveTimer) / 1000000;
            waveTimer = System.nanoTime();
        }
        if (waveTimerDiff > waveDelay) {
            createEnemies();
            waveTimer = 0;
            waveTimerDiff = 0;
        }
    }

    public boolean showWave() {
        if (waveTimer != 0) {
            return true;
        } else return false;
    }

    public void draw(Graphics2D g) {
        double divider = waveDelay / 180;
        double alpha = waveTimerDiff / divider;
        alpha = 255 * Math.sin(Math.toRadians(alpha));
        if (alpha < 0) alpha = 0;
        if (alpha > 255) alpha = 255;
        g.setFont(new Font("Consolas", Font.PLAIN, 20));
        g.setColor(new Color(255, 255, 255, (int)alpha));
        String s = waveText + waveNumber + "  >>> ";
        long length = (int)g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, GamePanel.WIDTH / 2 - (int) (length / 2), GamePanel.HEIGHT / 2);
    }
}
