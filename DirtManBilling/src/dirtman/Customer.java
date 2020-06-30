package dirtman;

public class Customer 
{
	private String name;
	private String streetAdr;
	private String cityState;
	
	public Customer()
	{
		setName("default");
		setStreetAdr("default");
		setCityState("default");
	}
	
	public Customer(String nameIn, String streetAdrIn, String cityStateIn)
	{
		setName(nameIn);
		setStreetAdr(streetAdrIn);
		setCityState(cityStateIn);
	}
	
	public void setName(String nameIn)
	{
		if (nameIn != null && !nameIn.isEmpty())
		{
			name = nameIn;
		}
	}
	
	public void setStreetAdr(String streetAdrIn)
	{
		if (streetAdrIn != null && !streetAdrIn.isEmpty())
		{
			streetAdr = streetAdrIn;
		}
	}
	
	public void setCityState(String cityStateIn)
	{
		if (cityStateIn != null && !cityStateIn.isEmpty() && cityStateIn.contains(","))
		{
			cityState = cityStateIn;
		}
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getStreetAdr()
	{
		return streetAdr;
	}
	
	public String getCityState()
	{
		return cityState;
	}
	
	public String toString()
	{
		return getName() + "\n" + getStreetAdr() + "\n" + getCityState();
	}
}
