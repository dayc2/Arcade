package com;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.border.*;

// Better looking button
public class JPanelButton extends JPanel{
    private Color backgroundColor;
    private Color pressedColor;
    private int cornerRadius = 15;
    private boolean pressed = false;
    private JLabel text;

    public JPanelButton(String text) {
        super();
        this.text = new JLabel(text);
        add(this.text);
        setLayout(new GridBagLayout());
        setOpaque(false);
        addMouseListener();
        setText(text);
    }

    public JPanelButton(String text, Color bgColor) {
        super();
        this.text = new JLabel(text);
        add(this.text);
        backgroundColor = bgColor;
        setLayout(new GridBagLayout());
        setOpaque(false);
        addMouseListener();
        setText(text);
    }

    public JPanelButton(int radius, Color bgColor) {
        super();
        this.text = new JLabel();
        add(this.text);
        setLayout(new GridBagLayout());
        cornerRadius = radius;
        backgroundColor = bgColor;
        setOpaque(false);
        addMouseListener();
    }

    public JPanelButton(Color bgColor) {
        super();
        this.text = new JLabel();
        add(this.text);
        setLayout(new GridBagLayout());
        backgroundColor = bgColor;
        setOpaque(false);
        addMouseListener();
    }

    public JPanelButton(){
        super();
        this.text = new JLabel();
        add(this.text);
        setLayout(new GridBagLayout());
        setOpaque(false);
        addMouseListener();
    }

    public String getText(){
        return text.getText();
    }

    public void setText(String text){
        this.text.setText(text);
    }

    public void setTextFont(java.awt.Font font){
        text.setFont(font);
    }

    public void setPressedColor(Color color){
        pressedColor = color;
    }

    public void setColor(Color color){
        backgroundColor = color;
    }

    public void setRadius(int radius){
        cornerRadius = radius;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Draws the rounded panel with borders.
        if (backgroundColor != null) {
            graphics.setColor(backgroundColor);
        } else {
            graphics.setColor(getBackground());
        }
        if(pressed){
            if(pressedColor == null){
                if(backgroundColor == null)
                    graphics.setColor(getBackground().darker());
                else
                    graphics.setColor(backgroundColor.darker());   
            }else{
                graphics.setColor(pressedColor);
            }
        }
        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height); //paint background
        graphics.setColor(getForeground());
        graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height); //paint border
    }

    void addMouseListener(){
        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                pressed = true;
                repaint(0);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                pressed = false;
                repaint(0);
                onClick();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    // 
    public void onClick(){System.out.println("OVERRIDE THIS");};
}
