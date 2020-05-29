package GGA;

import java.util.ArrayList;


public class Chromosomes {
private ArrayList<Chromosome> chromosomes;
	
	public Chromosomes(ArrayList<Chromosome> chromosomes)
	{
		this.chromosomes = chromosomes;
	}
	
	public Chromosome getIndexOfChromosomes(int index)
	{
		return this.chromosomes.get(index);
	}
	
	public ArrayList<Chromosome> getChromosomes()
	{
		return this.chromosomes;
	}
	
	public void addChromosome(Chromosome chromosome)
	{
		chromosomes.add(chromosome);
	}

}
