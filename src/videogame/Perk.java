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
public class Perk extends Item{
    private int ID; // uniquely identifies the powers of the perk
    
    /**
     * 
     * @param x <b>x</b> position of the object
     * @param y <b>y</b> position of the object
     * @param width the width of the object
     * @param height the height of the object
     * @param ID the ID of the perk
     */
    public Perk(int x, int y, int width, int height, int ID) {
        super(x, y, width, height);
        this.ID = ID;
    }

    /**
     * Gets the ID of the perk
     * @return The ID of the perk
     */
    public int getID() {
        return ID;
    }

    /**
     * Update the sttributes of the perk
     */
    @Override
    public void tick() {
        setY(getY() + 1);
    }

    /**
     * Paints the perk
     * @param g the graphics to paint the perk
     */
    @Override
    public void render(Graphics g) {
        if(ID <= 6){
            // first six are rewards
            g.setColor(Color.blue);
        }
        else{
            // it's a punishment
            g.setColor(Color.red);
        }
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}
