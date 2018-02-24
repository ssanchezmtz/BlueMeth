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
import javafx.util.Pair;

/**
 *
 * @author antoniomejorado
 */
public class Game implements Runnable {
    private BufferStrategy bs;          // to have several buffers when displaying
    private Graphics g;                 // to paint objects
    private Display display;            // to display in the game
    String title;                       // title of the window
    private int width;                  // width of the window
    private int height;                 // height of the window
    private Thread thread;              // thread to create the game
    private boolean running;            // to set the game
    private boolean started;            // to start the game
    private boolean paused;
    private boolean death;
    private Bar bar;                    // to use a bar
    private ArrayList<Ball> balls;      // to have multiple balls when rewarded 
    private ArrayList<Brick> bricks;    // bricks
    private ArrayList<Perk> perks;      // perks
    private KeyManager keyManager;      // to manage the keyboard
    private int lives;
    private int score;
    final private int LIVES;
    // store pairs of IDs and times of activeness to represent active perks
    private ArrayList<Pair<Integer, Integer>> activePerks;
    
    /**
     * to create title, width and height and set the game is still not running
     * @param title to set the title of the window
     * @param width to set the width of the window
     * @param height  to set the height of the window
     */
    public Game(String title, int width, int height) {
        this.activePerks = new ArrayList<Pair<Integer, Integer>>();
        this.balls = new ArrayList<Ball>();
        this.perks = new ArrayList<Perk>();
        this.title = title;
        this.width = width;
        this.height = height;
        LIVES = 3;
        lives = LIVES; /// NOT DOING ANYTHING ABOUT IT
        score = 0;
        paused = false;
        running = false;
        started = false;
        death = false;
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

    public int getLives() {
        return lives;
    }

    public boolean isDeath() {
        return death;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setDeath(boolean death) {
        this.death = death;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
    
    public void restart(){
         bar = new Bar(getWidth() / 2 - 50, getHeight() - 100, 100, 25, this);
         Ball ball = new Ball(getWidth() / 2 - 10, getHeight() - 120, 20, 20, 0, 0, this);
         balls.clear();
         balls.add(ball);
         perks.clear();
         activePerks.clear();
    }
    
    /**
     * initializing the display window of the game
     */
    private void init() {
         display = new Display(title, getWidth(), getHeight());  
         Assets.init();
         bar = new Bar(getWidth() / 2 - 50, getHeight() - 100, 100, 25, this);
         Ball ball = new Ball(getWidth() / 2 - 10, getHeight() - 120, 20, 20, 0, 0, this);
         balls.add(ball);
         bricks = new ArrayList<>();
         int width_brick = getWidth() / 10 - 6;
         int height_brick = getHeight() / 3 / 5  - 10;
         for (int i = 0; i < 10; i++) {
             for (int j = 0; j < 5; j++) {
                 int perk = (int)(Math.random() * 4.0); // 33% chance of having a perk
                 if(perk > 0){
                     perk = (int)(Math.random() * 10.0) + 1; // there are 7 different perks
                 }
                 Brick brick = new Brick(i * (width_brick + 3) + 15 , 
                         j * (height_brick + 5) + 15 , width_brick, height_brick, 1, this, 8);
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
                if(!death){
                    tick();
                }
                else{
                    keyManager.tick();
                    if(getKeyManager().isRestart()){
                        setStarted(false);
                        setDeath(false);
                        restart();
                    }
                }
                render();
                delta --;
            }
        }
        System.out.println("your score was: " + score); // this must be displayed instead
        render(); // in case we want to display a losing or winning picture
        // stop(); we should use something like thread.sleep() and then close
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }
    
    private void multiBall(){
        if(balls.size() > 0){
            Ball refBall = balls.get(0);
            Ball ballA = new Ball(refBall);
            Ball ballB = new Ball(refBall);
            Ball ballC = new Ball(refBall);
            ballA.setSpeedX(ballA.getSpeedX() * -1);
            ballB.setSpeedY(ballB.getSpeedY() * -1);
            ballC.setSpeedX(ballC.getSpeedX() * -1);
            ballC.setSpeedY(ballC.getSpeedY() * -1);
            balls.add(ballA);
            balls.add(ballB);
            balls.add(ballC);
        }
    }
    
    private void enablePerk(int ID){
        switch(ID){
            case 1:
                System.out.println("Triggered larger bar");
                bar.setX(bar.getX() - bar.getWidth() / 2);
                bar.setWidth(bar.getWidth() * 2);
                break;
            case 2:
                System.out.println("Triggered multi ball");
                multiBall();
                break;
            case 3:
                System.out.println("Triggered lower bound (immunity)");
                break;
            case 4:
                System.out.println("Triggered hold ball"); // messes with "started"
                break;
            case 5:
                System.out.println("Triggered power up");
                break;
            case 6:
                System.out.println("Triggered bullets");
                break;
            case 7:
                System.out.println("Triggered invisible");
                for(int i = 0; i < balls.size(); i++){
                    Ball ball = balls.get(i);
                    ball.setInvisible(true);
                }
                break;
            case 8:
                System.out.println("Triggered bloody fast");
                for(int i = 0; i < balls.size(); i++){
                    Ball ball = balls.get(i);
                    ball.setSpeedX(ball.getSpeedX() * 2);
                    ball.setSpeedY(ball.getSpeedY() * 2);
                }
                break;
            case 9:
                System.out.println("Triggered small bar");
                bar.setX(bar.getX() + bar.getWidth() / 4);
                bar.setWidth(bar.getWidth() / 2);
                break;
            case 10:
                System.out.println("Triggered the Jetty !!!");
                break;
        }
    }
    
    private void disablePerk(int ID){
        switch(ID){
            case 1:
                System.out.println("Disabling larger bar");
                bar.setX(bar.getX() + bar.getWidth() / 4);
                bar.setWidth(bar.getWidth() / 2);
                break;
            case 3:
                System.out.println("Disabling lower bound (immunity)");
                break;
            case 6:
                System.out.println("Disabling bullets");
                break;
            case 7:
                System.out.println("Disabling invisible");
                for(int i = 0; i < balls.size(); i++){
                    Ball ball = balls.get(i);
                    ball.setInvisible(false);
                }
                break;
            case 8:
                System.out.println("Disabling bloody fast");
                for(int i = 0; i < balls.size(); i++){
                    Ball ball = balls.get(i);
                    if(ball.getSpeedX() > 1){
                        ball.setSpeedX(ball.getSpeedX() / 2);
                        ball.setSpeedY(ball.getSpeedY() / 2);
                    }
                }
                break;
            case 9:
                System.out.println("Disabling small bar");
                bar.setX(bar.getX() - bar.getWidth() / 2);
                bar.setWidth(bar.getWidth() * 2);
                break;
            case 10:
                System.out.println("Disabling the Jetty !!!");
                break;
        }
    }
    
    private void tick() {
        keyManager.tick();
        if(getKeyManager().isPause()){
            getKeyManager().setPause(false);
            paused = !paused;
        }
        if(!paused){
            // if space and game has not started
            if (this.getKeyManager().space && !this.isStarted()) {
                this.setStarted(true);
                balls.get(0).setSpeedX(3);
                balls.get(0).setSpeedY(-3);
            }
            // moving bar
            bar.tick();
            // if game has started
            if (isStarted()) {
                // ticking the balls
                for(int i = 0; i < balls.size(); i++){
                    balls.get(i).tick();
                    if(isDeath() && balls.size() == 1){
                        balls.remove(0);
                        setLives(getLives() - 1);
                        if(getLives() == 0){
                            setRunning(false);
                        }
                    }
                    else if(isDeath()){
                        setDeath(false); // there are more balls still alive
                        balls.remove(i);
                        i--;
                    }
                }
            } else {
                // moving the ball based on the bar
                balls.get(0).setX(bar.getX() + bar.getWidth() / 2 - balls.get(0).getWidth() / 2);
            }

            // check collision bricks versus ball
            for (int i = 0; i < bricks.size(); i++) {
                Brick brick = (Brick) bricks.get(i);
                for(int j = 0; j < balls.size(); j++){
                    Ball ball = (Ball) balls.get(j);
                    if (ball.intersects(brick) && brick.getPower() > 1) {
                        // will not be destroyed
                        score += 100; // damage score
                        brick.setPower(brick.getPower()-1);
                        ball.setSpeedY(ball.getSpeedY() * -1);
                    }
                    else if(ball.intersects(brick)){
                        score += 150; // more points on destroying a brick
                        if(brick.getHiddenPerk() > 0){
                            // release the perk
                            Perk releasedPerk = new Perk(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight(), brick.getHiddenPerk());
                            perks.add(releasedPerk);
                        }
                        bricks.remove(brick);
                        ball.setSpeedY(ball.getSpeedY() * -1);
                        // avoid collision with two at the time (NOT WORKING) contact me through facebook to see why
                        break;
                    }
                }
            }

            // check collision ball versus bar and modify speed accordingly
            // the x-speed and y-speed of the ball can never be 0
            for(int i = 0; i < balls.size(); i++){
                Ball ball = (Ball) balls.get(i);
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
            
            // tick all the perks and check for their triggers
            for(int i = 0; i < perks.size(); i++){
                Perk perk = perks.get(i);
                perk.tick();
                if(bar.intersects(perk)){
                    // pick it up (triggering its power)
                    if(perk.getID() != 2 && perk.getID() != 4 && perk.getID() != 5){
                        enablePerk(perk.getID());
                        activePerks.add(new Pair<Integer, Integer>(perk.getID(), 0)); // for a while
                    }
                    else{
                        enablePerk(perk.getID()); // just once
                    }
                    // delete this perk
                    perks.remove(i);
                    i--;
                }
                else if(perk.getY() + perk.getHeight() > getHeight()){
                    // delete this perk
                    perks.remove(i);
                    i--;
                }
            }
            
            // add time to the enaction of all active perks
            for(int i = 0; i < activePerks.size(); i++){
                activePerks.set(i, new Pair<Integer, Integer>(activePerks.get(i).getKey(), activePerks.get(i).getValue() + 1));
                // disable after 250 frames
                if(activePerks.get(i).getValue() == 250){
                    disablePerk(activePerks.get(i).getKey());
                    activePerks.remove(i);
                    i--;
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
            // render all the balls
            for(int i = 0; i < balls.size(); i++){
                balls.get(i).render(g);
            }
            // render all the bricks
            for (Brick brick : bricks) {
                brick.render(g);
            }
            // render all the perks
            for(int i = 0; i < perks.size(); i++){
                perks.get(i).render(g);
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
