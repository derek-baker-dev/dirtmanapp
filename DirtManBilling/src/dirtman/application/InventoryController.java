package dirtman.application;

import java.net.URL;

import dirtman.AutoCompleteComboBoxListener;
import dirtman.FileMethods;
import dirtman.Item;
import dirtman.ItemType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class InventoryController 
{
	FileMethods in = new FileMethods();
	@FXML ComboBox<String> items = new ComboBox<String>();
	@FXML RadioButton plus = new RadioButton();
	@FXML RadioButton minus = new RadioButton();
	@FXML Label quant = new Label();
	@FXML TextField customNum = new TextField();
	boolean alreadyExecuted = false;
	boolean alreadyExecuted1 = false;
	AutoCompleteComboBoxListener auto;
	
	
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
	
	@FXML public void comboBoxPopulate()
	{
		if (!alreadyExecuted)
		{
			for (int i = 0; i < in.getItemList().size(); i++)
			{
				Item temp = in.getItemList().get(i);
				if (temp.getType() == ItemType.LABOR || temp.getType() == ItemType.EQUIPMENT)
				{
					continue;
				}
				else
				{
					items.getItems().add(temp.getName());
				}
			}
			alreadyExecuted = true;
		}
	}
	
	@FXML public void getReady()
	{
		if (!alreadyExecuted1)
		{
			auto = new AutoCompleteComboBoxListener(items);
			alreadyExecuted1 = true;
		}
	}
	
	@FXML public void enterPush(KeyEvent key)
	{
		if (key.getCode() == KeyCode.ENTER)
		{
			changeNumPress();
		}
	}
	
	@FXML private void getListPress(ActionEvent e)
	{
		try
		{
			URL url = getClass().getResource("itemlist.fxml");
			VBox root = FXMLLoader.load(url);
			Scene itemListScene = new Scene(root, 1000, 800);
			
			Stage newStage = new Stage();
			newStage.setTitle("Item List");
			newStage.getIcons().add(new Image(DirtManMain.class.getResourceAsStream("logo.jpg")));
			newStage.setScene(itemListScene);
			newStage.show();
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
	}
	
	@FXML private void changeNumPress()
	{
		String itemName = items.getValue();
		Integer numToChange = Integer.parseInt(customNum.getText());
		if (numToChange < 0)
		{
			if (in.getNum(itemName) < Math.abs(numToChange))
			{
				in.changeNum(itemName, 0);
			}
			else
			{
				in.changeNum(itemName, in.getNum(itemName) + (numToChange));
			}
		}
		else
		{
			in.changeNum(itemName, in.getNum(itemName) + (numToChange));
		}
		customNum.setText("");
		getTheNum();
	}
	
	@FXML private void getTheNum()
	{
		String itemName = items.getValue();
		quant.setText(in.getNum(itemName).toString());
	}
	
	@FXML private void numberPress(ActionEvent e)
	{
		String itemName = items.getValue();
		String numToChange = ((Button) e.getSource()).getText();
		
		if (plus.isSelected())
		{
			in.changeNum(itemName, in.getNum(itemName) + Integer.parseInt(numToChange));
		}
		else if (minus.isSelected())
		{
			if (in.getNum(itemName) < Integer.parseInt(numToChange))
			{
				in.changeNum(itemName, 0);
			}
			else
			{
				in.changeNum(itemName, in.getNum(itemName) - Integer.parseInt(numToChange));
			}
		}
		getTheNum();
	}
	
}
