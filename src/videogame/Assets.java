/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.image.BufferedImage;

/**
 *
 * @author antoniomejorado
 */
public class Assets {
    public static BufferedImage background; // to store background image
    public static BufferedImage bar;     // to store the bar image
    public static BufferedImage ball;     // to store the ball image
    public static BufferedImage brick1;     // to store the ball image
    public static BufferedImage brick2;     // to store the ball image
    public static BufferedImage brick3;     // to store the ball image

    /**
     * initializing the images of the game
     */
    public static void init() {
        background = ImageLoader.loadImage("/images/background.jpg");
        bar = ImageLoader.loadImage("/images/bar.png");
        ball = ImageLoader.loadImage("/images/ball.png");
        brick1 = ImageLoader.loadImage("/images/brick1.png");
        brick2 = ImageLoader.loadImage("/images/brick2.png");
        brick3 = ImageLoader.loadImage("/images/brick3.png");
    }
    
}
