package BBO;

import Utilities.Util;

public class BBO_Parameters {
	
	
	/**
	 * The mutation probability per solution per independent variable.
	 * */
	private double mutationProbability = 0.01;
	
	private double[] speciesProbabilities=null;
	
	/**
	 * 		How many of the best solutions to keep 
	 * from one generation to the next generation.
	 * */
	private int numberOfElites = 2;
	
	///////////////////////
	
	private int populationSize;
	
	double sumSpeciesProb = 0.0d;
	
	public BBO_Parameters()
	{
		
	}
	

	/**
	 * @return the popSize
	 */
	public int getPopulationSize() {
		return populationSize;
	}

	/**
	 * @param popSize the popSize to set
	 */
	public void setPopulationSize(int popSize) {
		this.populationSize = popSize;
	}
	
	////////////////////////
	
	/**
	 * @param	mutationProbability	The mutation probability per solution per independent variable.
	 * @param	numberOfElites	How many of the best solutions to keep from one generation to the next generation.
	 * */
	public BBO_Parameters(	double mutationProbability,
							int numberOfElites)
	{
		this.mutationProbability = mutationProbability;
		this.numberOfElites = numberOfElites;
	}
	
	public void setMutationProbability(double mutationProbability)
	{
		this.mutationProbability = mutationProbability;
	}
	
	public void setNumberOfElites(int numberOfElites)
	{
		this.numberOfElites = numberOfElites;
	}
	
	public double getMutationProbability()
	{
		return this.mutationProbability;
	}
	
	public int getNumberOfElites()
	{
		return this.numberOfElites;
	}


	/**
	 * @return the speciesProbabilties
	 */
	public double[] getSpeciesProbabilties() {
		return speciesProbabilities;
	}


	/**
	 * @param speciesProbabilties the speciesProbabilties to set
	 */
	public void setSpeciesProbabilties(double[] speciesProbabilties) {
		this.speciesProbabilities = speciesProbabilties;
	}
	
	public void initSpeciesProbabilties()
	{
		//for (int i = 1 ; i <= Math.ceil((populationSize + 1)/2) ; i++)
		for (int i = 1 ; i <= (int) Math.ceil((populationSize + 1)/2); i++)
		{
			//speciesProbabilties[ i-1 ] = (double)(populationSize + 1 - i) / (double)(populationSize + 1);
			//speciesProbabilties[ i-1 ] = (double)(populationSize + 1 - i) / (double)(populationSize + 1);
			//sumOfEmigrationRates += emigrationRates[ i-1 ];
			int a=1;
			for(int j=populationSize ;j>=(populationSize + 1 - i);j--)
			{
				a *= j;
			}
			//speciesProbabilities[i-1] = Util.factorial((int)(populationSize/2))/(Util.factorial((int) populationSize/2 + 1 - i) *  Util.factorial(i-1));
			//speciesProbabilities[i-1] = Util.factorial((int)(populationSize/2))/(a *  Util.factorial(i-1));
			speciesProbabilities[i-1] = a/Util.factorial(i-1);
			if(i-1 >0)
			{
				sumSpeciesProb += 2*speciesProbabilities[i-1];
			}
			else
			{
				sumSpeciesProb += speciesProbabilities[i-1];
			}
		}
		
		for (int i =(int) Math.ceil((populationSize + 1)/2); i>=1 ; i--)
		{
			speciesProbabilities[i-1] = speciesProbabilities[i-1]/sumSpeciesProb;
		}
	}

}
