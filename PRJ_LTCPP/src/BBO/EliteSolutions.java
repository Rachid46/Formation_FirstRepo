package BBO;

import java.util.ArrayList;

public class EliteSolutions {
	private ArrayList<Habitat> elites = null;
	private int numberOfElites = 0;
	
	@SuppressWarnings("unchecked")
	public EliteSolutions(int numberOfElites)
	{
		elites = new ArrayList<Habitat>();
		this.numberOfElites = numberOfElites;
	}
	
	public void saveBestCost(ArrayList<Habitat> habitats)
	{
		for(int i = 0 ; i < numberOfElites ; i++)
		{
			/*
			if (elites.get(i) != null)
			{
				//elites[ i ].buildSIV(SIV[ i ].getSIVs());
				elites.set(i, habitats.get(i));
			}else{
				elites.add(habitats.get(i));
			}
			*/
			if(elites.size()<numberOfElites)
			{
				elites.add((Habitat) habitats.get(i).clone());
			}
			else
			{
				elites.set(i, (Habitat) habitats.get(i).clone());
			}
			
		}
	}
	
	public ArrayList<Habitat> getElites()
	{
		return elites;
	}

}
