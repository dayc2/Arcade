package com.FlappyBird;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird implements ActionListener, KeyListener {
	
    JFrame jframe;
    JPanel jPanel;
    Timer timer;
    ArrayList<Rectangle> pipes;
    int ticks, yMotion, score;
    int yVelocity;
    boolean gameOver, gameStarted;

    public static final int WIDTH = 800, HEIGHT = 600;

    public FlappyBird(JFrame frame) {
        jframe = frame;
        jPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                draw(g);
            }
        };
        timer = new Timer(20, this);

        jframe.setTitle("Flappy Bird");
        jframe.setSize(WIDTH, HEIGHT);
        jframe.setResizable(false);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(jPanel);
        jframe.addKeyListener(this);

        pipes = new ArrayList<>();
        addPipe(true);
        addPipe(true);
        addPipe(true);
        addPipe(true);

        timer.start();
        jframe.setVisible(true);
    }

    public void addPipe(boolean start) {
        int space = 300;
        int width = 100;
        int height = 50 + new Random().nextInt(300);
        if (start) {
            pipes.add(new Rectangle(WIDTH + width + pipes.size() * 300, HEIGHT - height - 150, width, height));
            pipes.add(new Rectangle(WIDTH + width + (pipes.size() - 1) * 300, 0, width, HEIGHT - height - space));
        } else {
            pipes.add(new Rectangle(pipes.get(pipes.size() - 1).x + 600, HEIGHT - height - 150, width, height));
            pipes.add(new Rectangle(pipes.get(pipes.size() - 1).x, 0, width, HEIGHT - height - space));
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.cyan);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.orange);
        g.fillRect(0, HEIGHT - 150, WIDTH, 150);

        g.setColor(Color.green);
        g.fillRect(0, HEIGHT - 150, WIDTH, 20);

        g.setColor(Color.yellow);
        g.fillOval(WIDTH / 2 - 10, HEIGHT - 150 + yMotion - 10, 20, 20);
        
        g.setColor(Color.white);
        g.fillOval(WIDTH / 2 + 4, HEIGHT - 150 + yMotion - 7, 6, 6);
        g.setColor(Color.black);
        g.fillOval(WIDTH / 2 + 6, HEIGHT - 150 + yMotion - 5, 2, 2);

        
        g.setColor(new Color(255, 215, 0)); 
        g.fillArc(WIDTH / 2 - 16, HEIGHT - 150 + yMotion - 2, 20, 10, 0, 180);

        for (Rectangle pipe : pipes) {
            g.setColor(Color.green.darker());
            g.fillRect(pipe.x, pipe.y, pipe.width, pipe.height);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 100));

        Font font = new Font("Arial", Font.BOLD, 30);

        if (!gameStarted) {
            g.setFont(font);
            g.drawString("Press SPACE to Start", 50, HEIGHT / 2 - 100);
        }

        if (gameOver) {
            g.setFont(font);
            g.drawString("Game Over", 200, HEIGHT / 2 - 200);
            g.drawString("Press SPACE to Restart", 200, HEIGHT - 450);
        }

        if (!gameOver && gameStarted) {
            g.drawString(String.valueOf(score), WIDTH / 2 - 50, 100);
        }
    }



    public void jump() {
	    if (gameOver) {
	        pipes.clear();
	        yMotion = 0;
	        score = 0;
	        addPipe(true);
	        addPipe(true);
	        addPipe(true);
	        addPipe(true);
	        gameOver = false;
	    }
	
	    if (!gameStarted) {
	        gameStarted = true;
	    }
	
	    if (yMotion > 0) {
	        yMotion = 0;
	    }
	
	    // yMotion -= 55;
        yVelocity = 15;
	}

    public void actionPerformed(ActionEvent e) {
        int speed = 10;
        ticks++;

        if (gameStarted) {
            for (int i = 0; i < pipes.size(); i++) {
                Rectangle pipe = pipes.get(i);
                pipe.x -= speed;
            }

            if (ticks % 1 == 0 && yMotion < 15) {
                yMotion -= yVelocity;
                yVelocity -= 2;
                if(yVelocity < -15) {
                    yVelocity = -15;
                }
            }

            for (int i = 0; i < pipes.size(); i++) {
                Rectangle pipe = pipes.get(i);

                if (pipe.x + pipe.width < 0) {
                    pipes.remove(pipe);

                    if (pipe.y == 0) {
                        addPipe(false);
                    }
                }

                
                if (!gameOver && pipe.x + pipe.width == WIDTH / 2 - 10 && pipe.y == 0) {
                    score++;
                }
            }

            Rectangle bird = new Rectangle(WIDTH / 2 - 10, HEIGHT - 150 + yMotion - 10, 20, 20);

            for (Rectangle pipe : pipes) {
                if (pipe.intersects(bird)) {
                    gameOver = true;
                    bird.x = pipe.x - bird.width;
                }
            }

            
            if (bird.y < 0) {
                gameOver = true;
            }

            
            if (bird.y + yMotion >= HEIGHT - 175) {
                gameOver = true;
            }

            if(gameOver && gameStarted) {
                gameStarted = false;
                FlappyBirdGame.endGame(score);
                jframe.dispose();
            }
        }

        jPanel.repaint();
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            jump();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
