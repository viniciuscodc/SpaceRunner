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
import javax.sound.sampled.*;

/**
 *
 * @author Vinicius
 */

public class GameFrame extends JPanel implements Runnable {
    
    final int WINDOW_HEIGHT = 700;
    public final int WINDOW_WIDTH = 1000;
    
    private boolean running = false;   
    private ArrayList<Meteor> meteors;
    private int[] positionsYArray = new int[22];
    private int time = 25; //time delay for respawn
    private int scoreCount = 0;
     
    private Spaceship spaceship;
    private Meteor meteor;
    private Thread gameThread;
    private BufferedImage backgroundImage;
    private Score score;
     
    GameFrame(){
        
        this.setPreferredSize(new Dimension (WINDOW_WIDTH,WINDOW_HEIGHT));
        this.setFocusable(true);
        meteorAdd();  
        spaceshipAdd();
        this.addKeyListener(new KeyInput (spaceship));
        startGame(); 
        imageRead();    
        songPlay();
    }   

    public void songPlay(){
        try{
            File file = new File("audio/song.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(20);
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
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
    
    public void scoreCount(){
        int scoreX = (int) (WINDOW_WIDTH*0.10);
        int scoreY = (int) (WINDOW_HEIGHT*0.99);
        score = new Score(scoreX,scoreY, "Score:"+Integer.toString(scoreCount));
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
        score.draw(g);
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
      
        //update for each meteor
        for (int j =0; j < 80;j++){
        meteors.get(j).move(dt);
        }
        windowCollision();
        meteorCollision();
        scoreCount();
    }
    
    public void meteorCollision(){
        
        //reset spaceship position
        for(int j=0; j<80;j++){
            if(meteors.get(j).x+meteor.METEOR_SIZE > spaceship.x && 
            meteors.get(j).x+meteor.METEOR_SIZE < spaceship.x+spaceship.SPACESHIP_WIDTH &&
            meteors.get(j).y+meteor.METEOR_SIZE > spaceship.y &&      
            meteors.get(j).y < spaceship.y+spaceship.SPACESHIP_HEIGHT-5){
                spaceship.y = WINDOW_HEIGHT-spaceship.SPACESHIP_HEIGHT;  
                time =0;
            }
        }
        //time delay for respawn
        spaceship.setTime(time);
        time++;
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
            scoreCount = scoreCount+1;
        }
    } 

    public void meteorAdd(){
        
        meteors = new ArrayList<Meteor>(); 
        meteor = new Meteor();
        
        //possible y rows for meteor spawn
        for (int i =0; i <22;i++){
            positionsYArray[i] = 0+30*i;
        }
        
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
        spaceship = new Spaceship();
        spaceship = new Spaceship(WINDOW_WIDTH/2,WINDOW_HEIGHT-spaceship.SPACESHIP_HEIGHT);
    }   
}
