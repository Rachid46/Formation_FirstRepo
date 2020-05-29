package GGA;

import java.io.Serializable;
import java.util.ArrayList;

import Model.Pool;
import Model.Solution;

public class Chromosome extends Solution implements Serializable, Cloneable{
	
	private double fitness;
	private ArrayList<Pool> Genes;
	
	public Chromosome()
	{
		super();
		setGenes(super.getPools());
		
	}
	
	

	public void evaluateFitness()
	{
		double fitness = 0.0;
		
		int nbUsers = 0;
		for(int i=0;i<this.getPools().size();i++)
		{
			//hSI = hSI + this.getPools().get(i).getAvgCost();
			fitness = fitness + this.getGenes().get(i).getAvgCost();
			nbUsers = nbUsers + this.getGenes().get(i).getListOfUsers().size();
			
		}
		
		System.out.println("Chromosome size = " + nbUsers);
				
		this.setFitness(fitness);
	}

	/**
	 * @return the siv
	 */
	public ArrayList<Pool> getGenes() {
		return Genes;
	}

	/**
	 * @param siv the gene to set
	 */
	public void setGenes(ArrayList<Pool> genes) {
		Genes = genes;
	}



	/**
	 * @return the fitness
	 */
	public double getFitness() {
		return fitness;
	}



	/**
	 * @param fitness the fitness to set
	 */
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}


}
