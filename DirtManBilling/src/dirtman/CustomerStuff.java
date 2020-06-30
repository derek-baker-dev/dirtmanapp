package dirtman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import dirtman.application.DirtManMain;
@SuppressWarnings("unused")

public class CustomerStuff 
{
	private File inFile;
	private PrintWriter printer;
	private ArrayList<Customer> customers;
	
	public CustomerStuff()
	{
		customers = new ArrayList <Customer>();
		inFile = new File(DirtManMain.custFileName);
	}
	
	public CustomerStuff(String filenameIn)
	{
		if (filenameIn != null && !filenameIn.isEmpty())
		{
			inFile = new File(filenameIn);
			//fileName = filenameIn;
		}
	}
	
	@SuppressWarnings("resource")
	public void addCustomer(String name, String streetAdr, String cityState)
	{
		Customer newCustomer = new Customer(name, streetAdr, cityState);
		
		try
		{
			printer = new PrintWriter(new BufferedWriter(new FileWriter(inFile, true)));
			Scanner test = new Scanner(inFile);
			if (test.hasNextLine())
			{
				printer.println();
			}
			printer.printf("%s;%s;%s", newCustomer.getName(), newCustomer.getStreetAdr(), newCustomer.getCityState());
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		finally
		{
			printer.close();
		}
		
		//customers.add(newCustomer);
		
	}
	
	private void newPage()
	{
		try
		{
			printer = new PrintWriter(new BufferedWriter(new FileWriter(inFile, false)));
			printer.print("");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private boolean stringCheck(String name)
	{
		return name != null && !name.isEmpty() && name.length() < 50;
	}
	
	private void changeName(String name, String newName)
	{
		try
		{
			Scanner line = new Scanner(inFile);
			ArrayList<String[]> lsplits = new ArrayList<String[]>();
			if (stringCheck(name) && stringCheck(newName))
			{
				while (line.hasNext())
				{
					String rdline = line.nextLine();
					String[] vals = rdline.split(";");
					if (vals[0].equals(name))
					{
						vals[0] = newName;
						lsplits.add(vals);
					}
					else
					{
						lsplits.add(vals);
					}
				}
			}
			newPage();
			for (int i = 0; i < lsplits.size(); i++)
			{
				addCustomer(lsplits.get(i)[0], lsplits.get(i)[1], lsplits.get(i)[2]);
			}
			line.close();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	private void changeStreetAdr(String name, String newAddress)
	{
		try
		{
			Scanner line = new Scanner(inFile);
			ArrayList<String[]> lsplits = new ArrayList<String[]>();
			if (stringCheck(name) && stringCheck(newAddress))
			{
				while (line.hasNext())
				{
					String rdline = line.nextLine();
					String[] vals = rdline.split(";");
					if (vals[0].equals(name))
					{
						vals[1] = newAddress;
						lsplits.add(vals);
					}
					else
					{
						lsplits.add(vals);
					}
				}
			}
			newPage();
			for (int i = 0; i < lsplits.size(); i++)
			{
				addCustomer(lsplits.get(i)[0], lsplits.get(i)[1], lsplits.get(i)[2]);
			}
			line.close();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	private void changeCityState(String name, String cityState)
	{
		try
		{
			Scanner line = new Scanner(inFile);
			ArrayList<String[]> lsplits = new ArrayList<String[]>();
			if (stringCheck(name) && stringCheck(cityState))
			{
				while (line.hasNext())
				{
					String rdline = line.nextLine();
					String[] vals = rdline.split(";");
					if (vals[0].equals(name))
					{
						vals[2] = cityState;
						lsplits.add(vals);
					}
					else
					{
						lsplits.add(vals);
					}
				}
			}
			newPage();
			for (int i = 0; i < lsplits.size(); i++)
			{
				addCustomer(lsplits.get(i)[0], lsplits.get(i)[1], lsplits.get(i)[2]);
			}
			line.close();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	public ArrayList<Customer> getItemList()
	{
		ArrayList<Customer> custs = new ArrayList<Customer>();
		//String[] list = null;
		try
		{
			Scanner line = new Scanner(inFile);
			while (line.hasNextLine())
			{
				String rdline = line.nextLine();
				String[] vals = rdline.split(";");
				custs.add(new Customer(vals[0], vals[1], vals[2]));
			}
			line.close();
		}
		catch (FileNotFoundException fnfe)
		{
			fnfe.printStackTrace();
		}
		return custs;
	}
	
}
