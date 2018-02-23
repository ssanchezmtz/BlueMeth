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
 * @author arena
 */
public class Perk extends Item{
    private int ID;
    
    public Perk(int x, int y, int width, int height, int ID) {
        super(x, y, width, height);
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    @Override
    public void tick() {
        setY(getY() + 1);
    }

    @Override
    public void render(Graphics g) {
        if(ID <= 3){
            // first three are a reward
            g.setColor(Color.blue);
        }
        else{
            // it's a punishment
            g.setColor(Color.red);
        }
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}
