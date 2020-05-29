package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class InitPopulation {
	
	//Use of different strategies
	//according to the user choice
	//Sweep Line algorithm
	//Random approach
	//CAC : Clustering Ant Colony
	//K-means
	//Mixed methods
	//Others
	
	Users users;
	
	
	public InitPopulation(Users users){
		this.users = users;
		//this.clonedUsers = (Users) users.clone();
	}
	
	
	//public solution randPop() {
	public void randPop(Solution solution,int randomOpt) {
		
		//20 is the population size
		//Users clonedUsers;
		Users clonedUsers = (Users) CarPool.users.clone();
		
		System.out.println("Taille de clonedUsers : " + clonedUsers.getUsers().size());
		
		//Users unselectedUsers = (Users) users.clone();
		//remove workplace
		//clonedUsers.getUsers().remove(0);
		
		//Solution solution = new Solution();
		
		ArrayList<UserCandidat> candidatToPool = new ArrayList<UserCandidat>();
		
		while(clonedUsers.getUsers().size() > 0)
		{
			//
			Pool pool=new Pool();
			//select randomly a user
			
			int randomNum = (clonedUsers.getUsers().size()) + 1;
			while(randomNum >= clonedUsers.getUsers().size() &  clonedUsers.getUsers().size()>0)
			{
				int low = 1;
				int high = (clonedUsers.getUsers().size()) + 1;
				Random rand = new Random();
				randomNum = rand.nextInt(high - low);
			}
			
			System.out.println("randomNum : " + randomNum);
			System.out.println("Size : " + clonedUsers.getUsers().size());
			
			pool.addUser(clonedUsers.getIndexOfUsers(randomNum));
			pool.setMinCarCap();
						
			User currentUser;
			currentUser = clonedUsers.getIndexOfUsers(randomNum);
			pool.setRepresentative(currentUser);
			
			//candidat.setUser(userRem);
			//candidatToPool(candidat);
			
			
			//unselectedUsers.removeUser(currentUser.getNumUser());
			
			// remove User from the list
			clonedUsers.removeUser(randomNum);
			
			/*
			Iterator<Object> iterator = list.iterator();
			while ( iterator.hasNext() ) {
			    Object o = iterator.next();
			    if (test(o)) {
			        // On supprime l'élément courant de la liste
			        iterator.remove();
			    }
			}
			*/
			//Remove m nearest users of randomNum user
			//get the m nearest
			
			int i=0;
			int m= currentUser.getCarCapacity() - 1 ;
			
			//remove nearest potential users to add to the current pool
			while(clonedUsers.getUsers().size()>1 && i<m)
			{
				UserCandidat candidat = new UserCandidat();
				User userRem = Users.getNearestInList(currentUser,clonedUsers.getUsers());//clonedUsers.getNearest(currentUser);
				candidat.setUser(userRem);
				//candidat.addReprPool(pool);
				candidatToPool.add(candidat);
				clonedUsers.getUsers().remove(userRem);
				i++;
				
			}
			
			
			solution.addPool(pool);
			
		}
		
		//List of representative pool of each unselected user
		//ArrayList<User> reprPool;
		//reprPool = new ArrayList<User>();
		System.out.println("YYY : " + solution.getPools().size());
		System.out.println("ZZZ : " + candidatToPool.size());
		
		
		for(int i=0;i<candidatToPool.size();i++)
		{
			//add the pool for each candidat to pool
			for(int j=0;j<solution.getPools().size();j++)
			{
				candidatToPool.get(i).addReprPool(solution.getPools().get(j));
				
			}
			//Sort
			//candidatToPool.get(i).ComparatorDist.compare(o1, o2)
			//for(int j=0;j<candidatToPool.size();j++)
			//{
			//Collections.sort(candidatToPool.get(i).getCandidatPools(),candidatToPool.get(j).ComparatorDist);
			//}
			Collections.sort(candidatToPool.get(i).getCandidatPools(),candidatToPool.get(i).ComparatorDist);
			//candidatToPool.get(i).ComparatorDist();
			//candidatToPool.get(i).ComparatorDist();
			//Compute regret
			candidatToPool.get(i).computeRegret();
			//unselectedUsers.getIndexOfUsers(i).setRegret(Users.getEucliDistTime(reprPool.get(1), reprPool.get(0)));
			
			
		}
		
		//Sort by regret (decreasing or increasing ?)
		if(randomOpt==0)
		{
			Collections.sort(candidatToPool, UserCandidat.ComparatorRegret);
			Collections.reverse(candidatToPool);
		}
		
		//Affect the candidate users to pools
		boolean affected;
		int k;
		for(int i=0;i<candidatToPool.size();i++)
		{
			k=0;
			affected=false;
			while(k<candidatToPool.get(i).getCandidatPools().size() && !affected)
			{
				if(candidatToPool.get(i).getCandidatPools().get(k).getRestCarCap()>0 &&
				   candidatToPool.get(i).getUser().getCarCapacity()>candidatToPool.get(i).getCandidatPools().get(k).getListOfUsers().size())
				{
					
					candidatToPool.get(i).getCandidatPools().get(k).addUser(candidatToPool.get(i).getUser());
					candidatToPool.get(i).getCandidatPools().get(k).setMinCarCap();
					affected=true;
				}
				k++;
			}
			
			if(!affected)
			{
				//Create a new pool with one user
				Pool pool=new Pool();
				candidatToPool.get(i).getUser().setPhi(User.alone);
				pool.addUser(candidatToPool.get(i).getUser());
				solution.addPool(pool);
			}
		}
		
		//Verify time window constraints
		ArrayList<Pool> invalidPools = new ArrayList<Pool>();
		int m=0;
		for(int i=0;i<solution.getPools().size();i++)
		{
			//Verify the time window when the user i acts as a server
			boolean valid = solution.getPools().get(i).buildRoutes();
			
			if(!valid)
			{
				invalidPools.add(solution.getPools().get(i));
				m++;
			}
			else
			{	System.out.print("Pool : ");
				for(int j=0;j<solution.getPools().get(i).getListOfUsers().size();j++)
				{	
					System.out.print(solution.getPools().get(i).getListOfUsers().get(j).getNumUser() + ",");
					System.out.println();
					System.out.println("Route : " + solution.getPools().get(i).getListOfUsers().get(j).getTimToWP());
						
				}
				System.out.println();
				System.out.println("cost : " + solution.getPools().get(i).getAvgCost());
			}
			
			/*
			 * while(!valid) {
			 * 
			 * //divide : create a class Divide? m++; //verify time window };
			 */
			//for(int)
			//while()
		}
		
		int nbUsers = 0;
		for(int i=0;i<solution.getPools().size();i++)
		{	
			nbUsers = nbUsers + solution.getPools().get(i).getListOfUsers().size();
		}
		
		System.out.println("Users size 1 : " + nbUsers);
		
		//Repair of the invalid pools
		while(invalidPools.size()>0)
		{
			Pool invalidPool;
			ArrayList<Pool> validPools = new ArrayList<Pool>();
			invalidPool = (Pool) invalidPools.get(0).clone();
			//solution.getPools().remove(invalidPools.get(0));
			solution.removePool(invalidPools.get(0));
			System.out.println("Invalid Pool size : " + invalidPools.get(0).getListOfUsers().size());
			System.out.println("Invalid 2 : " + invalidPool.getListOfUsers().size());
			Operations.repairPool(invalidPool, validPools);
			invalidPools.remove(0);
			System.out.println("Valid Pools size : " + validPools.size());
			for(int i=0;i<validPools.size();i++)
			{
				solution.addPool(validPools.get(i));
				System.out.println("iteration " + i + " pool size : " + validPools.get(i).getListOfUsers().size());
			}
		}
		
		//Repair invalid pools
		m=0;
		for(int i=0;i<solution.getPools().size();i++)
		{
			if(!solution.getPools().get(i).isValidPool())
			{
				m++;
			}
		}
		
		double totalCost = 0.0;
		nbUsers = 0;
		for(int i=0;i<solution.getPools().size();i++)
		{	double poolcost=0.0;
			nbUsers = nbUsers + solution.getPools().get(i).getListOfUsers().size();
			System.out.println("Pool " + i + " size : " + solution.getPools().get(i).getListOfUsers().size());
			for(int j=0;j<solution.getPools().get(i).getListOfUsers().size();j++)
			{			
				System.out.println("User-" + solution.getPools().get(i).getListOfUsers().get(j).getNumUser() + " ; pckuptime" + solution.getPools().get(i).getListOfUsers().get(j).getPickUpTime());
			}
			poolcost = poolcost + solution.getPools().get(i).getAvgCost();
			System.out.println("Pool cost " + i + " cost = " + poolcost);
			System.out.println("Statut : " + solution.getPools().get(i).isValidPool());
			totalCost = totalCost + poolcost;
		}
		System.out.println("Nb Users : " + nbUsers);
		System.out.println("Nombre de pools : " + solution.getPools().size());
		System.out.println("Nombre de pools non valides : " + m);
		System.out.println("Total cost = " + totalCost);
		
		/*
		for(int i=0;i<clusterings.length;i++)
		{
			System.out.println("random i : " + i + " est " + clusterings[i]);
		}
		*/
		
		//return solution;
		
		if(randomOpt==0)
		{
		//Merger les pools avec 1 user
		//trier les pools selon la taille
		Collections.sort(solution.getPools(), Pool.ComparatorSize);
		
		ArrayList<Pool> poolsListToRemove = new ArrayList<Pool>();
		
		for(int i=0;i<solution.getPools().size();i++)
		{
			boolean found = false;
			for(int j=i+1;j<solution.getPools().size()-1 && !found;j++)
			{
				if(solution.getPools().get(i).getListOfUsers().size()==1 && solution.getPools().get(j).getListOfUsers().size()<4)
				{
					User user1 = solution.getPools().get(i).getListOfUsers().get(0);
					//int numU2 = solution.getPools().get(j).getListOfUsers().get(0).getNumUser();
					if(solution.getPools().get(j).areAllUsersAuthorized(user1.getNumUser()))
					{
						solution.getPools().get(j).addUser(user1);
						if(solution.getPools().get(j).buildRoutes())
						{
							poolsListToRemove.add(solution.getPools().get(i)); 
							found=true;
						}
						else
						{
							solution.getPools().get(j).removeUser(user1);
							solution.getPools().get(j).buildRoutes();
						}
					}
					
				}
			}
		}
		
		for(int i=0; i<poolsListToRemove.size();i++)
		{
			solution.getPools().remove(poolsListToRemove.get(i));
		}
		
		}
	}
		
	
	
	
}
