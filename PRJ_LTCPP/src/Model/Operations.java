package Model;

import java.util.ArrayList;
import java.util.Random;

public class Operations {

	//Try to pool one user from a list
	// TODO : add it to Pool class
	public static boolean poolOneUser(Pool pool, ArrayList<User> usersList)
	{
		//boolean valid = false;
		User userToPool;
		boolean pooled=false;
		
		for(int i=0;i<usersList.size() && !pooled;i++)
		{
			userToPool = usersList.get(i);
			pool.addUser(userToPool);
			if(!pool.buildRoutes())
			{
				pool.getListOfUsers().remove(pool.getListOfUsers().size() - 1);
			}
			else
			{
				usersList.remove(i);
				pooled=true;
			}
		}
		
		System.out.println("poolOneUser 1 usersList size : " + usersList.size());
		pool.buildRoutes();
		return pooled;
	}
	///////////////////// Repair : Begin ////////////////////////////////////////////
	
	public static boolean repairPool(Pool invalidPool,ArrayList<Pool> poolsList)
	{
		boolean valid = false;
		//poolsList = new ArrayList<Pool>();
		
		Pool pool1;
		Pool pool2;
		
		//ArrayList<Pool> repairedPools = new ArrayList<Pool>();
		
		User currentUser;
		
		int nbUsers = invalidPool.getListOfUsers().size();
		
		System.out.println("repairPool nbUsers : " + nbUsers);
		
		if(invalidPool.getListOfUsers().size()>2)
		{
					
			int randomNum = (invalidPool.getListOfUsers().size()) + 1;
			while(randomNum >= invalidPool.getListOfUsers().size() &&  invalidPool.getListOfUsers().size()>0)
			{
				int low = 1;
				int high = (invalidPool.getListOfUsers().size()) + 1;
				Random rand = new Random();
				randomNum = rand.nextInt(high - low);
			}
					
			System.out.println("repairPool randomNum : " + randomNum);
			currentUser = invalidPool.getListOfUsers().get(randomNum);
					
			invalidPool.getListOfUsers().remove(randomNum);
			pool1 = new Pool();
					
			//first user of the new pool
			pool1.addUser(currentUser);
					
			System.out.println("repairPool pool1.size : " + pool1.getListOfUsers().size());
					
			// try to pool one user
			valid = poolOneUser(pool1,invalidPool.getListOfUsers());
			System.out.println("bool valid : " + valid);
			if(!valid)
			{
				poolsList.add(pool1);
						
				pool1 = new Pool();
				pool1.addUser(invalidPool.getListOfUsers().get(0));
				invalidPool.getListOfUsers().remove(0);
						
				System.out.println("invalid Pool size 2 : " + invalidPool.getListOfUsers().size());
						
				while(invalidPool.getListOfUsers().size() > 0)
				{
					valid = poolOneUser(pool1,invalidPool.getListOfUsers());
					if(!valid)
					{
						poolsList.add(pool1);
						pool1 = new Pool();
						if(invalidPool.getListOfUsers().size() > 0)
						{
							pool1.addUser(invalidPool.getListOfUsers().get(0));
							invalidPool.getListOfUsers().remove(0);
						}
						if(invalidPool.getListOfUsers().size() == 0 && pool1.getListOfUsers().size() > 0)
						{
							pool1.buildRoutes();
							poolsList.add(pool1);
						}
					}
							
						
				}
						
				for(int k=0;k<poolsList.size();k++)
				{	
					nbUsers = nbUsers - poolsList.get(k).getListOfUsers().size();
				}
				if(nbUsers > 0)
				{
					pool1.buildRoutes();
					poolsList.add(pool1);
				}
						// TODO : a last add pool to poolsList
						//}
			}
			else
			{
				System.out.println("repair Pool pool1 size : " + pool1.getListOfUsers().size());
				//Case of 2 users in the first pool
						
				
				valid = invalidPool.buildRoutes();
				if(!valid)
				{
					System.out.println("invalidPool.buildRoutes() false. size " + invalidPool.getListOfUsers().size());
					//TODO : case of (3,1)
					valid = poolOneUser(pool1,invalidPool.getListOfUsers());
					if(valid)
					{
						poolsList.add(pool1);
					}
					else
					{
						poolsList.add(pool1);
					}
							
					//Case (2,1,1) or (3,1)
					while(invalidPool.getListOfUsers().size()>0)
					{
						pool2 = new Pool();
						pool2.addUser(invalidPool.getListOfUsers().get(0));
						pool2.buildRoutes();
						poolsList.add(pool2);
						invalidPool.getListOfUsers().remove(0);
					}
							
				}
				else
				{
					poolsList.add(pool1);
					
					System.out.println("invalidPool.buildRoutes() true. size " + invalidPool.getListOfUsers().size());
					pool2 = new Pool();
					while(invalidPool.getListOfUsers().size()>0)
					{
						pool2.addUser(invalidPool.getListOfUsers().get(0));
						invalidPool.getListOfUsers().remove(0);
					}
					System.out.println("pool2 size : " + pool2.getListOfUsers().size());
					pool2.buildRoutes();
					poolsList.add(pool2);
				}
				
				
			}
					
					
					
		}	
		else
		{
			// a pool with 2 users
			while(invalidPool.getListOfUsers().size()>0)
			{
				pool2 = new Pool();
				pool2.addUser(invalidPool.getListOfUsers().get(0));
				pool2.buildRoutes();
				poolsList.add(pool2);
				invalidPool.getListOfUsers().remove(0);
			}
		}
		
		nbUsers = 0;
		for(int i=0;i<poolsList.size();i++)
		{
			nbUsers  = nbUsers + poolsList.get(i).getListOfUsers().size();
		}
		System.out.println("XXX : " + nbUsers);
		System.out.println("repairPool poolsList size : " + poolsList.size());
		System.out.println("repairPool invalidPool users size : " + invalidPool.getListOfUsers().size());
		
		return valid;
	}
	
	
	//////////////////////// Repair : End ///////////////////////////////////////

	public void divide(Pool pool, boolean opt, ArrayList<Pool> poolsList) {

	}

	public boolean swap(Pool pool1, Pool pool2) 
	{
		return false;
	}

	public boolean merge(Pool pool1, Pool pool2, Pool mergedPool) 
	{
		//
		boolean valid_merge = true;
		//
		mergedPool = new Pool();
		
		
		if(pool1.getListOfUsers().size() + pool2.getListOfUsers().size() > Math.min(pool1.getMinCarCap(), pool2.getMinCarCap()))
		{
			valid_merge = false;
		}
		
		for(int i=0;i<pool1.getListOfUsers().size() && valid_merge;i++)
		{
			mergedPool.addUser(pool1.getListOfUsers().get(i));
		}
		
		for(int i=0;i<pool2.getListOfUsers().size() && valid_merge;i++)
		{
			mergedPool.addUser(pool2.getListOfUsers().get(i));
		}
		
		if(valid_merge)
		{
			valid_merge = mergedPool.buildRoutes();
		}
		
		if(valid_merge)
		{
			valid_merge = (mergedPool.getAvgCost()<= (pool1.getAvgCost() + pool2.getAvgCost()));
			
		}
		//Verify if the new cost is better then the ones of two precedent
		
		
		return valid_merge;
	}

	// TODO : to remove or to let
	/*
	 * public Object clone() { Users clonedUsers = null; try { // On récupère
	 * l'instance à renvoyer par l'appel de la // méthode super.clone() clonedUsers
	 * = (Users) super.clone(); } catch(CloneNotSupportedException cnse) { // Ne
	 * devrait jamais arriver car nous implémentons // l'interface Cloneable
	 * cnse.printStackTrace(System.err); }
	 * 
	 * clonedUsers.users = (ArrayList<User>) users.clone();
	 * 
	 * // On clone l'attribut de type User qui n'est pas immuable.
	 * 
	 * for (int i=0; i<users.size();i++) { //u = (User) user.clone();
	 * clonedUsers.users.set(i, (User) users.get(i).clone()); }
	 * 
	 * // on renvoie le clone return clonedUsers; }
	 */

	public boolean move(Pool pool1, Pool pool2) {
		return true;
	}
}
