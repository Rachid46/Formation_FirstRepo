package BBO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

import Model.Pool;
import Model.Solution;
import Model.User;

public class Habitat extends Solution implements Serializable, Cloneable{
	
	private double hSI;
	private ArrayList<Pool> Sivs;
	
	public Habitat()
	{
		super();
		setSivs(super.getPools());
		
	}
	
	public double getHSI() {
		return hSI;
	}

	public void setHSI(double hSI) {
		this.hSI = hSI;
	}

	public void evaluateHSI()
	{
		double hSI = 0.0;
		
		int nbUsers = 0;
		for(int i=0;i<this.getPools().size();i++)
		{
			//hSI = hSI + this.getPools().get(i).getAvgCost();
			//this.getPools().get(i).buildRoutes();
			hSI = hSI + this.getSivs().get(i).getAvgCost();
			nbUsers = nbUsers + this.getSivs().get(i).getListOfUsers().size();
			
		}
		
		System.out.println("Habitat size = " + nbUsers);
				
		this.setHSI(hSI);
	}

	/**
	 * @return the siv
	 */
	public ArrayList<Pool> getSivs() {
		return Sivs;
	}

	/**
	 * @param siv the siv to set
	 */
	public void setSivs(ArrayList<Pool> sivs) {
		Sivs = sivs;
	}
	
	public static Comparator<Habitat> ComparatorHsi = new Comparator<Habitat>() {
	     
        @Override
        public int compare(Habitat habitat1, Habitat habitat2) {
            //return (int) Math.round((uc1.getRegret() - uc2.getRegret()));
        	if (habitat1.getHSI() < habitat2.getHSI())
        	  return -1;
        	else if (habitat1.getHSI() > habitat2.getHSI())
        	  return 1;
        	else
        	  return 0;
        }
        
    };

	public static Habitat compare(Habitat habitat, Habitat habitat2) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Habitat compareHabitat(Habitat habitat1, Habitat habitat2) {
		
        	if (habitat1.getHSI() < habitat2.getHSI())
        	  return habitat1;
        	else 
        	  return habitat2;
	}

	/*
	 * public void immigrateSIV(Pool siv) { // TODO Auto-generated method stub //Add
	 * siv to the habitat
	 * 
	 * //remove from the other SIVs the users within siv
	 * 
	 * //compute HSI }
	 */
	
	@Override
	public Object clone() {
	    Habitat clonedHabitat = null;
	    // On récupère l'instance à renvoyer par l'appel de la 
		// méthode super.clone()
		clonedHabitat = (Habitat) super.clone();
	    
	    //clonedPool.listOfUsers = (ArrayList<User>) listOfUsers.clone();
	    //clonedPool.setListOfUsers((ArrayList<User>) listOfUsers.clone());
	    //ArrayList<Pool> Sivs=  new ArrayList<Pool>();
	    // On clone l'attribut de type User qui n'est pas immuable.
		
		//ArrayList<Pool> Sivs1 = null;
	    
	    clonedHabitat.setSivs(clonedHabitat.getPools());
	    
	    /*
	    for (int i=0; i<this.getSivs().size();i++) {
	    	//u = (User) user.clone();
	    	//
	    	//clonedPool.listOfUsers.set(i, (User) listOfUsers.get(i).clone());
	    	clonedHabitat.getSivs().add((Pool) this.getSivs().get(i).clone());
	    	//clonedHabitat.getListOfUsers().get(i).setPool(clonedPool);
	    }
	    	*/    
	    // on renvoie le clone
	    return clonedHabitat;
	}
	
	//get a list of nearest pools for a user
	public void getNearestSivs(Pool pool,int nbSivs,ArrayList<Pool> candidatPools)
	{
		for(int i=0;i<nbSivs;i++)
		{
			
		}
	}
	
	public void getNearestOpositePool(Pool pool)
	{
		User user= new User();
		
		user.setX(35.0 + ( 35.0 - pool.getGravityCentreX()));
		user.setY(35.0 + (35.0 - pool.getGravityCentreY()));
		user.setNumUser(-1);
		
		//GetOpposite User
		
		//Construct a pool based on this user
		
		
		
		//Users.getNearestInList(user, Utilisateurs.getNearest(users, currentUser))
	}
}
