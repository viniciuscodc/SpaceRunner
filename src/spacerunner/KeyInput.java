
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacerunner;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author Vinicius
 */
public class KeyInput extends KeyAdapter {
    
    private boolean up = false;
    private boolean down = false;
    
    private final Spaceship spaceship;
    
    KeyInput(Spaceship spship){
        spaceship = spship;
    }
   
     
    @Override
    public void keyPressed(KeyEvent e){
        
        int key = e.getKeyCode();
        switch(key){
        
            case KeyEvent.VK_W:
                up = true;
                spaceship.switchDirection(-1);
                break;

            case KeyEvent.VK_S:
                down = true;
                spaceship.switchDirection(1);
                break;    

        }
    }
    
    public void keyReleased(KeyEvent e){
       
        int key = e.getKeyCode();
        switch(key){
        
            case KeyEvent.VK_W:
                up = false;
                break;
                
            case KeyEvent.VK_S:
                down = false;
                break;    
        }
        if(!up && !down){
            spaceship.switchDirection(0);
        }  
    }
}
