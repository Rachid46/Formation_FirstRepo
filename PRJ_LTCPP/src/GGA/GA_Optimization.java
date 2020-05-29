package GGA;

import java.util.ArrayList;
import Model.InitPopulation;
import Model.Optimization;

public class GA_Optimization extends Optimization {
	
	Chromosomes chromosomes;
	GA_Parameters gaParameters;
	
	public GA_Optimization()
	{
		chromosomes = new Chromosomes(new ArrayList<Chromosome>());
	}

	@Override
	public void treatment() {
		// TODO Auto-generated method stub
		preprocessingGA();
		
		processGA();
		
	}

	private void processGA() {
		// TODO Auto-generated method stub
		
	}

	private void preprocessingGA() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		initParameters();
				
		System.out.println("treatment");
		//BBO_Parameters bboParam = new BBO_Parameters();
		//Initiate parameters
		gaParameters.setPopulationSize(20); // get parameters from graphical interface
				
				
		//Initiate population
		initRandPopulation();
				
		//Evaluate fitness
		// TODO
		evaluateGA();
				
		double avgCost = 0.0;
		for(int i=0;i<chromosomes.getChromosomes().size();i++)
		{
			System.out.println("Chromosome-" + (i+1));
			System.out.println("Cost : " + chromosomes.getChromosomes().get(i).getFitness());
			avgCost = avgCost + chromosomes.getChromosomes().get(i).getFitness();
		}
	}

	private void evaluateGA() {
		// TODO Auto-generated method stub
		for(int i=0;i<chromosomes.getChromosomes().size();i++)
		{
			chromosomes.getChromosomes().get(i).evaluateFitness();
		}
	}

	private void initParameters() {
		// TODO Auto-generated method stub
		gaParameters = new GA_Parameters();
	}

	@Override
	public void initRandPopulation() {
		// TODO Auto-generated method stub
		System.out.println("initPopulation"); 
		InitPopulation initPop = new InitPopulation(users);
		// a structured portion
		// sweep
		//a part randomly
		for(int i=0;i<gaParameters.getPopulationSize();i++)
		{
			//TODO : cast is incorrect. We can't cast a child in a father class
			Chromosome chromosome = new Chromosome(); 
			initPop.randPop(chromosome,0);
			chromosomes.addChromosome(chromosome);
		}
		
	}

	@Override
	public double getBestSol() {
		// TODO Auto-generated method stub
		return 0;
	}

}
