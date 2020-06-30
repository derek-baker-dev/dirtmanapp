package dirtman.application;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dirtman.BillMethods;
import dirtman.BilledAs;
import dirtman.FileMethods;
import dirtman.Item;
import dirtman.ItemType;
@SuppressWarnings("unused")
public class Driver {

	public static void main(String[] args) throws IOException 
	{
		/*FileInputStream in = new FileInputStream(new File("onelastry.xlsx"));
		XSSFWorkbook workbook = new XSSFWorkbook(in);
		XSSFSheet sheet = workbook.getSheetAt(0);
		BillMethods bill = new BillMethods();*/
		File a = new File("again.xlsx");
		String b = a.getName();
		b = FilenameUtils.removeExtension(b);
		b = b.concat(".ser");
		System.out.println(b);
		
		//FileOutputStream out = new FileOutputStream(new File("again.xlsx"));
		/*
		Row first = sheet.getRow(17);
		Row second = sheet.getRow(18);
		Row third = sheet.getRow(19);
		
		Cell custName = first.getCell(0);
		Cell custAddr = second.getCell(0);
		Cell custCity = third.getCell(0);
		
		System.out.println(custName.getStringCellValue());
		System.out.println(custAddr.getStringCellValue());
		System.out.println(custCity.getStringCellValue());
		
		
		/*Row row = sheet.getRow(24);
		Cell name = row.getCell(1); 
		name.setCellValue("Does this work");
		workbook.write(out);
		Cell name1 = row.getCell(1);
		System.out.println(name1.getStringCellValue());
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
		
		Item item = new Item(name.getStringCellValue(), price.getNumericCellValue(), (int) num.getNumericCellValue(), ItemType.UNCATEGORIZED, billed);
		a.add(item);
		
		
		
		/*for (int i = 24; i < sheet.getLastRowNum(); i++)
		{
			if (sheet.getRow(i) == null)
			{
				continue;
			}
			Row row = sheet.getRow(i);
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
			
			System.out.println(name);
			System.out.println(num);
			System.out.println(price);
			System.out.println(unit);
			
			//Item item = new Item(name.getStringCellValue(), price.getNumericCellValue(), (int) num.getNumericCellValue(), ItemType.UNCATEGORIZED, billed);
		//	a.add(item);*/
			
		//}
		
	}

}
