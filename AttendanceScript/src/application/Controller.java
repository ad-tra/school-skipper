package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import animatefx.animation.FadeInLeft;
import animatefx.animation.FadeInRight;
import animatefx.animation.FadeOutLeft;
import animatefx.animation.FadeOutRight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Controller implements Initializable {

	AttendanceScript c = new AttendanceScript();
	//initialize list that holds the all the grade values
	@FXML
    private ObservableList<String> gradeList = FXCollections.observableArrayList("12","11","10","9","8","7",
    		"6","5","4","3","2", "1");
    
	//initialize list that holds the all the school values
	@FXML
    private ObservableList<String> schoolList = FXCollections.observableArrayList("High School","Middle School",
    		"Eisenhower","Van Holten","Milltown","JFK","Hamilton","Crim","Bradley Gardens","Adamsville");
	 
	
	/* 
	 * Combo Boxes IDs 
	 */
    @FXML
    private JFXComboBox<String> gradeComboBox; 
    @FXML
    private JFXComboBox<String> schoolComboBox; 
    
	/* 
	 * Button IDs 
	 */
    @FXML
    private JFXButton nextButton;
    @FXML
    private ImageView backButton;    
    @FXML
    private JFXButton doneButton;    
    @FXML
    private JFXButton launchButton;
    @FXML
    private JFXButton logInButton;    
	
    /* 
	 * Pane IDs
	 */
    @FXML
    private Pane logInPane1;
    @FXML
    private Pane homePane;
    @FXML
    private Pane rightPane;  
    @FXML
    private Pane logInPaneMinor;  
    /* 
	 * Field IDs
	 */
    @FXML
    private JFXTextField lastNameField;
    @FXML
    private JFXTextField emailField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXTextField nameField;    
    

    @FXML
    private ToggleGroup browserToggleGroup;
    
    /* 
   	 * variables to store the user log in info 
   	 */
    public  String storredName;
    public String storredLastName;
    public String storredEmail;
    public String storredPassword;
    public String storredSchool;
    public String storredGrade;
    public String storredBrowser;

    /**
     *  Launch button
     *  Launches the AttendanceScript main test 
     */
    @FXML
    void launchButtonHandle(MouseEvent event) {
    	
    	try {	
    		new Thread(c).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    
    
    /**
     *  Log in button  
     *  Launches the AttendanceScript main test 
     */
    @FXML
    void logInButtonHandle(MouseEvent event) {
    	
    	new FadeInRight(rightPane).setSpeed(3).play();
    	new FadeOutLeft(homePane).setSpeed(3).setDelay(Duration.millis(150)).play();
    	homePane.setDisable(true);
    }



    
    /**
     *  Next button 
     *  will bring the 2nd log in pane in view after checking the validity of user info
     */
    @FXML
    void nextButtonHandle(MouseEvent  event)  {
    	storredName = nameField.getText();
    	storredLastName = lastNameField.getText();
    	storredEmail= emailField.getText();
    	storredPassword = passwordField.getText();
    	
    	Node fieldList[] = {nameField,lastNameField,emailField,passwordField};
    	
    	if(checkUserInput(fieldList, "field")) 
    	logInPane1.setVisible(false);
    	
    	backButton.requestFocus();
    


    }

    
    
    
    /**
     *  Done button 
     *  will display home pane instead of the second login pane 
     */
    @FXML
    void doneButtonHandle(MouseEvent event) {
		
    	JFXRadioButton browserRadio = (JFXRadioButton) browserToggleGroup.getSelectedToggle();
    	

    	

    	Node[] ComboBoxList = {(Node)schoolComboBox, (Node)gradeComboBox};
    	if(!checkUserInput(ComboBoxList, "comboBox")  && browserRadio != null)
    	{
    		return;
    	}
    	
    	storredSchool=schoolComboBox.getSelectionModel().getSelectedItem();    
    	storredGrade=gradeComboBox.getSelectionModel().getSelectedItem();
    	storredBrowser=browserRadio.getText(); 	
    	
    	logInPaneMinor.toBack();
    	logInPane1.setVisible(true);
    	homePane.setDisable(false);
		new FadeOutRight(rightPane).setSpeed(3).play();
    	new FadeInLeft(homePane).setSpeed(3).play();

    	try {
			AttendanceScript.setUserInput(storredName, storredLastName, storredEmail, 
					storredPassword, storredSchool,storredGrade, storredBrowser);
		} catch (IOException e) {
			System.out.println("could not save your info");
		}
			
    }
    
    
    
    
    /**
     *  Back button 
     *  2 scenarios: going back to the home pane, or going back to the first login pane
     */
    @FXML
    void backButtonHandle(MouseEvent  event)  {    	
			
    	if(logInPane1.isVisible())
			{
				homePane.setDisable(false);
				new FadeOutRight(rightPane).setSpeed(3).play();
		    	new FadeInLeft(homePane).setSpeed(3).play();
			}
    	logInPane1.setVisible(true);		
    }
   



    /**
     *  Checks the validity of user input
     *  returns  true if check passes and false otherwise 
     * @throws IOException 
     */

	public boolean checkUserInput(Node[] objects, String type ) 
    {    	
   	 	boolean result = true;

    	

    	for(int i =0; i< objects.length; i++)
    	{
    		if( itemIsNull(objects[i], type))
    			{
    			objects[i].getStyleClass().set(objects[i].getStyleClass().size()-1, "field-error");
    			result = false;
    			}	
    		else
    			objects[i].getStyleClass().set(objects[i].getStyleClass().size()-1, "field");
    	}
    	
    
    	return result;
    }
    
	// returns true if a node is null 
    @SuppressWarnings("unchecked")
	public boolean itemIsNull(Node object, String type)
    {
    	if(type.equals("field"))return((TextInputControl) object).getText().compareTo("") ==0;
    	else return ( (ComboBox<String>) object).getSelectionModel().getSelectedItem() == null;

    }
    
    
   
    @Override
	public void initialize(URL location, ResourceBundle resources) {
			// returns true if the name parameter is empty 
    		if(c.isUserInputEmpty())
			{
			logInPaneMinor.toFront();
			}
    		
    		schoolComboBox.setItems(schoolList);
			gradeComboBox.setItems(gradeList);
    		
	    	System.out.println("View is now loaded!");
    }

}
