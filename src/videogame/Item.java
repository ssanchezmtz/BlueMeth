/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package videogame;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author antoniomejorado
 */
public abstract class Item {
    protected int x;        // to store x position
    protected int y;        // to store y position
    protected int width;    // to store width
    protected int height;   // to store height
    
    /**
     * Set the initial values to create the item
     * @param x <b>x</b> position of the object
     * @param y <b>y</b> position of the object
     * @param width the width of the object
     * @param height the height of the object
     */
    public Item(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Get <b>x</b> value
     * @return <b>x</b> position of the object
     */
    public int getX() {
        return x;
    }

    /**
     * Get <b>y</b> value
     * @return <b>y</b> position of the object
     */
    public int getY() {
        return y;
    }

    /**
     * Get <b>width</b> value
     * @return <b>y</b> position of the object
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get <b>height</b> value
     * @return <b>height</b> position of the object
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set <b>x</b> value
     * @param x to modify
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set <b>y</b> value
     * @param y to modify
     */
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * Set <b>width</b> value
     * @param width to modify
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Set <b>height</b> value
     * @param height to modify
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Determines if this item intersects another item
     * @param obj the other object
     * @return a boolean value with the result
     */
    public boolean intersects(Object obj) {
        // check if the object is an Item
        return (obj instanceof Item && 
                this.getBounds().intersects(((Item) obj).getBounds()));
    }
    
    private Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
    /**
     * To update positions of the item for every tick
     */
    public abstract void tick();
    
    /**
     * To paint the item
     * @param g <code>Graphics</code> object to paint the item
     */
    public abstract void render(Graphics g);
}