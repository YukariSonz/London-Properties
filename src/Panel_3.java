import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;
import java.util.Arrays;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javax.swing.BorderFactory;
import javafx.scene.text.*;
/**
 * Panel_3 statistic panel
 * 
 * Zhenjie Jiang, Tao Lin, Yilei Liang,  Bonian Hu 
 * Version 1.0
 */
public class Panel_3 
{
    
    private int count = 1; // track which statistics is on
    private AirbnbDataLoader data = new AirbnbDataLoader("0","0"); // retrieve property data
    private Label body = new Label("Wellcome to statistics!"); // initial body
    private String min;
    private String max;
    
    
    /**
     * set and retreive panel 3
     */
    protected BorderPane start() 
    {   
        //set body for panel_3
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(20, 20, 20, 20));
        pane.setMinSize(500, 500);       
        // Add the button and label into the pane
        pane.setCenter(center());
        pane.setLeft(left());
        pane.setRight(right());
        pane.setBottom(extra());
        return pane;
    }
    
    /**
     * set the price range
     */
    protected void setMinMax(String min, String max)
    {
        this.min = min;
        this.max = max;
        data.setMinMax(min, max);       
    }
    
    /**
     * set extre statistics
     */
    protected GridPane extra()
    { 
      // pane containing navigation buttons for the extra statistics 
      GridPane extraB = new GridPane();
      extraB.setVgap(30);
      extraB.setHgap(30);
      
      //buttons to navigate to extra statistics
      Button extraStat1 = new Button("cheapest property");
      Button extraStat2 = new Button("Number of Long term Property");
      Button extraStat3 = new Button("The most reviewed Property");
      Button extraStat4 = new Button("The neibourhood with most Properties");
      
       
      //set functionality to the buttons     
      extraStat1.setOnAction(e -> extraBottonClick(1));
      extraStat2.setOnAction(e -> extraBottonClick(2));
      extraStat3.setOnAction(e -> extraBottonClick(3));
      extraStat4.setOnAction(e -> extraBottonClick(4));
      
      //add button to the pane
      extraB.add(extraStat1, 1, 0);
      extraB.add(extraStat2, 2,0);
      extraB.add(extraStat3, 3, 0);
      extraB.add(extraStat4, 4, 0);
      
      
      return extraB;
    }
    
    /**
     * The action of the extrabutton click
     */
    protected void extraBottonClick(int i)
    {   final Stage warning = new Stage();
        Text text = new Text("");
        // check which button is press and display the appopriate stat
        if(i == 1){
            text.setText("The cheapest property is \n"+ cheapProp());
        }
        else if (i == 2){
            text.setText("The number of Long term Property is \n"+ longTermNum());
        }
        else if (i == 3){
            text.setText("The most reviewed Property \n"+ mostReviewed());
        }
        else if (i == 4){
            text.setText("The neibourhood with most Properties \n"+ getMostProp());
        }
        VBox dialogVbox = new VBox(300);
        text.setFont(Font.font("Arial", FontWeight.NORMAL,30));
        text.setTextAlignment(TextAlignment.CENTER);
        dialogVbox.getChildren().add(text);
        Scene pop_up = new Scene(dialogVbox, 600, 200);
        warning.setScene(pop_up);
        warning.show();
    }

    /**
     * set the center of the panel
     */
    protected BorderPane center(){
        
        body.setFont(Font.font("Arial", FontWeight.BOLD,35));
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(body);
        
        return borderPane;
    }
    
    /**
     * set the left of the panel and the button to navigate between basic statistics 
     */
    protected BorderPane left(){
        // Create a Button or any control item
        Button myButtonL = new Button("<");
        myButtonL.setMaxHeight(Double.MAX_VALUE);
        myButtonL.setMaxWidth(Double.MAX_VALUE);
        myButtonL.setOnAction(this::LbuttonClick);
        BorderPane borderPane= new BorderPane();
        
        borderPane.setLeft(myButtonL);
        return borderPane;
    }
    
    /**
     * set the right of the panel and the button to navigate between basic statistics 
     */
    public BorderPane right(){
        // Create a Button or any control item
        Button myButtonR = new Button(">");
        myButtonR.setMaxHeight(Double.MAX_VALUE);
        myButtonR.setMaxWidth(Double.MAX_VALUE);
        
        //set an action on the button using method reference
        myButtonR.setOnAction(this::RbuttonClick);
        
        BorderPane borderPane= new BorderPane();
        borderPane.setRight(myButtonR);
        
        return borderPane;
    }
    
    /**
     * reset count
     */
    protected int countReset()
    {count = 1;
     return count;}
    
    /**
     * static 1
     * return the number of available properties in the range
     * @return count, the number of available orice
     */
    public int getNumberOfProperties(){
        return data.load(min,max).size();
    } 
     
     /**
     * static 2
     * return the average number of review of each propertires
     */
    public int getAvgNumberOfReviews(){
        int index = 0;
        int count = 0;
        for (AirbnbListing fetch: data.load(min, max) ){
            count += fetch.getNumberOfReviews();
            index += 1;
        }
        if(index==0)
        {return 0;}
        else{
            return count / index;}

    }
    
        /**
     * static 3
     * return the number of apartment
     */
    public int getNumApart(){
        int count = 0;
        for(AirbnbListing fetch: data.load(min, max)){
            if(fetch.getRoom_type().equals("Entire home/apt")){
                count++;
            }
        }
        return count;
    }
     
    /**
     * static 4
     * return the most expensive property in the range of selected price.
     */

    public String expensiveNeigh(){

        AirbnbListing mostExpen = data.load(min, max).get(0);
        for(AirbnbListing fetch: data.load(min, max)){
            if((mostExpen.getPrice()*mostExpen.getMinimumNights())<=(fetch.getPrice()*fetch.getMinimumNights())){
                mostExpen = fetch;
            }
        }

        return mostExpen.getNeighbourhood();
    }
    
    
    /**
     * static 5
     * find the number properties where the availability is long-term( more than 90 days)
     */
    public int longTermNum(){
        int count = 0;
        for(AirbnbListing fetch: data.load(min, max)){
            if(fetch.getAvailability365()>=90){
                count++;
            }
        }
        return count;
    }

    /**
     * static 6
     * find the cheapest properties 
     */
    public String cheapProp(){
        AirbnbListing mostcCheap = data.load(min, max).get(0);
        for(AirbnbListing fetch: data.load(min, max)){
            if((mostcCheap.getPrice()*mostcCheap.getMinimumNights())>=(fetch.getPrice()*fetch.getMinimumNights())){
                mostcCheap = fetch;
            }
        }
        return mostcCheap.getName();                   
    }

    /**
     * static 7
     * retrive the property with the most review
     */
    public String mostReviewed(){
        AirbnbListing mostRe = data.load(min, max).get(0);
        for(AirbnbListing fetch: data.load(min, max)){
            if(mostRe.getNumberOfReviews()<fetch.getNumberOfReviews()){
                mostRe = fetch;
            }
        }
        return mostRe.getName();
    }

    /**
     * static 8
     * return the neighbourhood with the most properties.
     */
    public String getMostProp(){
        Map<String, Integer> map = data.getHashMapNeighbour();
        String maxKey = Collections.max(map.entrySet(), Map.Entry.comparingByValue()).getKey();

        return maxKey;
    }
    
    /**
     * This will be executed when the left button is clicked
     * It decreases the count by 1
     * navigate to the statistic according to the count
     */
    private void LbuttonClick(ActionEvent event)
    {
        
        // Counts the number of button clicks and shows the result on a label
        if(count>0)
        {
            count = count - 1;
            switch(count)
            {
                case 1: body.setText("Wellcome to statistics!");
                break;
                case 2: body.setText("The total number of available properties:" + "\n" + 
                getNumberOfProperties());
                break;
                case 3: body.setText("Average number of reviews per property: "+"\n"+
                getAvgNumberOfReviews());
                break;
                case 4: body.setText("The number of entire homes and apartments:" +"\n"
                + getNumApart());
                break;
                case 5: body.setText("The pricest neighbourhood:"+ "\n"+
                expensiveNeigh());
                break;
            }     
        }
        else
        {count = 1;}
    }
    
    /**
     * This will be executed when the right button is clicked
     * It increments the count by 1
     * navigate to the statistic according to the count
     */
    private void RbuttonClick(ActionEvent event)
    {
        if(count<5)
        {
            count = count + 1;
            switch(count)
            {
                case 1: body.setText("Wellcome to statistics!");
                break;
                case 2: body.setText("Total number of properties avalible:" + "\n" + 
                getNumberOfProperties());
                break;
                case 3: body.setText("Average number of reviews per property: "+"\n"+
                getAvgNumberOfReviews());
                break;
                case 4: body.setText("The number of entire homes and apartments:" +"\n"+ 
                getNumApart());
                break;
                case 5: body.setText("The pricest neighbourhood: "+"\n" +
                expensiveNeigh());
                break;
            }    
        }
        else
        {count = 5;} 
    }
}

