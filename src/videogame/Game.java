/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author antoniomejorado
 */
public class Game implements Runnable {
    private BufferStrategy bs;      // to have several buffers when displaying
    private Graphics g;             // to paint objects
    private Display display;        // to display in the game
    String title;                   // title of the window
    private int width;              // width of the window
    private int height;             // height of the window
    private Thread thread;          // thread to create the game
    private boolean running;        // to set the game
    private boolean started;        // to start the game
    private Bar bar;                // to use a bar
    private Ball ball;              // little ball
    private ArrayList<Brick> bricks;// bricks
    private KeyManager keyManager;  // to manage the keyboard
    
    
    /**
     * to create title, width and height and set the game is still not running
     * @param title to set the title of the window
     * @param width to set the width of the window
     * @param height  to set the height of the window
     */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;
        started = false;
        keyManager = new KeyManager();
    }
    
    /**
     * To get the width of the game window
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * To get the height of the game window
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
    
    
    /**
     * initializing the display window of the game
     */
    private void init() {
         display = new Display(title, getWidth(), getHeight());  
         Assets.init();
         bar = new Bar(getWidth() / 2 - 50, getHeight() - 100, 100, 25, this);
         ball = new Ball(getWidth() / 2 - 10, getHeight() - 120, 20, 20, 0, 0, this);
         bricks = new ArrayList<Brick>();
         int width_brick = getWidth() / 10 - 6;
         int height_brick = getHeight() / 3 / 5  - 10;
         for (int i = 0; i < 10; i++) {
             for (int j = 0; j < 5; j++) {
                 Brick brick = new Brick(i * (width_brick + 3) + 15 , 
                         j * (height_brick + 5) + 15 , width_brick, height_brick, 3, this);
                 bricks.add(brick);
             }
         }
         display.getJframe().addKeyListener(keyManager);
    }
    
    @Override
    public void run() {
        init();
        // frames per second
        int fps = 50;
        // time for each tick in nano segs
        double timeTick = 1000000000 / fps;
        // initializing delta
        double delta = 0;
        // define now to use inside the loop
        long now;
        // initializing last time to the computer time in nanosecs
        long lastTime = System.nanoTime();
        while (running) {
            // setting the time now to the actual time
            now = System.nanoTime();
            // acumulating to delta the difference between times in timeTick units
            delta += (now - lastTime) / timeTick;
            // updating the last time
            lastTime = now;
            
            // if delta is positive we tick the game
            if (delta >= 1) {
                tick();
                render();
                delta --;
            }
        }
        stop();
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }
    
    private void tick() {
        keyManager.tick();
        // if space and game has not started
        if (this.getKeyManager().space && !this.isStarted()) {
            this.setStarted(true);
            ball.setSpeedX(1);
            ball.setSpeedY(-1);
        }
        // moving bar
        bar.tick();
        // if game has started
        if (this.isStarted()) {
            // moving the ball
            ball.tick();
        } else {
            // moving the ball based on the bar
            ball.setX(bar.getX() + bar.getWidth() / 2 - ball.getWidth() / 2);
        }
        
        // check collision bricks versus ball
        for (int i = 0; i < bricks.size(); i++) {
            Brick brick = (Brick) bricks.get(i);
            if (ball.intersects(brick) && brick.getPower()==3) {
                brick.setPower(brick.getPower()-1);
                ball.setSpeedY(ball.getSpeedY() * -1);
            }else if(ball.intersects(brick) && brick.getPower()==2){
                brick.setPower(brick.getPower()-1);
                ball.setSpeedY(ball.getSpeedY() * -1);
            }
            else if(ball.intersects(brick) && brick.getPower()==1){
                brick.setPower(brick.getPower()-1);
                ball.setSpeedY(ball.getSpeedY() * -1);
            }else if(ball.intersects(brick) && brick.getPower()==0){
                bricks.remove(brick);
                ball.setSpeedY(ball.getSpeedY() * -1);
                i--;                
            }
        }
        
        // check collision ball versus bar and modify speed accordingly
        // the x-speed and y-speed of the ball can never be 0
        if (ball.intersects(bar)) {
            ball.setY(bar.getY() - 21);
            ball.setSpeedY(ball.getSpeedY() * -1);
            if(this.getKeyManager().left || this.getKeyManager().right){
                // speed up
                if(ball.getSpeedX() > 0){
                    ball.setSpeedX(ball.getSpeedX() + 1);
                }
                else{
                    ball.setSpeedX(ball.getSpeedX() - 1);
                }
                ball.setSpeedY(ball.getSpeedY() - 1);
            }
            else{
                // slow down
                if(ball.getSpeedX() > 0 && ball.getSpeedX() != 1){
                    ball.setSpeedX(ball.getSpeedX() - 1);
                }
                else if(ball.getSpeedX() < 0 && ball.getSpeedX() != -1){
                    ball.setSpeedX(ball.getSpeedX() + 1);
                }
                if(ball.getSpeedY() != -1){
                    ball.setSpeedY(ball.getSpeedY() + 1);
                }
            }
        }
    }
    
    private void render() {
        // get the buffer strategy from the display
        bs = display.getCanvas().getBufferStrategy();
        /* if it is null, we define one with 3 buffers to display images of
        the game, if not null, then we display every image of the game but
        after clearing the Rectanlge, getting the graphic object from the 
        buffer strategy element. 
        show the graphic and dispose it to the trash system
        */
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        }
        else
        {
            g = bs.getDrawGraphics();
            g.drawImage(Assets.background, 0, 0, width, height, null);
            bar.render(g);
            ball.render(g);
            for (Brick brick : bricks) {
                brick.render(g);
            }
            bs.show();
            g.dispose();
        }
       
    }
    
    /**
     * setting the thead for the game
     */
    public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }
    
    /**
     * stopping the thread
     */
    public synchronized void stop() {
        if (running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }           
        }
    }

 
    


}
