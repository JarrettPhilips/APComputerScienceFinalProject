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
import java.text.*;

public class UI4 extends JFrame implements ActionListener{
    //Initizes objects and components here

    //Terrain
    Terrain terr;

    //Fonts
    Font bodyFont = new Font("Vendana", Font.PLAIN, 14);

    //Colors
    Color backgroundColor = new Color(108, 122, 137);
    Color textColor = new Color(255, 255, 255);
    Color translucentBackground = new Color(0, 0, 0, 180);
    //Color uiColor = new Color(211, 84, 0);
    Color uiColor = new Color(16, 124, 15);
    Color titleColor = new Color(34, 34, 34);

    //Frames
    JFrame window = new JFrame("The Watershed Project, UI 4");

    //Borders
    EmptyBorder spaceBorder = new EmptyBorder(2, 15, 2, 15);
    EmptyBorder paneBorder = new EmptyBorder(20, 0, 20, 0);
    LineBorder outlineBorder = new LineBorder(textColor, 3);
    CompoundBorder buttonBorder = new CompoundBorder(outlineBorder, spaceBorder);
    LineBorder panelBorder = new LineBorder(textColor, 10);

    //Dimensions
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    //Panels
    JPanel pixelInfoPanel = new JPanel();
    JPanel programInfoPanel = new JPanel();
    JPanel inputPanel = new JPanel();
    JPanel controlPanel = new JPanel();
    JPanel settingsPanel = new JPanel();
    JPanel helpPanel = new JPanel();
    JPanel menuPanel = new JPanel();

    //Image panels
    BackgroundPanel masterPanel = new BackgroundPanel();

    //Buttons
    JButton inputPanelButton = new JButton("Input");
    JButton infoPanelButton = new JButton("Info");
    JButton controlPanelButton = new JButton("Controls");
    JButton helpPanelButton = new JButton("Help");
    JButton reset = new JButton("Reset");
    JButton setup = new JButton("Set Up");
    JButton singleCycle = new JButton("Cycle");

    //TextFields
    JTextField rain = new JTextField("0", 4);
    JTextField snow = new JTextField("0", 4);
    JTextField xField = new JTextField("0", 4);
    JTextField yField = new JTextField("0", 4);

    //Variables
    public double rainfall = 0.0;
    public double snowfall = 0.0;
    boolean panelBackground = false;

    boolean controlPanelActive = true;
    boolean infoPanelActive = true;
    boolean inputPanelActive = true;
    boolean helpPanelActive = false;
    boolean settingsPanelActive = true;
    boolean panelsActive = true;

    int xLocation = 0;
    int yLocation = 0;

    //Labels
    JLabel rainLabel = new JLabel("Rain (cm) ");
    JLabel snowLabel = new JLabel("Snow (cm) ");
    JLabel pixelInfoPanelLabel = new JLabel("---- Pixel Info ----");
    JLabel baseElevationValue = new JLabel("Elevation: ");
    JLabel depthValue = new JLabel("Depth (cm): ");
    JLabel projectInfoLabel = new JLabel("---- Project Info ----");
    JLabel zoomLabel = new JLabel("Zoom");
    JLabel helpLabel = new JLabel("---- Help Menu ----");
    JLabel inputLabel = new JLabel("---- Input Menu ----");
    JLabel cycleLabel = new JLabel("Cycle: 0");
    JLabel xLabel = new JLabel("X: ");
    JLabel yLabel = new JLabel("Y: ");

    //Loading bars
    JProgressBar cycleLoadingBar = new JProgressBar();

    //Panes
    JScrollPane terrainPane = new JScrollPane();

    //Grid bag contraints
    GridBagConstraints c = new GridBagConstraints();

    //Establishes the Gui
    public UI4(){
        //Code for keybinding
        InputMap im = masterPanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = masterPanel.getActionMap();

        //Input Maps
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "RightArrow");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "LeftArrow");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "UpArrow");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "DownArrow");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_H, 0), "H");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "R");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "S");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), "C");

        //Actions Maps
        am.put("RightArrow", new ArrowAction("RightArrow"));
        am.put("LeftArrow", new ArrowAction("LeftArrow"));
        am.put("UpArrow", new ArrowAction("UpArrow"));
        am.put("DownArrow", new ArrowAction("DownArrow"));
        am.put("Enter", new ArrowAction("Enter"));
        am.put("H", new ArrowAction("H"));
        am.put("R", new ArrowAction("R"));
        am.put("S", new ArrowAction("S"));
        am.put("C", new ArrowAction("C"));

        //Frames and window components
        setUpWindow();
        setUpMasterPanel();

        //Other panels
        setUpMenuPanel();
        setUpInputPanel();
        setUpControlPanel();
        setUpHelpPanel();
        setUpTerrainPane();
        setUpPixelInfoPanel();
        setUpProgramInfoPanel();
    }

    //Sets the size and parameters of the window, as well as the background and master panel
    public void setUpWindow(){
        window.setSize(findScreenWidth(), findScreenHeight());
        window.setVisible(true);
        window.setFocusTraversalKeysEnabled(false);
    }

    //Sets up the master panel
    public void setUpMasterPanel(){
        //Adjusts the panel settings
        masterPanel.setLayout(new GridBagLayout());
        masterPanel.setOpaque(true);
        masterPanel.setVisible(true);

        //Adds the panel to the window
        window.add(masterPanel);
    }

    //Sets up the menu bar
    public void setUpMenuPanel(){
        //Adjusts the panel settings
        menuPanel.setLayout(new FlowLayout());
        menuPanel.setBackground(titleColor);
        menuPanel.setVisible(true);

        //Sets gridbag constraints
        resetc();

        //Adjusts the input panel button
        inputPanelButton.addActionListener(this);
        inputPanelButton.setBorderPainted(false);
        inputPanelButton.setContentAreaFilled(false);
        inputPanelButton.setFont(bodyFont);
        inputPanelButton.setForeground(textColor);
        inputPanelButton.setPreferredSize(new Dimension(100, 60));
        c.gridx = 1;
        c.gridy = 1;
        menuPanel.add(inputPanelButton, c);

        //Adjusts the info panel button
        infoPanelButton.addActionListener(this);
        infoPanelButton.setBorderPainted(false);
        infoPanelButton.setContentAreaFilled(false);
        infoPanelButton.setFont(bodyFont);
        infoPanelButton.setForeground(textColor);
        infoPanelButton.setPreferredSize(new Dimension(100, 60));
        c.gridx = 3;
        c.gridy = 1;
        menuPanel.add(infoPanelButton, c);

        //Adjust the help panel button
        helpPanelButton.addActionListener(this);
        helpPanelButton.setBorderPainted(false);
        helpPanelButton.setContentAreaFilled(false);
        helpPanelButton.setFont(bodyFont);
        helpPanelButton.setForeground(textColor);
        helpPanelButton.setPreferredSize(new Dimension(100, 60));
        c.gridx = 4;
        c.gridy = 1;
        menuPanel.add(helpPanelButton, c);

        //Adjusts the control panel button
        controlPanelButton.addActionListener(this);
        controlPanelButton.setBorderPainted(false);
        controlPanelButton.setContentAreaFilled(false);
        controlPanelButton.setFont(bodyFont);
        controlPanelButton.setForeground(textColor);
        controlPanelButton.setPreferredSize(new Dimension(100, 60));
        c.gridx = 5;
        c.gridy = 1;
        menuPanel.add(controlPanelButton, c);    

        //Adds the menu bar to the window
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 4;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 1.0;
        c.weighty = 0;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        masterPanel.add(menuPanel, c);
    }

    //Sets up the control panel
    public void setUpControlPanel(){
        //Adjusts the panel settings
        controlPanel.setLayout(new FlowLayout());
        controlPanel.setBackground(uiColor);
        controlPanel.setVisible(true);

        //Sets gridbag constraints
        resetc(); 

        //Adjusts the single cycle button
        singleCycle.addActionListener(this);
        singleCycle.setFont(bodyFont);
        singleCycle.setPreferredSize(new Dimension(100, 40));
        singleCycle.setBorder(buttonBorder);
        singleCycle.setContentAreaFilled(false);
        singleCycle.setForeground(textColor);
        c.gridx = 1;
        c.gridy = 1;
        controlPanel.add(singleCycle, c);

        //Adjusts the reset button
        reset.addActionListener(this);
        reset.setFont(bodyFont);
        reset.setPreferredSize(new Dimension(100, 40));
        reset.setBorder(buttonBorder);
        reset.setContentAreaFilled(false);
        reset.setForeground(textColor);
        c.gridx = 2;
        c.gridy = 1;
        controlPanel.add(reset, c);

        //Adjusts the x location label
        xLabel.setFont(bodyFont);
        xLabel.setForeground(textColor);
        c.gridx = 3;
        c.gridy = 1;
        controlPanel.add(xLabel, c);

        //Adjusts the x location text field
        xField.setFont(bodyFont);
        xField.setPreferredSize(new Dimension(100, 40));
        c.gridx = 4;
        c.gridy = 1;
        controlPanel.add(xField, c);

        //Adjusts the x location label
        yLabel.setFont(bodyFont);
        yLabel.setForeground(textColor);
        c.gridx = 5;
        c.gridy = 1;
        controlPanel.add(yLabel, c);

        //Adjusts the y location text field
        yField.setFont(bodyFont);
        yField.setPreferredSize(new Dimension(100, 40));
        c.gridx = 6;
        c.gridy = 1;
        controlPanel.add(yField, c);

        //Adds the panel to the window
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 4;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 1.0;
        c.weighty = 0;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        masterPanel.add(controlPanel, c);
    }

    //Sets up the input panel
    public void setUpInputPanel(){
        //Adjusts the panel settings
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBackground(uiColor);
        inputPanel.setVisible(true);

        //Sets gridbag constraints
        resetc();

        //Adjusts the input panel label
        inputLabel.setFont(bodyFont);
        inputLabel.setForeground(textColor);
        c.anchor = GridBagConstraints.LINE_END;
        c.gridx = 1;
        c.gridy = 1;
        inputPanel.add(inputLabel, c);

        //Adjusts the rain label
        rainLabel.setFont(bodyFont);
        rainLabel.setForeground(textColor);
        c.anchor = GridBagConstraints.LINE_END;
        c.gridx = 1;
        c.gridy = 2;
        inputPanel.add(rainLabel, c);

        //Adjusts the snow label
        snowLabel.setFont(bodyFont);
        snowLabel.setForeground(textColor);
        c.anchor = GridBagConstraints.LINE_END;
        c.gridx = 1;
        c.gridy = 3;
        inputPanel.add(snowLabel, c);

        //Adjusts the rain text field
        rain.setFont(bodyFont);
        c.anchor = GridBagConstraints.LINE_START;
        c.ipadx = 40;
        c.gridx = 2;
        c.gridy = 2;
        inputPanel.add(rain, c);

        //Adjusts the snow text field
        snow.setFont(bodyFont);
        c.anchor = GridBagConstraints.LINE_START;
        c.ipadx = 40;
        c.gridx = 2;
        c.gridy = 3;
        inputPanel.add(snow, c);

        //Adjusts the setup button
        setup.addActionListener(this);
        setup.setFont(bodyFont);
        setup.setPreferredSize(new Dimension(100, 40));
        setup.setBorder(buttonBorder);
        setup.setContentAreaFilled(false);
        setup.setForeground(textColor);
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 5;
        inputPanel.add(setup, c);

        //Adds the panel to the window
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 2;
        c.fill = GridBagConstraints.VERTICAL;
        c.ipadx = 40;
        c.ipady = 0;
        c.weightx = 0;
        c.weighty = 1.0;
        c.insets = new Insets(20, 20, 20, 0);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        masterPanel.add(inputPanel, c);
    }

    //Sets up the pixel information panel
    public void setUpPixelInfoPanel(){
        //Adjusts the panel settings
        pixelInfoPanel.setLayout(new GridBagLayout());
        pixelInfoPanel.setBackground(uiColor);
        pixelInfoPanel.setVisible(true);

        //Sets gridbag constraints
        resetc();

        //Adjusts the pixel panel label
        pixelInfoPanelLabel.setFont(bodyFont);
        pixelInfoPanelLabel.setForeground(textColor);
        c.gridx = 1;
        c.gridy = 1;
        pixelInfoPanel.add(pixelInfoPanelLabel, c);

        //Adjusts the base elevation label
        baseElevationValue.setFont(bodyFont);
        baseElevationValue.setForeground(textColor);
        c.gridx = 1;
        c.gridy = 2;
        pixelInfoPanel.add(baseElevationValue, c);

        //Adjusts the depth label
        depthValue.setFont(bodyFont);
        depthValue.setForeground(textColor);
        c.gridx = 1;
        c.gridy = 3;
        pixelInfoPanel.add(depthValue, c);

        //Adds the panel to the window
        c.gridx = 3;
        c.gridy = 5;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        c.ipadx = 0;
        c.ipady = 40;
        c.weightx = 1.0;
        c.weighty = 0;
        c.insets = new Insets(0, 10, 20, 20);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        masterPanel.add(pixelInfoPanel, c);
    }

    //Sets up the program info panel
    public void setUpProgramInfoPanel(){
        //Adjusts the panel settings
        programInfoPanel.setLayout(new GridBagLayout());
        programInfoPanel.setBackground(uiColor);
        programInfoPanel.setVisible(true);

        //Sets gridbag constraints
        resetc();

        //Adjusts the project panel label
        projectInfoLabel.setFont(bodyFont);
        projectInfoLabel.setForeground(textColor);
        c.gridx = 1;
        c.gridy = 1;
        programInfoPanel.add(projectInfoLabel, c);

        //Adjusts the cycle count label
        cycleLabel.setFont(bodyFont);
        cycleLabel.setForeground(textColor);
        c.gridx = 1;
        c.gridy = 2;
        programInfoPanel.add(cycleLabel, c);

        //Adds the panel to the window
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 1.0;
        c.weighty = 0;
        c.insets = new Insets(0, 20, 20, 10);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        masterPanel.add(programInfoPanel, c);
    }

    //Sets up the help panel
    public void setUpHelpPanel(){
        //Adjusts the panel settings
        helpPanel.setLayout(new GridBagLayout());
        helpPanel.setBackground(uiColor);
        helpPanel.setVisible(false);

        //Sets gridbag constraints
        resetc();
        c.anchor = GridBagConstraints.PAGE_END;

        //Adjusts the help label
        helpLabel.setFont(bodyFont);
        helpLabel.setForeground(textColor);
        c.gridx = 1;
        c.gridy = 1;
        helpPanel.add(helpLabel, c);
        
        //Adjusts the help labels
        JLabel help1 = new JLabel("The menu bar controls which panels are visible, clicking a button will toggle the given panel.");
        help1.setFont(bodyFont);
        help1.setForeground(textColor);
        c.gridx = 1;
        c.gridy = 2;
        helpPanel.add(help1, c);
        
        JLabel help2 = new JLabel("To setup the simulation, enter an amount of rain and snow, then hit the 'setup' button.");
        help2.setFont(bodyFont);
        help2.setForeground(textColor);
        c.gridx = 1;
        c.gridy = 3;
        helpPanel.add(help2, c);
        
        JLabel help3 = new JLabel("To advnace the simulation, use the 'cycle' button.");
        help3.setFont(bodyFont);
        help3.setForeground(textColor);
        c.gridx = 1;
        c.gridy = 4;
        helpPanel.add(help3, c);
        
        JLabel help4 = new JLabel("Information on the program as a whole can be viewed in the program info panel, while information on a specific pixel can be found in the pixel information panel");
        help4.setFont(bodyFont);
        help4.setForeground(textColor);
        c.gridx = 1;
        c.gridy = 5;
        helpPanel.add(help4, c);
        
        JLabel help5 = new JLabel("To change the position of the cursor, use the x and y text fields in the control panel, or use the arror keys.");
        help5.setFont(bodyFont);
        help5.setForeground(textColor);
        c.gridx = 1;
        c.gridy = 6;
        helpPanel.add(help5, c);
        
        JLabel help6 = new JLabel("To restart the simulation with the same settings, hit the 'reset' button, to restart with new inputs, enter the inputs then hit the 'setup' button.");
        help6.setFont(bodyFont);
        help6.setForeground(textColor);
        c.gridx = 1;
        c.gridy = 7;
        helpPanel.add(help6, c);
        
        JLabel help7 = new JLabel("There are several keybindings / shortcuts as well. 'R' will reset, 'C' will cycle, 'S' will setup, 'H' will hide the gui, and the arrow keys will move the cursor.");
        help7.setFont(bodyFont);
        help7.setForeground(textColor);
        c.gridx = 1;
        c.gridy = 8;
        helpPanel.add(help7, c);
        
        //Adds the panel to the window
        c.gridx = 4;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 2;
        c.fill = GridBagConstraints.VERTICAL;
        c.ipadx = 40;
        c.ipady = 0;
        c.weightx = 0;
        c.weighty = 1.0;
        c.insets = new Insets(20, 0, 20, 20);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        masterPanel.add(helpPanel, c);
    }

    //Sets up the terrain panel
    public void setUpTerrainPane(){
        //Adjusts the panel settings
        terrainPane.setBorder(BorderFactory.createEmptyBorder());
        terrainPane.setBackground(uiColor);
        terrainPane.setVisible(false);

        //Sets gridbag constraints
        resetc();

        //Adds the panel to the window
        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 2;
        c.gridheight = 2;
        //c.fill = GridBagConstraints.BOTH;
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(20, 20, 20, 20);
        c.anchor = GridBagConstraints.CENTER;
        masterPanel.add(terrainPane, c);
    }

    //Adds terrain to the terrain pane, also used to refresh terrain image
    public void setUpTerrainImage(){
        try{
            BufferedImage image = terr.getImage();
            ImageIcon terrainIcon = new ImageIcon(image);
            terrainPane.setViewportView(new JLabel(terrainIcon));

        } catch(Exception e){
            Error error = new Error("Error setting up terrain pane");
        }

        terrainPane.setVisible(true);
    }

    //Builds a title for the panel
    public JPanel createTitlePanel(String title){
        //Creates the objects needed
        JPanel panel = new JPanel();
        JLabel label = new JLabel(title);

        //Adjusts the components\
        panel.setBackground(titleColor);
        label.setFont(bodyFont);
        label.setForeground(textColor);

        panel.add(label);

        //Returns the finished panel
        return panel;
    }

    //Resets all gridbaglayout constraints
    public void resetc(){
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.NONE;
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0;
        c.weighty = 0;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
    }

    //Finds the width of the monitor
    public int findScreenWidth(){
        return (int)(screenSize.getWidth());
    }

    //Finds the height of the monitor
    public int findScreenHeight(){
        return (int)(screenSize.getHeight());
    }

    //Repaints the terrain and window
    public void refresh(){
        //Refreshes the project information
        cycleLabel.setText("Cycle: " + Initiate.getCycleCount());
        if(! infoPanelActive || ! panelsActive)
            programInfoPanel.setVisible(false);

        //Refreshes the pixel information
        //Retreives the correct pixel from terrain
        TerrainPixel point = Initiate.ui.terr.getTerrainPixel(xLocation, yLocation);
        baseElevationValue.setText("Elevation: " + point.getElevation());
        DecimalFormat df = new DecimalFormat("#.##");
        depthValue.setText("Depth: " + df.format(point.getDepth()));
        
        //Refreshes the terrain image
        Initiate.ui.terr.editImage();
        setUpTerrainImage();

        //Refreshes the control panel
        xField.setText(String.valueOf(xLocation));
        yField.setText(String.valueOf(yLocation));
    }

    //Resets all the information given, and creates a clean slate
    public void reset(){
        terr = new Terrain("heightmap.jpg", rainfall);
        setUpTerrainImage();
    }

    //Toggles all panels off, then upon another cycle, resets them to their previous positions
    public void togglePanels(){
        if(panelsActive){
            panelsActive = false;
            helpPanel.setVisible(false);
            inputPanel.setVisible(false);
            programInfoPanel.setVisible(false);
            pixelInfoPanel.setVisible(false);
            menuPanel.setVisible(false);
            controlPanel.setVisible(false);
        } else {
            panelsActive = true;
            menuPanel.setVisible(true);
            if(helpPanelActive)
                helpPanel.setVisible(true);
            if(inputPanelActive)
                inputPanel.setVisible(true);
            if(controlPanelActive)
                controlPanel.setVisible(true);
            if(infoPanelActive){
                programInfoPanel.setVisible(true);
                pixelInfoPanel.setVisible(true);
            }
        }
    }

    //Sets up the simulation after the setup button has been pressed
    public void setUp(){
        //Collects input
        rainfall = Double.parseDouble(rain.getText());
        snowfall = Double.parseDouble(snow.getText());

        //Calculates the amount of snow as rain
        //Is calculated according to the estimate that 10 inches of snow is equivilant to an inch of rainfall at 30 degrees (F)
        rainfall = rainfall + (snowfall / 10);

        //Sets up the terrain
        terr = new Terrain("heightmap.jpg", rainfall);
        setUpTerrainPane();
        setUpTerrainImage();
        
        //Centers the cursor
        xLocation = (Initiate.ui.terr.getWidth() / 2);
        yLocation = (Initiate.ui.terr.getHeight() / 2);
    }

    //Detects a button press and takes action
    public void actionPerformed(ActionEvent e){
        //If the setup button is pressed
        if(e.getSource() == setup){
            setUp();
            refresh();
        }

        //If cycle is pressed
        if(e.getSource() == singleCycle){
            Initiate.singleCycle();
            refresh();
        }

        //If reset is pressed
        if(e.getSource() == reset){
            Initiate.reset();
            reset(); 
            refresh();
        }

        //If control panel button is pressed
        if(e.getSource() == controlPanelButton){
            if(controlPanelActive){
                controlPanel.setVisible(false);
                controlPanelActive = false;
            } else {
                controlPanel.setVisible(true);
                controlPanelActive = true;
            }
        }

        //If info panel button is pressed
        if(e.getSource() == infoPanelButton){
            if(infoPanelActive){
                pixelInfoPanel.setVisible(false);
                programInfoPanel.setVisible(false);
                infoPanelActive = false;
            } else {
                pixelInfoPanel.setVisible(true);
                programInfoPanel.setVisible(true);
                infoPanelActive = true;
            }
        }

        //If input panel button is pressed
        if(e.getSource() == inputPanelButton){
            if(inputPanelActive){
                inputPanel.setVisible(false);
                inputPanelActive = false;
            } else {
                inputPanel.setVisible(true);
                inputPanelActive = true;
            }
        }

        //If help panel button is pressed
        if(e.getSource() == helpPanelButton){
            if(helpPanelActive){
                if(settingsPanelActive){
                    settingsPanel.setVisible(false);
                    settingsPanelActive = false;
                }
                helpPanel.setVisible(false);
                helpPanelActive = false;
            } else {
                helpPanel.setVisible(true);
                helpPanelActive = true;
            }
        }
    }
}