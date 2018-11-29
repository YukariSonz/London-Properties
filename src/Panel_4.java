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
import java.util.Date;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * Panel 4 is the registion panel. Users need to create a account in order to 
 * book the room.
 *
 * Zhenjie Jiang, Tao Lin, Yilei Liang,  Bonian Hu
 * Version 1.0
 */
public class Panel_4 
{
    //create the field
    private TextField name;
    private TextField surname;
    private DatePicker birthdate;
    private TextField email;
    private TextField userName;
    private PasswordField password;
    private PasswordField rePassword;
    private ComboBox gender;
    /**
     * start panel 4
     */
    protected BorderPane start()
    {
        
        // Create a new grid pane
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(20, 20, 20, 20));
        pane.setMinSize(300, 300);
        
        pane.setTop(top());
        pane.setCenter(center());
        pane.setBottom(bottom());
      
        return pane;
    }
    
    
    /**
     * Set the text field in the center
     */
    private GridPane center(){
        //create horizontal box
        HBox userNameBox = new HBox();
        HBox nameBox = new HBox();
        HBox surNameBox = new HBox();
        HBox emailBox = new HBox();
        HBox passwordBox = new HBox();
        HBox passwordReBox = new HBox();
        HBox birthdateBox = new HBox();
        HBox genderBox = new HBox();
        
        //create the username
        Label userNameL = new Label("User name*: ");
        this.userName = new TextField ();
        userName.setPromptText("Create a username: ");
       
        //create name session
        Label nameL = new Label("Name*: ");
        this.name = new TextField ();
        name.setPromptText("Enter your first name: ");
                
        // create surname session
        Label surL = new Label("Surame*: ");
        this.surname = new TextField ();
        surname.setPromptText("Enter your sur name: ");
        
        
        //create email session
        Label emailL = new Label("Email*: ");
        this.email = new TextField ();
        email.setPromptText("Enter your email: ");
       
        
        //create password session
        Label passwordL = new Label("New Password*: ");
        this.password = new PasswordField();
        password.setPromptText("Create your password");
        
        
        //create re-enter password session
        Label passwordReL = new Label("Repeat Password*: ");
        this.rePassword = new PasswordField();
        rePassword.setPromptText("Repreat your password");
        
        
        //create birthdate password session
        Label birthdateL = new Label("Birthdate: ");
        this.birthdate = new DatePicker();
        birthdate.setPromptText("Select birthdate");
        
        //create gender session 
        Label genderL = new Label("Gender: ");
        this.gender = new ComboBox();
        gender.setPromptText("Gender");
        gender.getItems().addAll("Male", "Female");
        
        
        //create the reminder
        Label reminder = new Label("You must complete all the information with *!!");
        
        
        //set image
        Image image = new Image("CW2.png");
        ImageView iv1 = new ImageView();
        iv1.setImage(image);
         
        // add elements to the boxes
        userNameBox.getChildren().addAll(userNameL,userName);
        nameBox.getChildren().addAll(nameL, name);
        surNameBox.getChildren().addAll(surL, surname);
        emailBox.getChildren().addAll(emailL, email);
        passwordBox.getChildren().addAll(passwordL, password);
        passwordReBox.getChildren().addAll(passwordReL, rePassword);
        birthdateBox.getChildren().addAll(birthdateL, birthdate);
        genderBox.getChildren().addAll(genderL, gender);
        
        
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(30);
        pane.setHgap(30);
        
        //layout the horizontal boxes
        pane.add(userNameBox, 1, 0);
        pane.add(emailBox, 2, 0);
        pane.add(nameBox, 1, 1);
        pane.add(surNameBox, 2, 1);
        pane.add(birthdateBox, 1, 2);
        pane.add(genderBox, 2, 2);
        pane.add(passwordBox, 1, 3);
        pane.add(passwordReBox, 1, 4);
        pane.add(reminder,1,5);
        pane.add(iv1,2,5);
        
        return pane;
    }
    
    /**
     * create the submit button in the bottom
     */
    private BorderPane bottom(){
        Button submit = new Button("Sign up");
        
        BorderPane borderPane= new BorderPane();
        borderPane.setCenter(submit);
        submit.setOnAction(e -> buttonAction());
        return borderPane;
    }
    
    /**
     * check whether the input from * is null
     */
    private boolean isValid(){
        boolean result;
        if(name.getText().trim().isEmpty() || surname.getText().trim().isEmpty() 
        || email.getText().trim().isEmpty() || name.getText().isEmpty() ||
        password.getText().trim().isEmpty() || rePassword.getText().trim().isEmpty()){
            result = false;
        }
        else{
            result = true;
        }
        return result;
    }
    
    /**
     * the action afterthe button clicks
     */
    private void buttonAction(){
        final Stage warning = new Stage();
        Text text = new Text("");
        // check which button is press and display the appopriate stat
        if(isValid() == false){
            text.setText("You must complete all the fields with *");
            
        }
        else if (password.getText().equals(rePassword.getText()) == false){
            text.setText("You must keep the password same!!");
        }
        else{
            text.setText(getMessage());
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
     * print successful message
     */
    private String getMessage(){
        String message =" Dear " + userName.getText() + "\n The registration was successful!!! \n"
        + "\n You can use the email or user name to log in to your account." ;
        return message;
    }
    
    /**
     * set top of the Pane
     */
    private BorderPane top(){
        BorderPane pane = new BorderPane();
        Label top = new Label("CREATE AN ACCOUNT");
        top.setFont(Font.font("Arial", FontWeight.NORMAL,25));
        pane.setTop(top);
        return pane;
    }
    
    
}
