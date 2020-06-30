package dirtman;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Item implements Serializable
{
	private String name;
	private Double price;
	private Integer quant;
	private ItemType type;
	private BilledAs billed;
	
	public Item()
	{
		setName("default");
		setType(ItemType.UNCATEGORIZED);
		setBilledAs(BilledAs.NONE);
	}
	
	public Item(String nameIn, ItemType typeIn)
	{
		setName(nameIn);
		setType(typeIn);
	}
	
	public Item(String nameIn, Double priceIn, ItemType typeIn, BilledAs billedIn)
	{
		setName(nameIn);
		setPrice(priceIn);
		setType(typeIn);
		setBilledAs(billedIn);
	}
	
	public Item(String nameIn, Double priceIn, Integer quantIn, ItemType typeIn)
	{
		setName(nameIn);
		setPrice(priceIn);
		setQuant(quantIn);
		setType(typeIn);
		setBilledAs(BilledAs.NONE);
	}
	
	public Item(String nameIn, Double priceIn, Integer quantIn, ItemType typeIn, BilledAs billedIn)
	{
		setName(nameIn);
		setPrice(priceIn);
		setQuant(quantIn);
		setType(typeIn);
		setBilledAs(billedIn);
	}
	
	
	public void setName(String nameIn)
	{
		if(nameIn != null && !nameIn.isEmpty() && nameIn.length() < 20)
		{
			name = nameIn;
		}
		else
		{
			name = "default";
		}
	}
	
	public void setPrice(Double priceIn) 
	{
		if (priceIn >= 0.0)
		{
			price = priceIn;
		}
	}
	
	public void setQuant(Integer quantIn)
	{
		if (quantIn >= 0)
		{
			quant = quantIn;
		}
	}
	
	public void setBilledAs(BilledAs bill)
	{
		billed = bill;
	}
	
	public void setType(ItemType typeIn)
	{
		type = typeIn;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Double getPrice()
	{
		return price;
	}
	
	public Integer getQuant()
	{
		return quant;
	}
	
	public BilledAs getBilledAs()
	{
		return billed;
	}
	
	public String toString()
	{
		return name;
	}
	
	public ItemType getType()
	{
		return type;
	}
	
	public Double getTotalPrice()
	{
		return getPrice() * getQuant();
	}
	
	public boolean isNonInventory()
	{
		return getType() == ItemType.LABOR || getType() == ItemType.EQUIPMENT;
	}
}
