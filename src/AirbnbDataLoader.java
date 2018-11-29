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
import java.util.Map;
/**
 * Load a list of files
 * 
 * Zhenjie Jiang K1764072, Tao Lin K1763808, Yilei Liang K1764097,  Bonian Hu K1764139 
 * Version 1.0
 * 
 */
public class AirbnbDataLoader {

    private String min;
    private String max;
    /**
     * Constructor of the AirbnbDataLoader
     */
    public AirbnbDataLoader(String min, String max){
        this.min = min;
        this.max = max;
    }

    /**
     * set the limits of Airbnb loader
     */
    public void setMinMax(String min, String max)
    {
        this.min = min;
        this.max = max;
    }

    /**
     * Return an ArrayList containing the rows in the AirBnB London data set csv file.
     */
    public ArrayList<AirbnbListing> load() {
        System.out.print("Begin loading Airbnb london dataset...");
        ArrayList<AirbnbListing> listings = new ArrayList<AirbnbListing>();
        try{
            URL url = getClass().getResource("airbnb-london.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            //skip the first row (column headers)
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                String id = line[0];
                String name = line[1];
                String host_id = line[2];
                String host_name = line[3];
                String neighbourhood = line[4];
                double latitude = convertDouble(line[5]);
                double longitude = convertDouble(line[6]);
                String room_type = line[7];
                int price = convertInt(line[8]);
                int minimumNights = convertInt(line[9]);
                int numberOfReviews = convertInt(line[10]);
                String lastReview = line[11];
                double reviewsPerMonth = convertDouble(line[12]);
                int calculatedHostListingsCount = convertInt(line[13]);
                int availability365 = convertInt(line[14]);

                AirbnbListing listing = new AirbnbListing(id, name, host_id,
                        host_name, neighbourhood, latitude, longitude, room_type,
                        price, minimumNights, numberOfReviews, lastReview,  
                        reviewsPerMonth, calculatedHostListingsCount, availability365
                    );
                listings.add(listing);

            }
        } catch(IOException | URISyntaxException e){
            System.out.println("Failure! Something went wrong");
            e.printStackTrace();
        }
        System.out.println("Success! Number of loaded records: " + listings.size());
        return listings;
    }

    /**
     * returns a array list contains the properties in side a price range
     */
    public ArrayList<AirbnbListing> load(String a, String b){
        ArrayList<AirbnbListing> listings = new ArrayList<AirbnbListing>();
        int minimum = convertInt(a);
        int maximum  = convertInt(b);

        try{
            URL url = getClass().getResource("airbnb-london.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            //skip the first row (column headers)
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                String id = line[0];
                String name = line[1];
                String host_id = line[2];
                String host_name = line[3];
                String neighbourhood = line[4];
                double latitude = convertDouble(line[5]);
                double longitude = convertDouble(line[6]);
                String room_type = line[7];
                int price = convertInt(line[8]);
                int minimumNights = convertInt(line[9]);
                int numberOfReviews = convertInt(line[10]);
                String lastReview = line[11];
                double reviewsPerMonth = convertDouble(line[12]);
                int calculatedHostListingsCount = convertInt(line[13]);
                int availability365 = convertInt(line[14]);

                AirbnbListing listing = new AirbnbListing(id, name, host_id,
                        host_name, neighbourhood, latitude, longitude, room_type,
                        price, minimumNights, numberOfReviews, lastReview,
                        reviewsPerMonth, calculatedHostListingsCount, availability365
                    );
                if(price>= minimum && price< maximum){
                    listings.add(listing);
                }               
            }
        } catch(IOException | URISyntaxException e){
            System.out.println("Failure! Something went wrong");
            e.printStackTrace();
        }        
        return listings;
    }

    /**
     * return number of properties in a neighbourhood in a range of price
     */
    public int getNumNeigh(String name){
        int count = 0;
        for(AirbnbListing y: load(min, max)){
            if(y.getNeighbourhood().equals(name)){
                count++;
            }
        }
        return count;
    }
    
    /**
     * return a list of properties that has the neibhourhood name as same as
     * the neihbourhood name.
     */
    public ArrayList<AirbnbListing> getSpecificData(String name){
        ArrayList<AirbnbListing> allDetails = load(min,max);
        ArrayList<AirbnbListing> requiredDetails = new ArrayList();
        for (AirbnbListing property: allDetails){
            if (property.getNeighbourhood().equals(name)){
                requiredDetails.add(property);
            }
        }
        return requiredDetails;
    }
    
    /**
     * return a hash map with the name of neighbourhood and the number of 
     * available properties assigns to it.
     */
    public HashMap<String, Integer> getHashMapNeighbour(){

        HashMap<String,Integer> count = new HashMap<>();


        for(AirbnbListing w: load(min, max))     
        {
            String neighbourhoodName = w.getNeighbourhood();
            if (count.containsKey(neighbourhoodName)){
                int num = count.get(neighbourhoodName);
                num++;
                count.put(neighbourhoodName,num);
            }
            else{
                count.put(neighbourhoodName,1);
            }
        }
        return count;
    }

    /**
     * HashMap: return the latitude and longitude of a neighbourhood
     */
    public HashMap<String,double[][]> getLocation()
    {
        HashMap<String,double[][]> locationMap = new HashMap();
        for(AirbnbListing neighbourhood: load(min, max))     
        {
            double[][] location = {{neighbourhood.getLatitude(),neighbourhood.getLongitude()}};
            locationMap.put(neighbourhood.getNeighbourhood(),location);    
        }
        return locationMap;
    }

    /**
     * <Map>
     * List the properties' name, price, number of views and minimum nights when
     * the name of the neighbour is entered. 
     */
    public String getMapDescription(String neighbour){
        String description = "";
        for(AirbnbListing w: load(min, max)){
            if(w.getNeighbourhood().equals(neighbour)){
                description += "name: " + w.getName() + "\t Price: " + w.getPrice() 
                + "\t Number of Views: " + w.getNumberOfReviews() + 
                "\t Minimum nights: " + w.getMinimumNights() + "\n";
            }
        }
        return description;
    }

    /**
     *
     * @param doubleString the string to be converted to Double type
     * @return the Double value of the string, or -1.0 if the string is 
     * either empty or just whitespace
     */
    private Double convertDouble(String doubleString){
        if(doubleString != null && !doubleString.trim().equals("")){
            return Double.parseDouble(doubleString);
        }
        return -1.0;
    }

    /**
     *
     * @param intString the string to be converted to Integer type
     * @return the Integer value of the string, or -1 if the string is 
     * either empty or just whitespace
     */
    private Integer convertInt(String intString){
        if(intString != null && !intString.trim().equals("")){
            return Integer.parseInt(intString);
        }
        return -1;
    }

}