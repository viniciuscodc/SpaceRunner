/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacerunner;

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
    
 
    public final int SPACESHIP_HEIGHT = 35;
    public final int SPACESHIP_WIDTH = (int) (35*1.1354);
    
    private int timer;
    private int velocityY;
    private int speed = 1;
    
    private BufferedImage spaceshipImage;
    private BufferedImage boostImage;
    
    Spaceship(int x , int y) {
        this.x = x;
        this.y = y;
        imageRead();
    }
    
    Spaceship() {
    }
    
    public void setTime(int time){
        timer = time;
    }
       
   public void imageRead(){
        try {
            spaceshipImage = ImageIO.read(new File("images/spaceship.png"));
        } catch (IOException ex) {
            Logger.getLogger(Spaceship.class.getName()).log(Level.SEVERE, null, ex);
        }  
        
        try {
            boostImage = ImageIO.read(new File("images/boost.png"));
        } catch (IOException ex) {
            Logger.getLogger(Spaceship.class.getName()).log(Level.SEVERE, null, ex);
        }
        
   }
    
    public void draw(Graphics g){
        
        //draw spaceship
        g.drawImage(spaceshipImage, x, y, x+SPACESHIP_WIDTH, y+SPACESHIP_HEIGHT, 0, 0, 
        spaceshipImage.getWidth(), spaceshipImage.getHeight(), null);
        
        //draw boost
        if(velocityY <0){
            g.drawImage(boostImage, x+12, y+SPACESHIP_HEIGHT, x+SPACESHIP_WIDTH-12, y+SPACESHIP_HEIGHT*2-15, 0, 0, 
            boostImage.getWidth(), boostImage.getHeight(), null);
        }
        //hitbox draw g.drawRect(x, y, 35, (int) (35*1.1354)-5);   
    }
    
    public void move(double dt){
        if(timer>=25){
        y = (int) (y +velocityY*dt*100); 
        }
    }
       
    public void switchDirection(int direction){  
       velocityY = speed *direction;
    } 
}
