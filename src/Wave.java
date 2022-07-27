import java.awt.*;

public class Wave {

    private int waveNumber;
    private int waveMultiplier;

    private long waveTimer;
    private long waveDelay;
    private long waveTimerDiff;

    private boolean waveStart;

    private String waveText;

    public Wave() {
        waveNumber = 0;  // first wave = 0
        waveMultiplier = 5;

        waveTimer = 0;
        waveDelay = 2000;
        waveTimerDiff = 0;
        waveStart = true;

        waveText = " <<<  W A V E  ";
    }

    public void createEnemies() {
        GamePanel.enemies.clear();

        if (waveNumber < 2) {
            addEnemies(new Enemy(1, 1));
        }
        else if (waveNumber < 4) {
            addEnemies(
                    new Enemy(1, 1),
                    new Enemy(2, 1)
            );
            GamePanel.enemies.add(new Enemy(2, 2));
        }
        else if (waveNumber < 6) {
            addEnemies(
                    new Enemy(1, 1),
                    new Enemy(2, 1),
                    new Enemy(3, 1)
            );
            GamePanel.enemies.add(new Enemy(2, 3));
            GamePanel.enemies.add(new Enemy(2, 4));
        }
        else if (waveNumber < 8) {
            addEnemies(
                    new Enemy(1, 1),
                    new Enemy(2, 1),
                    new Enemy(2, 2),
                    new Enemy(2, 3),
                    new Enemy(2, 4),
                    new Enemy(3, 1),
                    new Enemy(4, 1)
            );
        }
        // if waveNumber >= 8 then spawn enemies all type & rank, it increases only enemy count
        else {
            for (int i = 1; i <= 4; i++) { // spawn type 1 rank 1
                addEnemies(new Enemy(i, 1));
                if (i % 2 == 0) {
                    for (int j = 2; j <= 4; j++) { // spawn enemies with others ranks
                        if (i == 4 && j > 2) { // only type 2 have 4 ranks, so check it
                            break;
                        }
                        addEnemies(new Enemy(i, j));
                    }
                }
            }
        }
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

    private void addEnemies(Enemy e) {
        int enemyCount = waveNumber * waveMultiplier;
        while (enemyCount > 0) {
            int type = e.getType();
            GamePanel.enemies.add(new Enemy(e.getType(), e.getRank()));
            enemyCount -= type;
        }
    }

    private void addEnemies(Enemy... e) {
        int maxType = getMaxTypeFromEnemies(e);
        int enemyCount = waveNumber * waveMultiplier;
        while (enemyCount > 0) {
            for (Enemy enemy: e) {
                GamePanel.enemies.add(new Enemy(
                        enemy.getType(), enemy.getRank()
                ));
            }
            enemyCount -= maxType;
        }
    }

    private int getMaxTypeFromEnemies(Enemy... e) {
        int maxType = 0;
        for (Enemy enemy: e) {
            int currType = enemy.getType();
            if (maxType < currType) {
                maxType = currType;
            }
        }
        return maxType;
    }

    public boolean showWave() {
        return waveTimer != 0;
    }

    public void draw(Graphics2D g) {
        double divider = waveDelay / 180.0;
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
