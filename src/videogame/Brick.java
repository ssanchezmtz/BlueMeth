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
 * @author antoniomejorado
 */
public class Brick extends Item{

    private Game game;
    private int power;
    private int hiddenPerk;
    
    public Brick(int x, int y, int width, int height, int power ,Game game, int hiddenPerk) {
        super(x, y, width, height);
        this.game = game;
        this.power = power;
        this.hiddenPerk = hiddenPerk;
    }

    public int getPower() {
        return power;
    }

    public int getHiddenPerk() {
        return hiddenPerk;
    }

    public void setPower(int power) {
        this.power = power;
    }
    
    @Override
    public void tick() {
    }

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
    