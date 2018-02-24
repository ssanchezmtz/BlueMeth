package videogame;

import java.awt.Color;
import java.awt.Graphics;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Arturo Arenas Esparza (A00820982)
 * @author Sergio Sanchez Martinez
 */
public class Brick extends Item{

    private Game game;
    private int power;
    private int hiddenPerk;
    
    /**
     * 
     * @param x <b>x</b> position of the object
     * @param y <b>y</b> position of the object
     * @param width the width of the object
     * @param height the height of the object
     * @param power the amount of times it has to be hit to be destroyed
     * @param game the reference to the game
     * @param hiddenPerk the ID of the hidden perk if it has one
     */
    public Brick(int x, int y, int width, int height, int power ,Game game, int hiddenPerk) {
        super(x, y, width, height);
        this.game = game;
        this.power = power;
        this.hiddenPerk = hiddenPerk;
    }

    /**
     * Getter of the power
     * @return the power of the object
     */
    public int getPower() {
        return power;
    }

    /**
     * Getter for the ID of the hidden perk
     * @return the ID of the hidden perk 
     */
    public int getHiddenPerk() {
        return hiddenPerk;
    }

    /**
     * Setter of the power
     * @param power the amount of power to be assigned
     */
    public void setPower(int power) {
        this.power = power;
    }
    
    /**
     * Update the sttributes of the bar
     */
    @Override
    public void tick() {
    }

    /**
     * Paints the brick
     * @param g the graphics to paint the brick
     */
    @Override
    public void render(Graphics g) {
        if(this.getPower()==3){
            g.drawImage(Assets.brick1, getX(), getY(), getWidth(), getHeight(), null);
        }else if (this.getPower() == 2){
            g.drawImage(Assets.brick2, getX(), getY(), getWidth(), getHeight(), null);
        }else if (this.getPower() == 1){
            g.drawImage(Assets.brick3, getX(), getY(), getWidth(), getHeight(), null);            
        }

    }
}
    