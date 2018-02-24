/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Arturo Arenas Esparza (A00820982)
 * @author Sergio Sanchez Martinez
 */
public class KeyManager implements KeyListener {
    public boolean left;    // flag to move left the bar
    public boolean right;   // flag to move right the bar
    public boolean space;   // flag to space
    private boolean pause;
    private boolean pauseEnabled;
    private boolean restart;
    
    private boolean keys[];  // to store all the flags for every key
    
    /**
     * Constructor of the key manager
     */
    public KeyManager() {
        keys = new boolean[256];
        pauseEnabled = true;
    }

    /**
     * Setter for the pause status
     * @param pause to set the pause
     */
    public void setPause(boolean pause) {
        keys[KeyEvent.VK_P] = pause;
    }

    /**
     * Determines the pause status
     * @return the pause status
     */
    public boolean isPause() {
        return pause;
    }

    /**
     * Determines the restart status
     * @return the restart status
     */
    public boolean isRestart() {
        return restart;
    }
    
    /**
     * Event of typing a key
     * @param e the event of the key typed
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Event of pressing a key
     * @param e the event of a key pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_P){
            if(pauseEnabled){
                keys[KeyEvent.VK_P] = true;
                pauseEnabled = false;
            }
        }
        else{
            keys[e.getKeyCode()] = true;
        }
    }

    /**
     * Event of releasing a key
     * @param e the event of releasing a key
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // set false to every key released
        if(e.getKeyCode() == KeyEvent.VK_P){
            pauseEnabled = true;
        }
        keys[e.getKeyCode()] = false;
    }
    
    /**
     * to enable or disable moves on every tick
     */
    public void tick() {
        left = keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_RIGHT];
        space = keys[KeyEvent.VK_SPACE];
        pause = keys[KeyEvent.VK_P];
        restart = keys[KeyEvent.VK_R];
    }
}
