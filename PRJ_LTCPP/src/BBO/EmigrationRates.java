package BBO;

public class EmigrationRates 
{
	
	double[] emigrationRates = null;
	int populationSize = 0;
	double sumOfEmigrationRates = 0.0d;
	
	public EmigrationRates(int populationSize)
	{
		emigrationRates = new double[populationSize];
		this.populationSize = populationSize;
		initializeEmigrationRates();
	}
	
	private void initializeEmigrationRates()
	{
		for (int i = 1 ; i <= populationSize ; i++)
		{
			emigrationRates[ i-1 ] = (double)(populationSize + 1 - i) / Math.sqrt((double)(populationSize + 1));
			//emigrationRates[ i-1 ] = 0.5*(-Math.cos((i*Math.PI/populationSize)) + 1.0);//(double)(populationSize + 1 - i) / Math.sqrt((double)(populationSize + 1));
			sumOfEmigrationRates += emigrationRates[ i-1 ];
		}
		
	}
	
	public double[] getEmigrationRates()
	{
		return emigrationRates;
	}
	
	public double getSum()
	{
		return sumOfEmigrationRates;
	}
	
}
