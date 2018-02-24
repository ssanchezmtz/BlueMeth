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
public class Ball extends Item{

    private Game game;          // reference to the game
    private int speedX;         // speed x
    private int speedY;         // speed y
    private boolean invisible;  // invisibility flag
    
    /**
     * Constructor for the ball
     * @param x the <b>x</b> position of the player
     * @param y the <b>y</b> position of the player
     * @param width the width of the player
     * @param height the height of the player
     * @param speedX the <b>x</b> speed of the player
     * @param speedY the <b>y</b> speed of the player
     * @param game the copy of the game
     */
    public Ball(int x, int y, int width, int height, int speedX, int speedY, 
            Game game) {
        super(x, y, width, height);
        this.speedX = speedX;
        this.speedY = speedY;
        this.game = game;
    }
    
    /**
     * Constructor of the ball from another ball
     * @param refBall the ball to be cloned
     */
    public Ball(Ball refBall){
        super(refBall.x, refBall.y, refBall.width, refBall.height);
        this.speedX = refBall.speedX;
        this.speedY = refBall.speedY;
        this.game = refBall.game;
    }

    /**
     * Getter for the <b>x</b> speed of the object
     * @return the <b>x</b> speed of the object
     */
    public int getSpeedX() {
        return speedX;
    }

     /**
     * Getter for the <b>y</b> speed of the object
     * @return the <b>y</b> speed of the object
     */
    public int getSpeedY() {
        return speedY;
    }

    /**
     * Determines the invisibility status of the ball
     * @return the invisibility status of the ball
     */
    public boolean isInvisible() {
        return invisible;
    }

    /**
     * Setter for the <b>x</b> speed of the object
     * @param speedX the <b>x</b> speed of the player
     */
    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    /**
     * Setter for the <b>y</b> speed of the object
     * @param speedY the <b>y</b> speed of the player
     */
    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    /**
     * Sets the invisibility status of the ball
     * @param invisible the new invisibility status of the ball
     */
    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }
    
    /**
     * Updates the sttributes of the bar
     */
    @Override
    public void tick() {
        // moving bar depending on keys <-  ->
        setX(getX() + getSpeedX());
        setY(getY() + getSpeedY());
        // collision with walls
        if (getX() + 20 >= game.getWidth()) {
            setX(game.getWidth() - 20);
            setSpeedX(getSpeedX() * -1);
        }
        else if (getX() <= 0) {
            setX(0);
            setSpeedX(getSpeedX() * -1);
        }
        if(getY() + 20 >= game.getHeight()){
            game.setDeath(true);
        }
        else if(getY() <= 0){
            setY(0);
            setSpeedY(getSpeedY() * -1);
        }

    }

    /**
     * Paints the ball
     * @param g the graphics to paint the ball
     */
    @Override
    public void render(Graphics g) {
        if(!isInvisible()){
            g.drawImage(Assets.ball, getX(), getY(), getWidth(), getHeight(), null);
        }
   }
}
