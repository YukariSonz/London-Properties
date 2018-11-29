import javafx.scene.layout.BorderPane;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import java.util.HashMap;
/**
 * In Panel 2 , it will probide a interative map with the minimum and maximum range provided in Panel 1 
 *
 * Zhenjie Jiang K1764072, Tao Lin K1763808, Yilei Liang K1764097,  Bonian Hu K1764139 
 * Version 1.0
 */
public class NewPanel2 
{
    

    private LocationInfoReader infoReader = new LocationInfoReader();
    private AirbnbDataLoader propertiesReader = new AirbnbDataLoader("0","99999");
    private String min;
    private String max;
    /**
     * Start a panel 2.
     */
    public BorderPane start(String minValue,String maxValue) 
    {
        setMinMax(minValue,maxValue);
        propertiesReader.setMinMax(min,max);
        BorderPane mapPane = new BorderPane();
        mapPane.setCenter(getDetailedImage());
        return mapPane;
    }
    
    /**
     * Set the minimum and maximum range.
     */
    private void setMinMax(String minValue, String maxValue){
        min = minValue;
        max = maxValue;
    }
    /**
     * Place the marker on the Anchorpane. And this pane will place on the 
     * stack pane with the map.
     * The Anchorpane will not automanage itself and allow the object overlaps.
     */
    private AnchorPane makeMarker()
    {
      AnchorPane markerPane= new AnchorPane();
      for (LocationInfo info: infoReader.loadInfo())
      {
          int buttonSize = getSizeOfLocation(info.getName());
          if (buttonSize >=0){
              if (buttonSize >0){
                  Button button = MarkerButton.makeButton(buttonSize,info.getName());
                  AnchorPane.setTopAnchor(button,(double) info.getYPosition());
                  AnchorPane.setLeftAnchor(button,(double) info.getXPosition());
                  button.setOnAction(e -> displayInfo(info.getName()));
                  markerPane.getChildren().addAll(button);
              }
              else{
                  Button button = MarkerButton.makeButton(1,info.getName());
                  AnchorPane.setTopAnchor(button,(double) info.getYPosition());
                  AnchorPane.setLeftAnchor(button,(double) info.getXPosition());
                  button.setOnAction(e -> displayInfo(info.getName()));
                  markerPane.getChildren().addAll(button);
              }
          }
      }
      return markerPane;
    }
    
    /**
     * Put the map and the marker on the stackpane
     */
    private StackPane getDetailedImage()
    {
        StackPane map = new StackPane();
        map.setAlignment(Pos.TOP_LEFT); 
        Image mapOfLondon = new Image("MapOfLondon.png");
        ImageView viewMap = new ImageView(mapOfLondon);
        map.getChildren().addAll(viewMap);
        map.getChildren().addAll(makeMarker());
        return map;
    }
    
    /**
     * As the size of the marker will be changed with the number of the properties
     * in that neighbourhood, so this method can calculate the size of the 
     * marker by dividing the number of properties with 100.
     */
    private int getSizeOfLocation(String nameOfLocation)
    {
        HashMap<String,Integer> numOfPropertiesInfo = propertiesReader.getHashMapNeighbour();
        if (numOfPropertiesInfo.containsKey(nameOfLocation)){
            int num = numOfPropertiesInfo.get(nameOfLocation);
            int size = num/100;
            return size;
        }
        else{
            //Return an invaild when there is no property here so that no marker will be made
            return -1; 
        }
    }
    
    /**
     * Open a new window to display the detail of the specific neighbourhood.
     */
    private void displayInfo(String nameOfNeighbourhood)
    {
        Stage stage = new Stage();
        DisplayProperties details = new DisplayProperties(nameOfNeighbourhood,min,max);
        Scene scene = new Scene(details.start(), 1000,800);
        stage.setScene(scene);
        stage.show();
    }
}
