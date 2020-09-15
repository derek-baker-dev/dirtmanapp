package dirtman;

import java.io.File;
import java.io.FileInputStream;
//import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//TODO:FIX THIS
public class BillMethods 
{
	ArrayList<Item> items;
	Double totalCost;
	FileInputStream in;
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	 
	public BillMethods()
	{
		 items = new ArrayList<Item>();
	}
	
	//Don't think this is working
	public Item getBillItem(File inFile, int pos)
	{
		Item item = null;
		try
		{
			FileInputStream in = new FileInputStream(inFile);
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(in);
			XSSFSheet sheet = workbook.getSheetAt(0);
			//ArrayList<Item> a = new ArrayList<Item>();
			
			//FileOutputStream out = new FileOutputStream(new File("again.xlsx"));
			
			
			Row row = sheet.getRow(24);
			Cell name = row.getCell(1);
			Cell num = row.getCell(4);
			Cell price = row.getCell(5);
			Cell unit = row.getCell(6);
			
			BilledAs billed = BilledAs.NONE;
			switch (unit.getStringCellValue())
			{
			case "per piece":
				billed = BilledAs.PIECE;
				break;
			case "per foot":
				billed = BilledAs.LENGTH;
				break;
			case "per hour":
				billed = BilledAs.HOUR;
				break;
			}
			
			item = new Item(name.getStringCellValue(), price.getNumericCellValue(), (int) num.getNumericCellValue(), ItemType.UNCATEGORIZED, billed);
			//items.add(item);
		}
		catch (Exception e)
		{
			
		}
		return item;
	}
	
	public ArrayList<Item> getBillItems(File inFile, int start, int end)
	{
		for (int i = start; i <= end; i++)
		{
			items.add(getBillItem(inFile, i));
		}
		
		return items;
	}
	
	public void addItemToABill(String name, Double price, Integer num, ItemType type, BilledAs billed)
	{
		Item newItem = new Item(name, price, num, type, billed);
		items.add(newItem);
	}
	
	public void addItemToABill(Item item)
	{
		items.add(item);
	}
	
	
	public Double getTotalCost()
	{
		ArrayList<Item> temp = items;
		Double price = 0.0;
		
		for(int i = 0; i < temp.size(); i++)
		{
			price += temp.get(i).getTotalPrice();
		}
		
		return price;
	}
	
	public Double getTotalCost(ArrayList<Item> list)
	{
		ArrayList<Item> temp = list;
		Double price = 0.0;
		
		for(int i = 0; i < temp.size(); i++)
		{
			price += temp.get(i).getTotalPrice();
		}
		
		return price;
	}
	
	public ArrayList<Item> getBillItems()
	{
		return items;
	}
}
