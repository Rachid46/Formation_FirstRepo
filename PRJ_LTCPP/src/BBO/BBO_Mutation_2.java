package BBO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import Model.Pool;
import Model.PoolUtilities;
import Model.User;
//import Model.UserCandidat;
import Model.Users;
import Model.Utilisateurs;

public class BBO_Mutation_2 {
	private Habitats habitats;
	private BBO_Parameters bboParameters;
	
	public BBO_Mutation_2(Habitats habitats, BBO_Parameters bboParameters)
	{
		this.habitats = habitats;
		this.bboParameters = bboParameters;
	}
	
	///////////////////////////////////////////////////////
	
	public void getMutatedHabitat(int iter)
	{
		//Apply a serie of operations
		double mutationProbability = new BBO_Parameters().getMutationProbability();
		Random rand = new Random(0);//parameters.getRandomSeed());
		for(int i = 0 ; i < bboParameters.getPopulationSize() ; i++)
		{
			//double randNum = rand.nextDouble();
			int indProb;
			if(i<bboParameters.getSpeciesProbabilties().length)
			{
				indProb = i;  
			}
			else
			{
				indProb = i - bboParameters.getSpeciesProbabilties().length;
			}
			
			System.out.println("length of probspec : " + bboParameters.getSpeciesProbabilties().length);
			mutationProbability = 0.1 * mutationProbability * (1.0 - bboParameters.getSpeciesProbabilties()[indProb]/bboParameters.getSpeciesProbabilties()[bboParameters.getSpeciesProbabilties().length - 1]);
			int k = Math.max((new Random()).nextInt(5),3);
			//int k = 5;
			//for(int j=0;j<habitats.getHabitats().get(i).getSivs().size();j++)
			for(int j=0;j<k;j++)
			{	
				//if(rand.nextDouble() < mutationProbability)
				if(rand.nextDouble() < mutationProbability)
				{
					Random rand2 = new Random();
					int randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
					
					//randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
					//if(habitats.getHabitats().get(i).getSivs().get(randomNum).getListOfUsers().size() >= 3 && iter/200 >= 0.95)
					//if(iter/400 >= 0.95)
					//randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
					
					if(habitats.getHabitats().get(i).getSivs().get(randomNum).getListOfUsers().size() >= 3)
					{
							move3(habitats.getHabitats().get(i),habitats.getHabitats().get(i).getSivs().get(randomNum));
					}
										
					
					//Collections.sort(habitats.getHabitats().get(i).getSivs(), Pool.ComparatorCost);
					//Collections.reverseOrder();
					randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
					//randomNum = rand2.nextInt(10);
					while(habitats.getHabitats().get(i).getSivs().get(randomNum).getListOfUsers().size()==1)
					{
						randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
						//randomNum = rand2.nextInt(10);
					}
					
										
					if(habitats.getHabitats().get(i).getSivs().get(randomNum).getListOfUsers().size()>=2)
					{
						//swap2(habitats.getHabitats().get(i),habitats.getHabitats().get(i).getSivs().get(randomNum));
						swap3(habitats.getHabitats().get(i),habitats.getHabitats().get(i).getSivs().get(randomNum));
					}
					
					randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
					int loops=0;
					while(habitats.getHabitats().get(i).getSivs().get(randomNum).getRestCarCap() == 0 && loops <5)
					{
						randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
						loops++;
					}
					Collections.sort(habitats.getHabitats().get(i).getSivs(), Pool.ComparatorCost);
					Collections.reverseOrder();
					//randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
					randomNum = rand2.nextInt(5);
					loops = 0;
					while(habitats.getHabitats().get(i).getSivs().get(randomNum).getRestCarCap() == 0 && loops <5)
					{
						//randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
						randomNum = rand2.nextInt(5);
						loops++;
					}
					//if(habitats.getHabitats().get(i).getSivs().get(randomNum).getListOfUsers().size() < 4)
					if(habitats.getHabitats().get(i).getSivs().get(randomNum).getRestCarCap() > 0)
					{
						//merge2(habitats.getHabitats().get(i),habitats.getHabitats().get(i).getSivs().get(randomNum));
						moveAll(habitats.getHabitats().get(i),habitats.getHabitats().get(i).getSivs().get(randomNum));
					}
					//else if(habitats.getHabitats().get(i).getSivs().get(randomNum).getListOfUsers().size() >= 3 && (new Random()).nextDouble()<0.10)
					
					randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
					
					if(habitats.getHabitats().get(i).getSivs().get(randomNum).getRestCarCap() == 0 
					   && (new Random()).nextDouble()<0.10)
					{
						
						if((new Random()).nextDouble()<0.99)
						{
							divide(habitats.getHabitats().get(i),habitats.getHabitats().get(i).getSivs().get(randomNum));
						}
						else
						{
							divideTo3(habitats.getHabitats().get(i),habitats.getHabitats().get(i).getSivs().get(randomNum));
						}
						
					}
					
					//randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
					//if(habitats.getHabitats().get(i).getSivs().get(randomNum).getListOfUsers().size() >= 1 && 
					//   habitats.getHabitats().get(i).getSivs().get(randomNum).getRestCarCap() > 0)
					//{
					//	if((new Random()).nextDouble()<0.10)
					//	{
					//		moveAll(habitats.getHabitats().get(i),habitats.getHabitats().get(i).getSivs().get(randomNum));
					//	}
					//}
					
					//swapWorst(habitats.getHabitats().get(i));
					//divideWorst(habitats.getHabitats().get(i));
					//randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
					
								
					
					
					//habitats.getHabitat()[i].getSIVs()[j] = parameters.getMinDomain() +
					//												(parameters.getMaxDomain() - parameters.getMinDomain()) *
					//												rand.nextDouble();
				}
			}
			
		}
		//return habitats;
	}
	
	private void divideTo3(Habitat habitat, Pool pool) {
		// TODO Auto-generated method stub
		//Random rand = new Random();
		boolean found=false;
		
		// from two pools of size 4 construct 3 pools with 3,3 and 2 users
		
		//pool2.addUser(pool.getListOfUsers().get(0));
		//pool.removeUser(pool.getListOfUsers().get(0));
		PoolUtilities.setPool(pool);
		Collections.sort(habitat.getSivs(), PoolUtilities.ComparatorGC);
		for(int i=0;i<habitat.getSivs().size() && !found ;i++)
		{
			if((habitat.getSivs().get(i).getListOfUsers().size()>3) && habitat.getSivs().get(i) != pool)
			{
				for(int j=0;j<habitat.getSivs().get(i).getListOfUsers().size() && !found;j++)
				{
					//int randomNum = rand.nextInt(habitat.getSivs().get(i).getListOfUsers().size());
					for(int k=0;k<pool.getListOfUsers().size() && !found;k++)
					{
						int numU1,numU2;
						numU1 = habitat.getSivs().get(i).getListOfUsers().get(j).getNumUser();
						numU2 = pool.getListOfUsers().get(k).getNumUser();
						if(Utilisateurs.getAuthorizedUsers()[numU1][numU2]==1)
						{
							Pool pool2 = new Pool();
							pool2.addUser(habitat.getSivs().get(i).getListOfUsers().get(j));
							pool2.addUser(pool.getListOfUsers().get(k));
							if(pool2.buildRoutes())
							{
								pool.removeUser(pool.getListOfUsers().get(k));
								habitat.getSivs().get(i).removeUser(habitat.getSivs().get(i).getListOfUsers().get(j));
								habitat.getSivs().get(i).buildRoutes();
								pool.buildRoutes();
								habitat.addPool(pool2);
								found=true;
							}
						}
					}
				}
				
				//pool2.addUser(user);
			}
		}
		
	}

	///////////////////////////////////////////////////////
	private void divide(Habitat habitat,Pool pool) {
		// TODO Auto-generated method stub
		Pool pool1 = (Pool) pool.clone();
		Pool pool2 = new Pool();
		Random rand2 = new Random();
		
		
		pool2.addUser(pool1.getListOfUsers().get(0));
		pool1.removeUser(pool1.getListOfUsers().get(0));
		int randomNum = rand2.nextInt(pool1.getListOfUsers().size());
		
		pool2.addUser(pool1.getListOfUsers().get(randomNum));
		pool1.removeUser(pool1.getListOfUsers().get(randomNum));
		
		if(pool1.buildRoutes() && pool2.buildRoutes())
		{
			habitat.removePool(pool);
			habitat.addPool(pool1);
			habitat.addPool(pool2);
		}
		
		
	}


	// find a valid merge
	public void merge(Habitat habitat,Pool pool)
	{
		//double poolAvgCost = pool.getAvgCost();
		ArrayList<Pool> poolsList = new ArrayList<Pool>();
		boolean firstMerge = true;
		double bestCost = 1000;
		//Select randomly a number of pools
		//int nbPools = 5;
		
		//Get the potential list of the pool to merge 
		for(int i=0;i<habitat.getSivs().size();i++)
		{
			if((habitat.getSivs().get(i).getListOfUsers().size()<= pool.getRestCarCap()) && 
				(pool.getListOfUsers().size() <= habitat.getSivs().get(i).getRestCarCap()) && habitat.getSivs().get(i) != pool)
			{
				poolsList.add(habitat.getSivs().get(i));
			}
		}
		
		//
		boolean improvedMerge = false;
		int selectedMergedPool =-1;
		Pool mergedPool = new Pool();
		for(int i=0;i<poolsList.size() && !improvedMerge;i++)
		{
			Pool clonedPool = (Pool) poolsList.get(i).clone();
			double clonedPoolCost = clonedPool.getAvgCost();
			for(int j=0;j<pool.getListOfUsers().size();j++)
			{
				clonedPool.addUser(pool.getListOfUsers().get(j));
			}
			
			if(clonedPool.buildRoutes())
			{
				if(firstMerge)
				{
					bestCost = pool.getAvgCost() + clonedPoolCost;
					firstMerge = false;
				}
				if(bestCost > clonedPool.getAvgCost())
				{
					//improvedMerge = true;
					selectedMergedPool=i;
					bestCost = clonedPool.getAvgCost();
					mergedPool = clonedPool;
				}
				
				
			}
		}
		
		// A valid merge was found
		if(selectedMergedPool>=0)
		{
			//remove the two pools
			habitat.removePool(pool);
			habitat.removePool(poolsList.get(selectedMergedPool));
			//add the new merged pool
			habitat.addPool(mergedPool);
		}
		
	}
	
	/////////////// SWAP 3
	private void swap3(Habitat habitat,Pool pool) {
		// TODO Auto-generated method stub
		boolean improvedSwapedPool = false;
		Pool pool1 = null;
		Pool pool2 = null; 
		int poolToRemove=-1;
		
		ArrayList<Integer> tabuSivs = new ArrayList<Integer>();
		tabuSivs.add(habitat.getSivs().indexOf(pool));
		
		//ArrayList<Pool>
		//PoolUtilities.setPool(pool);
		//Collections.sort(habitat.getSivs(), PoolUtilities.ComparatorGC);
		//int posPool = habitat.getSivs().indexOf(pool);
		
		for(int i=0;i<habitat.getSivs().size() - 1 && !improvedSwapedPool;i++)
		//for(int i = Math.max(0, posPool -2);i<Math.min(posPool+2, habitat.getSivs().size()) && !improvedSwapedPool;i++)
		{
			int randNumPool = (new Random()).nextInt(habitat.getSivs().size());
			//Math.max(0, posPool -2);i<Math.min(posPool+2, habitat.getSivs().size())
			//nb = borneInf+random.nextInt(borneSup-borneInf);
			//int randNumPool = Math.max(0, posPool -2) + (new Random()).nextInt(Math.min(posPool+2, habitat.getSivs().size()) - Math.max(0, posPool -2));
			int loops = 0;
			while((habitat.getSivs().get(randNumPool) == pool || tabuSivs.contains(randNumPool) /*|| habitat.getSivs().get(randNumPool).getListOfUsers().size() * pool.getListOfUsers().size()==2*/) && loops<10)
			{
				randNumPool = (new Random()).nextInt(habitat.getSivs().size());
				loops++;
			}
			if(!tabuSivs.contains(randNumPool))
			{
				tabuSivs.add(randNumPool);
			}
			else
			{
				continue;
			}
			
			if(habitat.getSivs().get(randNumPool) != pool)
			{
				Pool clonedPool = (Pool) pool.clone();
				Pool swapedPool = (Pool) habitat.getSivs().get(randNumPool).clone();
				for(int j=0;j<swapedPool.getListOfUsers().size() && !improvedSwapedPool;j++)
				{
					
					for(int k=0;k<clonedPool.getListOfUsers().size() && !improvedSwapedPool;k++)
					{
						if((clonedPool.areAllUsersAuthorizedSwap(swapedPool.getListOfUsers().get(j).getNumUser(),clonedPool.getListOfUsers().get(k).getNumUser())
						&& swapedPool.areAllUsersAuthorizedSwap(clonedPool.getListOfUsers().get(k).getNumUser(),swapedPool.getListOfUsers().get(j).getNumUser()))
						&& true
						//&& (clonedPool.isUserAuthorized(swapedPool.getListOfUsers().get(j))
						//	&& swapedPool.isUserAuthorized(clonedPool.getListOfUsers().get(k)))
						)
						{
							User swapedUser = (User) clonedPool.getListOfUsers().get(k).clone();
							clonedPool.getListOfUsers().set(k, swapedPool.getListOfUsers().get(j));
							swapedPool.getListOfUsers().set(j, swapedUser);
							if(clonedPool.buildRoutes() && swapedPool.buildRoutes())
							{
								improvedSwapedPool = true;
								poolToRemove = randNumPool;
								pool1 = clonedPool;
								pool2 = swapedPool;
									
							}
							else
							{
								swapedUser = (User) clonedPool.getListOfUsers().get(k).clone();
								clonedPool.getListOfUsers().set(k, swapedPool.getListOfUsers().get(j));
								swapedPool.getListOfUsers().set(j, swapedUser);
								clonedPool.buildRoutes();
								swapedPool.buildRoutes();
							}
						}
					}
				}
			}
		}
		
		if(poolToRemove >=0)
		{
			habitat.removePool(habitat.getSivs().get(poolToRemove));
			habitat.removePool(pool);
			
			habitat.addPool(pool1);
			habitat.addPool(pool2);
		}
	}
	///////////////////////////////////////
	
	
	public void merge2(Habitat habitat,Pool pool)
	{
		//double poolAvgCost = pool.getAvgCost();
		//ArrayList<Pool> poolsList = new ArrayList<Pool>();
		
		//Select randomly a number of pools
		//int nbPools = 5;
		//if(pool.getListOfUsers().size()==1)
		
		//Get the potential list of the pool to merge 
		//PoolUtilities.setPool(pool);
		//Collections.sort(habitat.getSivs(), PoolUtilities.ComparatorGC);
		int posPool = habitat.getSivs().indexOf(pool);
		
		//for(int i=0;i<habitat.getSivs().size() - 1 && !improvedSwapedPool;i++)
		
		//for(int i=0;i<habitat.getSivs().size();i++)
		//{
		//	if((habitat.getSivs().get(i).getListOfUsers().size()<= pool.getRestCarCap()) && 
		//		(pool.getListOfUsers().size() <= habitat.getSivs().get(i).getRestCarCap()) && habitat.getSivs().get(i) != pool)
		//	{
		//		poolsList.add(habitat.getSivs().get(i));
		//	}
		//}
		
		//
		boolean improvedMerge = false;
		int selectedMergedPool =-1;
		Pool mergedPool = new Pool();
		//for(int i=0;i<poolsList.size() && !improvedMerge;i++)
		//for(int i = Math.max(0, posPool -10);i<Math.min(posPool+10, habitat.getSivs().size()) && !improvedMerge;i++)
		for(int i =0;i<habitat.getSivs().size() && !improvedMerge;i++)
		{
			// Add a condition to reduce the compute time
			if(habitat.getSivs().get(i) != pool)
			{	
				if((habitat.getSivs().get(i).getListOfUsers().size()<= pool.getRestCarCap()) && 
					(pool.getListOfUsers().size() <= habitat.getSivs().get(i).getRestCarCap()) && habitat.getSivs().get(i) != pool)
				{
					boolean validToMerge = true;
					for(int k=0;k<pool.getListOfUsers().size() && validToMerge;k++)
					{
						validToMerge = validToMerge && habitat.getSivs().get(i).areAllUsersAuthorized(pool.getListOfUsers().get(k).getNumUser());
					}
					
					if(validToMerge)
					{
						//Pool clonedPool = (Pool) poolsList.get(i).clone();
						Pool clonedPool = (Pool)  habitat.getSivs().get(i).clone();
						for(int j=0;j<pool.getListOfUsers().size();j++)
						{
							clonedPool.addUser(pool.getListOfUsers().get(j));
						}
						
						if(clonedPool.buildRoutes())
						{
							improvedMerge = true;
							selectedMergedPool=i;
							mergedPool = clonedPool;
							
						}
					}
				}
			}
		}
		
		// A valid merge was found
		if(selectedMergedPool>=0)
		{
			//remove the two pools
			if(posPool > selectedMergedPool)
			{ 
				habitat.removePool(pool);
				habitat.removePool(habitat.getSivs().get(selectedMergedPool));
			}
			else
			{
				habitat.removePool(habitat.getSivs().get(selectedMergedPool));
				habitat.removePool(pool);
			}
			
			//add the new merged pool
			habitat.addPool(mergedPool);
		}
		
	}
	
	
	////////////////// MOVE 3 ///////////////////////
	private void move3(Habitat habitat,Pool pool)
	{
		// TODO Auto-generated method stub
		boolean moved = false;
		Pool clonedPool=null;
		ArrayList<Integer> tabuSivs = new ArrayList<Integer>();
		tabuSivs.add(-1);
		
		//Sort the users of the pool compared to gravity center
		//Collections.sort(pool.getListOfUsers(), User.ComparatorDistG);
		
		for(int i =0;i<habitat.getSivs().size() - 1 && !moved;i++)
		{
			int k = (new Random()).nextInt(habitat.getSivs().size());
			int loops = 0;
			while((habitat.getSivs().get(k) == pool || habitat.getSivs().get(k).getRestCarCap()==0 || tabuSivs.contains(k)) && loops<10 )
			{
				k = (new Random()).nextInt(habitat.getSivs().size());
				loops++;
			}
			
			if(!tabuSivs.contains(k))
			{
				tabuSivs.add(k);
			}
			else
			{
				continue;
			}
			
			//if(habitat.getSivs().get(i) != pool && habitat.getSivs().get(i).getListOfUsers().size()==1) 
			if(habitat.getSivs().get(k) != pool && habitat.getSivs().get(k).getRestCarCap()>0)
			{
				clonedPool = (Pool) pool.clone();
				//for(int j=0;j<pool.getListOfUsers().size() && !moved;j++)
				for(int j=pool.getListOfUsers().size() - 1; j>=0 && !moved;j--)
				{
					if(habitat.getSivs().get(k).areAllUsersAuthorized(clonedPool.getListOfUsers().get(j).getNumUser()))
					{
						habitat.getSivs().get(k).addUser(clonedPool.getListOfUsers().get(j));
						if(habitat.getSivs().get(k).buildRoutes())
						{
							moved = true;
							clonedPool.getListOfUsers().remove(j);
							clonedPool.buildRoutes();
						}
						else
						{
							habitat.getSivs().get(k).removeUser(clonedPool.getListOfUsers().get(j));
							habitat.getSivs().get(k).buildRoutes();
						}
					}
				}
			}
		}
		
		if(moved)
		{
			habitat.removePool(pool);
			habitat.addPool(clonedPool);
		}
	}
	
	/////////////// SWAP Worst /////////////////////////////
	private void swapWorst(Habitat habitat)
	{
		//Use Swap3
		Collections.sort(habitat.getSivs(), Pool.ComparatorCost);
		//Collections.reverseOrder();
		
		for(int i=0;i<1;i++)
		{
			swap3(habitat, habitat.getSivs().get(i));
		}
	}
	
	private void divideBest(Habitat habitat,Pool pool) {
		// TODO Auto-generated method stub
		Pool pool2 = new Pool();
		//Random rand2 = new Random();
		
		
		pool2.addUser(pool.getListOfUsers().get(0));
		pool.removeUser(pool.getListOfUsers().get(0));
		//int randomNum = rand2.nextInt(pool.getListOfUsers().size());
		User user = Users.getNearestInList(pool2.getListOfUsers().get(0), pool.getListOfUsers()); 
		pool2.addUser(user);
		//pool.removeUser(pool.getListOfUsers().get(randomNum));
		pool.removeUser(user);
		
		pool.buildRoutes();
		pool2.buildRoutes();
		
		habitat.addPool(pool2);
		
	}
	
	private void divideWorst(Habitat habitat)
	{
		//Use Swap3
		Collections.sort(habitat.getSivs(), Pool.ComparatorCost);
		Collections.reverseOrder();
		
		boolean found = false;
		
		for(int i=0;i<habitat.getSivs().size() && !found;i++)
		{
			if(habitat.getSivs().get(i).getRestCarCap()==0)
			{
				divideBest(habitat,habitat.getSivs().get(i));
				found=true;
			}
		}
	}
	
	/////////////////////////////////////////////////
	
	private void moveAll(Habitat habitat, Pool pool) 
	{
		
		//Random rand2 = new Random();
		Pool poolToMove = (Pool) pool.clone();
		ArrayList<Pool> poolsList = new ArrayList<Pool>();
		ArrayList<Integer> poolsReceived = new ArrayList<Integer>();
		//Sort the pools in habitat
		//PoolUtilities.setPool(pool);
		//Collections.sort(habitat.getSivs(), PoolUtilities.ComparatorGC);
		
		boolean addedUser = true;
		for(int i=poolToMove.getListOfUsers().size() -1;i>=0 && addedUser;i--)
		{
				addedUser = false;
				boolean insertedUser=false;
				for(int j=0;j<habitat.getSivs().size() && !insertedUser;j++)
				{
					if(habitat.getSivs().get(j)!=pool && habitat.getSivs().get(j).getRestCarCap()>0)
					{
						//int randomNum = rand2.nextInt(habitat.getSivs().size());
						
						//int j=0;
						
						//ArrayList<User> usersList = new ArrayList<User>();
						if(!poolsReceived.contains(j))
						{
							if(habitat.getSivs().get(j).areAllUsersAuthorized(poolToMove.getListOfUsers().get(i).getNumUser())
							  && habitat.getSivs().get(j).isUserAuthorized(poolToMove.getListOfUsers().get(i))
							  )
							{
								
								Pool tempPool = (Pool) habitat.getSivs().get(j).clone(); 
										
								//habitat.getSivs().get(randomNum).getListOfUsers().add(pool.getListOfUsers().get(i));
								tempPool.getListOfUsers().add(poolToMove.getListOfUsers().get(i));
								if(!tempPool.buildRoutes())
								{
									tempPool.getListOfUsers().remove(poolToMove.getListOfUsers().get(i));
									//poolsList.get(poolsList.size()-1).buildRoutes();
								}
								else
								{
									addedUser = true;
									insertedUser=true;
									//usersList.add(poolToExplose.getListOfUsers().get(i));
									poolToMove.getListOfUsers().remove(i);
									poolsReceived.add(j);
									poolsList.add(tempPool);
								}
							 }
						}
						else
						{
							if(poolsList.get(poolsReceived.indexOf(j)).getRestCarCap()>0)
							{
								if(poolsList.get(poolsReceived.indexOf(j)).areAllUsersAuthorized(poolToMove.getListOfUsers().get(i).getNumUser())
										  && poolsList.get(poolsReceived.indexOf(j)).isUserAuthorized(poolToMove.getListOfUsers().get(i))
										  )
								{
									poolsList.get(poolsReceived.indexOf(j)).getListOfUsers().add(poolToMove.getListOfUsers().get(i));
									if(!poolsList.get(poolsReceived.indexOf(j)).buildRoutes())
									{
										poolsList.get(poolsReceived.indexOf(j)).getListOfUsers().remove(poolToMove.getListOfUsers().get(i));
										poolsList.get(poolsReceived.indexOf(j)).buildRoutes();
										//poolsList.get(poolsList.size()-1).buildRoutes();
									}
									else
									{
										addedUser = true;
										insertedUser=true;
										//usersList.add(poolToExplose.getListOfUsers().get(i));
										poolToMove.getListOfUsers().remove(i);
										//poolsReceived.add(j);
										//poolsList.add(tempPool);
									}
								}
							}
						}
							
					}
				}
			
		
		}
		
		if(poolToMove.getListOfUsers().size()==0)
		{
			for(int i=poolsReceived.size()-1;i>=0;i--)
			{
				habitat.getSivs().set(poolsReceived.get(i), poolsList.get(i));
			}
			habitat.getSivs().remove(pool);
		}
		
	}
}
