package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class Pool implements Serializable, Cloneable{
	
	private double avgCost;
	private ArrayList<User> listOfUsers;
	private int minCarCap;
	private User representative;
	
	private boolean validPool;
	
	//To accelerate research
	//private double minTimePool;
	private double minEarliestLeaveTime;
	private double maxEarliestLeaveTime;
	private double minLatestArrivalTime;
	private double minUserTimeToWP;
	private double minShortTime;
	private double minMaxTravelTime;
	//private double max
	
	private double gravityCentreX;
	private double gravityCentreY;
	private double gravityCentreDist;
	
	//Polar coordinates
	private double gcPolarR;
	private double gcPolarTheta;
	//private double gcPolarDist;
	
	private User fartherUser;
	private double fartherDistG = 0.0;
	
	public double getGravityCentreDist() {
		return gravityCentreDist;
	}

	public void setGravityCentreDist(double gravityCentreDist) {
		this.gravityCentreDist = gravityCentreDist;
	}

	public Pool()
	{
		listOfUsers = new ArrayList<User>();
		this.setMinCarCap();
	}
	
	/**
	 * @return the avgCost
	 */
	public double getAvgCost() {
		return avgCost;
	}

	/**
	 * @param avgCost the avgCost to set
	 */
	public void setAvgCost(double avgCost) {
		this.avgCost = avgCost;
	}

	/**
	 * @return the listOfUsers
	 */
	public ArrayList<User> getListOfUsers() {
		return listOfUsers;
	}

	/**
	 * @param listOfUsers the listOfUsers to set
	 */
	public void setListOfUsers(ArrayList<User> listOfUsers) {
		this.listOfUsers = listOfUsers;
	}
	
	public double calculateAvgPoolCost()
	{
		double cost=0.0;
		for(int i=0;i<listOfUsers.size();i++)
		{
			//cost=cost+listOfUsers.get(i).getHamCost() + listOfUsers.get(i).getPenalty() * listOfUsers.get(i).getPhi() ;
			cost = cost + listOfUsers.get(i).getHamCost();
			if(listOfUsers.size()==1)
			{
				cost = cost + listOfUsers.get(i).getPenalty();
			}
		}
		
		cost=cost/listOfUsers.size();
		return cost;
	}

	/**
	 * @return the minCarCap
	 */
	public int getMinCarCap() {
		return minCarCap;
	}

	/**
	 * @param minCarCap the minCarCap to set
	 */
	public void setMinCarCap(int minCarCap) {
		this.minCarCap = minCarCap;
	}
	
	//Classe Pool
	
	public boolean isCarCapValid()
	{
		return(this.minCarCap <= this.listOfUsers.size());
	}
	
	//Add a user to the pool
	public void addUser(User user)
	{
		//TODO : Verify car capacity before add a user
		listOfUsers.add(user);
		//TODO : Update the car capacity and the rest of car capacity
	}
	
	//Remove a user to the pool
	public void removeUser(User user)
	{
		listOfUsers.remove(user);
		//TODO : Update the car capacity and the rest of car capacity 
	}
	
	public void setMinCarCap()
	{
		if(listOfUsers.size()==0)
		{
			minCarCap=4;
		}
		else
		{
			minCarCap=listOfUsers.get(0).getCarCapacity();
			for(int i=1;i<listOfUsers.size();i++)
			{
				minCarCap = Math.min(minCarCap, listOfUsers.get(i).getCarCapacity());
			}
		}
	}
	
	public int getRestCarCap()
	{
		
		return minCarCap - listOfUsers.size();
		
	}

	/**
	 * @return the representative
	 */
	public User getRepresentative() {
		return representative;
	}

	/**
	 * @param representative the representative to set
	 */
	public void setRepresentative(User representative) {
		this.representative = representative;
	}
	
	public boolean buildRoutes()
	{
		
		boolean valid=true;
		int i=0;
		while(i<listOfUsers.size() && valid)
		{
			// an order of list for a user
			ArrayList<Integer> usersList = new ArrayList<Integer>();
			int driverNum =  listOfUsers.get(i).getNumUser();
			usersList.add(driverNum);
			for(int j=0;j<listOfUsers.size();j++)
			{
				if(listOfUsers.get(j).getNumUser() != driverNum)
				{
					usersList.add(listOfUsers.get(j).getNumUser());
				}
			}
			
			//Affect the list to userList of the driver
			listOfUsers.get(i).settRouteList(usersList);
			listOfUsers.get(i).setPool(this);
			
			//Build the best route beginning from the current user (driver) 
			valid = valid && listOfUsers.get(i).buildRoute();
			i++;
		}
		
		//Compute the pool average cost
		if(valid)
		{
			setAvgCost(calculateAvgPoolCost());
		}
		
		//Save the validity of current pool
		this.setValidPool(valid);
		if(valid)
		{
			this.setMaxEarliestLeaveTime();
			this.setMinEarliestLeaveTime();
			this.setMinShortTime();
			this.setMinUserTimeToWP();
			this.computeGravityCentre();
			this.computeDistGravCent();
			this.computeFartherUserG();
			this.computeGcPolarR();
			this.computeGcPolarTheta();
			this.computeMinMaxTravelTime();
		}
		return valid;
	}
	
	//Return the user instance from the number of the user
	public User getUser(int numUser)
	{
		User user=null;
		
		for(int i=0;i<listOfUsers.size();i++)
		{
			if(listOfUsers.get(i).getNumUser()==numUser)
			{
				user=listOfUsers.get(i);
			}
		}
		
		return user;
	}

	/**
	 * @return the validPool
	 */
	public boolean isValidPool() {
		return validPool;
	}

	/**
	 * @param validPool the validPool to set
	 */
	public void setValidPool(boolean validPool) {
		this.validPool = validPool;
	}
	
	@Override
	public Object clone() {
	    Pool clonedPool = null;
	    try {
	    	// On récupère l'instance à renvoyer par l'appel de la 
	      	// méthode super.clone()
	      	clonedPool = (Pool) super.clone();
	    } catch(CloneNotSupportedException cnse) {
	      	// Ne devrait jamais arriver car nous implémentons 
	      	// l'interface Cloneable
	      	cnse.printStackTrace(System.err);
	    }
	    
	    //clonedPool.listOfUsers = (ArrayList<User>) listOfUsers.clone();
	    //clonedPool.setListOfUsers((ArrayList<User>) listOfUsers.clone());
	    clonedPool.setListOfUsers(new ArrayList<User>());
	    // On clone l'attribut de type User qui n'est pas immuable.
	    
	    for (int i=0; i<listOfUsers.size();i++) {
	    	//u = (User) user.clone();
	    	//
	    	//clonedPool.listOfUsers.set(i, (User) listOfUsers.get(i).clone());
	    	clonedPool.getListOfUsers().add((User) listOfUsers.get(i).clone());
	    	clonedPool.getListOfUsers().get(i).setPool(clonedPool);
	    }
	    	    
	    // on renvoie le clone
	    return clonedPool;
	}
	
	public boolean areAllUsersAuthorized(int numUserToPool)
	{
		boolean authorized = true;
		for(int i=0; i<listOfUsers.size() && authorized;i++)
		{
			if(Utilisateurs.getAuthorizedUsers()[listOfUsers.get(i).getNumUser()][numUserToPool]==0)
			{
				authorized=false;
			}
		}
		
		return authorized;
	}
	
	public boolean areAllUsersAuthorizedSwap(int numUserToPool, int excludeNumUser )
	{
		boolean authorized = true;
		for(int i=0; i<listOfUsers.size() && authorized;i++)
		{
			if(listOfUsers.get(i).getNumUser() != excludeNumUser)
			{
				if(Utilisateurs.getAuthorizedUsers()[listOfUsers.get(i).getNumUser()][numUserToPool]==0)
				{
					authorized=false;
				}
			}
		}
		
		return authorized;
	}
	
	public void setMinShortTime()
	{
		double minShortTime=10000.0;
		for(int i=0;i<listOfUsers.size();i++)
		{
			if(minShortTime>(listOfUsers.get(i).getSpeed()/listOfUsers.get(i).getHamCost()))
			{
				minShortTime = listOfUsers.get(i).getSpeed()/listOfUsers.get(i).getHamCost();
			}
		}
		
		this.minShortTime = minShortTime; 
	}
	
	public void setMinUserTimeToWP()
	{
		double minTimeToWP=10000.0;
		for(int i=0;i<listOfUsers.size();i++)
		{
			if(minTimeToWP>(listOfUsers.get(i).getSpeed()/listOfUsers.get(i).getDistToWP()))
			{
				minTimeToWP = listOfUsers.get(i).getSpeed()/listOfUsers.get(i).getHamCost();
			}
		}
		
		this.minUserTimeToWP = minTimeToWP;
	}
	
	public void setMaxEarliestLeaveTime()
	{
		double maxLeaveTime=1.0;
		for(int i=0;i<listOfUsers.size();i++)
		{
			if(maxLeaveTime<listOfUsers.get(i).getEarliestLeaveTime())
			{
				maxLeaveTime=listOfUsers.get(i).getEarliestLeaveTime();
			}
		}
		
		this.maxEarliestLeaveTime = maxLeaveTime;
	}
	
	public void setMinEarliestLeaveTime()
	{
		double minLeaveTime=10000.0;
		for(int i=0;i<listOfUsers.size();i++)
		{
			if(minLeaveTime>listOfUsers.get(i).getEarliestLeaveTime())
			{
				minLeaveTime=listOfUsers.get(i).getEarliestLeaveTime();
			}
		}
		
		this.minEarliestLeaveTime = minLeaveTime;
	}
	
	public double getMinLatestArrivalTime()
	{
		double minLatestArrivalTime=10000.0;
		for(int i=0;i<listOfUsers.size();i++)
		{
			if(minLatestArrivalTime>listOfUsers.get(i).getLatestArrivalTime())
			{
				minLatestArrivalTime=listOfUsers.get(i).getLatestArrivalTime();
			}
		}
		
		return minLatestArrivalTime;
	}
	public boolean isUserAuthorized(User user)
	{
		boolean isAuthorized = true;
		
		
		//User nearestUser = Utilisateurs.getNearest(listOfUsers, user);
		double minTimeUserToPool=10000.0;
		for(int i=0;i<listOfUsers.size();i++)
		{
			if(Utilisateurs.getTim_users()[user.getNumUser()][listOfUsers.get(i).getNumUser()]<minTimeUserToPool)
			{
				minTimeUserToPool=Utilisateurs.getTim_users()[user.getNumUser()][listOfUsers.get(i).getNumUser()];
			}
		}
		
		if((minTimeUserToPool + this.getMinShortTime() > user.getMaxTravelTime()) //+ user.getTimToWP()) 
		  || (Math.max(user.getEarliestLeaveTime() + minTimeUserToPool, this.getMinEarliestLeaveTime()) + 
				  this.getMinShortTime() > Math.min(user.getLatestArrivalTime(),this.getMinLatestArrivalTime()))
		  )
		{
			isAuthorized = false;
		}
		
		return isAuthorized;
	}

	/**
	 * @return the minEarliestLeaveTime
	 */
	public double getMinEarliestLeaveTime() {
		return minEarliestLeaveTime;
	}

	
	/**
	 * @return the minUserTimeToWP
	 */
	public double getMinUserTimeToWP() {
		return minUserTimeToWP;
	}

	
	/**
	 * @return the minShortTime
	 */
	public double getMinShortTime() {
		return minShortTime;
	}
	
	public void computeGravityCentre()
	{
		gravityCentreX = 0.0;
		gravityCentreY = 0.0;
		for(int i=0;i<listOfUsers.size();i++)
		{
			gravityCentreX = gravityCentreX + listOfUsers.get(i).getX();
			gravityCentreY = gravityCentreY + listOfUsers.get(i).getY();
		}
		
		gravityCentreX = gravityCentreX/listOfUsers.size();
		gravityCentreY = gravityCentreY/listOfUsers.size();
	}
	
	public void computeDistGravCent()
	{
		//this.setGravityCentreDist(0.75 * 1.0/Math.sqrt(Math.pow(this.getGravityCentreX() ,2) + Math.sqrt(Math.pow(this.getGravityCentreY(),2))) + 0.25 * 1.0/(this.getMinLatestArrivalTime() - this.getMinEarliestLeaveTime()));
		this.setGravityCentreDist(Math.sqrt(Math.pow(this.getGravityCentreX() ,2) + Math.sqrt(Math.pow(this.getGravityCentreY(),2))));
	}

	/**
	 * @return the gavityCentreX
	 */
	public double getGravityCentreX() {
		return gravityCentreX;
	}

	/**
	 * @param gavityCentreX the gavityCentreX to set
	 */
	public void setGravityCentreX(double gavityCentreX) {
		this.gravityCentreX = gavityCentreX;
	}

	/**
	 * @return the gavityCentreY
	 */
	public double getGravityCentreY() {
		return gravityCentreY;
	}

	/**
	 * @param gavityCentreY the gavityCentreY to set
	 */
	public void setGravityCentreY(double gravityCentreY) {
		this.gravityCentreY = gravityCentreY;
	}

	
	public void computeGcPolarR()
	{
		setGcPolarR(this.getGravityCentreDist());
	}
	
	public void computeGcPolarTheta()
	{
		setGcPolarTheta(Math.atan(this.getGravityCentreY()/this.getGravityCentreX()));
	}
	/**
	 * @return the gcPolarX
	 */
	public double getGcPolarR() {
		return gcPolarR;
	}

	/**
	 * @param gcPolarX the gcPolarX to set
	 */
	public void setGcPolarR(double gcPolarR) {
		this.gcPolarR = gcPolarR;
	}

	/**
	 * @return the gcPolarTheta
	 */
	public double getGcPolarTheta() {
		return gcPolarTheta;
	}

	/**
	 * @param gcPolarY the gcPolarTheta to set
	 */
	public void setGcPolarTheta(double gcPolarTheta) {
		this.gcPolarTheta = gcPolarTheta;
	}

	public void computeMinMaxTravelTime()
	{
		double minMaxTravelTime=10000.0;
		for(int i=0;i<listOfUsers.size();i++)
		{
			if(minMaxTravelTime>(listOfUsers.get(i).getMaxTravelTime()))
			{
				minMaxTravelTime=listOfUsers.get(i).getMaxTravelTime();
			}
		}
		
		this.setMinMaxTravelTime(minMaxTravelTime);
	}
	
	/**
	 * @return the minMaxTravelTime
	 */
	public double getMinMaxTravelTime() {
		return minMaxTravelTime;
	}

	/**
	 * @param minMaxTravelTime the minMaxTravelTime to set
	 */
	public void setMinMaxTravelTime(double minMaxTravelTime) {
		this.minMaxTravelTime = minMaxTravelTime;
	}

	
    
    public static Comparator<Pool> ComparatorCost = new Comparator<Pool>() {
   	 
	     
        @Override
        public int compare(Pool pool1, Pool pool2) {
            //return (int) Math.round((uc1.getRegret() - uc2.getRegret()));
        	if ((pool1.getAvgCost() - Utilisateurs.getAll_users()[pool1.getListOfUsers().get(0).getNumUser()][0])/(double)Math.max(pool1.getListOfUsers().size(),2)  < (pool2.getAvgCost() - Utilisateurs.getAll_users()[pool2.getListOfUsers().get(0).getNumUser()][0])/(double)Math.max(pool2.getListOfUsers().size(),2) )
        		//pool2.getGravityCentreDist())
        		return -1;
        	else if ((pool1.getAvgCost() - Utilisateurs.getAll_users()[pool1.getListOfUsers().get(0).getNumUser()][0])/(double)Math.max(pool1.getListOfUsers().size(),2)  > (pool2.getAvgCost() - Utilisateurs.getAll_users()[pool2.getListOfUsers().get(0).getNumUser()][0])/(double)Math.max(pool2.getListOfUsers().size(),2) )
        		return 1;
           	else
        		return 0;
        	
        }
        
    };
    
    public static Comparator<Pool> ComparatorDistG = new Comparator<Pool>() {
      	 
	     
        @Override
        public int compare(Pool pool1, Pool pool2) {
            //return (int) Math.round((uc1.getRegret() - uc2.getRegret()));
        	if (pool1.getFartherDistG()  < pool2.getFartherDistG() )
        		//pool2.getGravityCentreDist())
        		return -1;
        	else if (pool1.getFartherDistG() > pool2.getFartherDistG() )
        		return 1;
           	else
        		return 0;
        	
        }
        
    };
    
    public static Comparator<Pool> ComparatorSize = new Comparator<Pool>() {
      	 
	     
        @Override
        public int compare(Pool pool1, Pool pool2) {
            //return (int) Math.round((uc1.getRegret() - uc2.getRegret()));
        	if (pool1.getListOfUsers().size() < pool2.getListOfUsers().size())
        		return -1;
        	else if (pool1.getListOfUsers().size() > pool2.getListOfUsers().size())
        		return 1;
           	else
        		return 0;
        	
        }
        
    };
    
    public boolean isUserInPool(int numUser)
    {
    	boolean userIn = false;
    	for(int i=0;i<this.getListOfUsers().size() && !userIn;i++)
    	{
    		if(this.getListOfUsers().get(i).getNumUser()==numUser)
    		{
    			userIn=true;
    		}
    	}
    	
    	return userIn;
    }
    
    public boolean isPoolIdentical(Pool pool)
    {
    	boolean identical = true;
    	
    	    	
    	if(this.getListOfUsers().size() == pool.getListOfUsers().size())
    	{
    		for(int i=0;i<pool.getListOfUsers().size() && identical;i++)
    		{
    			identical = identical && isUserInPool(pool.getListOfUsers().get(i).getNumUser());
    			
    		}
    	}
    	else
    	{
    		identical=false;
    	}
    	
    	
    	return identical;
    }

	/**
	 * @return the fartherUser
	 */
	public User getFartherUser() {
		return fartherUser;
	}

	/**
	 * @param fartherUser the fartherUser to set
	 */
	public void setFartherUser(User fartherUser) {
		this.fartherUser = fartherUser;
	}

	/**
	 * @return the fartherDistG
	 */
	public double getFartherDistG() {
		return fartherDistG;
	}

	/**
	 * @param fartherDistG the fartherDistG to set
	 */
	public void computeFartherUserG() {
		//this.fartherDistG = fartherDistG;
		double distanceMax=0.0;
		User userG = new User();
		userG.setX(this.getGravityCentreX());
		userG.setY(this.getGravityCentreY());
		double currentDist=0.0;
		int posFartherUser=0;
		if(this.getListOfUsers().size()==0)
			return;
		for(int i=0;i<this.getListOfUsers().size();i++)
		{
			currentDist = Utilisateurs.euclidienDistance(this.getListOfUsers().get(i), userG); 
			if(currentDist > distanceMax)
			{
				distanceMax = currentDist;
				posFartherUser = i;
			}
		}
		
		this.fartherDistG = distanceMax; 
		this.setFartherUser(this.getListOfUsers().get(posFartherUser));
	}
	
}
