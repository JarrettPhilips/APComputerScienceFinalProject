import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File.*;
import java.io.*;
import javax.swing.UIManager;
import java.awt.Font;
import javax.imageio.ImageIO;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory.*;
import javax.swing.border.*;
import java.awt.GridBagLayout;

//This class gives a popup error message to the user
public class Error{
    public Error(String error){
        JFrame frame = new JFrame("Error");
        JPanel panel = new JPanel();
        
        JLabel errorLabel = new JLabel(error);
        errorLabel.setFont(Initiate.ui.bodyFont);
        errorLabel.setForeground(Initiate.ui.textColor);
        
        panel.setBackground(Initiate.ui.titleColor);
        
        frame.setSize(400, 200);
        frame.setVisible(true);
        
        panel.add(errorLabel);
        frame.add(panel);
    }
    
    //A small testing main
    public static void main(String[] args){
        Error error = new Error("test");
    }
}