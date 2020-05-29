package BBO;

public class BestCost{
	Habitat habitat = null;
	
	public BestCost(Habitat habitat)
	{
		this.habitat = habitat;
	}
	
	public void saveBestCost(Habitat habitat)
	{
		this.habitat = habitat;
	}
	
	
	public Habitat getHabitat()
	{
		return this.habitat;
	}
	
	public double getHSIFromBestCost()
	{
		return this.habitat.getHSI();
	}

}
