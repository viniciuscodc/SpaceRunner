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
public class Meteor extends Rectangle {
    
    int velocityX = 1;
    
    BufferedImage meteorImage;
    GameFrame gameframe;
    public final int METEOR_SIZE = 20;
    int id;
    
    Meteor(int x , int y){
        this.x = x;
        this.y = y;
        imageRead();
    }
  
    Meteor(){
    }
    
    public void imageRead(){
        try {
            meteorImage = ImageIO.read(new File("images/meteor.png"));
        } catch (IOException ex) {
            Logger.getLogger(Meteor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
 
    public void draw(Graphics g){
        g.setColor(Color.white);
        g.drawImage(meteorImage, x, y, x+METEOR_SIZE, y+METEOR_SIZE, 0,0,
        meteorImage.getWidth(),meteorImage.getHeight(), null); 
        //hitbox draw g.drawRect(x, y,METEOR_SIZE, METEOR_SIZE);
       
    }
    
     public void move(double dt){
        x = (int) (x +velocityX*dt*100);   
    } 
}
