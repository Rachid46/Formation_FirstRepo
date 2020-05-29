package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


public class Users implements Serializable, Cloneable{
	private ArrayList<User> users;
	
		
	Users()
	{
		this.users = new ArrayList<User>();
	}
	
	    
	/**
	 * @return the userList
	 */
	public ArrayList<User> getUsers() {
		
		return users;
	}

	/**
	 * @param userList the userList to set
	 */
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	} 
	
	public User getIndexOfUsers(int index)
	{
		return this.users.get(index);
	}
	
	public void addUser(User user)
	{
		this.users.add(user);
	}
	
	public int getSize()
	{
		return users.size();
	}
	
	public void removeUser(int i)
	{
		users.remove(i);
	}
	
	@Override
	public Object clone() {
	    Users clonedUsers = null;
	    try {
	    	// On récupère l'instance à renvoyer par l'appel de la 
	      	// méthode super.clone()
	      	clonedUsers = (Users) super.clone();
	    } catch(CloneNotSupportedException cnse) {
	      	// Ne devrait jamais arriver car nous implémentons 
	      	// l'interface Cloneable
	      	cnse.printStackTrace(System.err);
	    }
	    
	    //clonedUsers.users = (ArrayList<User>) users.clone();
	    clonedUsers.setUsers(new ArrayList<User>());
	    // On clone l'attribut de type User qui n'est pas immuable.
	    
	    for (int i=0; i<users.size();i++) {
	    	//u = (User) user.clone();
	    	clonedUsers.users.add((User) users.get(i).clone());
	    }
	    	    
	    // on renvoie le clone
	    return clonedUsers;
	}
	
	
		
	public int[] clustering()
	{   // Clustering
		int[] clusters = new int[Math.round(users.size()/4)];
		for(int i=1; i<Math.round(users.size()/4) + 1;i++)
		{
			Random rand = new Random();
			int randomNum = rand.nextInt((users.size() - 1) + 1) + 1;
			boolean exists = true;
			if (i>1 & exists) {
				exists = false;
				for(int j=0;j<i & !exists;j++)
				{
					if(clusters[j] == randomNum) {
						exists = true;
						randomNum = rand.nextInt((users.size() - 1) + 1) + 1;
					}
					
				}
			}
			
			clusters[i-1] = randomNum;
			
		}
		
		return clusters;
	}


	//getNearest
	
	
	
	public static double getEucliDistTime (User user1, User user2)
	{
		//0.95 and 0.05
		return 0.95 * ( Math.sqrt(Math.pow(user1.getX() - user2.getX(), 2) + Math.pow(user1.getY() - user2.getY(), 2)))  
				+ 0.05 * (Math.abs(user1.getEarliestLeaveTime() + Utilisateurs.computeTravelTime(user1,user2) - user2.getEarliestLeaveTime()))/60.0 ;
				//+ 0.05 * (Math.abs(user1.getEarliestLeaveTime() + Utilisateurs.computeTravelTime(user1,user2) - user2.getEarliestLeaveTime()))/60.0 ;
		
	}
	
	
	public static double getEucliDistance (User user1, User user2)
	{
		return Math.sqrt(Math.pow(user1.getX() - user2.getX(), 2) + Math.pow(user1.getY() - user2.getY(), 2)); 
				
	}
	public static User getNearestInList(User currentUser, ArrayList<User> usersList)
	{
		User nearestUser = null;
		double maxdist = 2000.0;
		double euclidist = 0.0;
		for(int i=0;i<usersList.size();i++)
		{	
			if(currentUser.getNumUser()!=usersList.get(i).getNumUser())
			{
				euclidist = Users.getEucliDistance(currentUser, usersList.get(i));
				if(euclidist < maxdist)
				{
					nearestUser = usersList.get(i);
					maxdist = euclidist;
				}
			}
		}
		
		return nearestUser;
	}
	
	/*
	public int[] getNearest(int m)
	{
		return a[];
	}
	*/
	
	public User getUser(int numUser)
	{
		User user=null;
		
		for(int i=0;i<users.size();i++)
		{
			if(users.get(i).getNumUser()==numUser)
			{
				user=users.get(i);
			}
		}
		
		return user;
	}

}
