package keyboardInputs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
 
public class KeyEventDemo extends JFrame
        implements KeyListener,
        ActionListener
{
    public boolean arUp, arRight, arLeft, arDown;



    public void keyTyped(KeyEvent e) 
    {
        
    }
     
    public void keyPressed(KeyEvent e) 
    {
        int keyCode = e.getKeyCode();
        if (KeyEvent.getKeyText(keyCode).equals( "Left" ))
        {
            arLeft=true;
        }
        if (KeyEvent.getKeyText(keyCode).equals( "Up" ))
        {
            arUp=true;
        }
        if (KeyEvent.getKeyText(keyCode).equals( "Right" ))
        {
            arRight=true;
        }
        if (KeyEvent.getKeyText(keyCode).equals( "Down" ))
        {
            arDown=true;
        }
    }
     
    public void keyReleased(KeyEvent e) 
    {
        int keyCode = e.getKeyCode();
        if (KeyEvent.getKeyText(keyCode).equals( "Left" ))
        {
            arLeft=false;
        }
        if (KeyEvent.getKeyText(keyCode).equals( "Up" ))
        {
            arUp=false;
        }
        if (KeyEvent.getKeyText(keyCode).equals( "Right" ))
        {
            arRight=false;
        }
        if (KeyEvent.getKeyText(keyCode).equals( "Down" ))
        {
            arDown=false;
        }
    }
     
    public boolean getRight()
    {
        return arRight;
    }
    public boolean getLeft()
    {
        return arLeft;
    }
    public boolean getUp()
    {
        return arUp;
    }
    public boolean getDown()
    {
        return arDown;
    }
   

    
    public static void main(String[] args) 
    {
       
    }

    @Override
    public void actionPerformed( ActionEvent arg0 )
    {
        // TODO Auto-generated method stub
        
    }

}