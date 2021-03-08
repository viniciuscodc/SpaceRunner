/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacerunner;

import java.awt.*;

/**
 *
 * @author Vinicius
 */
public class Score extends Rectangle {
    
    private String scoreText;
    
    Score(int x, int y , String scoreText){
        this.x = x;
        this.y = y; 
        this.scoreText = scoreText;
    }
    
    public void draw(Graphics g){ 
        g.setFont(new Font("SansSerif", Font.BOLD, 27));
        g.drawString(scoreText, x, y);  
    }
    
}
