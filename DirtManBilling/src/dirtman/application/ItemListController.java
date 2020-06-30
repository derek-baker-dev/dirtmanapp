package dirtman.application;

import dirtman.BilledAs;
import dirtman.FileMethods;
import dirtman.Item;
import dirtman.ItemType;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ItemListController 
{
	FileMethods ab = new FileMethods();
	@FXML TableView<Item> a = new TableView<Item>();
	@FXML TableColumn<String, Item> first = new TableColumn<String, Item>();
	@FXML TableColumn<Double, Item> second = new TableColumn<Double, Item>();
	@FXML TableColumn<Integer, Item> third = new TableColumn<Integer, Item>();
	@FXML TableColumn<ItemType, Item> fourth = new TableColumn<ItemType, Item>();
	@FXML TableColumn<BilledAs, Item> fifth = new TableColumn<BilledAs, Item>();
	boolean alreadyExecuted = false;
	
	@FXML public void addItems()
	{
		if(!alreadyExecuted)
		{
			first.setCellValueFactory(new PropertyValueFactory<>("name"));
			second.setCellValueFactory(new PropertyValueFactory<>("price"));
			third.setCellValueFactory(new PropertyValueFactory<>("quant"));
			fourth.setCellValueFactory(new PropertyValueFactory<>("type"));
			fifth.setCellValueFactory(new PropertyValueFactory<>("billedAs"));
			
			for (int i = 0; i < ab.getItemList().size(); i++)
			{
				a.getItems().add(ab.getItemList().get(i));
				
			}
			alreadyExecuted = true;
		}
	}
	
}
