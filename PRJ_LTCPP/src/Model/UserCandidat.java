package Model;

import java.util.ArrayList;
import java.util.Comparator;

public class UserCandidat {

	private User user;
	private ArrayList<Pool> candidatPools;
	
	public UserCandidat()
	{
		candidatPools = new ArrayList<Pool>();
	}
	public ArrayList<Pool> getCandidatPools() {
		return candidatPools;
	}
	public void setCandidatPools(ArrayList<Pool> candidatPools) {
		this.candidatPools = candidatPools;
	}

	private double regret;
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * @return the regret
	 */
	public double getRegret() {
		return regret;
	}
	/**
	 * @param regret the regret to set
	 */
	public void setRegret(double regret) {
		this.regret = regret;
	}
	
	public void addReprPool(Pool pool)
	{
		candidatPools.add(pool);
	}
	
	/*
     * Comparator pour le tri des pool par distance 
     */
    public Comparator<Pool> ComparatorDist = new Comparator<Pool>() {
     
        @Override
		public int compare(Pool pool1, Pool pool2) {
			// TODO Auto-generated method stub
			//return (int) (Users.getEucliDistTime(user, pool1.getRepresentative()) - Users.getEucliDistTime(user, pool2.getRepresentative()));
			if (Users.getEucliDistance(user, pool1.getRepresentative()) < Users.getEucliDistance(user, pool2.getRepresentative()))
	        	  return -1;
	        	else if (Users.getEucliDistance(user, pool1.getRepresentative()) > Users.getEucliDistance(user, pool2.getRepresentative()))
	        	  return 1;
	        	else
	        	  return 0;
		}

    };
    
    public void computeRegret()
    {
    	regret = Math.abs(Users.getEucliDistTime(user, candidatPools.get(1).getRepresentative()) - Users.getEucliDistTime(user, candidatPools.get(0).getRepresentative()));
    	
    }
    
    /*
     * Comparator pour le tri des User par regret 
     */
    public static Comparator<UserCandidat> ComparatorRegret = new Comparator<UserCandidat>() {
     
        @Override
        public int compare(UserCandidat uc1, UserCandidat uc2) {
            if (uc1.getRegret()  < uc2.getRegret())
        	  return -1;
        	else if (uc1.getRegret() > uc2.getRegret())
        	  return 1;
        	else
        	  return 0;
        }
    };
	
}
