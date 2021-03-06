/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacerunner;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Vinicius
 */
public class Spaceship extends Rectangle {
    
    int velocityY;
    int speed = 1;
    int x1;
    int x2;
    int y1;
    int y2;
    public final int SPACESHIP_HEIGHT = 35;
    public final int SPACESHIP_WIDTH = (int) (35*1.1354);
    
    BufferedImage spaceshipImage;
    
    Spaceship(int x , int y) {
        this.x = x;
        this.y = y;
        imageRead();
    }
    
    Spaceship() {
    }
    
   public void imageRead(){
        try {
            spaceshipImage = ImageIO.read(new File("images/spaceship.png"));
        } catch (IOException ex) {
            Logger.getLogger(Spaceship.class.getName()).log(Level.SEVERE, null, ex);
        }  
   }
    
    public void draw(Graphics g){
        g.drawImage(spaceshipImage, x, y, x+SPACESHIP_WIDTH, y+SPACESHIP_HEIGHT, 0, 0, 
        spaceshipImage.getWidth(), spaceshipImage.getHeight(), null);
        g.drawRect(x, y, 35, (int) (35*1.1354)-5);   
    }
    
    public void move(double dt){
        y = (int) (y +velocityY*dt*100);  
    }
       
    public void switchDirection(int direction){
       velocityY = speed *direction;
    } 
}
