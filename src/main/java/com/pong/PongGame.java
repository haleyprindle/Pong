//
//  Class author:  Haley Prindle
//  Date created:  11/21/25
//  General description: Creates a game of Pong for the user to play with an AI opponent.
//


package com.pong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class PongGame extends JPanel implements MouseMotionListener {
    static int width = 640; // this is the amount of pixels to the right side of the screen
    static int height = 480; // this is the amount of pixels to the top of the screen.
    private int userMouseY;
    private Paddle aiPaddle;
    private int playerScore;
    private int aiScore;
    private Ball ball;
    
    // step 1 add any other private variables you may need to play the game.
    private Paddle userPaddle;
    private Wall wall;
    private SlowDown slowDown;
    private Speedup speedUp;

    public PongGame() {

        aiPaddle = new Paddle(610, 240, 50, 9, Color.WHITE);
        JLabel pScore = new JLabel("0");
        JLabel aiScore = new JLabel("0");
        pScore.setBounds(280, 440, 20, 20);
        aiScore.setBounds(360, 440, 20, 20);
        pScore.setVisible(true);
        aiScore.setVisible(true);
        userMouseY = 0;
        addMouseMotionListener(this);
        ball = new Ball(320, 200, 10, 3, Color.RED, 10);

        userPaddle = new Paddle(20, 100, 50, 9, Color.BLUE);
        wall = new Wall (300, 250, 80, 60, Color.PINK);
        slowDown = new SlowDown (450, 350, 70, 20);
        speedUp = new Speedup (200, 80, 70, 20);
        //create any other objects necessary to play the game.

    }

    // precondition: None
    // postcondition: returns playerScore
    public int getPlayerScore() {
        return playerScore;
    }

    // precondition: None
    // postcondition: returns aiScore
    public int getAiScore() {
        return aiScore;
    }

    //precondition: All visual components are initialized, non-null, objects 
    //postcondition: A frame of the game is drawn onto the screen.
    public void paintComponent(Graphics g) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.WHITE);
        g.drawString("The Score is User:" + playerScore + " vs Ai:" + aiScore, 240, 20);
        ball.draw(g);
        aiPaddle.draw(g);
        userPaddle.draw(g);
        wall.draw(g);
        speedUp.draw(g);
        slowDown.draw(g);
        
        //call the "draw" function of any visual component you'd like to show up on the screen

    }

    // precondition: all required visual components are intialized to non-null
    // values
    // postcondition: one frame of the game is "played"
    public void gameLogic() {
        //add commands here to make the game play propperly
        ball.moveBall();
        aiPaddle.moveY(ball.getY());
        userPaddle.moveY(userMouseY);
        if (aiPaddle.isTouching(ball)) {
           ball.reverseX();
        }
        if (userPaddle.isTouching(ball)) {
           ball.reverseX();
        }
        ball.bounceOffwalls(0,450);
        
        pointScored();
       

        if(wall.isTouching(ball))
            ball.reverseX();

        if(slowDown.isTouching(ball))
        {
            if(ball.getChangeX()>0){
           ball.setChangeX(ball.getChangeX()-.1);
            ball.setChangey(ball.getChangeY()-.1); 
            }
            else
            {
              ball.setChangeX(ball.getChangeX()+.1);
            ball.setChangey(ball.getChangeY()+.1);  
            }
        }
            
        if(speedUp.isTouching(ball)){

           if(ball.getChangeX()>0){
            ball.setChangeX(ball.getChangeX()+.2);
            ball.setChangey(ball.getChangeY()+.2);  
           
            }
            else
            {
              ball.setChangeX(ball.getChangeX()-.2);
            ball.setChangey(ball.getChangeY()-.2); 
            }
        }
         
          
        



            

        
    }

    // precondition: ball is a non-null object that exists in the world
    // postcondition: determines if either ai or the player score needs to be
    // updated and re-sets the ball
    // the player scores if the ball moves off the right edge of the screen (640
    // pixels) and the ai scores
    // if the ball goes off the left edge (0)
    public void pointScored() {
        
        if (ball.getX()<= 0 )
            aiScore+=1;
        else if (ball.getX()>=640)
            playerScore+=1;  
        if (ball.getX()<= 0 || ball.getX() >= 640)
        {
            ball.setX(320);
            ball.sety(200);
            

        }
        
    }

    // you do not need to edit the below methods, but please do not remove them as
    // they are required for the program to run.
    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        userMouseY = e.getY();
    }

}
