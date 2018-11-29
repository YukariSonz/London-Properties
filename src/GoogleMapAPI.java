import java.util.HashMap;
import java.util.Map;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.Node;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.BorderPane;
/**
 * In this class, it will return a borderpane with the google map that show the location 
 * with given latitude and longitude
 *
 * Zhenjie Jiang K1764072, Tao Lin K1763808, Yilei Liang K1764097,  Bonian Hu K1764139 
 * version 1.0
 */
public class GoogleMapAPI
{
    public BorderPane start(String latitude,String longitude) 
    {
        BorderPane mapPane = new BorderPane();
        Label mapNote = new Label("If connected to network, the location on the google map will be displayed here:");
        mapPane.setTop(mapNote);
        mapPane.setCenter(new MapViewer(latitude,longitude));
        return mapPane;
    }
    
    /**
     * In this inner class, I used the google map API to present the MAP of London and the location of the property
     * As this API need to be accessed by a web, so I made a easy browser here.
     * 
     */
    class MapViewer extends Region {
 
    final WebView viewer = new WebView();
    final WebEngine webEngine = viewer.getEngine();
    String mapUrl;
    public MapViewer(String latitude,String longitude) {
        //Note: %7C here stands for the pipe note(|)
        //The URL of the Google Map API
        mapUrl = "https://maps.googleapis.com/maps/api"+
        //I used the static map API and set the center to be London
        //Zoom value will change the proportion of the real distance and the map distance
        //Size & Scale value will change the size of the map
        //MapType will change the type of the map(In static map, it provides:roadmap, satellite, hybrid and terrain
        "/staticmap?center=London&zoom=10&size=6000x6000&scale=2&maptype=roadmap"+
        //Markers will change the style of the marker (We can also use our own marker by using "icon:" with the URL of the icon
        //Label will change what will be displayed on the marker
        //Then it take the latitude and longitude
        "&markers=color:blue%7Clabel:H%7C"+latitude+","+longitude+
        //The key for the API users
        "&key=SomeKeyOfAPIHere";
        // load the web page
        webEngine.load(mapUrl);
        //add the web view to the scene
        getChildren().add(viewer);
 
    }
    
 
    protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(viewer,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }
    
    }
}
