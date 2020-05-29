package BBO;

public class ImmigrationRates 
{
	double[] immigrationRates = null;
	
	public ImmigrationRates(EmigrationRates emigrationRates)
	{
		immigrationRates = new double[emigrationRates.getEmigrationRates().length];
		minus(emigrationRates);
	}
	
	private void minus(EmigrationRates emigrationRates)
	{
		for(int i=0 ; i < immigrationRates.length ; i++)
		{
			immigrationRates[ i ] = 1.0 -  emigrationRates.getEmigrationRates()[i];
			//immigrationRates[ i ] = 0.5*(Math.cos((i+1)*Math.PI/immigrationRates.length) + 1.0);//1.0 -  emigrationRates.getEmigrationRates()[i];
		}
	}
	
	public double[] getImmigrationRates()
	{
		return immigrationRates;
	}
}
