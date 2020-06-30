package dirtman.application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class BillingController
{
	@FXML CheckBox check;
	@FXML Label mark;
	@FXML TextField newMark;
	@FXML Label highMark;
	@FXML TextField newHighMark;
	
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
	
	@FXML private void newBillPress(ActionEvent e)
	{
		try 
		{
			Parent parent = FXMLLoader.load(getClass().getResource(DirtManMain.newBillFXMLFilePath));
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
		Double mrkup = DirtManMain.markup * 100.0;
		String mrk = mrkup.toString();
		
		Double hmrkup = DirtManMain.expensiveMarkup * 100.0;
		String hmrk = hmrkup.toString();
		
		mark.setText(mrk.substring(1,mrk.length()));
		highMark.setText(hmrk.substring(1, 3));
	}
	
	@FXML private void enterPush(KeyEvent key)
	{
		if (key.getCode() == KeyCode.ENTER)
		{
			changeMarkup();
		}
	}
	
	private void changeMarkup()
	{
		mark.setText(newMark.getText());
		Double newMrk = Double.parseDouble("1" + newMark.getText());
		newMrk = newMrk / 100;
		DirtManMain.markup = newMrk;
		newMark.setText("");
	}
	
	@FXML private void enterPushh(KeyEvent key)
	{
		if (key.getCode() == KeyCode.ENTER)
		{
			changeHighMarkup();
		}
	}
	
	private void changeHighMarkup()
	{
		highMark.setText(newHighMark.getText());
		Double newHMrk = Double.parseDouble("1" + newHighMark.getText());
		newHMrk = newHMrk / 100;
		DirtManMain.expensiveMarkup = newHMrk;
		newHighMark.setText("");
	}
	
	@FXML private void setVisibility()
	{
		if (check.isSelected())
		{
			newMark.setVisible(true);
			newHighMark.setVisible(true);
		}
		else
		{
			newMark.setVisible(false);
			newHighMark.setVisible(false);
		}
	}
	
	
	@FXML private void editBillPress(ActionEvent e)
	{
		try 
		{
			Parent parent = FXMLLoader.load(getClass().getResource(DirtManMain.editBillFXMLFilePath));
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
	
	@FXML private void openBillPress(ActionEvent e)
	{
		FileChooser filechooser = new FileChooser();
		try
		{
			filechooser.setTitle("Open Bill File");
			ExtensionFilter ex = new ExtensionFilter("Excel Files", "*.xlsx");
			filechooser.getExtensionFilters().add(ex);
			filechooser.setSelectedExtensionFilter(ex);
			filechooser.setInitialDirectory(new File(DirtManMain.excelFileDirectory));
			Desktop desktop = Desktop.getDesktop();
			desktop.open(filechooser.showOpenDialog((Stage)(( (Node) e.getSource())).getScene().getWindow()));
		}
		catch(NullPointerException n)
		{
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		
	}
	
	@FXML private void printBillPress(ActionEvent e)
	{
		try
		{
			FileChooser filechooser = new FileChooser();
			filechooser.setTitle("Print Bill File");
			ExtensionFilter ex = new ExtensionFilter("Excel Files", "*.xlsx");
			filechooser.getExtensionFilters().add(ex);
			filechooser.setSelectedExtensionFilter(ex);
			filechooser.setInitialDirectory(new File(DirtManMain.excelFileDirectory));
			Desktop desktop = Desktop.getDesktop();
			desktop.print(filechooser.showOpenDialog((Stage)(( (Node) e.getSource())).getScene().getWindow()));
		}
		catch(NullPointerException n)
		{
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		
	}
}
















