package BBO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import Model.CarPool;
import Model.Pool;
import Model.PoolUtilities;
import Model.Utilisateurs;

public class Migration {

	private Habitats habitats;
	private BBO_Parameters bboParameters;
	private EmigrationRates emigrationRates = null;
	private ImmigrationRates immigrationRates = null;

	public Migration(Habitats habitats, BBO_Parameters bboParameters, ImmigrationRates immigrationRates,
			EmigrationRates emigrationRates) {
	
		this.habitats = habitats;
		this.bboParameters = bboParameters;
		this.emigrationRates = emigrationRates;
		this.immigrationRates = immigrationRates;
	}
		
	//////////////////////////////////////////
	public void getMigratedHabitats() {
		
		// Random rand = new Random(basicParameters.getRandomSeed());
		Random rand = new Random(0);
		
		
		//////////////////////////////
		for (int i = 0; i < habitats.getHabitats().size(); i++) 
		{
			
			if (rand.nextDouble() < immigrationRates.getImmigrationRates()[i]) 
			{
				//int nbSivs=0;
				//int k = Math.max((new Random()).nextInt(5),3);
				for (int j = 0; j < habitats.getHabitats().size(); j++) 
				{
					//int l = (new Random()).nextInt(habitats.getHabitats().size());
					if(j!=i)
					{
						
						if(rand.nextDouble()< emigrationRates.getEmigrationRates()[j])
						{
							
							
							Random rand2 = new Random();
							//int randomNum = rand2.nextInt(habitats.getHabitats().get(j).getSivs().size());
							int randomNum = rand2.nextInt(habitats.getHabitats().get(j).getSivs().size());
							
							//Avoid SIVs containing one component
							
							//int randomNum;
							
							
							
							//nbSivs++;
							
							int loops = 0;						
							while( habitats.getHabitats().get(j).getSivs().get(randomNum).getListOfUsers().size()==1 && loops<5)
							{
								randomNum = rand2.nextInt(habitats.getHabitats().get(j).getSivs().size());
								loops++;
							}
							
																			
								//Select Hj with probability muj
								//int m = Math.min(j, habitats.getHabitats().get(j).getSivs().size() - 1);
								
								//Habitat clonedHabitat = (Habitat) habitats.getIndexOfHabitats(i).clone();
								
								if(habitats.getHabitats().get(j).getSivs().get(randomNum).getListOfUsers().size()>1) 
								{
									immigrateSIV(habitats.getIndexOfHabitats(i) ,(Pool) habitats.getHabitats().get(j).getSivs().get(randomNum).clone());
								}
							
							 
							//Pool oppPool= new Pool();
							//CarPool.buildNearestOppositePool((Pool) habitats.getHabitats().get(j).getSivs().get(randomNum).clone(), oppPool);
							//immigrateSIV2(clonedHabitat ,oppPool);
							
							//if (clonedHabitat.getHSI()<habitats.getIndexOfHabitats(i).getHSI())
							//{
							//	habitats.getHabitats().set(i, clonedHabitat);
							//}
							
						}
						
					}
				} 
			}
		}

		//////////////////////////////
	}
	/////////////////////////////////////////
	
	private void immigrateSIV(Habitat habitati, Pool pool) {
		
		
		boolean removed = false;

		//pool.buildRoutes();
		
		ArrayList<Pool> poolsListOneUser = new ArrayList<Pool>();
		
		//avoid to add a pool that exists yet
		boolean newSiv=false;
		for(int i=0;i<habitati.getSivs().size() && !newSiv;i++)
		{
			//if(habitati.getSivs().get(i).getListOfUsers().size()==pool.getListOfUsers().size())
			if(habitati.getSivs().get(i).isUserInPool(pool.getListOfUsers().get(0).getNumUser()))
			{
				if(!habitati.getSivs().get(i).isPoolIdentical(pool))
				{
					newSiv = true;
				}
			}			
			
		}
		
		if(!newSiv)
		{
			return;
		}
		
		// add
		habitati.getSivs().add(pool);
		
		// remove the users in the other pools
		for (int i = habitati.getPools().size() - 1; i >= 0; i--) {
			if (habitati.getPools().get(i) != pool) {
				boolean emptyPool = false;
				for (int k = 0; k < pool.getListOfUsers().size() && !emptyPool; k++) {
					removed = false;
					for (int j = habitati.getPools().get(i).getListOfUsers().size() - 1; j >= 0 && !removed; j--) {

						if (habitati.getPools().get(i).getListOfUsers().get(j).getNumUser() == pool.getListOfUsers()
								.get(k).getNumUser()) {
							habitati.getPools().get(i).getListOfUsers().remove(j);
							if(!poolsListOneUser.contains(habitati.getPools().get(i)))
							{
								poolsListOneUser.add(habitati.getPools().get(i));
							}
							removed = true;
							
						}

						if (habitati.getPools().get(i).getListOfUsers().size() == 0) {
							emptyPool = true;
							break;
						}
					}
				}

				if (removed) {
					if (habitati.getSivs().get(i).getListOfUsers().size() == 0) {
						habitati.getSivs().remove(i);
					} else {
						habitati.getSivs().get(i).buildRoutes();
					}
				}
				
				//for(int)
			}
		}
		
		//Try to merge pools affected by migration
		//group users alone
		for(int i=0; i<poolsListOneUser.size();i++)
		{
					if(poolsListOneUser.get(i).getListOfUsers().size()==1)
					{
						for(int j=i+1;j<poolsListOneUser.size();j++)
						{
							if(poolsListOneUser.get(j).getListOfUsers().size()==1)
							{
								int numU1 = poolsListOneUser.get(i).getListOfUsers().get(0).getNumUser();
								int numU2 = poolsListOneUser.get(j).getListOfUsers().get(0).getNumUser();
								if(Utilisateurs.getAuthorizedUsers()[numU1][numU2]==1)
								{
									poolsListOneUser.get(i).addUser(poolsListOneUser.get(j).getListOfUsers().get(0));
									if(poolsListOneUser.get(i).buildRoutes())
									{
										poolsListOneUser.get(j).removeUser(poolsListOneUser.get(j).getListOfUsers().get(0));
									}
									else
									{
										poolsListOneUser.get(i).removeUser(poolsListOneUser.get(j).getListOfUsers().get(0));
										poolsListOneUser.get(i).buildRoutes();
									}
								}
							}
						}
					}
		}
		
		//Those ones with one user
		for(int i=0;i<poolsListOneUser.size();i++)
		{
			boolean found = false;
			
			if(poolsListOneUser.get(i).getListOfUsers().size()==1)
				
			{	
				poolsListOneUser.get(i).buildRoutes();
				//PoolUtilities.setPool(poolsListOneUser.get(i));
				//Collections.sort(habitati.getSivs(), PoolUtilities.ComparatorGC);
				//Collections.reverseOrder();
				//int posPool = habitati.getSivs().indexOf(pool);
				//double bestPoolCost=1000.0;
				//int bestPoolIndex=-1;
			
				for(int j=0;j<habitati.getSivs().size() && !found ;j++)
				{
					
					if(poolsListOneUser.get(i) != habitati.getSivs().get(j) &&  habitati.getSivs().get(j)!=pool)
					{
						if(habitati.getSivs().get(j).getRestCarCap()>0 && 
						  (habitati.getSivs().get(j).areAllUsersAuthorized(poolsListOneUser.get(i).getListOfUsers().get(0).getNumUser()))
					      && habitati.getSivs().get(j).isUserAuthorized(poolsListOneUser.get(i).getListOfUsers().get(0))
					     )
						{
							double poolCost= poolsListOneUser.get(i).getAvgCost() + habitati.getSivs().get(j).getAvgCost();
							habitati.getSivs().get(j).addUser(poolsListOneUser.get(i).getListOfUsers().get(0));
							if(!habitati.getSivs().get(j).buildRoutes())
							{
								habitati.getSivs().get(j).getListOfUsers().remove(habitati.getSivs().get(j).getListOfUsers().size()-1);
								habitati.getSivs().get(j).buildRoutes();
							}
							else
							{
								//if(bestPoolCost > habitati.getSivs().get(j).getAvgCost())
								//{
								//	bestPoolCost=habitati.getSivs().get(j).getAvgCost();
								//	habitati.getSivs().get(j).getListOfUsers().remove(habitati.getSivs().get(j).getListOfUsers().size()-1);
								//	bestPoolIndex = j;
								//}
								if(habitati.getSivs().get(j).getAvgCost()<= poolCost)
								{
									poolsListOneUser.get(i).getListOfUsers().remove(0);
									found=true;
								}
								else
								{
									habitati.getSivs().get(j).getListOfUsers().remove(habitati.getSivs().get(j).getListOfUsers().size()-1);
									habitati.getSivs().get(j).buildRoutes();
								}
								
							}
						}
					}
				}
				//if(bestPoolIndex>=0)
				//{
				//	habitati.getSivs().get(bestPoolIndex).addUser(poolsListOneUser.get(i).getListOfUsers().get(0));
				//	habitati.getSivs().get(bestPoolIndex).buildRoutes();
				//}
			}
			else
			{
				if(poolsListOneUser.get(i).getListOfUsers().size()>0)
				{
					poolsListOneUser.get(i).buildRoutes();
				}
			}
		}
		
		
		
		//Eliminate the affected pools without any user
		for(int j=habitati.getSivs().size()-1; j>=0 ;j--)
		{
			if(habitati.getSivs().get(j).getListOfUsers().size()==0)
			{
				habitati.getSivs().remove(j);
			}
		}
	}
	
	
////////Broke pool with one user //////////////////////
public boolean affectAlonePool(Habitat habitat, Pool pool)
{
	boolean improved = false;
	
	ArrayList<Integer> candidatUsers = new ArrayList<Integer>();
	ArrayList<Pool> candidatPools = new ArrayList<Pool>();
	
	//get list of user candidates
	for(int i=0;i<CarPool.getUsers().getUsers().size();i++)
	{
		if(pool.getListOfUsers().get(0).getNumUser()!=CarPool.getUsers().getUsers().get(i).getNumUser())
		{
			if(Utilisateurs.getAuthorizedUsers()[pool.getListOfUsers().get(0).getNumUser()][CarPool.getUsers().getUsers().get(i).getNumUser()]==1)
			{
				candidatUsers.add(CarPool.getUsers().getUsers().get(i).getNumUser());
			}
		}	
	}
	
	//get list of potential pools
	for(int i=0;i<candidatUsers.size();i++)
	{
		boolean found=false;
		for(int j=0;j<habitat.getSivs().size() && !found;j++)
		{
			if(habitat.getSivs().get(j) != pool)
			{
				for(int k=0;k<habitat.getSivs().get(j).getListOfUsers().size() && !found;k++)
				{
					if(habitat.getSivs().get(j).getListOfUsers().get(k).getNumUser()==candidatUsers.get(i))
					{
						found=true;
						candidatPools.add(habitat.getSivs().get(j));
					}
				}
			}
		}
	}
	
	//get nearest user pool
	
	//by priority do by order these operations
	
	//merge
	for(int i=0;i<candidatPools.size() && !improved;i++)
	{
		if(candidatPools.get(i).getRestCarCap() > pool.getListOfUsers().size() && 
		   pool.getRestCarCap() > candidatPools.get(i).getListOfUsers().size())
		{
			candidatPools.get(i).addUser(pool.getListOfUsers().get(0));
			if(candidatPools.get(i).buildRoutes())
			{
				improved = true;
				habitat.removePool(pool);
			}
			else
			{
				//candidatPools.get(i).getListOfUsers().remove(candidatPools.get(i).getListOfUsers().size()-1);
				candidatPools.get(i).removeUser(pool.getListOfUsers().get(0));
				candidatPools.get(i).buildRoutes();
			}
		}	
	}
	
	
	//move from the potential 
	for(int i=0;i<candidatPools.size() && !improved;i++)
	{
		if(candidatPools.get(i).getListOfUsers().size() >= 2)
		{
			for(int j=0;j<candidatPools.get(i).getListOfUsers().size() && !improved;j++)
			{
				double bestCost = pool.getAvgCost() + candidatPools.get(i).getAvgCost(); 
				pool.getListOfUsers().add(candidatPools.get(i).getListOfUsers().get(j));
				if(pool.buildRoutes())
				{
					if(pool.getAvgCost() < bestCost)
					{	
						improved = true;
						candidatPools.get(i).getListOfUsers().remove(j);
						candidatPools.get(i).buildRoutes();
					}
					else
					{
						pool.removeUser(candidatPools.get(i).getListOfUsers().get(j));
						pool.buildRoutes();
					}
				}
				else
				{
					pool.removeUser(candidatPools.get(i).getListOfUsers().get(j));
					pool.buildRoutes();
				}
			}
		}	
	}
	//swap
	
	return improved;
}

public boolean affectAlonePool_2(Habitat habitat, Pool pool)
{
	boolean improved = false;
	
	ArrayList<Integer> candidatUsers = new ArrayList<Integer>();
	ArrayList<Pool> candidatPools = new ArrayList<Pool>();
	
	Pool identicalPool = new Pool();
	//
	boolean toImprove = true;
	for(int i=0;i<habitat.getSivs().size() && toImprove;i++)
	{
		if(habitat.getSivs().get(i).isUserInPool(pool.getListOfUsers().get(0).getNumUser()))
		{
			if(habitat.getSivs().get(i).getListOfUsers().size()>1)
			{
				toImprove=false;
			}
			else
			{
				identicalPool = habitat.getSivs().get(i); 
			}
		}
	}
	
	if(!toImprove)
	{
		return improved;
	}
	
	//get list of user candidates
	for(int i=0;i<CarPool.getUsers().getUsers().size();i++)
	{
		if(identicalPool.getListOfUsers().get(0).getNumUser()!=CarPool.getUsers().getUsers().get(i).getNumUser())
		{
			if(Utilisateurs.getAuthorizedUsers()[identicalPool.getListOfUsers().get(0).getNumUser()][CarPool.getUsers().getUsers().get(i).getNumUser()]==1)
			{
				candidatUsers.add(CarPool.getUsers().getUsers().get(i).getNumUser());
			}
		}	
	}
	
	//get list of potential pools
	for(int i=0;i<candidatUsers.size();i++)
	{
		boolean found=false;
		for(int j=0;j<habitat.getSivs().size() && !found;j++)
		{
			if(!habitat.getSivs().get(j).isPoolIdentical(identicalPool))
			{
				for(int k=0;k<habitat.getSivs().get(j).getListOfUsers().size() && !found;k++)
				{
					if(habitat.getSivs().get(j).getListOfUsers().get(k).getNumUser()==candidatUsers.get(i))
					{
						found=true;
						candidatPools.add(habitat.getSivs().get(j));
					}
				}
			}
		}
	}
	
	//get nearest user pool
	
	//by priority do by order these operations
	
	if(candidatPools.size()==0)
	{
		return improved;
	}
	
	//merge randomly
	int iMerge = (new Random()).nextInt(candidatPools.size());
	if(candidatPools.get(iMerge).getRestCarCap() > identicalPool.getListOfUsers().size() 
		&& identicalPool.getRestCarCap() > candidatPools.get(iMerge).getListOfUsers().size())
	{
		candidatPools.get(iMerge).addUser(identicalPool.getListOfUsers().get(0));
		if(candidatPools.get(iMerge).buildRoutes())
		{
			improved = true;
			habitat.removePool(identicalPool);
		}
		else
		{
			//candidatPools.get(i).getListOfUsers().remove(candidatPools.get(i).getListOfUsers().size()-1);
			candidatPools.get(iMerge).removeUser(identicalPool.getListOfUsers().get(0));
			candidatPools.get(iMerge).buildRoutes();
		}
	}	
	
	
	
	//move from the potential
	if(!improved)
	{
		int i = (new Random()).nextInt(candidatPools.size());
		//Pool clonedPool;			
		for(int j=candidatPools.get(i).getListOfUsers().size() - 1; j>=0 && !improved;j--)
		{
			double bestCost = identicalPool.getAvgCost() + candidatPools.get(i).getAvgCost();
			identicalPool.getListOfUsers().add(candidatPools.get(i).getListOfUsers().get(j));
			if(identicalPool.buildRoutes())
			{
				//if(pool.getAvgCost() < bestCost)
				//{	
				//improved = true;
				candidatPools.get(i).getListOfUsers().remove(j);
				candidatPools.get(i).buildRoutes();
				if((identicalPool.getAvgCost() + candidatPools.get(i).getAvgCost()) < bestCost)
				{
					improved=true;
				}
				else
				{
					candidatPools.get(i).getListOfUsers().add(j, identicalPool.getListOfUsers().get(identicalPool.getListOfUsers().size() - 1));
					identicalPool.removeUser(candidatPools.get(i).getListOfUsers().get(j));
					identicalPool.buildRoutes();
					candidatPools.get(i).buildRoutes();
				}
				//}
				//else
				//{
				//	pool.removeUser(candidatPools.get(i).getListOfUsers().get(j));
				//	pool.buildRoutes();
				//}
			}
			else
			{
				identicalPool.removeUser(candidatPools.get(i).getListOfUsers().get(j));
				identicalPool.buildRoutes();
			}
		}
		
	}
	
	//swap
	
	return improved;
}

}
