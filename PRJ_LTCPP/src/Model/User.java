package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import Utilities.Util;

public class User implements Serializable, Cloneable{
	
	public static short alone=1;
	public static short notalone=0;
	
	//Data set values
	private int numUser;
	private double x;
	private double y;
	private int carCapacity;
	
	private int maxTravelTime;
	private int penalty;
	private int earliestLeaveTime;
	private int latestArrivalTime;
	
	private double speed= 1.2; //km/min
	
	// Computed values 
	private double totalDistance;
	private int travelTime;
	private double hamCost;
	private int arrivalTime;
	
	
	
	// Liste des passagers à visiter par ordre
	//private ArrayList<Integer> usersList;
	private ArrayList<Integer> routeList;
	// Liste des temps de passages
	private ArrayList<Double> pickUpTime;
	
	//travel alone or not
	private short phi = 0;
	
	private int distToWP; // WP : WorkPlace
	private double timToWP;
	
	private Pool pool;
	
	public User()
	{
		routeList = new ArrayList<Integer>();
		pickUpTime = new ArrayList<Double>();
	}
		
	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}
	/**
	 * @return the car_capacity
	 */
	public int getCarCapacity() {
		return carCapacity;
	}
	/**
	 * @param car_capacity the car_capacity to set
	 */
	public void setCarCapacity(int carCapacity) {
		this.carCapacity = carCapacity;
	}
	/**
	 * @return the maxTravelTime
	 */
	public double getMaxTravelTime() {
		return maxTravelTime;
	}
	/**
	 * @param maxTravelTime the maxTravelTime to set
	 */
	public void setMaxTravelTime(int maxTravelTime) {
		this.maxTravelTime = maxTravelTime;
	}
	/**
	 * @return the penalty
	 */
	public int getPenalty() {
		return penalty;
	}
	/**
	 * @param penalty the penalty to set
	 */
	public void setPenalty(int penalty) {
		this.penalty = penalty;
	}
	/**
	 * @return the earliestLeaveTime
	 */
	public double getEarliestLeaveTime() {
		return earliestLeaveTime;
	}
	/**
	 * @param earliestLeaveTime the earliestLeaveTime to set
	 */
	public void setEarliestLeaveTime(int earliestLeaveTime) {
		this.earliestLeaveTime = earliestLeaveTime;
	}
	
	public void updateEarliestLeaveTime()
	{
		this.earliestLeaveTime = (int) Math.round((this.getLatestArrivalTime() - Math.max(this.getTimToWP() + 30, 2*this.getTimToWP())));
	}
	/**
	 * @return the latestArrivalTime
	 */
	public int getLatestArrivalTime() {
		return latestArrivalTime;
	}
	/**
	 * @param latestArrivalTime the latestArrivalTime to set
	 */
	public void setLatestArrivalTime(int latestArrivalTime) {
		this.latestArrivalTime = latestArrivalTime;
	}
	/**
	 * @return the hamCost
	 */
	public double getHamCost() {
		return hamCost;
	}
	/**
	 * @param hamCost the hamCost to set
	 */
	public void setHamCost(double hamCost) {
		this.hamCost = hamCost;
	}
	/**
	 * @return the arrivalTime
	 */
	public double getArrivalTime() {
		return arrivalTime;
	}
	/**
	 * @param arrivalTime the arrivalTime to set
	 */
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	/**
	 * @return the numUser
	 */
	public int getNumUser() {
		return numUser;
	}
	/**
	 * @param numUser the numUser to set
	 */
	public void setNumUser(int numUser) {
		this.numUser = numUser;
	}
	/**
	 * @return the listUsers
	 */
	public ArrayList<Integer> getRouteList() {
		return routeList;
	}
	/**
	 * @param listUsers the listUsers to set
	 */
	public void settRouteList(ArrayList<Integer> usersList) {
		this.routeList = usersList;
	}
	/**
	 * @return the pickUpTime
	 */
	public List<Double> getPickUpTime() {
		return pickUpTime;
	}
	/**
	 * @param pickUpTime the pickUpTime to set
	 */
	public void setPickUpTime(ArrayList<Double> pickUpTime) {
		this.pickUpTime = pickUpTime;
	}
	
	
	@Override
	public Object clone() {
	    User user = null;
	    try {
	    	// On récupère l'instance à renvoyer par l'appel de la 
	      	// méthode super.clone()
	      	user = (User) super.clone();
	    } catch(CloneNotSupportedException cnse) {
	      	// Ne devrait jamais arriver car nous implémentons 
	      	// l'interface Cloneable
	      	cnse.printStackTrace(System.err);
	    }
	    
	    // On clone l'attribut de type Patronyme qui n'est pas immuable.
	    //personne.patronyme = (Patronyme) patronyme.clone();
	    
	    // on renvoie le clone
	    return user;
	}
	
	/**
	 * @return the totalDistance
	 */
	public double getTotalDistance() {
		return totalDistance;
	}
	/**
	 * @param totalDistance the totalDistance to set
	 */
	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}
	/**
	 * @return the travelTime
	 */
	public int getTravelTime() {
		return travelTime;
	}
	/**
	 * @param travelTime the travelTime to set
	 */
	public void setTravelTime(int travelTime) {
		this.travelTime = travelTime;
	}
	/**
	 * @return the speed
	 */
	public double getSpeed() 
	{
		return speed;
	}
	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(double speed) 
	{
		this.speed = speed;
	}
	/**
	 * @return the distToWP
	 */
	public double getDistToWP() {
		return distToWP;
	}
	/**
	 * @param distToWP the distToWP to set
	 */
	public void setDistToWP(int distToWP) {
		this.distToWP = distToWP;
	}
	/**
	 * @return the timToWP
	 */
	public double getTimToWP() {
		return timToWP;
	}
	/**
	 * @param timToWP the timToWP to set
	 */
	public void setTimToWP(double timToWP) {
		this.timToWP = timToWP;
	}
	
	public int computeDistWP(User workplace)
	{
		return Utilisateurs.euclidienDistance(this, workplace);
	}
	
	public double computeTravelTimWP(User workplace)
	{
		return Utilisateurs.computeTravelTime(this, workplace);
	}
	/**
	 * @return the phi
	 */
	public short getPhi() {
		return phi;
	}
	/**
	 * @param phi the phi to set
	 */
	public void setPhi(short phi) {
		this.phi = phi;
	}
	
	
	//
	public boolean isMaxTravelTimeViolated(double driverTravelTime)
	{
		return(driverTravelTime> this.getMaxTravelTime()); //+ this.getTimToWP());
	}
	
		
	public boolean buildRoute()
	{
		this.setPhi(notalone);
		double timPickup=0.0;
		boolean valid=true;
		boolean existRoute=false;
		ArrayList<Integer> bestRoute;
		bestRoute = new ArrayList<Integer>();
		boolean first=true; // first valid build of route
		//int iter = 0;
		double bestDist=10000.0;
		
		
		this.setHamCost(0.0);
		pickUpTime.clear();
		
		this.setPhi(notalone);
		
		//Case of a user alone
		if(routeList.size()==1)
		{
			if(isMaxTravelTimeViolated(this.getTimToWP()))
			{
				valid = false;
			};
			
			if(this.getEarliestLeaveTime() + this.getTimToWP() > this.getLatestArrivalTime())
			{
				valid = false;
			}
			
			
			if(valid)
			{
				pickUpTime.add(0,this.getEarliestLeaveTime());
				this.setHamCost(this.getDistToWP());
				this.setPhi(alone);
			}	
			
			return valid;
			
		}
		
		//Case of more than one user in a pool
		//ArrayList<ArrayList<Integer>> l ;
		if(Util.arrRet == null)
		{
			Util.arrRet = new ArrayList<ArrayList<Integer>>();
		}
		else
		{
			Util.arrRet.clear();
		}
		Util.permute(routeList, 1);
		for(int j=0;j<Util.arrRet.size();j++)
		{
			double distRoute=0.0;
			double travelTime=0.0;
			ArrayList<Integer> currRoute = new ArrayList<Integer>();
			currRoute = Util.arrRet.get(j);
			ArrayList<Double> currPickUpTime = new ArrayList<Double>(); 
			currPickUpTime.add(this.getEarliestLeaveTime());
			
			System.out.println("currPick size : " + currPickUpTime.size());
			valid=true;
									
			for(int i=1;i<currRoute.size() && valid ;i++)
			{
				User user;
								
				//timPickup = Math.max(Users.getTim_users()[currRoute.get(i-1)][currRoute.get(i)],pool.getListOfUsers().get(currRoute.get(i)).getEarliestLeaveTime());
				System.out.println("i : " + i);
				System.out.println("i-1 " + currRoute.get(i-1));
				System.out.println("i " + currRoute.get(i));
				
				travelTime = travelTime + Utilisateurs.getTim_users()[currRoute.get(i-1)][currRoute.get(i)];
				timPickup = Math.max(currPickUpTime.get(i-1) + Utilisateurs.getTim_users()[currRoute.get(i-1)][currRoute.get(i)],pool.getUser(currRoute.get(i)).getEarliestLeaveTime());
				
				currPickUpTime.add(timPickup);
				
				
				for(int s=i;s>0;s--)
				{
					currPickUpTime.set(s-1, currPickUpTime.get(s) - Utilisateurs.getTim_users()[currRoute.get(s-1)][currRoute.get(s)]);
				}
					
				//Verify the arrival time of the current user
				user = pool.getUser(currRoute.get(i));
				
				//valid = !isMaxTravelTimeViolated(travelTime + user.getTimToWP());
				
				if((timPickup + Utilisateurs.getTim_users()[currRoute.get(i)][0] > user.getLatestArrivalTime()) || (this.isMaxTravelTimeViolated(travelTime)) )
				{
					valid=false;
					break;
				}
				
				distRoute = distRoute + Utilisateurs.getAll_users()[currRoute.get(i-1)][currRoute.get(i)];
				
				if(distRoute>=bestDist)
				{
					valid=false;
					break;
				}
				//timPickup = max(Users.tim_Users[routeList.get(i-1)][routeList.get(i)],users.get(routeList.get(i)).getEarliestLeaveTime());
				//pickUpTime[i-1] = timPickup - timUsers[i-1][i];
				//Add the time from the latest user to the workplace
				if(i==currRoute.size() -1)
				{
					travelTime = travelTime + Utilisateurs.getTim_users()[currRoute.get(i)][0];//pool.getListOfUsers().get(i).getTimToWP();
					this.setArrivalTime((int) Math.round(currPickUpTime.get(i)) + (int) Math.round(Utilisateurs.getTim_users()[currRoute.get(i)][0]));
					distRoute = distRoute + Utilisateurs.getAll_users()[currRoute.get(i)][0];
				}
			}
			
			if(valid)
			{
			
			
				// control the travel time of the driver
				if(isMaxTravelTimeViolated(travelTime) || this.getArrivalTime() > this.getLatestArrivalTime())
				{
					valid = false;
				}
				
				System.out.println("currPickUpTime Size : " + currPickUpTime.size());
				System.out.println("currRoute Size : " + currRoute.size());
				System.out.println("Pool size : " + pool.getListOfUsers().size());
				//Verify the latestArrivalTime for each User
				for(int i=0;i<currRoute.size() && valid ;i++)
				{
					//if((travelTime > pool.getUser(currRoute.get(i)).getLatestArrivalTime()) 
					if((this.getArrivalTime() > pool.getUser(currRoute.get(i)).getLatestArrivalTime())
					|| (currPickUpTime.get(i) < pool.getUser(currRoute.get(i)).getEarliestLeaveTime())
					|| (currPickUpTime.get(i) > pool.getUser(currRoute.get(i)).getLatestArrivalTime())
					   )
					{
						valid = false;
					}
				}
				
				//set the best route if valid
				if(valid)
				{
					
					existRoute=true;
					if(first)
					{
						bestDist = distRoute;
						bestRoute.clear();
						for(int k=0;k<currRoute.size();k++)
						{
							bestRoute.add(currRoute.get(k));
						}
						
						pickUpTime = currPickUpTime;
						
						first=false;
					 
					}
					else
					{
						if(distRoute<bestDist)
						{
							bestDist=distRoute;
							bestRoute.clear();
							for(int k=0;k<currRoute.size();k++)
							{
								bestRoute.add(currRoute.get(k));
							}
							
							pickUpTime = currPickUpTime;
						}
					}
					
					routeList = bestRoute;
					this.setHamCost(bestDist);
				}
			
			}
	
		}
		
		return existRoute;
	}
	/**
	 * @return the pool
	 */
	public Pool getPool() {
		return pool;
	}
	/**
	 * @param pool the pool to set
	 */
	public void setPool(Pool pool) {
		this.pool = pool;
	}
	
	public void swap(int i, int j)
	{
		int swap = routeList.get(i);
		routeList.set(i,routeList.get(j));
		routeList.set(j, swap);
	}
	
	 public static Comparator<User> ComparatorDistG = new Comparator<User>() {
	   	 
	     
	        @Override
	        public int compare(User user1, User user2) {
	            //return (int) Math.round((uc1.getRegret() - uc2.getRegret()));
	        	if (Math.sqrt(Math.pow(user1.getX() - user1.getPool().getGravityCentreX() ,2) + Math.sqrt(Math.pow(user1.getY() - user1.getPool().getGravityCentreY(),2))) < 
	        			Math.sqrt(Math.pow(user2.getX() - user2.getPool().getGravityCentreX() ,2) + Math.sqrt(Math.pow(user2.getY() - user2.getPool().getGravityCentreY(),2))))
	        		
	        		//pool2.getGravityCentreDist())
	        		return -1;
	        	else if (Math.sqrt(Math.pow(user1.getX() - user1.getPool().getGravityCentreX() ,2) + Math.sqrt(Math.pow(user1.getY() - user1.getPool().getGravityCentreY(),2))) > 
	        			Math.sqrt(Math.pow(user2.getX() - user2.getPool().getGravityCentreX() ,2) + Math.sqrt(Math.pow(user2.getY() - user2.getPool().getGravityCentreY(),2))))
	        		return 1;
	           	else
	        		return 0;
	        	
	        }
	        
	    };
	
}
