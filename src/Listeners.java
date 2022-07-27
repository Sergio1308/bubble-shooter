import java.awt.event.*;

public class Listeners implements KeyListener, MouseListener, MouseMotionListener {

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
                Player.up = true;
                break;
            case KeyEvent.VK_S:
                Player.down = true;
                break;
            case KeyEvent.VK_A:
                Player.left = true;
                break;
            case KeyEvent.VK_D:
                Player.right = true;
                break;
            case KeyEvent.VK_Z:
                Player.isShotgun = !Player.isShotgun;
                break;
            // debug: kill all enemies to get new wave
            case KeyEvent.VK_F1:
                GamePanel.enemies.clear();
                break;
        }
        if (key == KeyEvent.VK_ESCAPE && !Player.healthy) {
            if (GamePanel.state.equals(GamePanel.STATES.PLAY)) {
                GamePanel.state = GamePanel.STATES.MENU;
            } else {
                GamePanel.state = GamePanel.STATES.PLAY;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch(key) {
            case KeyEvent.VK_W:
                Player.up = false;
                break;
            case KeyEvent.VK_S:
                Player.down = false;
                break;
            case KeyEvent.VK_A:
                Player.left = false;
                break;
            case KeyEvent.VK_D:
                Player.right = false;
                break;
            case KeyEvent.VK_SPACE:
                if (!Player.healthy) {
                    GamePanel.state = GamePanel.STATES.PLAY;
                    GamePanel.space = true;
                }
                break;
        }
    }

    public void keyTyped(KeyEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (GamePanel.state.equals(GamePanel.STATES.PLAY))
                Player.isFiring = true;
            GamePanel.leftMouse = true;
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            Player.isFiring = false;
            GamePanel.leftMouse = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {
        GamePanel.mouseX = e.getX();
        GamePanel.mouseY = e.getY();
    }

    public void mouseMoved(MouseEvent e) {
        GamePanel.mouseY = e.getX();
        GamePanel.mouseY = e.getY();
    }
}
