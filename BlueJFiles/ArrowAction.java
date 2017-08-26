import javax.swing.ActionMap;
import javax.swing.AbstractAction;
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

//Takes input from keys to perform actions
public class ArrowAction extends AbstractAction {

    private String cmd;

    public ArrowAction(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (cmd.equalsIgnoreCase("LeftArrow")) {
            Initiate.ui.xLocation --;
            Initiate.ui.refresh();
        } else if (cmd.equalsIgnoreCase("RightArrow")) {
            Initiate.ui.xLocation ++;
            Initiate.ui.refresh();
        } else if (cmd.equalsIgnoreCase("UpArrow")) {
            Initiate.ui.yLocation --;
            Initiate.ui.refresh();
        } else if (cmd.equalsIgnoreCase("DownArrow")) {
            Initiate.ui.yLocation ++;
            Initiate.ui.refresh();
        } else if (cmd.equalsIgnoreCase("Enter")) {
            Initiate.ui.xLocation = (int) (Double.parseDouble(Initiate.ui.xField.getText()));
            Initiate.ui.yLocation = (int) (Double.parseDouble(Initiate.ui.yField.getText()));
            Initiate.ui.refresh();
        } else if (cmd.equalsIgnoreCase("H")) {
            Initiate.ui.togglePanels();
        } else if (cmd.equalsIgnoreCase("R")) {
            Initiate.reset();
            Initiate.ui.reset(); 
            Initiate.ui.refresh();
        } else if (cmd.equalsIgnoreCase("S")) {
            Initiate.ui.setUp();
            Initiate.ui.refresh();
        } else if (cmd.equalsIgnoreCase("C")) {
            Initiate.singleCycle();
            Initiate.ui.refresh();
        }
    }
}