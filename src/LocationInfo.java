
/**
 * The Name, X Position and Y position of each neighbourhood
 *
 * Zhenjie Jiang, Tao Lin, Yilei Liang,  Bonian Hu
 * Version 1.0
 */
public class LocationInfo
{
    private String neighbourhoodName;
    private int xPosition;
    private int yPosition;
    /**
     * Constructor for objects of class LocationInfo
     */
    public LocationInfo(String Name, int xPos, int yPos)
    {
        neighbourhoodName = Name;
        xPosition = xPos;
        yPosition = yPos;
    }
    
    /**
     * Return the name of the neighbourhood
     */
    public String getName()
    {
        return neighbourhoodName;
    }
    
    /**
     * Return the X Position of the neighbourhood
     */
    public int getXPosition()
    {
        return xPosition;
    }
    
    /**
     * Return the Y position of the neighbourhood
     */
    public int getYPosition()
    {
        return yPosition;
    }
    
}
