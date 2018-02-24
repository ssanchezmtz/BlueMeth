/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Arturo Arenas Esparza (A00820982)
 * @author Sergio Sanchez Martinez
 */
public class Bar extends Item{

    private Game game;  // Reference to the game
    
    /**
     * Constructor of the bar
     * @param x the <b>x</b> position of the player
     * @param y the <b>y</b> position of the player
     * @param width the width of the player
     * @param height the height of the player
     * @param game the copy of the game
     */
    public Bar(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
    }

    /**
     * Update the sttributes of the bar
     */
    @Override
    public void tick() {
        // moving bar depending on keys <-  ->
        if (game.getKeyManager().left) {
           setX(getX() - 6);
        }
        if (game.getKeyManager().right) {
           setX(getX() + 6);
        }
        // collision with walls
        if (getX() + 100 >= game.getWidth()) {
            setX(game.getWidth() - 100);
        }
        else if (getX() <= 0) {
            setX(0);
        }

    }

    /**
     * Paints the bar
     * @param g the grphics to paint the bar
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.bar, getX(), getY(), getWidth(), getHeight(), null);
    }
}
