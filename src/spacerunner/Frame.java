/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacerunner;

import java.io.IOException;
import javax.swing.*;

/**
 *
 * @author Vinicius
 */
public class Frame extends JFrame {
    
    GameFrame gameframe;
;
    
    Frame() throws IOException{
        
       gameframe  = new GameFrame();
       
        this.add(gameframe);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("SpaceRunner");
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }
}
