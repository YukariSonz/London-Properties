import javafx.application.Application;
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
 * This is the main frame/panel1
 * 
 * Zhenjie Jiang K1764072, Tao Lin K1763808, Yilei Liang K1764097,  Bonian Hu K1764139 
 * Version 1.0
 */
public class Panel_1_Main extends Application
{
    private int count = 0; //Track which panel is currently displayed
    private BorderPane mainPane = new BorderPane(); // the main frame
    private final ComboBox searchFrom = new ComboBox(); // for user to chose the minimum price
    private final ComboBox searchTo = new ComboBox();  // for user to chose the maximum price
    /**
     * Main pane for the application.
     * Connects other elements of the application
     */
    @Override
    public void start(Stage stage) throws Exception
    {     
        //pane which holds other panes
        //set size
        mainPane.setMinSize(500, 500);
        //add other section of the main frame
        mainPane.setTop(top());
        mainPane.setBottom(bottom());
        mainPane.setBorder(new Border(new BorderStroke(Color.BLACK, 
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        // set the initial panel
        mainPane.setCenter(welcomePane());

        // JavaFX must have a Scene (window content) inside a Stage (window)
        Scene panel1 = new Scene(mainPane, 1000,800);

        stage.setTitle("London property");
        stage.setScene(panel1);
        stage.show();
    }

    /**
     * greet the user and display instrusctrion/Panel 1
     */
    protected BorderPane welcomePane()
    {BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setMinSize(300, 300);
        //greet the user
        Text welcome = new Text("Welcome");
        welcome.setFont(Font.font("Arial", FontWeight.BOLD,80));

        // set user instruction
        Text instruction = new Text("Instruction:\nPlease select a price range\nclick '>' and '>' to navigate to between Panels, when the price range is selected\npress reset to reset the application" );
        welcome.setFont(Font.font("Arial", FontWeight.NORMAL,40));
        //set layout
        pane.setTop(welcome);
        pane.setCenter(instruction);

        return pane;

    }

    /**
     * Bottom section of the main panel, contain the navigation and reset buttons
     */
    protected BorderPane bottom()
    {//Allow navigation to next or previous page
        final Button forward = new Button(">");
        final Button backward = new Button("<"); 
        final Button reset = new Button("reset");

        // set butttons' functionality
        forward.setOnAction(e -> RbuttonClick());
        backward.setOnAction(e -> LbuttonClick());
        reset.setOnAction(e -> resets());

        //create pane to contain the buttons
        BorderPane bottompane = new BorderPane();
        bottompane.setPadding(new Insets(10, 10, 10, 10));
        bottompane.setRight(forward);
        bottompane.setCenter(reset);
        bottompane.setLeft(backward);
        bottompane.setBorder(new Border(new BorderStroke(Color.BLACK, 
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));   

        return bottompane;        

    }

    /**
     * Top section of the main pane, allows the selection of the price range
     */    
    protected BorderPane top()
    { //Contain Search functions
        final HBox menu = new HBox(); 
        menu.setSpacing(10);

        final Label from = new Label("From");
        final Label to = new Label("To");

        //locate the property in the correct price range
        //Create drop down menu indicating the prices

        searchFrom.setPromptText("min price"); // Allow maximum price selection

        searchTo.setPromptText("max price");

        //Checking if the selected price is valid
        searchFrom.setOnAction(e -> valueCheck());
        searchTo.setOnAction(e -> valueCheck());

        //add price ranges
        searchFrom.getItems().addAll("0" , "50", "100","200", "400", "500");
        searchTo.getItems().addAll("25", "50", "100","200", "400", "999999");

        //add the fuctions to the pane
        menu.getChildren().addAll(from, searchFrom, to, searchTo); 

        BorderPane topPane = new BorderPane();
        topPane.setPadding(new Insets(10, 10, 10, 10));
        topPane.setRight(menu);
        topPane.setBorder(new Border(new BorderStroke(Color.BLACK, 
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        return topPane;        
    }

    /**
     * Pop up message, when user input invalid values
     */
    protected void popUp()
    {   final Stage warning = new Stage();
        VBox dialogVbox = new VBox(20);
        Text error = new Text("error Value");
        error.setFont(Font.font("Arial", FontWeight.BOLD,30));
        dialogVbox.getChildren().add(error);
        Scene pop_up = new Scene(dialogVbox, 200, 50);
        warning.setScene(pop_up);
        warning.show();
    }

    /**
     * Check if the min price is smaller or bigger than max price
     */
    private void valueCheck()
    { if(searchTo.getValue() != null && searchFrom.getValue() != null){
            //convert string to integer
            String min = (String) searchFrom.getValue();
            Integer mins = Integer.parseInt(min);
            String max = (String) searchTo.getValue();
            Integer maxs = Integer.parseInt(max);

            if(mins > maxs){
                popUp();
                setMinMax(null, null);
            }     
        }
    }

    /**
     * return minimum price as String
     */
    protected String getMin()
    {String testMin = (String) searchFrom.getValue();
        return testMin;}

    /**
     * return maximum price as String
     */
    protected String getMax()
    {String testMax = (String) searchTo.getValue();
     return testMax;}

    /**
     * set Minimum Maximum price
     */
    protected void setMinMax(String newMin,String newMax)
    { searchFrom.setValue(newMin);
        searchTo.setValue(newMax);

    }
    
    /**
     * action for the bottom right button
     */
    private void RbuttonClick()  

    {// disable the button until a valid price range is selected
        if(searchTo.getValue() != null && searchFrom.getValue() != null &&count<3)
        {
            count++;
            //allow navigation to the next panel
            switch(count)
            {
                case 1: panel_2();
                break;
                case 2: panel_3();
                break;
                case 3: panel_4();
                break;
            }

        }
    }
    
    /**
     * resets the application
     */
    protected void resets()
    {mainPane.setCenter(welcomePane());
        count = 0;
        setMinMax(null, null);
    }

    /**
     * function for the bottom left buttom, allow use to go the the previous window
     */
    private void LbuttonClick()  
    {// disable the button until a valid price range is selected
        if(count>1){
            count--;
            //allow navigation to the previous panel
            switch(count)
            {
                case 1: panel_2();
                break;
                case 2: panel_3();
                break;

            }}

    }

    /**
     * initialize panel_3
     */
    private void panel_3()    
    {
        final Panel_3 panel3 = new Panel_3();

        panel3.setMinMax(getMin(), getMax());        
        mainPane.setCenter(panel3.start());

    }

    /**
     * initialize panel_2
     */        
    private void panel_2()
    {
        String min = (String) searchFrom.getValue();
        String max = (String) searchTo.getValue();
        NewPanel2 panel2 = new NewPanel2();
        mainPane.setCenter(panel2.start(min,max));
    }
    
    /**
     * initialize panel_4
     */
    private void panel_4()
    {
     Panel_4 panel4= new Panel_4();
    
     mainPane.setCenter(panel4.start());
    }
}