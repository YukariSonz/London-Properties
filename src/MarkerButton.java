

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 * Make a marker button with the image we selected 
 *
 * Zhenjie Jiang , Tao Lin, Yilei Liang ,  Bonian Hu 
 * Version 1.0
 */
public class MarkerButton
{
    public  static Button makeButton(int size,String nameOfNeighbourhood) 
    {
        Image mapMarker = new Image("CW.png",size,size,false,false);
        ImageView marker = new ImageView(mapMarker);
        Button markerButton = new Button(null,marker);
        return markerButton;
    }
    
}
