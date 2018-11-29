import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;
import java.util.Arrays;
import com.opencsv.CSVReader;
import java.net.URISyntaxException;
/**
 * As the location of the marker of each neighbourhood is stored in a CSV file, so a CSV reader is needed
 * to read the X & Y position of the marker
 *
 * Zhenjie Jiang, Tao Lin, Yilei Liang,  Bonian Hu 
 * Version 1.0
 */
public class LocationInfoReader
{

    /**
     * Nothing here
     */
    public LocationInfoReader()
    {
    }
    
    /**
     * Similar to the Class AirbnbDataReader, read the X & Y position of each neighbourhood
     */
    public ArrayList<LocationInfo> loadInfo()
    {
        ArrayList<LocationInfo> locationInfo = new ArrayList();
        try{
            URL url = getClass().getResource("MarkerLocationInfo.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            reader.readNext();
            while ((line = reader.readNext()) != null){
                String neighbourhoodName = line[0];
                String xPosition = line[1];
                String yPosition = line[2];
                System.out.println(line[0]+line[1]+line[2]);
                int xInt = Integer.valueOf(xPosition);
                int yInt = Integer.valueOf(yPosition);
                LocationInfo neighLocationInfo = new LocationInfo(neighbourhoodName,xInt,yInt);
                locationInfo.add(neighLocationInfo);
            }
        }
        catch(IOException | URISyntaxException e){
            System.out.println("Failure! Something went wrong");
            e.printStackTrace();
        }
        return locationInfo;
    }
}
