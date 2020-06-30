package dirtman.application;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.function.UnaryOperator;

import dirtman.FileMethods;
import dirtman.Item;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//TODO: Make sure buttons are consistent
//TODO: Figure out the resizing stuff in billing
//TODO: Figure out why the edit bill creates a corrupted file
//TODO: Make the program installable
//TODO: Verify everything works
//TODO: Add the tableViews to billing
//TODO: Look at the to-dos in editbill and new bill

public class DirtManMain extends Application 
{
	private static final int APP_WIDTH = 640;
	private static final int APP_HEIGHT = 400;
	//Base and storage file names
	public static String csvFileName = "pricesheet.csv";
	public static String custFileName = "customers.csv";
	public static String baseExcelFile = "excelbasefile.xlsx";
	public static String serialFileName = "pricesheet.ser";
	//Non Java file path names
	public static String homeFXMLFilePath = "mainpage.fxml";
	public static String addFXMLFilePath = "additem.fxml";
	public static String changeFXMLFilePath = "change.fxml";
	public static String billingFXMLFilePath = "billing.fxml";
	public static String newBillFXMLFilePath = "newbill.fxml";
	public static String editBillFXMLFilePath = "editbill.fxml";
	public static String inventoryFXMLFilePath = "inventory.fxml";
	public static String advancedFXMLFilePath = "advanced.fxml";
	public static String itemListFXMLFilePath = "itemlist.fxml";
	public static String billListFXMLFilePath = "";
	public static String logoFilePath = "logo.jpg";
	public static String homeCSSFilePath = "mainpage.css";
	//File Directories
	public static String excelFileDirectory = "../DirtManBilling";
	public static String serialFileDirectory = "../DirtManBilling";
	public static String baseAndStorageDirectory = "../DirtManBilling";
	//Used in program
	public static ArrayList<Item> items = new ArrayList<Item>();
	public static boolean flip = true;
	public static Double markup = 1.3;
	public static Double expensiveMarkup = 1.1;
	
	public static UnaryOperator<Change> integerFilter = change -> 
	{
        String newText = change.getControlNewText();
        if (newText.matches("\\d{0,15}?")) 
        { 
            return change;
        }
        return null;
    };
    
    public static UnaryOperator<Change> doubleFilter = change -> 
	{
        String newText = change.getControlNewText();
        if (newText.matches("\\d{0,15}([\\.]\\d{0,4})?"))
        { 
            return change;
        }
        return null;
    };
    
    @FXML public void exitApplication(ActionEvent e)
    {
    	Platform.exit();
    }
    
    @Override
    public void stop()
    {
    	FileMethods filemeth = new FileMethods();
    	filemeth.writeToFile();
    }
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		try
		{
			
			URL url = getClass().getResource(homeFXMLFilePath);
			VBox root = FXMLLoader.load(url);
			Scene scene = new Scene(root, APP_WIDTH, APP_HEIGHT);
			primaryStage.setScene(scene);
			primaryStage.setTitle("DirtManApp");
			primaryStage.getIcons().add(new Image(DirtManMain.class.getResourceAsStream(logoFilePath)));
			primaryStage.show();
			
			FileInputStream in = new FileInputStream(serialFileName);
			ObjectInputStream objIn = new ObjectInputStream(in);
			
			while(true)
			{
				try {
					items = (ArrayList<Item>)objIn.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				catch (EOFException eof)
				{
					break;
				}
			}
			objIn.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}


