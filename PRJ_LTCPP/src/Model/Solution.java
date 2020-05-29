package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Solution implements Serializable, Cloneable {
	
	private ArrayList<Pool> pools;
	private double totalCost;
	
	public Solution()
	{
		pools = new ArrayList<Pool>();
	}

	/**
	 * @return the totalCost
	 */
	public double getTotalCost() {
		return totalCost;
	}

	/**
	 * @param totalCost the totalCost to set
	 */
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public void computeHSI()
	{
		
	}

	/**
	 * @return the pools
	 */
	public ArrayList<Pool> getPools() {
		return pools;
	}

	/**
	 * @param pools the pools to set
	 */
	public void setPools(ArrayList<Pool> pools) {
		this.pools = pools;
	}
	
	public void addPool(Pool pool)
	{
		pools.add(pool);
	}
	
	public boolean removePool(Pool pool)
	{
		return pools.remove(pool);
	}
	
	//Verify that all users are present in a solution
	//TODO : to implement
	public boolean isAllUsersPresent()
	{
		boolean allUsersPresent = false;
		
		//hashset?
		
		return allUsersPresent;
	}

	@Override
	public Object clone() {
	    Solution clonedSolution = null;
	    try {
	    	// On récupère l'instance à renvoyer par l'appel de la 
	      	// méthode super.clone()
	    	clonedSolution = (Solution) super.clone();
	    } catch(CloneNotSupportedException cnse) {
	      	// Ne devrait jamais arriver car nous implémentons 
	      	// l'interface Cloneable
	      	cnse.printStackTrace(System.err);
	    }
	    
	    //clonedPool.listOfUsers = (ArrayList<User>) listOfUsers.clone();
	    //clonedPool.setListOfUsers((ArrayList<User>) listOfUsers.clone());
	    ArrayList<Pool> pools1=  new ArrayList<Pool>();
	    // On clone l'attribut de type User qui n'est pas immuable.
	    
	    clonedSolution.setPools(pools1);
	    
	    for (int i=0; i<this.getPools().size();i++) {
	    	//u = (User) user.clone();
	    	//
	    	//clonedPool.listOfUsers.set(i, (User) listOfUsers.get(i).clone());
	    	clonedSolution.getPools().add((Pool) this.getPools().get(i).clone());
	    	//clonedHabitat.getListOfUsers().get(i).setPool(clonedPool);
	    }
	    	    
	    // on renvoie le clone
	    return clonedSolution;
	}
}
