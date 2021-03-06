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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Vinicius
 */

public class GameFrame extends JPanel implements Runnable {
    
    Spaceship spaceship;
    Meteor meteor;
    Thread gameThread;
    BufferedImage backgroundImage;
    boolean running = false;   
    final int WINDOW_HEIGHT = 700;
    public final int WINDOW_WIDTH = 1000;
    ArrayList<Meteor> meteors;
    int[] positionsYArray = new int[22];
    
    GameFrame(){
        this.setPreferredSize(new Dimension (WINDOW_WIDTH,WINDOW_HEIGHT));
        this.setFocusable(true);
   
        meteors = new ArrayList<Meteor>(); 
        meteor = new Meteor();
        
        //possible y rows for meteor spawn
        for (int i =0; i <22;i++){
            positionsYArray[i] = 0+30*i;
        }
        
        meteorAdd();  
        spaceship = new Spaceship();
        spaceshipAdd();
        this.addKeyListener(new KeyInput (spaceship));
        startGame(); 
        imageRead();          
    }  
    
    public void imageRead(){
         try {   
            backgroundImage = ImageIO.read(new File("images/space.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public int findDistance(int x1, int y1, int x2, int y2){
        int distX = x2-x1;
        int distY = y2-y1;
        return (int) Math.ceil(Math.sqrt(Math.pow(distX, 2)+Math.pow(distY, 2)));
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g); //clear screen
        
        //draw background image
        g.drawImage(backgroundImage, 0, 0,WINDOW_WIDTH, WINDOW_HEIGHT, 0, 
        0,backgroundImage.getWidth(),backgroundImage.getHeight(), null);
        
        //meteors draw
        for (int j =0; j <80;j++){
        meteors.get(j).draw(g);
        }
        
        spaceship.draw(g);   
    }
    
    public void startGame(){
        gameThread = new Thread(this);
        gameThread.start();
        running = true;
    }
    
    public void run(){
        
        final int FPS = 60;
        final double OPTIMAL_TIME = 1000000000/FPS;
        double deltaTime = 0;
        
        long startTime = System.nanoTime();
        
        while(running){
            
            long currentTime = System.nanoTime();
        
            deltaTime = deltaTime + (currentTime-startTime);
            startTime = currentTime;

            if(deltaTime >= OPTIMAL_TIME){
                update(deltaTime/1000000000);
                repaint();
                deltaTime = deltaTime - OPTIMAL_TIME;
            }
        
        }
    }
    
    public void update(double dt){
        spaceship.move(dt);
        for (int j =0; j < 80;j++){
        meteors.get(j).move(dt);
        }
        windowCollision();
        meteorCollision();
    }
    
    public void meteorCollision(){
        
        //reset spaceship position
        for(int j=0; j<80;j++){
            if(meteors.get(j).x+meteor.METEOR_SIZE > spaceship.x && 
            meteors.get(j).x+meteor.METEOR_SIZE < spaceship.x+spaceship.SPACESHIP_WIDTH &&
            meteors.get(j).y+meteor.METEOR_SIZE > spaceship.y &&      
            meteors.get(j).y < spaceship.y+spaceship.SPACESHIP_HEIGHT-5){
                spaceship.y = WINDOW_HEIGHT-spaceship.SPACESHIP_HEIGHT;  
            }
        }
      
    }
    public void windowCollision(){
        
        //meteor pass screen
        for (int i =0; i < 80;i++){
            if (meteors.get(i).x>WINDOW_WIDTH && WINDOW_WIDTH<1020){
                meteors.get(i).x = -30;
            }
        }
        
        //spaceship finish race
        if(spaceship.y<0-spaceship.SPACESHIP_HEIGHT){
            spaceship.y = WINDOW_HEIGHT-spaceship.SPACESHIP_HEIGHT;
        
        }
    } 

    public void meteorAdd(){
        
            //spawn meteor random position
            for (int j =0; j <80;j++){
                int posX = (int) (Math.random() * (WINDOW_WIDTH - 0 +1) + 0);
                int posY = (int) (Math.random() * (positionsYArray[21] - positionsYArray[0] +1) + positionsYArray[0]);    
                
                if(j !=0){ // check overlap and calculate again
                  for (int k =0;k<meteors.size();k++){
                      if(findDistance(posX,posY,meteors.get(k).x,meteors.get(k).y) <25){
                            posX = (int) (Math.random() * (WINDOW_WIDTH - 0 +1) + 0);
                            posY = (int) (Math.random() * (positionsYArray[21] - positionsYArray[0] +1) + positionsYArray[0]); 
                            k= -1;
                      }
                  } 
                }
                meteors.add(new Meteor(posX,posY));
            }
    }
    
      public void spaceshipAdd(){
        spaceship = new Spaceship(WINDOW_WIDTH/2,WINDOW_HEIGHT-spaceship.SPACESHIP_HEIGHT);
    }   
}
