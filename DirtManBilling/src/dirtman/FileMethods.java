package dirtman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


import dirtman.application.DirtManMain;

public class FileMethods 
{
	private File inFile;
	private PrintWriter printer;
	private FileOutputStream out;
	private ObjectOutputStream objOut;
	private ArrayList<Item> items = DirtManMain.items;
	private ArrayList<Item> csvItems;
	boolean flip = DirtManMain.flip;
	
	
	public FileMethods()
	{
		if (flip == false)
		{
			inFile = new File(DirtManMain.csvFileName);
			csvItems = getItemList();
		}
		else
		{
			inFile = new File(DirtManMain.serialFileName);
			try 
			{
				out = new FileOutputStream(inFile);
				objOut = new ObjectOutputStream(out);
			}
			catch (IOException ioe) 
			{
				ioe.printStackTrace();
			}
			
		}
	}
	
	public FileMethods(String filenameIn)
	{
		if (flip == false)
		{
			if (filenameIn != null && !filenameIn.isEmpty() && filenameIn.endsWith(".csv"))
			{
				inFile = new File(filenameIn);
				csvItems = getItemList();
			}	
		}
		else
		{
			if (filenameIn != null && !filenameIn.isEmpty() && filenameIn.endsWith(".ser"))
			{
				inFile = new File(filenameIn);
				try 
				{
					out = new FileOutputStream(inFile);
					objOut = new ObjectOutputStream(out);
				} 
				catch (IOException ioe) 
				{
					ioe.printStackTrace();
				}
			}
		}
	}
	
	
	@SuppressWarnings("resource")
	public void addItem(String name, Double price, Integer num, ItemType type, BilledAs billed)
	{
		Item newItem = new Item(name, price, num, type, billed);
		if (flip = false)
		{
			csvItems.add(newItem);
		}
		else
		{
			items.add(newItem);
		}
	}
	
	public void closeStream()
	{
		try 
		{
			objOut.close();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public void addItem(Item item)
	{
		Item newItem = item;
		if (flip == false)
		{
			try
			{
				printer = new PrintWriter(new BufferedWriter(new FileWriter(inFile, true)));
				Scanner test = new Scanner(inFile);
				if (test.hasNextLine())
				{
					printer.println();
				}
				printer.printf("%s,%.2f,%d,%s,%s", newItem.getName(), newItem.getPrice(), newItem.getQuant(), newItem.getType().toString(), newItem.getBilledAs().toString());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			finally 
			{
				printer.close();
			}
		}
		else if (flip == true)
		{
			items.add(newItem);
		}
	}
	
	public String getFileName()
	{
		return inFile.getName();
	}
	
	public Double getPrice(String name)
	{
		Double price = 0.0;
		if (flip == false)
		{
			for (int i = 0; i < csvItems.size(); i++)
			{
				Item temp = csvItems.get(i);
				if (temp.getName().equals(name))
				{
					price = temp.getPrice();
					break;
				}
			}
		}
		else
		{
			for (int i = 0; i < items.size(); i++)
			{
				Item temp = items.get(i);
				if (temp.getName().equals(name))
				{
					price = temp.getPrice();
					break;
				}
			}
		}
		return price;
			
	}
	
	public Integer getNum(String name)
	{
		if (flip == false)
		{
			int num = 0;
			for (int i = 0; i < csvItems.size(); i++)
			{
				Item temp = csvItems.get(i);
				if (temp.getName().equals(name))
				{
					num = temp.getQuant();
					break;
				}
			}
			return num;
		}
		else
		{
			Integer ret = 0;
			for (int i = 0; i < items.size(); i++)
			{
				Item temp = items.get(i);
				if (temp.getName().equals(name))
				{
					ret = temp.getQuant();
					break;
				}
			}
			return ret;
		}
	}
	
	public ItemType getType(String name)
	{
		if (flip == false)
		{
			ItemType ret = ItemType.UNCATEGORIZED;
			for (int i = 0; i < csvItems.size(); i++)
			{
				Item temp = csvItems.get(i);
				if (temp.getName().equals(name))
				{
					ret = temp.getType();
					break;
				}
			}
			return ret;
		}
		else
		{
			ItemType ret = ItemType.UNCATEGORIZED;
			for (int i = 0; i < items.size(); i++)
			{
				Item temp = items.get(i);
				if (temp.getName().equals(name))
				{
					ret = temp.getType();
					break;
				}
			}
			return ret;
		}
	}
	
	public BilledAs getBilledAs(String name)
	{
		if (flip == false)
		{
			BilledAs ret = BilledAs.NONE;
			for (int i = 0; i < csvItems.size(); i++)
			{
				Item temp = csvItems.get(i);
				if (temp.getName().equals(name))
				{
					ret = temp.getBilledAs();
					break;
				}
			}
			return ret;
		}
		else
		{
			BilledAs ret = BilledAs.NONE;
			for (int i = 0; i < items.size(); i++)
			{
				Item temp = items.get(i);
				if (temp.getName().equals(name))
				{
					ret = temp.getBilledAs();
					break;
				}
			}
			return ret;
		}
	}
	
	public void changePrice(String name, Double newprice)
	{
		if (flip == false)
		{
			for (int i = 0; i < csvItems.size(); i++)
			{
				Item temp = csvItems.get(i);
				if (temp.getName().equals(name))
				{
					temp.setPrice(newprice);
					csvItems.set(i, temp);
					break;
				}
			}
		}
		else
		{
			for (int i = 0; i < items.size(); i++)
			{
				Item temp = items.get(i);
				if (temp.getName().equals(name))
				{
					temp.setPrice(newprice);
					items.set(i, temp);
					break;
				}
			}
		}
	}
	
	public void changeNum(String name, Integer newnum)
	{
		if (flip == false)
		{
			for (int i = 0; i < csvItems.size(); i++)
			{
				Item temp = csvItems.get(i);
				if (temp.getName().equals(name))
				{
					temp.setQuant(newnum);
					csvItems.set(i, temp);
					break;
				}
			}
		}
		else
		{
			for (int i = 0; i < items.size(); i++)
			{
				Item temp = items.get(i);
				if (temp.getName().equals(name))
				{
					temp.setQuant(newnum);
					items.set(i, temp);
					break;
				}
			}
		}
	}
	
	public void changeName(String name, String newname)
	{
		if (flip == false)
		{
			for (int i = 0; i < csvItems.size(); i++)
			{
				Item temp = csvItems.get(i);
				if (temp.getName().equals(name))
				{
					temp.setName(newname);
					csvItems.set(i, temp);
					break;
				}
			}
		}
		else
		{
			for (int i = 0; i < items.size(); i++)
			{
				Item temp = items.get(i);
				if (temp.getName().equals(name))
				{
					temp.setName(newname);;
					items.set(i, temp);
					break;
				}
			}
		}
	}
	
	public void changeType(String name, ItemType newType)
	{
		if (flip == false)
		{
			for (int i = 0; i < csvItems.size(); i++)
			{
				Item temp = csvItems.get(i);
				if (temp.getName().equals(name))
				{
					temp.setType(newType);
					csvItems.set(i, temp);
					break;
				}
			}
		}
		else
		{
			for (int i = 0; i < items.size(); i++)
			{
				Item temp = items.get(i);
				if (temp.getName().equals(name))
				{
					temp.setType(newType);
					items.set(i, temp);
					break;
				}
			}
		}
	}
	
	public void changeBilledAs(String name, BilledAs newBilled)
	{
		if (flip == false)
		{
			for (int i = 0; i < csvItems.size(); i++)
			{
				Item temp = csvItems.get(i);
				if (temp.getName().equals(name))
				{
					temp.setBilledAs(newBilled);
					csvItems.set(i, temp);
					break;
				}
			}
		}
		else
		{
			for (int i = 0; i < items.size(); i++)
			{
				Item temp = items.get(i);
				if (temp.getName().equals(name))
				{
					temp.setBilledAs(newBilled);
					items.set(i, temp);
					break;
				}
			}
		}
	}
	
	public void deleteItem(String name)
	{
		if (flip == false)
		{
			for (int i = 0; i < csvItems.size(); i++)
			{
				Item temp = csvItems.get(i);
				if (temp.getName().equals(name))
				{
					csvItems.remove(i);
					break;
				}
			}
		}
		else
		{
			for (int i = 0; i < items.size(); i++)
			{
				Item temp = items.get(i);
				if (temp.getName().equals(name))
				{
					items.remove(i);
					break;
				}
			}
		}
	}
	
	public ArrayList<Item> getItemList()
	{
		
		if (flip == false)
		{
			csvItems = new ArrayList<Item>();
			try
			{
				
				Scanner line = new Scanner(inFile);
				while (line.hasNextLine())
				{
					String rdline = line.nextLine();
					String[] vals = rdline.split(",");
					csvItems.add(new Item(vals[0], Double.parseDouble(vals[1]), Integer.parseInt(vals[2]), getTypeFromString(vals[3]), getBilledAsFromString(vals[4])));
				}
				line.close();
			}
			catch (FileNotFoundException fnfe)
			{
				fnfe.printStackTrace();
			}
			return csvItems;
		}
		else
		{
			return items;
		}
			
		//return items;
	}
	
	public static ItemType getTypeFromString(String in)
	{
		ItemType ret = ItemType.UNCATEGORIZED;
		String upIn = in.toUpperCase();
		switch(upIn)
		{
		case "ELECTRICAL":
			ret = ItemType.ELECTRICAL;
			break;
		case "PLUMBING":
			ret = ItemType.PLUMBING;
			break;
		case "LABOR":
			ret = ItemType.LABOR;
			break;
		case "EQUIPMENT":
			ret = ItemType.EQUIPMENT;
			break;
		}
		return ret;
	}
	
	public static BilledAs getBilledAsFromString(String in)
	{
		BilledAs ret = BilledAs.NONE;
		String upIn = in.toUpperCase();
		switch(upIn)
		{
		case "HOUR":
			ret = BilledAs.HOUR;
			break;
		case "LENGTH":
			ret = BilledAs.LENGTH;
			break;
		case "PIECE":
			ret = BilledAs.PIECE;
			break;
		}
		return ret;
	}
	
	public  void writeToFile()
	{
		if (flip == false)
		{
			try
			{
				printer = new PrintWriter(new BufferedWriter(new FileWriter(inFile, false)));
				Scanner test = new Scanner(inFile);
				if (test.hasNextLine())
				{
					printer.println();
				}
				for (int i = 0; i < csvItems.size(); i++)
				{
					Item newItem = csvItems.get(i);
					printer.printf("%s,%.2f,%d,%s,%s", newItem.getName(), newItem.getPrice(), newItem.getQuant(), newItem.getType().toString(), newItem.getBilledAs().toString());
				}
				test.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			finally 
			{
				printer.close();
			}
		}
		else
		{
			try
			{
				objOut.writeObject(items);
				objOut.close();
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
			
		}
	}
}
