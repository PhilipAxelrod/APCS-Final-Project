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
    JTextArea displayArea;
    JTextField typingArea;
    static final String newline = System.getProperty("line.separator");
    public boolean arUp, arRight, arLeft, arDown;

    private static void GUI() 
    {
        KeyEventDemo frame = new KeyEventDemo("KeyEventDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        frame.addComponentsToPane();
        frame.pack();
        frame.setVisible(true);
    }
     
    private void addComponentsToPane() 
    {
         
        typingArea = new JTextField(20);
        typingArea.addKeyListener(this);
         
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setPreferredSize(new Dimension(375, 125));
         
        getContentPane().add(typingArea, BorderLayout.PAGE_START);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
     
    public KeyEventDemo(String name) 
    {
        super(name);
    }
     

    public void keyTyped(KeyEvent e) 
    {
        
    }
     
    public void keyPressed(KeyEvent e) 
    {
        displayInfo(e, "KEY PRESSED: ");
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
     
    public void actionPerformed(ActionEvent e) 
    {
        displayArea.setText("");
        typingArea.setText("");
        typingArea.requestFocusInWindow();
    }

    private void displayInfo(KeyEvent e, String keyStatus)
    {
        int id = e.getID();
        String keyString;
        if (id == KeyEvent.KEY_TYPED)
        {
            char c = e.getKeyChar();
            keyString = "key character = '" + c + "'";
        } 
        else 
        {
            int keyCode = e.getKeyCode();
           
            keyString = " ("+ KeyEvent.getKeyText(keyCode)+ ")"+arRight;
        }
         
        
         
        String actionString = "action key? ";
        if (e.isActionKey()) 
        {
            actionString += "YES";
        } 
        else 
        {
            actionString += "NO";
        }
         
        String locationString = "key location: ";
        int location = e.getKeyLocation();
        if (location == KeyEvent.KEY_LOCATION_STANDARD) 
        {
            locationString += "standard";
        } 
        else if (location == KeyEvent.KEY_LOCATION_LEFT)
        {
            locationString += "left";
            arLeft=true;
        } 
        else if (location == KeyEvent.KEY_LOCATION_RIGHT) 
        {
            locationString += "right";
            arRight=true;
        } 
        else if (location == KeyEvent.KEY_LOCATION_NUMPAD)
        {
            locationString += "numpad";
        } 
        else 
        {
            locationString += "unknown";
        }
         
        displayArea.append("test"+keyString+newline);
        displayArea.setCaretPosition(displayArea.getDocument().getLength());

    }
    public static void main(String[] args) 
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
                GUI();
            }
        } );
    }
}