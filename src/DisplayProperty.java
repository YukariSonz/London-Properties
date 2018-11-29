
import javafx.scene.layout.VBox;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ScrollPane;
/**
 * In this pane, the GUI will display the details of the specific property.
 * If the user is connected to the network and able to access google, the location on the google map will also be displayed here.
 *
 * Zhenjie Jiang K1764072, Tao Lin K1763808, Yilei Liang K1764097,  Bonian Hu K1764139 
 * Version 1.0
 */
public class DisplayProperty 
{


    public BorderPane start(AirbnbListing selfInformation) 
    {
        BorderPane propertyDetail = new BorderPane();
        VBox details = new VBox(5);
        
        Label propertyID = new Label("Property ID: "+selfInformation.getId());
        Label propertyName = new Label("Property Name: "+selfInformation.getName());
        Label hostID = new Label("Host ID: "+selfInformation.getHost_id());
        Label hostName = new Label("Host Name: "+selfInformation.getHost_name());
        Label neighbourhood = new Label("Neighbourhood: "+selfInformation.getNeighbourhood());
        Label latitude = new Label("Latitude: "+String.valueOf(selfInformation.getLatitude()));
        Label longitude = new Label ("Longitude: "+String.valueOf(selfInformation.getLongitude()));
        Label roomType = new Label ("Room Type: "+selfInformation.getRoom_type());
        Label price = new Label("Price: "+String.valueOf(selfInformation.getPrice()));
        Label minimumNights = new Label("Minimum number of nights that someone can stay: "+String.valueOf(selfInformation.getMinimumNights()));
        Label numOfReviews = new Label("Number of reviews: "+selfInformation.getNumberOfReviews());
        Label lastReview = new Label("Last review date: "+selfInformation.getLastReview());
        //As some property does not have this value, if it's converted to a String, it will become -1.0, so it need to be processed
        double reviewsPMonth = selfInformation.getReviewsPerMonth();
        String numReview = String.valueOf(reviewsPMonth);
        if (numReview.equals("-1.0")){
            numReview = "No data";
        }
        Label reviewsPerMonth = new Label ("Reviews per month: "+numReview);
        Label calculatedHostListingsCount = new Label("Calculated Host Listing Count: "+String.valueOf(selfInformation.getCalculatedHostListingsCount()));
        Label availability365 = new Label ("Number of days that is available in a year: "+String.valueOf(selfInformation.getAvailability365()));
        details.getChildren().addAll(propertyID,propertyName,hostID,hostName,neighbourhood,latitude,longitude,roomType,price,minimumNights,numOfReviews,
            lastReview,reviewsPerMonth,calculatedHostListingsCount,availability365);
        
        //To show the property on the Map, it need the latitude and longitude of the property.
        GoogleMapAPI googleMap = new GoogleMapAPI();
        propertyDetail.setLeft(details);
        String strLatitude = String.valueOf(selfInformation.getLatitude());
        String strLongitude = String.valueOf(selfInformation.getLongitude());
        propertyDetail.setRight(googleMap.start(strLatitude,strLongitude));
        return propertyDetail;
    }

}
