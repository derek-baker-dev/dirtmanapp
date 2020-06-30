package dirtman.application;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dirtman.BillMethods;
import dirtman.BilledAs;
import dirtman.FileMethods;
import dirtman.Item;
//import dirtman.ItemType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

//TODO: Implement adding the items back into the bill
//TODO: Add a TableListView to show all current items in bill, possibly be able to edit with that
//TODO: Implement adding or removing inventory 

public class EditBillController 
{
	@FXML private AnchorPane ap;
	private boolean alreadyExecuted = false;
	private boolean alreadyExecuted1 = false;
	private boolean alreadyExecuted2 = false;
	private boolean cantAdd = false;
	private FileMethods list = new FileMethods();
	private BillMethods bill = new BillMethods();
	private int startAt = 24;
	//private File fileToEdit;
	FileInputStream in;
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	FileOutputStream out;
	ArrayList<Item> itemsInBill;
	@FXML ComboBox<String> items = new ComboBox<String>();
	@FXML ComboBox<String> billItems = new ComboBox<String>();
	@FXML TextField changeQuant;
	@FXML TextField addQuant;
	@FXML TextField changePrice;
	@FXML TextField changeName;
	@FXML TextField changeCName;
	@FXML TextField changeAddress;
	@FXML TextField changeCityState;
	@FXML RadioButton length;
	@FXML RadioButton piece;
	@FXML RadioButton hour;
	@FXML CheckBox check;
	@FXML Rectangle rec;
	
	
	
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
	
	@FXML private void finishPress(ActionEvent e)
	{
		try 
		{
			addItemsBackIn();
			workbook.write(out);
			out.close();
		}
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		backPress(e);
	}
	
	@FXML public void itemsComboBoxPopulate()
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
	
	@FXML public void billItemsComboBoxPopulate()
	{
		if (!alreadyExecuted1)
		{
			for (int i = 0; i < itemsInBill.size(); i++)
			{
				billItems.getItems().add(itemsInBill.get(i).getName());
			}
			alreadyExecuted1 = true;
		}
	}
	
	@FXML private void checkPress()
	{
		if (check.isSelected())
		{
			rec.setVisible(false);
		}
		else
		{
			rec.setVisible(true);
		}
	}
	
	@FXML private void removePress(ActionEvent e)
	{
		for (int i = 0; i < itemsInBill.size(); i++)
		{
			Item temp = itemsInBill.get(i);
			if (temp.getName().equals(billItems.getValue()))
			{
				itemsInBill.remove(i);
				break;
			}
		}
		billItems.getItems().clear();
		alreadyExecuted1 = false;
	}
	
	@FXML private void displayCustomerInfo()
	{
		
		Row first = sheet.getRow(17);
		Row second = sheet.getRow(18);
		Row third = sheet.getRow(19);
		
		Cell custName = first.getCell(0);
		Cell custAddr = second.getCell(0);
		Cell custCity = third.getCell(0);
		
		changeCName.setText(custName.getStringCellValue());
		changeAddress.setText(custAddr.getStringCellValue());
		changeCityState.setText(custCity.getStringCellValue());
	}
	
	@FXML private void changeCustomerInfo()
	{
		Row first = sheet.getRow(17);
		Row second = sheet.getRow(18);
		Row third = sheet.getRow(19);
		
		Cell custName = first.getCell(0);
		Cell custAddr = second.getCell(0);
		Cell custCity = third.getCell(0);
		
		custName.setCellValue(changeCName.getText());
		custAddr.setCellValue(changeAddress.getText());
		custCity.setCellValue(changeCityState.getText());
		try
		{
		workbook.write(out);
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		//displayCustomerInfo();
	}
	
	@FXML private void enterPushCust(KeyEvent key)
	{
		if (key.getCode() == KeyCode.ENTER)
		{
			changeCustomerInfo();
		}
	}
	
	@FXML private void enterPushName(KeyEvent key)
	{
		if (key.getCode() == KeyCode.ENTER)
		{
			changeNameInBill();
		}
	}
	
	@FXML private void enterPushQuant(KeyEvent key)
	{
		if (key.getCode() == KeyCode.ENTER)
		{
			changeQuantInBill();
		}
	}
	
	@FXML private void enterPushPrice(KeyEvent key)
	{
		if (key.getCode() == KeyCode.ENTER)
		{
			changePriceInBill();
		}
	}
	
	
	
	@FXML private void changeBilledAsInBill(ActionEvent e)
	{
		BilledAs change = BilledAs.NONE;
		if (length.isSelected())
		{
			change = BilledAs.LENGTH;
		}
		else if (hour.isSelected())
		{
			change = BilledAs.HOUR;
		}
		else if (piece.isSelected())
		{
			change = BilledAs.PIECE;
		}
		
		for (int i = 0; i < itemsInBill.size(); i++)
		{
			Item temp = itemsInBill.get(i);
			if (temp.getName().equals(billItems.getValue()))
			{
				temp.setBilledAs(change);
				itemsInBill.set(i, temp);
				break;
			}
		}
		length.setSelected(false);
		hour.setSelected(false);
		piece.setSelected(false);
		
	}

	private int findRow(String cellContent) 
	{
	    for (Row row : sheet) {
	        for (Cell cell : row) {
	            if (cell.getCellType() == CellType.STRING) {
	                if (cell.getRichStringCellValue().getString().trim().equals(cellContent)) {
	                    return row.getRowNum();
	                }
	            }
	        }
	    }               
	    return 0;
	}
	
	@FXML private void changeQuantInBill()
	{
		Integer change = Integer.parseInt(changeQuant.getText());
		for (int i = 0; i < itemsInBill.size(); i++)
		{
			Item temp = itemsInBill.get(i);
			if (temp.getName().equals(billItems.getValue()))
			{
				temp.setQuant(change);
				itemsInBill.set(i, temp);
				break;
			}
		}
	}
	
	@FXML private void changePriceInBill()
	{
		Double change = Double.parseDouble(changePrice.getText());
		for (int i = 0; i < itemsInBill.size(); i++)
		{
			Item temp = itemsInBill.get(i);
			if (temp.getName().equals(billItems.getValue()))
			{
				temp.setPrice(change);
				itemsInBill.set(i, temp);
				break;
			}
		}
	}
	
	@FXML private void addNewItem()
	{
		if (!cantAdd)
		{
			ArrayList<Item> tempList = list.getItemList();
			for (int i = 0; i < tempList.size(); i++)
			{
				Item temp = tempList.get(i);
				if (temp.getName().equals(items.getValue()))
				{
					temp.setQuant(Integer.parseInt(addQuant.getText()));
					itemsInBill.add(temp);
					addQuant.setText("");
					items.setValue("");
					break;
				}
			}
		}
	}
	
	private void addItemsBackIn()
	{
		int count = 0;
		for (int i = startAt; i < itemsInBill.size(); i++)
		{
			Item temp = itemsInBill.get(count);
			
			Row row = sheet.createRow(i);
			Cell name = row.createCell(1);
			Cell num = row.createCell(4);
			Cell price = row.createCell(5);
			Cell unit = row.createCell(6);
			Cell total = row.createCell(7);
	
			name.setCellValue(temp.getName());
			num.setCellValue(temp.getQuant());
			price.setCellValue(temp.getPrice());
			total.setCellValue(temp.getTotalPrice());
			
			BilledAs billed = temp.getBilledAs();
			switch (billed)
			{
				case HOUR:
					unit.setCellValue("per hour");
					break;
				case LENGTH:
					unit.setCellValue("per foot");
					break;
				case PIECE:
					unit.setCellValue("per piece");
					break;
				default:
					unit.setCellValue("");
			}
			count++;
		}
		
		startAt += itemsInBill.size();
		
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
		
		CellStyle style = workbook.createCellStyle();
		style.setFont(fontSm);
		style.setAlignment(HorizontalAlignment.LEFT);

		
		Row row = sheet.createRow(startAt + 2);
		Cell totalDue = row.createCell(6);
		Cell totalVal = row.createCell(7);
		
		totalDue.setCellValue((String) "Total Due");
		totalVal.setCellValue((Double) bill.getTotalCost(itemsInBill));
		style4.setDataFormat((short) 7);
		totalVal.setCellStyle(style4);
		
		Row row1 = sheet.createRow(startAt + 5);
		Cell thanks = row1.createCell(2);
		
		thanks.setCellValue((String) "Thank you for your business!");
		thanks.setCellStyle(style3);
		
		Row row2 = sheet.createRow(startAt + 9);
		Cell pay = row2.createCell(0);
		
		pay.setCellValue((String) "Please remit payment to Bob Heimes  -  88430 567th Avenue  -  Hartington, NE  68739");
		pay.setCellStyle(style);
		
		cantAdd = true;
	}
	
	@FXML private void changeNameInBill()
	{
		String change = changeName.getText();
		for (int i = 0; i < itemsInBill.size(); i++)
		{
			Item temp = itemsInBill.get(i);
			if (temp.getName().equals(billItems.getValue()))
			{
				temp.setName(change);
				itemsInBill.set(i, temp);
				break;
			}
		}
		billItems.getItems().clear();
		alreadyExecuted1 = false;
	}
	
	
	@SuppressWarnings("unchecked")
	@FXML private void getStarted()
	{
		
		if (!alreadyExecuted2)
		{
			try
			{
				itemsInBill = new ArrayList<Item>();
				Stage stage = (Stage) ap.getScene().getWindow();
				
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Bill File");
				fileChooser.setInitialDirectory(new File(DirtManMain.excelFileDirectory));
				ExtensionFilter ex = new ExtensionFilter("Excel Files", "*.xlsx");
				fileChooser.getExtensionFilters().add(ex);
				fileChooser.setSelectedExtensionFilter(ex);
				File fileToEdit = fileChooser.showOpenDialog(stage);
				
				in = new FileInputStream(fileToEdit);
				workbook = new XSSFWorkbook(in);
				sheet = workbook.getSheetAt(0);
				
				//displayCustomerInfo();
				
				Row first = sheet.getRow(17);
				Row second = sheet.getRow(18);
				Row third = sheet.getRow(19);
				
				Cell custName = first.getCell(0);
				Cell custAddr = second.getCell(0);
				Cell custCity = third.getCell(0);
				
				String cuName = custName.getStringCellValue(); 
				String cuAddr = custAddr.getStringCellValue();
				String cuCity = custCity.getStringCellValue();
				//changeCName.setText(custName.getStringCellValue());
				//changeAddress.setText(custAddr.getStringCellValue());
				//changeCityState.setText(custCity.getStringCellValue());
				
				Row total = sheet.getRow(findRow("Total Due"));
				Cell due = total.getCell(6);
				Cell money = total.getCell(7);
				due.setCellValue("");
				money.setCellValue("");
				
				Row thank = sheet.getRow(findRow("Thank you for your business!"));
				Cell thanks = thank.getCell(2);
				thanks.setCellValue("");
				
				Row pay = sheet.getRow(findRow("Please remit payment to Bob Heimes  -  88430 567th Avenue  -  Hartington, NE  68739"));
				Cell payment = pay.getCell(0);
				payment.setCellValue("");
				
				out = new FileOutputStream(fileToEdit);
				workbook.write(out);
				
				FileInputStream listIn = new FileInputStream(FilenameUtils.removeExtension(fileToEdit.getName()) + ".ser");
				ObjectInputStream inList = new ObjectInputStream(listIn);
				
				while (true)
				{
					try
					{
						itemsInBill = (ArrayList<Item>) inList.readObject();
					}
					catch (EOFException e)
					{
						break;
					}
					catch (Exception ae)
					{
						ae.printStackTrace();
					}
				}
				
				for (int i = 24; i < sheet.getLastRowNum(); i++)
				{
					if (sheet.getRow(i) == null)
					{
						continue;
					}
					Row row = sheet.getRow(i);
					for (int z = 0; z < 8; z++)
					{
						if (row.getCell(z) == null)
						{
							continue;
						}
						Cell temp = row.getCell(z);
						temp.setCellValue("");
					}
				}
				
				workbook.write(out);
				//out.close();

				
				alreadyExecuted2 = true;
				
				
			}
			catch (NullPointerException n)
			{
				n.printStackTrace();
				try 
				{
					Parent priceCheckParent = FXMLLoader.load(getClass().getResource(DirtManMain.billingFXMLFilePath));
					Scene priceCheckScene = new Scene(priceCheckParent);
					
					Stage window = (Stage)(( (Node) ap)).getScene().getWindow();
					window.setScene(priceCheckScene);
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
		}
		
	}
}

