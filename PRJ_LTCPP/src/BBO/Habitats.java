package BBO;

import java.util.ArrayList;

public class Habitats {
	private ArrayList<Habitat> habitats;
	
	public Habitats(ArrayList<Habitat> habitats)
	{
		this.habitats = habitats;
	}
	
	public Habitat getIndexOfHabitats(int index)
	{
		return this.habitats.get(index);
	}
	
	public ArrayList<Habitat> getHabitats()
	{
		return this.habitats;
	}
	
	public void addHabitat(Habitat habitat)
	{
		habitats.add(habitat);
	}
	/*
	@SuppressWarnings("unchecked")
	public void sort(@SuppressWarnings("rawtypes") Comparator habitats_Comparator)
	{
		Arrays.sort(habitats, habitats_Comparator);
	}
	*/
	

}
