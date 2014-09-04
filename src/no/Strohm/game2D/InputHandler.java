package no.Strohm.game2D;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Ole on 14/12/13.
 */
public class InputHandler implements KeyListener {

    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    public boolean enter;
    public boolean use;
    public boolean attack;
    public boolean escape;
    private boolean[] keys = new boolean[120];

    public void tick() {
        up = keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_DOWN];
        left = keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_RIGHT];
        enter = keys[KeyEvent.VK_ENTER] || keys[KeyEvent.VK_E];
        use = keys[KeyEvent.VK_X];
        attack = keys[KeyEvent.VK_C];
        escape = keys[KeyEvent.VK_ESCAPE];
    }

    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        if(c >= keys.length) return;
        keys[c] = true;
    }

    public void keyReleased(KeyEvent e) {
        int c = e.getKeyCode();
        if(c >= keys.length) return;
        keys[c] = false;
    }

    public void keyTyped(KeyEvent e) {
    }
}
