package dirtman.application;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DirtManController
{
	@FXML private Button priceEdit;
	@FXML private Button priceCheck;
	@FXML private Button billing;
	@FXML private Button inventory;
	@FXML private Button advanced;
	
	@FXML private void pricePress(ActionEvent e)
	{
		try 
		{
			Parent parent = FXMLLoader.load(getClass().getResource(DirtManMain.changeFXMLFilePath));
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
	
	@FXML private void billingPress(ActionEvent e)
	{
		try 
		{
			Parent parent = FXMLLoader.load(getClass().getResource(DirtManMain.billingFXMLFilePath));
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
	
	@FXML private void inventoryPress(ActionEvent e)
	{
		try 
		{
			 Parent parent = FXMLLoader.load(getClass().getResource(DirtManMain.inventoryFXMLFilePath));
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
	
	
	@FXML private void advancedPress(ActionEvent e)
	{
		try 
		{
			 Parent parent = FXMLLoader.load(getClass().getResource(DirtManMain.advancedFXMLFilePath));
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
	
	@FXML private void addItemPress(ActionEvent e)
	{
		try 
		{
			Parent parent = FXMLLoader.load(getClass().getResource(DirtManMain.addFXMLFilePath));
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
	
}





























