package dirtman.application;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;


public class AdvancedController 
{
	@FXML RadioButton csv;
	@FXML RadioButton ser;
	@FXML CheckBox change;
	@FXML Button button;
	private String filename;
	@FXML Label alert;
	
	
	
	@FXML private void homePress(ActionEvent e)
	{
		try 
		{
			Parent parent = FXMLLoader.load(getClass().getResource(DirtManMain.homeFXMLFilePath));
			Scene scene = new Scene(parent);
			
			Stage window = (Stage)(( (Node) e.getSource())).getScene().getWindow();
			window.setScene(scene);
			window.show();
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
	}
	
	@FXML private void getStarted()
	{
		ser.setSelected(DirtManMain.flip);
		csv.setSelected(!DirtManMain.flip);
	}
	
	@FXML private void checkbxPush()
	{
		if (change.isSelected())
		{
			button.setVisible(true);
		}
		else
		{
			button.setVisible(false);
			alert.setText("");
		}
	}
	
	@FXML private void changeFileType(ActionEvent e)	
	{
		if (ser.isSelected())
		{
			DirtManMain.flip = true;
		}
		else
		{
			DirtManMain.flip = false;
		}
	}
	
	@FXML private void changeFilePress(ActionEvent e) 
	{
		alert.setText("");
		try
		{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open New File");
			ExtensionFilter serEx = new ExtensionFilter("Serialized Files", "*.ser");
			ExtensionFilter csvEx = new ExtensionFilter("CSV Files", "*.csv");
			fileChooser.getExtensionFilters().add(serEx);
			fileChooser.getExtensionFilters().add(csvEx);
			if (ser.isSelected())
			{
				fileChooser.setInitialDirectory(new File("../DirtManBilling"));
				fileChooser.setSelectedExtensionFilter(serEx);
			}
			else
			{
				fileChooser.setInitialDirectory(new File("../DirtManBilling"));
				fileChooser.setSelectedExtensionFilter(csvEx);
			}
			File fileIn = fileChooser.showOpenDialog((Stage)(( (Node) e.getSource())).getScene().getWindow());
			filename = fileIn.getName();
			if (ser.isSelected())
			{
				DirtManMain.serialFileName = filename;
			}
			else
			{
				DirtManMain.csvFileName = filename;
			}
		}
		catch (NullPointerException n)
		{
			alert.setText("NO FILE SELECTED!!!");
			alert.setTextAlignment(TextAlignment.CENTER);
		}
		
	}
	
}
