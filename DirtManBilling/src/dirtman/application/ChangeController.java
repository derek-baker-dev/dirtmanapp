package dirtman.application;

import dirtman.AutoCompleteComboBoxListener;
import dirtman.BilledAs;
import dirtman.FileMethods;
import dirtman.ItemType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
 

public class ChangeController 
{
	@FXML ComboBox<String> items = new ComboBox<String>();
	FileMethods in = new FileMethods();
	@FXML Label price;
	@FXML Label unit;
	@FXML Label type;
	@FXML TextField newPriceIn;
	@FXML RadioButton length;
	@FXML RadioButton piece;
	@FXML RadioButton hour;
	@FXML RadioButton equipment;
	@FXML RadioButton labor;
	@FXML RadioButton electrical;
	@FXML RadioButton plumbing;
	@FXML Label currentName;
	@FXML TextField newName;
	AutoCompleteComboBoxListener auto;
	boolean alreadyExecuted = false;
	boolean alreadyExecuted1 = false;
	
	@FXML public void getReady()
	{
		if (!alreadyExecuted1)
		{
			auto = new AutoCompleteComboBoxListener(items);
			alreadyExecuted1 = true;
		}
	}
	
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
	
	@FXML private void comboBoxPopulate()
	{
		if (!alreadyExecuted)
		{
			for (int i = 0; i < in.getItemList().size(); i++)
			{
				items.getItems().add(in.getItemList().get(i).getName());
			}
			alreadyExecuted = true;
		}
	}
	
	private void getAPrice()
	{
		String name = items.getValue();
		Double priceOut = in.getPrice(name);
		price.setText(String.format("$%.2f", priceOut));
		BilledAs billed = in.getBilledAs(name);
		
		switch(billed)
		{
			case LENGTH:
				unit.setText("foot");
				break;
			case PIECE:
				unit.setText("piece");
				break;
			case HOUR:
				unit.setText("hour");
				break;
			default:
				unit.setText("NA");
		}
	}
	
	@FXML private void enterPush1(KeyEvent key)
	{
		if (key.getCode() == KeyCode.ENTER)
		{
			changeThePrice();
		}
	}
	
	@FXML private void changeThePrice()
	{
		if (items.getValue() != null || !items.getValue().isEmpty())
		{
			String name = items.getValue();
			Double newPrice = Double.parseDouble(newPriceIn.getText());
			in.changePrice(name, newPrice);
			getAPrice();
			newPriceIn.setText("");
		}
	}
	
	@FXML private void changeBilledAs()
	{
		if (items.getValue() != null)
		{
			String name = items.getValue();
			BilledAs billed = BilledAs.NONE;
			
			if (length.isSelected())
			{
				billed = BilledAs.LENGTH;
				unit.setText("foot");
			}
			else if (piece.isSelected())
			{
				billed = BilledAs.PIECE;
				unit.setText(billed.toString().toLowerCase());
			}
			else if (hour.isSelected())
			{
				billed = BilledAs.HOUR;
				unit.setText(billed.toString().toLowerCase());
			}
			
			in.changeBilledAs(name, billed);
			
			length.setSelected(false);
			piece.setSelected(false);
			hour.setSelected(false);
		}
		
	}
	
	@FXML private void enterPush2(KeyEvent key)
	{
		if (key.getCode() == KeyCode.ENTER)
		{
			changeTheName();
		}
	}
	
	@FXML private void showStuff()
	{
		if (items.getValue() != null)
		{
			getAPrice();
			displayName();
			displayType();
		}
	}
	
	@FXML private void changeTheType()
	{
		if (items.getValue() != null)
		{
			ItemType type = ItemType.UNCATEGORIZED;
			if (electrical.isSelected())
			{
				type = ItemType.ELECTRICAL;
			}
			else if (plumbing.isSelected())
			{
				type = ItemType.PLUMBING;
			}
			else if (labor.isSelected())
			{
				type = ItemType.LABOR;
				in.changeNum(items.getValue(), 0);
				in.changeBilledAs(items.getValue(), BilledAs.HOUR);
			}
			else if (equipment.isSelected())
			{
				type = ItemType.EQUIPMENT;
				in.changeNum(items.getValue(), 0);
				in.changeBilledAs(items.getValue(), BilledAs.HOUR);
			}
			in.changeType(items.getValue(), type);
			displayType();
		}
	}
	
	private void displayName()
	{
		currentName.setText(items.getValue());
	}
	
	private void displayType()
	{
		type.setText(in.getType(items.getValue()).toString().toLowerCase());
	}
	
	private void changeTheName()
	{
		if (items.getValue() != null)
		{
			in.changeName(items.getValue(), newName.getText());
			items.getItems().clear();
			items.setValue(newName.getText());
			displayName();
			newName.setText("");
			alreadyExecuted = false;	
		}
	}
	
}
