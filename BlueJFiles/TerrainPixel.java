public class TerrainPixel{
    //The amount of water on the pixel
    double depth;
    double tempDepth;

    //The level of elevation, between 0 and 255, of the pixel
    double elevation;
    double totalElevation;

    //Cordinates of the arraylist matrix that this pixel lies in
    int x;
    int y;

    //Creates the TerrainPixel object
    public TerrainPixel(int xCordinate, int yCordinate, double rainfall, double height){
        x = xCordinate;
        y = yCordinate;
        
        elevation = height;
        depth = rainfall;
        tempDepth = rainfall;
    }
    
    public TerrainPixel()
    {
        depth = 0;
        
    }
    
    //Zeros the depth
    public void zeroDepth() {depth = 0;}
    
    public void setDepth(double x) {depth = x;}
    
    public double getTempDepth() { return tempDepth;}
    
    public void setTempDepth(double x) {tempDepth = x;}
    
    public void addTempDepth(double x) {tempDepth+=x;}
    
    public void subtractTempDepth(double x) {tempDepth-=x;}
    
    //Reduces the depth of the water on the pixel
    public void subtractDepth(double input){
        depth = depth - input;

        if(depth < 0)
            depth = 0;
    }

    //Increases the depth of the water on the pixel
    public void addDepth(double input){
        depth = depth + input;
    }
    
    //Returns the depth of the water on the pixel
    public double getDepth(){
        return depth;
    }

    //Returns the elevation of the pixel
    public double getElevation(){
        return elevation;
    }
    
    public double getTotalElevation() {
        return totalElevation;
    }
    
    public void addTotalElevation(double x) {
        totalElevation+=x;
    }
    
    public void setTotalElevation(double scale) {
        totalElevation = elevation + (depth/scale);
    }
    
    public int getX() {return x; }
        
    public int getY() { return y; }
}

