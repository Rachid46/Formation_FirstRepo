package Model;

import java.util.ArrayList;


public class Utilisateurs {

	private static int[][] all_users; //Initial users
	//public double[][] all_users; //Initial users
	private static double[][] tim_users; //Initial users
	//public double[][] tim_users; //Initial users
	private static int[][] authorizedUsers; //0 not authorized, 1 authorized
	
	
	public static int[][] getAuthorizedUsers() {
		return authorizedUsers;
	}

	public static int euclidienDistance (User user1, User user2)
	{
		return (int) Math.round(Math.sqrt(Math.pow(user1.getX() - user2.getX(), 2) + Math.pow(user1.getY() - user2.getY(), 2)));
	}

	public static void distances(ArrayList<User> users, User workplace)
	{
		all_users = new int[users.size() + 1][users.size() + 1];
		
		System.out.println("Users size : " + users.size());
		
		//distances to the workplace
		for(int i=0;i<users.size();i++)
		{
			all_users[0][i+1] =  euclidienDistance(workplace,users.get(i));
			all_users[i+1][0] = all_users[0][i+1]; 
		}
		
		all_users[0][0] = 0;
		
		// distances between users
		for (int i=1;i<all_users.length;i++)
		{
			for(int j=i+1;j<all_users.length;j++)
			{
				all_users[i][j] = euclidienDistance(users.get(i-1), users.get(j-1));
				all_users[j][i]= all_users[i][j];
				all_users[i][i] = 0;
								
			}
		}
	}
		
	/*
	 * public int[] clustering() { // Clustering int[] clusters = new
	 * int[Math.round(users.size()/4)]; for(int i=1; i<Math.round(users.size()/4) +
	 * 1;i++) { Random rand = new Random(); int randomNum =
	 * rand.nextInt((users.size() - 1) + 1) + 1; boolean exists = true; if (i>1 &
	 * exists) { exists = false; for(int j=0;j<i & !exists;j++) { if(clusters[j] ==
	 * randomNum) { exists = true; randomNum = rand.nextInt((users.size() - 1) + 1)
	 * + 1; } } }
	 * 
	 * clusters[i-1] = randomNum;
	 * 
	 * }
	 * 
	 * return clusters; }
	 */

	/**
	 * @return the tim_users
	 */
	
	public static double[][] getTim_users() {
		return tim_users;
	}
	

	/**
	 * @param tim_users the tim_users to set
	 */
	/*
	public static void setTim_users(double[][] tim_users) {
		tim_users = tim_users;
	}
	*/
	/**
	 * @return the tim_users
	 */
	
	public static int[][] getAll_users() {
		return all_users;
	}
	
	public static double computeTravelTime(User user1, User user2)
	{
		return  euclidienDistance (user1,user2) * user1.getSpeed();
	}
	
	public static void computeTravelTime(ArrayList<User> users)
	{
		System.out.println("computeTravelTime ");
		tim_users = new double[users.size() + 1][users.size() + 1];
		
		//travel times to the workplace
		for(int i=0;i<users.size();i++)
		{
			tim_users[0][i+1] =  all_users[0][i+1] * users.get(i).getSpeed();
			tim_users[i+1][0] = tim_users[0][i+1]; 
		}
				
		tim_users[0][0] = 0.0;
		
		for (int i=1;i<tim_users.length;i++)
		{
			System.out.println("i : " + i);
			for(int j=i+1;j<tim_users.length;j++)
			{
				tim_users[i][j] = all_users[i][j] * users.get(i-1).getSpeed();
				tim_users[j][i]= tim_users[i][j];
				tim_users[i][i] = 0.0;
				
			}
		}
	}
	
	public static User getNearest(ArrayList<User> users,User currentUser)
	{
		User nearest=null;
		boolean first=true;
		double minDist=1000.0;
		for(int i=0;i<users.size();i++)
		{
			if(currentUser.getNumUser() != users.get(i).getNumUser()) 
			{
				if (first)
				{
					minDist = euclidienDistance(currentUser, users.get(i));
					nearest = users.get(i);
					first=false;
				}
				else
				{
					if(minDist != Math.min(minDist,euclidienDistance(currentUser, users.get(i))))
					{
						minDist = Math.min(minDist,euclidienDistance(currentUser, users.get(i)));
						nearest = users.get(i);
					}
				}
			}
		}
		return nearest;
	}
	
	public static double getEucliDistTime (User user1, User user2)
	{
		return 0.90 * Math.sqrt(Math.pow(user1.getX() - user2.getX(), 2) + Math.pow(user1.getY() - user2.getY(), 2)) 
				+ 0.10 * Math.abs(user1.getEarliestLeaveTime() + computeTravelTime(user1,user2) - user2.getEarliestLeaveTime() );
	}
	
	/*
	public int[] getNearest(int m)
	{
		return a[];
	}
	*/
	
	public static void computeAuthorizedUsers(ArrayList<User> users)
	{
		System.out.println("computeAuthorizedUsers");
		authorizedUsers = new int[users.size() + 1][users.size() + 1];
		
		//travel times to the workplace
		for(int i=0;i<users.size();i++)
		{
			authorizedUsers[0][i+1] = 1;
			authorizedUsers[i+1][0] = 1; 
		}
				
		authorizedUsers[0][0] = 0;
		
		for (int i=1;i<authorizedUsers.length;i++)
		{
			System.out.println("i : " + i);
			for(int j=i+1;j<authorizedUsers.length;j++)
			{
				//tim_users[i][j] = all_users[i][j] * users.get(i-1).getSpeed();
				//tim_users[j][i]= tim_users[i][j];
				//tim_users[i][i] = 0.0;
				int authorized=1;
				if((users.get(i-1).getEarliestLeaveTime() + tim_users[i][j] + tim_users[j][0] > users.get(i-1).getLatestArrivalTime())
				|| (users.get(i-1).getEarliestLeaveTime() + tim_users[i][j] + tim_users[j][0] > users.get(j-1).getLatestArrivalTime())
				|| (users.get(j-1).getEarliestLeaveTime() + tim_users[j][i] + tim_users[i][0] > users.get(i-1).getLatestArrivalTime())
				|| (users.get(j-1).getEarliestLeaveTime() + tim_users[j][i] + tim_users[i][0] > users.get(j-1).getLatestArrivalTime())
				|| (tim_users[i][j] + tim_users[j][0] > users.get(i-1).getMaxTravelTime())
				|| (tim_users[j][i] + tim_users[i][0] > users.get(j-1).getMaxTravelTime())
					)
				{
					authorized=0;
				}
				
				authorizedUsers[i][j]=authorized;
				authorizedUsers[j][i]=authorized;
				authorizedUsers[i][i]=0;
				
			}
		}
	}


	
}
