import java.util.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Terrain
{
    ArrayList<ArrayList<TerrainPixel>> terrain;
    public BufferedImage image;
    TerrainPixel deepestTerrainPixel = new TerrainPixel();

    public Terrain(String filename, double rainfall)
    {
        try{
            image = ImageIO.read(new File(filename));
            load(filename, rainfall);
        } catch(Exception e)
        {
            System.out.println("Could not find Picture");
        }
    }

    public void load(String filename, double rainfall)
    {
        terrain = new ArrayList<ArrayList<TerrainPixel>>();
        int height = image.getHeight();
        int width = image.getWidth();

        //creates TerrainPixel matrix from heightmap
        for(int y=0; y < height; y++)
        {
            //creates temporary arraylist - x axis
            ArrayList<TerrainPixel> temp = new ArrayList<TerrainPixel>();

            //adds a border Pixel with 255 elevation
            TerrainPixel first = new TerrainPixel(-1,y,0,255);
            temp.add(first);

            //adds pixels to the temporary array
            for(int x=0; x < width; x++)
            {
                int rgb = image.getRGB(x,y);
                TerrainPixel tp = new TerrainPixel(x,y,rainfall,(rgb & 0xFF));
                temp.add(tp);
            }

            //adds border Pixel to the end of temporary array
            TerrainPixel last = new TerrainPixel(width+1,y,0,255);
            temp.add(last);

            //adds temporary array to terrain
            terrain.add(temp);
        }

        //creates border arrays for top and bottom
        ArrayList<TerrainPixel> top = new ArrayList<TerrainPixel>();
        ArrayList<TerrainPixel> bot = new ArrayList<TerrainPixel>();
        for(int i=-1;i<image.getWidth()+1; i++)
        {
            TerrainPixel temp = new TerrainPixel(i,-1,0,255);
            top.add(temp);
        }
        terrain.add(0,top);
        for(int i=-1;i<image.getWidth()+1; i++)
        {
            TerrainPixel temp = new TerrainPixel(i,height,0,255);
            bot.add(temp);
        }
        terrain.add(bot);
    }

    //Adjusts the amount of water on each pixel depending on the height of the surrounding pixels.
    public void distribute(int x, int y)
    {
        double[][] differenceElevation = new double[3][3];
        boolean[][] belowPeak = new boolean[3][3];
        int totalElevationDrop = 0;
        TerrainPixel terPix = terrain.get(y).get(x);
        if(x!=0 && y!=0 && y<image.getHeight()+1 && x<image.getWidth()+1)
        {
            for(int i= 0; i<3; i++)
            {
                for(int j=0; j<3; j++)
                {
                    differenceElevation[i][j] = terPix.getElevation() - terrain.get(y-1+i).get(x-1+j).getElevation();
                    if(differenceElevation[i][j] > 0){
                        belowPeak[i][j] = true;
                        totalElevationDrop+=differenceElevation[i][j];
                    }
                }
            }

            double ratio;
            double change;
            double totalChange = 0;;
            for(int i= 0; i<3; i++)
            {
                for(int j=0; j<3; j++)
                {
                    if(belowPeak[i][j]==true)
                    {
                        ratio = totalElevationDrop/differenceElevation[i][j];
                        change = terPix.getDepth() / ratio;
                        terrain.get(y-1+i).get(x-1+j).addTempDepth(change);
                        totalChange+=change;
                    }
                }
            }
            terrain.get(y).get(x).subtractTempDepth(totalChange);
        }

    }

    public void flowy() {
        //System.out.println();
        for(int y=0;y<image.getHeight()+2;y++)
        {
            for(int x=0;x<image.getWidth()+2;x++)
            {
                distribute(x,y);
            }
        }

        //System.out.println();
        for(int i=0;i<image.getHeight() + 2;i++)
        {
            for(int j=0;j<image.getWidth() + 2;j++)
            {
                if(terrain.get(i).get(j).getTempDepth() <= 0.001)
                    terrain.get(i).get(j).setDepth(0);
                else
                    terrain.get(i).get(j).setDepth(terrain.get(i).get(j).getTempDepth());
            }
        }
        editImage();
    }

    public int[][] returnDepth()
    {
        int height = image.getHeight();
        int width = image.getWidth();
        int[][] depth = new int[height+2][width+2];
        for(int y=1; y < height +1; y++)            
        {
            for(int x=1; x <width +1; x++)
            {
                checkForDeepest(terrain.get(y).get(x));
            }
        }
        for(int y=1; y < height +1; y++)            
        {
            for(int x=1; x <width +1; x++)
            {
                depth[y][x] = (int)((terrain.get(y).get(x).getDepth() * 255)/deepestTerrainPixel.getDepth());
            }
        }
        return depth;
    }

    public int[] getRGBArray()
    {
        int[][] depths = returnDepth();
        int height = image.getHeight();
        int width = image.getWidth();
        int hold = 0;
        int[] rgbArray = new int[height*width];
        int value = 0;
        for(int y=1; y < height+1; y++)
        {
            for(int x=1; x < width+1; x++)
            {
                if(depths[y][x] == 0)
                {
                    int c = (int)terrain.get(y).get(x).getElevation();
                    int rgb = ((c&0x0ff)<<16)|((c&0x0ff)<<8)|(c&0x0ff);
                    rgbArray[hold] = rgb;
                } else {
                    int rgb = ((29&0x0ff)<<16)|((190&0x0ff)<<8)|(224&0x0ff);
                    rgbArray[hold] = rgb;
                }
                
                if(x > (Initiate.ui.xLocation - 10) && x < (Initiate.ui.xLocation + 10) && y > Initiate.ui.yLocation - 2 && y < Initiate.ui.yLocation + 1){
                    int rgb = ((255&0x0ff)<<16)|((58&0x0ff)<<8)|(58&0x0ff);
                    rgbArray[hold] = rgb;
                }
                
                if(y > (Initiate.ui.yLocation - 10) && y < (Initiate.ui.yLocation + 10) && x > Initiate.ui.xLocation - 2 && x < Initiate.ui.xLocation + 1){
                    int rgb = ((255&0x0ff)<<16)|((58&0x0ff)<<8)|(58&0x0ff);
                    rgbArray[hold] = rgb;
                }
                
                hold++; 
            }
        }

        return rgbArray;
    }

    public void checkForDeepest(TerrainPixel current)
    {
        if(current.getDepth() > deepestTerrainPixel.getDepth())
            deepestTerrainPixel = current;
    }

    //Returns a pixel from the provided coordinates
    public TerrainPixel getTerrainPixel(int x, int y){
        return terrain.get(y).get(x);
    }

    public void editImage()
    {
        int height = image.getHeight();
        int width = image.getWidth();
        int[] rgb = getRGBArray();
        image = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0,0,width,height,rgb,0,width);
    }

    public BufferedImage getImage()
    {
        return image;
    }
    
    public int getHeight(){
        return image.getHeight();
    }
    
    public int getWidth(){
        return image.getWidth();
    }

    public ArrayList<ArrayList<TerrainPixel>> getTerrain()
    {
        return terrain;
    }

}
