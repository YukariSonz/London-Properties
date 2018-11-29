import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ComboBox;
import java.util.ArrayList;
import javafx.scene.control.ScrollPane;
import java.util.Collections;
import java.util.Comparator;
/**
 * Display all properties in specific neighbourhood
 * Bug Found: When too many property need to be displayed, there will be a out of memory expection.
 *
 * Zhenjie Jiang K1764072, Tao Lin K1763808, Yilei Liang K1764097,  Bonian Hu K1764139 
 * Version 1.0
 */
public class DisplayProperties 
{
    private String name;
    private String min;
    private String max;
    private AirbnbDataLoader details;
    private BorderPane mainPane;
    public DisplayProperties(String nameOfNeighbourhood,String minValue,String maxValue)
    {
        name = nameOfNeighbourhood;
        min = minValue;
        max = maxValue;
        details = new AirbnbDataLoader(min,max);
    }
    /**
     * Display the properties
     */
    public BorderPane start() 
    {
       mainPane = new BorderPane();
       ArrayList<AirbnbListing> properties = details.getSpecificData(name); //The properties in the specific neighbourhood
       VBox propertiesList = centerPane(properties);
       //As there are too many properties , therefore a scrollbar is needed. And a scrollpane can provide this
       ScrollPane propertiesListSP = new ScrollPane(); 
       propertiesListSP.setContent(propertiesList);//Put the VBox to he scrollpane
       mainPane.setTop(topPane());
       
       mainPane.setCenter(propertiesListSP);
       return mainPane;
    }
    /**
     * Display the properties after sorted
     * It will clear the mainpane first, then load the mainpane again with sorted properties.
     */
    public void startSorted(ArrayList<AirbnbListing> sortedData)
    {
        mainPane.getChildren().clear(); //Clear everything
        ScrollPane propertiesListSP = new ScrollPane();
        VBox propertiesList = centerPane(sortedData);
        propertiesListSP.setContent(propertiesList);
        mainPane.setCenter(propertiesListSP);
        mainPane.setTop(topPane());
    }
    /**
     * The top pane of this interface. Showing the name of the neighbourhood and providing a sorting method for the user.
     */
    private BorderPane topPane()
    {
        BorderPane topPanel = new BorderPane();
        //Implement a border to the borderpane for a better view
        topPanel.setBorder(new Border(new BorderStroke(Color.BLACK, 
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        Label neighbourhoodName = new Label(name);
        ComboBox sortingMethod = new ComboBox();
        sortingMethod.setValue("Sort By");
        sortingMethod.getItems().addAll("Number of Review low to high","Number of Review high to low","Price low to high","Price high to low","Host Name A to Z");
        sortingMethod.setOnAction(e -> checkSort((String) sortingMethod.getValue()));
        topPanel.setLeft(neighbourhoodName);
        topPanel.setRight(sortingMethod);
        return topPanel;
    }
    /**
     * In this method, it will load the properties in the specific neighbourhood and add it to a pane with VBox layout.
     * For a better view, I use a Borderpane to present the information.
     * In the center (as there is no left object, it will present at the left hand size), it will display the brief information of the property
     * In the right hand side, there is a button to open the details of that property.
     */
    private VBox centerPane(ArrayList<AirbnbListing> properties)
    {
        VBox propertiesDetails = new VBox(5);
        for (AirbnbListing property : properties){
            BorderPane propertyPane = new BorderPane();
            propertyPane.setBorder(new Border(new BorderStroke(Color.BLACK, 
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            VBox labelDetails = new VBox();
            Label propertyName = new Label("Name:"+property.getName());
            Label price = new Label("Price: "+String.valueOf(property.getPrice()));
            //As some property does not have this value, if it's converted to a String, it will become -1.0, so it need to be processed
            double reviewsPerMonth = property.getReviewsPerMonth();
            String numReview = String.valueOf(reviewsPerMonth);
            if (numReview.equals("-1.0")){
                numReview = "No data";
            }
            Label reviewScore = new Label("Reviews per month: "+numReview);
            
            Label minNight = new Label("Minimum number of nights that someone can stay: "+String.valueOf(property.getMinimumNights()));
            Button showDetailsButton = new Button("Show Details");
            String propertyID = property.getId();
            showDetailsButton.setOnAction(e -> displayDetails(property));
            labelDetails.getChildren().addAll(propertyName,price,reviewScore,minNight);
            propertyPane.setCenter(labelDetails);
            propertyPane.setRight(showDetailsButton);
            propertiesDetails.getChildren().addAll(propertyPane);
        }
        return propertiesDetails;
    }
    
    /**
     * Open the detail of the specific property in a new window.
     */
    private void displayDetails(AirbnbListing selfInfo)
    {
        Stage stage = new Stage();
        DisplayProperty details = new DisplayProperty();
        Scene scene = new Scene(details.start(selfInfo));
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * In this method, it will check the sorting method that the use selected
     * and then call the corresponding method
     * Sorting methods provided: 
     * Number of Review - from low to high, from high to low
     * Price - from low to high, from high to low
     * Host Name - From A to Z
     */
    private void checkSort(String sortingMethod)
    {
        if (sortingMethod.equals("Number of Review low to high")){
            sortByNumOfReviewLowToHigh(); 
        }
        else if (sortingMethod.equals("Price low to high")){
            sortByPriceLowToHigh();
        }
        else if (sortingMethod.equals("Host Name A to Z")){
            sortByHostName();
        }
        else if (sortingMethod.equals("Number of Review high to low")){
            sortByNumOfReviewHighToLow();
        }
        else if (sortingMethod.equals("Price high to low")){           
            sortByPriceHighToLow();
        }
    }
    /**
     *  To sort the properties by price, we need to override the compare method in Collecition to sort the ArrayList 
     *  by the attribute of a object.(Price of the property here)
     *  Sort the arrayList from low to high.
     */
    private void sortByPriceLowToHigh()
    {
        ArrayList<AirbnbListing> properties = details.getSpecificData(name);
        Collections.sort(properties, new Comparator<AirbnbListing>(){
            @Override public int compare(AirbnbListing property1, AirbnbListing property2){
                
                if (property1.getPrice() > property2.getPrice()){
                    return 1;
                    
                }
                if (property1.getPrice() < property2.getPrice()){
                    return -1;
                }
                else{
                    return 0;
                }
            }
        });
        startSorted(properties);
    }
    /**
     *  To sort the properties by price, we need to override the compare method in Collecition to sort the ArrayList 
     *  by the attribute of a object.(Price of the property here)
     *  Sort the arrayList from high to low.
     */
    private void sortByPriceHighToLow()
    {
        ArrayList<AirbnbListing> properties = details.getSpecificData(name);
        Collections.sort(properties, new Comparator<AirbnbListing>(){
            @Override public int compare(AirbnbListing property1, AirbnbListing property2){
                if (property1.getPrice() < property2.getPrice()){
                    return 1;
                    
                }
                if (property1.getPrice() > property2.getPrice()){
                    return -1;
                }
                else{
                    return 0;
                }
            }
        });
        startSorted(properties);
    }
    /**
     *  To sort the properties by the number of reviews, we need to override the compare method in Collecition to sort the ArrayList 
     *  by the attribute of a object.(Number of review of the property here)
     *  Sort the arrayList from low to high.
     */
    private void sortByNumOfReviewLowToHigh()
    {
        ArrayList<AirbnbListing> properties = details.getSpecificData(name);
        Collections.sort(properties, new Comparator<AirbnbListing>(){
            @Override public int compare(AirbnbListing property1, AirbnbListing property2){
                if (property1.getNumberOfReviews() > property2.getNumberOfReviews()){
                    return 1;
                    
                }
                if (property1.getNumberOfReviews() < property2.getNumberOfReviews()){
                    return -1;
                }
                else{
                    return 0;
                }
            }
        });
        startSorted(properties);
    }
    /**
     *  To sort the properties by the number of reviews, we need to override the compare method in Collecition to sort the ArrayList 
     *  by the attribute of a object.(Number of review of the property here)
     *  Sort the arrayList from high to low.
     */
    private void sortByNumOfReviewHighToLow()
    {
        ArrayList<AirbnbListing> properties = details.getSpecificData(name);
        Collections.sort(properties, new Comparator<AirbnbListing>(){
            @Override public int compare(AirbnbListing property1, AirbnbListing property2){
                if (property1.getNumberOfReviews() < property2.getNumberOfReviews()){
                    return 1;
                    
                }
                if (property1.getNumberOfReviews() > property2.getNumberOfReviews()){
                    return -1;
                }
                else{
                    return 0;
                }
            }
        });
        startSorted(properties);
    }
    /**
     *  To sort the properties by the host name, we need to override the compare method in Collecition to sort the ArrayList 
     *  by the attribute of a object.(Host Name of the property here)
     *  There is a method "A.compareTo(B)" will finish this task
     *  Sort the arrayList from A to Z.
     */
    private void sortByHostName()
    {
        ArrayList<AirbnbListing> properties = details.getSpecificData(name);
        Collections.sort(properties, new Comparator<AirbnbListing>(){
            @Override public int compare(AirbnbListing property1, AirbnbListing property2){
                return (property1.getHost_name().compareTo(property2.getHost_name()));
            }
        });
        startSorted(properties);
    }
}
