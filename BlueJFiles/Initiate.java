import java.awt.Toolkit.*;
import java.awt.*;
import javax.swing.UIManager;

//This class really just starts everything going, but does not control it. It initilizes the rest of the program.
public class Initiate{

    public static UI4 ui = new UI4();
    public static void main(String[] args){
        //Uses the computers default look and feel, instead of java's
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e){
            Error error = new Error("Error loading the default look and feel.");
        }
    }
    
    //Controls whether or not the clock is running
    static int cycleCount = 0;
    
    //A single clock cycle
    public static void singleCycle(){
        Initiate.ui.terr.flowy();
        Initiate.ui.setUpTerrainImage();
        Initiate.ui.setUpProgramInfoPanel();

        //Controls the time in between cycles (in milliseconds)
        cycleCount ++;
        Initiate.ui.refresh();
    }

    //Resets the clock
    public static void reset(){
        cycleCount = 0;
    }

    //Returns the cycle count
    public static int getCycleCount(){
        return cycleCount;
    }
}