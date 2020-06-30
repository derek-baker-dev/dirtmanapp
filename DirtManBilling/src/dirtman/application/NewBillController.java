package dirtman.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dirtman.BillMethods;
import dirtman.BilledAs;
import dirtman.FileMethods;
import dirtman.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

//TODO: Add TableListView to show all current items

public class NewBillController 
{ 
	@FXML ComboBox<String> items = new ComboBox<String>();
	@FXML TextField quantIn = new TextField();
	@FXML TextField cName;
	@FXML TextField cAddress;
	@FXML TextField cCityState;
	String excelFileName = DirtManMain.baseExcelFile;
	FileInputStream in;
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	FileOutputStream out;
	FileOutputStream serOut;
	FileMethods list = new FileMethods();
	BillMethods bill = new BillMethods();
	Integer count = 24;
	CellStyle style;
	CellStyle style1;
	boolean alreadyExecuted = false;
	boolean alreadyExecuted1 = false;
	
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
	
	@FXML private void backPress(ActionEvent e)
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
	
	@FXML public void comboBoxPopulate()
	{
		if (!alreadyExecuted)
		{
			for (int i = 0; i < list.getItemList().size(); i++)
			{
				items.getItems().add(list.getItemList().get(i).getName());
			}
			alreadyExecuted = true;
		}
	}
	
	@FXML private void finishPress(ActionEvent e)
	{
		try
		{
			finishBill();
			workbook.write(out);
			out.close();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		backPress(e);
	}
	
	@FXML private void createFile()
	{
		if (!alreadyExecuted1)
		{
			try
			{
				Stage stage = (Stage)(( (Node) cName).getScene().getWindow());
				FileChooser fileChooser = new FileChooser();
				fileChooser.setInitialDirectory(new File("../DirtManBilling"));
				fileChooser.setTitle("Save New Bill");
				ExtensionFilter ex = new ExtensionFilter("Excel Files", "*.xlsx");
				fileChooser.getExtensionFilters().add(ex);
				fileChooser.setSelectedExtensionFilter(ex);
				
				in = new FileInputStream(new File(excelFileName));
				workbook = new XSSFWorkbook(in);
				sheet = workbook.getSheetAt(0);
				File exFile = fileChooser.showSaveDialog(stage);
				out = new FileOutputStream(exFile);
				serOut = new FileOutputStream(new File(FilenameUtils.removeExtension(exFile.getName()) + ".ser"));
				Font font = workbook.createFont();
				font.setFontHeightInPoints((short) 12);
				font.setFontName("Bookman Old Style");
				style = workbook.createCellStyle();
				style1 = workbook.createCellStyle();
				style.setFont(font);
				style1.setFont(font);
				style1.setAlignment(HorizontalAlignment.RIGHT);
				style.setAlignment(HorizontalAlignment.CENTER);
				style1.setDataFormat((short) 4);
				
			}
			catch (NullPointerException n)
			{
				try 
				{
					Parent parent = FXMLLoader.load(getClass().getResource(DirtManMain.billingFXMLFilePath));
					Scene scene = new Scene(parent);
					
					Stage window = (Stage)(( (Node) cName)).getScene().getWindow();
					window.setScene(scene);
					window.show();
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}
			}
		 	catch (IOException ioe)
			{
				ioe.printStackTrace();
			}
			alreadyExecuted1 = true;
		}
	}
	
	@FXML private void addToBill(ActionEvent e)
	{
		String itemName = items.getValue();
		String unit = "";
		Item added = new Item(itemName, list.getPrice(itemName), Integer.parseInt(quantIn.getText()), list.getType(itemName), list.getBilledAs(itemName));
		
		if (added.getBilledAs() == BilledAs.LENGTH)
		{
			unit = "per foot";
		}
		else if (added.getBilledAs() == BilledAs.PIECE)
		{
			unit = "per piece";
		}
		else if (added.getBilledAs() == BilledAs.HOUR)
		{
			unit = "per hour";
		}
			
		if (added.isNonInventory())
		{
		}
		else if (added.getPrice() > 500)
		{
			added.setPrice(added.getPrice() * DirtManMain.expensiveMarkup);
		}
		else
		{
			added.setPrice(added.getPrice() * DirtManMain.markup);
		}
		
		Object[] stuff = new Object[] {itemName, added.getPrice(), Integer.parseInt(quantIn.getText()), added.getTotalPrice(), unit };
		
		Row row = sheet.createRow(count);
        Cell name = row.createCell(1);
        Cell quant = row.createCell(4);
        Cell price = row.createCell(5);
        Cell unitCell = row.createCell(6);
        Cell total = row.createCell(7);
        
        name.setCellValue((String) stuff[0]);
        quant.setCellValue((Integer) stuff[2]);
        price.setCellValue((Double) stuff[1]);
        unitCell.setCellValue((String) stuff[4]);
        total.setCellValue((Double) stuff[3]);
            
        name.setCellStyle(style);
        quant.setCellStyle(style);
        unitCell.setCellStyle(style);
        total.setCellStyle(style1);
        price.setCellStyle(style1);
            
            
            
		bill.addItemToABill(added);
		items.setValue("");
		quantIn.setText("");
		count++;
	}
	
	private void finishBill()
	{
		
		Font fontBI = workbook.createFont();
		fontBI.setBold(true);
		fontBI.setFontHeightInPoints((short) 12);
		fontBI.setItalic(true);
		fontBI.setFontName("Bookman Old Style");
		
		Font fontB = workbook.createFont();
		fontB.setBold(true);
		fontB.setFontHeightInPoints((short) 12);
		fontB.setFontName("Bookman Old Style");
		
		Font fontSm = workbook.createFont();
		fontSm.setBold(false);
		fontSm.setFontHeightInPoints((short) 11);
		fontSm.setFontName("Bookman Old Style");
		
		Font fontNorm = workbook.createFont();
		fontNorm.setFontHeightInPoints((short) 12);
		fontNorm.setFontName("Bookman Old Style");
		
		CellStyle style3 = workbook.createCellStyle();
		style3.setFont(fontBI);
		
		CellStyle style4 = workbook.createCellStyle();
		style4.setFont(fontB);
		
		style.setFont(fontSm);
		style.setAlignment(HorizontalAlignment.LEFT);
		
		Row row = sheet.createRow(count + 2);
		Cell totalDue = row.createCell(6);
		Cell totalVal = row.createCell(7);
		
		totalDue.setCellValue((String) "Total Due");
		totalVal.setCellValue((Double) bill.getTotalCost());
		totalDue.setCellStyle(style4);
		style4.setDataFormat((short) 7);
		totalVal.setCellStyle(style4);
		
		
		Row row1 = sheet.createRow(count + 5);
		Cell thanks = row1.createCell(2);
		
		thanks.setCellValue((String) "Thank you for your business!");
		thanks.setCellStyle(style3);
		
		Row row2 = sheet.createRow(count + 9);
		Cell pay = row2.createCell(0);
		
		pay.setCellValue((String) "Please remit payment to Bob Heimes  -  88430 567th Avenue  -  Hartington, NE  68739");
		pay.setCellStyle(style);
		
		style.setFont(fontNorm);
		
		Row first = sheet.createRow(17);
		Row second = sheet.createRow(18);
		Row third = sheet.createRow(19);
		
		Cell custName = first.createCell(0);
		Cell custAddr = second.createCell(0);
		Cell custCity = third.createCell(0);
		
		custName.setCellStyle(style);
		custAddr.setCellStyle(style);
		custCity.setCellStyle(style);
		
		custName.setCellValue((String) cName.getText());
		custAddr.setCellValue((String) cAddress.getText());
		custCity.setCellValue((String) cCityState.getText());
		
		ArrayList<Item> billItems = bill.getBillItems();
		try
		{
			ObjectOutputStream billOut = new ObjectOutputStream(serOut);
			billOut.writeObject(billItems);
			billOut.close();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		
		for (int i = 0; i < billItems.size(); i++) 
		{
			Item temp = billItems.get(i);
			int numUsed = temp.getQuant();
			
			if (temp.isNonInventory())
			{
				continue;
			}
			else if (temp.getQuant() < list.getNum(temp.getName()))
			{
				list.changeNum(temp.getName(), list.getNum(temp.getName()) - numUsed);
			}
			else
			{
				list.changeNum(temp.getName(), 0);
			}
		}	
	}
}
