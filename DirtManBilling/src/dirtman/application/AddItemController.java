package dirtman.application;

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
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import dirtman.AutoCompleteComboBoxListener;
import dirtman.BilledAs;
import dirtman.FileMethods;
import dirtman.Item;
import dirtman.ItemType;


public class AddItemController 
{
	@FXML AnchorPane ap;
	@FXML TextField name;
	@FXML TextField price;
	@FXML TextField num;
	@FXML Label invalid;
	@FXML ComboBox<String> items = new ComboBox<String>();
	FileMethods in = new FileMethods();
	AutoCompleteComboBoxListener auto;
	@FXML RadioButton length;
	@FXML RadioButton piece;
	@FXML RadioButton hour;
	@FXML RadioButton electrical;
	@FXML RadioButton plumbing;
	@FXML RadioButton labor;
	@FXML RadioButton equipment;
	@FXML ToggleGroup toggle;
	@FXML ToggleGroup toggle1;
	private boolean alreadyExecuted = false;
	private boolean alreadyExecuted1 = false;
	
	
	@FXML private void getReady()
	{
		if (!alreadyExecuted1)
		{
			TextFormatter<Integer> textFormatter = 
	                new TextFormatter<Integer>(new IntegerStringConverter(),null, DirtManMain.integerFilter);
	        num.setTextFormatter(textFormatter);
	        
	        TextFormatter<Double> textFormatter1 =
	        		new TextFormatter<Double>(new DoubleStringConverter(), null, DirtManMain.doubleFilter);
	        price.setTextFormatter(textFormatter1);
	        
	        alreadyExecuted1 = true;
	        
	        auto = new AutoCompleteComboBoxListener(items);
	        
	        toggle = length.getToggleGroup();
	        toggle1 = electrical.getToggleGroup();
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
	
	@FXML private void setVisiblity(ActionEvent e)
	{
		RadioButton a = (RadioButton)e.getSource();
		if (a.getText().equalsIgnoreCase("plumbing") || a.getText().equalsIgnoreCase("electrical"))
		{
			num.setVisible(true);
			hour.setVisible(false);
			hour.setSelected(false);
			length.setVisible(true);
			piece.setVisible(true);
			num.setText("");
		}
		else 
		{
			hour.setVisible(true);
			length.setVisible(false);
			piece.setVisible(false);
			hour.setSelected(true);
			num.setVisible(false);
			num.setText("0");
		}
	}
	
	@FXML private void addNewItem() 
	{
		Item newItem = new Item();
		if (checkPress() && !name.getText().isEmpty())
		{
			if (length.isSelected())
			{
				newItem.setBilledAs(BilledAs.LENGTH);
			}
			else if (piece.isSelected())
			{
				newItem.setBilledAs(BilledAs.PIECE);
			}
			else if (hour.isSelected())
			{
				newItem.setBilledAs(BilledAs.HOUR);
			}
			
			if (electrical.isSelected())
			{
				newItem.setType(ItemType.ELECTRICAL);
			}
			else if (plumbing.isSelected())
			{
				newItem.setType(ItemType.PLUMBING);
			}
			else if (labor.isSelected())
			{
				newItem.setType(ItemType.LABOR);
			}
			else if (equipment.isSelected())
			{
				newItem.setType(ItemType.EQUIPMENT);
			}
				
			newItem.setName(name.getText());
			if (price.getText().isEmpty())
			{
				price.setText("0.0");
			}
			newItem.setPrice(Double.parseDouble(price.getText()));
			if (num.getText().isEmpty())
			{
				num.setText("0");
			}
			newItem.setQuant(Integer.parseInt(num.getText()));
			in.addItem(newItem);
			name.setText("");
			price.setText("");
			num.setText("");
			
			length.setSelected(false);
			piece.setSelected(false);
			hour.setSelected(false);
			
			electrical.setSelected(false);
			labor.setSelected(false);
			equipment.setSelected(false);
			plumbing.setSelected(false);
			
			items.getItems().clear();
			alreadyExecuted = false;
		}
	}
	
	@FXML private void clearRedText()
	{
		invalid.setText("");
	}
	
	@FXML public void comboBoxPopulate()
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
	
	@FXML private void deleteAnItem(ActionEvent e)
	{
		String a = items.getValue();
		in.deleteItem(a);
		items.setValue("");
		items.getItems().remove(a);
		
	}
	
	@FXML private void enterPush(KeyEvent key)
	{
		if (checkPress() && key.getCode() == KeyCode.ENTER)
		{
			addNewItem();
		}
	}
	
	private boolean checkPress()
	{
		return toggle.getSelectedToggle() != null && toggle1.getSelectedToggle() != null;
	}
	
	
}
