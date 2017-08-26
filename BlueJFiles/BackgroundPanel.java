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

public class BackgroundPanel extends JPanel{
    Image image;

    //Constructor for the Image Panel
    public BackgroundPanel(){
        try{
            image = ImageIO.read(new File("background.jpg"));
        } catch (Exception e){
            Error error = new Error("Error creating Image Panel");
        }
    }

    //Adjusts JPanel to work with images
    public void paintComponent(Graphics page)
    {
        super.paintComponent(page);

        int height = image.getHeight(null);
        int width = image.getWidth(null);

        //Scale Vertically:
        if (height > this.getHeight()){
            image = image.getScaledInstance(-1, getHeight(), Image.SCALE_DEFAULT);
        }

        // Center Images
        int x = (getWidth() - image.getWidth(null)) / 2;
        int y = (getHeight() - image.getHeight(null)) / 2;

        // Draw it
        page.drawImage( image, x, y, null );
    }
}
