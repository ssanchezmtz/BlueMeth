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
 * @author antoniomejorado
 */
public class Ball extends Item{

    private Game game;
    private int speedX;     // speed x
    private int speedY;     // speed y
    
    public Ball(int x, int y, int width, int height, int speedX, int speedY, 
            Game game) {
        super(x, y, width, height);
        this.speedX = speedX;
        this.speedY = speedY;
        this.game = game;
    }
    
    public Ball(Ball refBall){
        super(refBall.x, refBall.y, refBall.width, refBall.height);
        this.speedX = refBall.speedX;
        this.speedY = refBall.speedY;
        this.game = refBall.game;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    
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

    @Override
    public void render(Graphics g) {
         g.drawImage(Assets.ball, getX(), getY(), getWidth(), getHeight(), null);
   }
}
