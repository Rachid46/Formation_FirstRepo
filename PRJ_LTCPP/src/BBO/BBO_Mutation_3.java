package BBO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import Model.CarPool;
import Model.Pool;
import Model.PoolUtilities;
import Model.User;
import Model.Utilisateurs;

public class BBO_Mutation_3 {
	private Habitats habitats;
	private BBO_Parameters bboParameters;
	
	public BBO_Mutation_3(Habitats habitats, BBO_Parameters bboParameters)
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
			//int k = Math.max((new Random()).nextInt(5),3);
			int k = Math.max((new Random()).nextInt(5),3);
			//int k = 5;
			for(int j=0;j<habitats.getHabitats().get(i).getSivs().size();j++)
			//for(int j=0;j<k;j++)
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
							move(habitats.getHabitats().get(i),habitats.getHabitats().get(i).getSivs().get(randomNum));
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
						swapBest(habitats.getHabitats().get(i),habitats.getHabitats().get(i).getSivs().get(randomNum));
					}
					
					randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
					
					//if(habitats.getHabitats().get(i).getSivs().get(randomNum).getRestCarCap() == 0) 
					   //&& (new Random()).nextDouble()<0.10)
					if(habitats.getHabitats().get(i).getSivs().get(randomNum).getListOfUsers().size() > 2)
					{
						//ArrayList<Pool> pools = new ArrayList<Pool>();
						if((new Random()).nextDouble()<0.90)
						{
							divide(habitats.getHabitats().get(i),habitats.getHabitats().get(i).getSivs().get(randomNum));
						}
						else
						{
							divideTo3(habitats.getHabitats().get(i),habitats.getHabitats().get(i).getSivs().get(randomNum));
						}
						//divideWorst(habitats.getHabitats().get(i));
					}
					
					randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
					int loops=0;
					while(habitats.getHabitats().get(i).getSivs().get(randomNum).getRestCarCap() == 0 && loops <5)
					{
						randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
						loops++;
					}
					//Collections.sort(habitats.getHabitats().get(i).getSivs(), Pool.ComparatorCost);
					//Collections.reverseOrder();
					//randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
					//randomNum = rand2.nextInt(5);
					//while(habitats.getHabitats().get(i).getSivs().get(randomNum).getListOfUsers().size()==1)
					//{
					//	randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
						//randomNum = rand2.nextInt(5);
					//}
					//if(habitats.getHabitats().get(i).getSivs().get(randomNum).getListOfUsers().size() < 4)
					if(habitats.getHabitats().get(i).getSivs().get(randomNum).getRestCarCap() > 0)
					{
						merge(habitats.getHabitats().get(i),habitats.getHabitats().get(i).getSivs().get(randomNum));
						//moveAll(habitats.getHabitats().get(i),habitats.getHabitats().get(i).getSivs().get(randomNum));
					}
					//else if(habitats.getHabitats().get(i).getSivs().get(randomNum).getListOfUsers().size() >= 3 && (new Random()).nextDouble()<0.10)
					
					
					
					
					
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
					
					// broke pools with one user
					Collections.sort(habitats.getHabitats().get(i).getSivs(), Pool.ComparatorSize);
					//Habitat habitat = habitats.getHabitats().get(i);
					int counter = 0;
					ArrayList<Pool> listPoolPen = new ArrayList<Pool>();
					while(habitats.getHabitats().get(i).getSivs().get(counter).getListOfUsers().size()==1)
					{
						if(Arrays.stream(Utilisateurs.getAuthorizedUsers()[habitats.getHabitats().get(i).getSivs().get(counter).getListOfUsers().get(0).getNumUser()]).sum()>1)
						{
							listPoolPen.add(habitats.getHabitats().get(i).getSivs().get(counter));
						}
						counter++;
					}
					
					if(listPoolPen.size()==0)
					{
						continue;
					}
					
					randomNum = rand2.nextInt(listPoolPen.size());
					
					affectAlonePool_2(habitats.getHabitats().get(i),listPoolPen.get(randomNum));			
					
					
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

	private boolean divide(Habitat habitat, Pool pool)
	{
		
		
		//ArrayList<Pool> tabooList = new ArrayList<Pool>();
		
		
		
		
			boolean improved=false;
			//Habitat habitat = habitats.getHabitats().get((new Random()).nextInt(habitats.getHabitats().size()));
			
			
			//Collections.sort(habitat.getSivs(), Pool.ComparatorCost);
			//Collections.reverseOrder();
			
			//for(int i=0;i<habitat.getSivs().size() && !improved;i++)
			//{
				double bestCost = pool.getAvgCost();//habitat.getSivs().get(i).getAvgCost();
				Pool pool1, pool2;
				//if(habitat.getSivs().get(i).getRestCarCap() == 0)
				//Collections.sort(habitat.getSivs(), Pool.ComparatorCost);
				//Collections.reverseOrder();
				if(pool.getRestCarCap() == 0)
				{
					pool1 = (Pool) pool.clone();//habitat.getSivs().get(i).clone();
					pool2 = new Pool();
					pool2.addUser(pool1.getListOfUsers().get(0));//habitat.getSivs().get(i).getListOfUsers().get(0));
					pool1.getListOfUsers().remove(0);//habitat.getSivs().get(i).getListOfUsers().get(0));
					if(pool1.getListOfUsers().size()>1)
					{
						pool2.addUser(Utilisateurs.getNearest(pool1.getListOfUsers(), pool2.getListOfUsers().get(0)));
					}
					
					pool1.buildRoutes();
					pool2.buildRoutes();
					
					if(bestCost > pool1.getAvgCost() + pool2.getAvgCost())
					{
						habitat.removePool(pool);//habitat.getSivs().get(i));
						habitat.addPool(pool1);
						habitat.addPool(pool2);
						improved = true;
					}
					
					
				}
				//Divide
				//Pool po
		
		return improved;
	}
	private boolean swapBest(Habitat habitat, Pool pool)
	//(Habitat habitat,Pool pool) {
	{
		
		
		//ArrayList<Integer> tabooHabitatList = new ArrayList<Integer>();
		
		
		
			boolean improvedSwapedPool = false;
			//int habitatIndex = (new Random()).nextInt(habitats.getHabitats().size());
			
			
			//for(int l=0; l<10 && !improvedSwapedPool;l++)
			//{
				
				
				
				//Sort according to pure pool cost
				//Collections.sort(habitat.getSivs(),Pool.ComparatorCost);
				//Collections.reverseOrder();
				ArrayList<Pool> tabooPoolList = new ArrayList<Pool>();
				
				//Pool pool = habitat.getSivs().get((new Random()).nextInt(habitat.getSivs().size()));
				
				//Pool pool = habitat.getSivs().get((new Random()).nextInt(10));
				//int loops = 0;
				//while((pool.getListOfUsers().size()<2 || tabooPoolList.contains(pool)) && loops<5)
				//while((tabooPoolList.contains(pool) || Arrays.stream(Utilisateurs.getAuthorizedUsers()[pool.getListOfUsers().get(0).getNumUser()]).sum()==1)
				//		&& loops<5
				//	  )
				//{
				//	pool = habitat.getSivs().get((new Random()).nextInt(habitat.getSivs().size()));
					//pool = habitat.getSivs().get((new Random()).nextInt(10));
				//	loops++;
				//}
				//if(pool.getListOfUsers().size()<2 || tabooPoolList.contains(pool))
				//if(tabooPoolList.contains(pool) || Arrays.stream(Utilisateurs.getAuthorizedUsers()[pool.getListOfUsers().get(0).getNumUser()]).sum()==1)
				//{
				//	return improvedSwapedPool;
				//}
				
									
				Pool pool1 = null;
				Pool pool2 = null; 
				int poolToRemove=-1;
				
				//ArrayList<Integer> tabuSivs = new ArrayList<Integer>();
				//tabuSivs.add(-1);
				
				//ArrayList<Pool>
				PoolUtilities.setPool(pool);
				Collections.sort(habitat.getSivs(), PoolUtilities.ComparatorGC);
				//int posPool = habitat.getSivs().indexOf(pool);
				
				
				for(int i=0;(i<habitat.getSivs().size() && !improvedSwapedPool);i++)
				//for(int i = Math.max(0, posPool -3);i<Math.min(posPool+3, habitat.getSivs().size()) && !improvedSwapedPool;i++)
				{
					//int randNumPool = (new Random()).nextInt(habitat.getSivs().size());
					//Math.max(0, posPool -2);i<Math.min(posPool+2, habitat.getSivs().size())
					//nb = borneInf+random.nextInt(borneSup-borneInf);
					//int randNumPool = Math.max(0, posPool -2) + (new Random()).nextInt(Math.min(posPool+2, habitat.getSivs().size()) - Math.max(0, posPool -2));
					//int loops = 0;
					//while((habitat.getSivs().get(randNumPool) == pool || tabuSivs.contains(randNumPool)) && loops<5)
					//{
						//randNumPool = (new Random()).nextInt(habitat.getSivs().size());
					//	randNumPool = Math.max(0, posPool -2) + (new Random()).nextInt(Math.min(posPool+2, habitat.getSivs().size()) - Math.max(0, posPool -2));
					//	loops++;
					//}
					//if(!tabuSivs.contains(randNumPool))
					//{
					//	tabuSivs.add(randNumPool);
					//}
					
					
					
					//if(habitat.getSivs().get(randNumPool) != pool)
					if(habitat.getSivs().get(i) != pool && (habitat.getSivs().get(i).getListOfUsers().size() + pool.getListOfUsers().size())>2)
					{
						double bestCost = pool.getAvgCost() + habitat.getSivs().get(i).getAvgCost();
						Pool clonedPool = (Pool) pool.clone();
						Pool swapedPool = (Pool) habitat.getSivs().get(i).clone();
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
										if(bestCost > (clonedPool.getAvgCost() + swapedPool.getAvgCost()) && 
											Math.abs(bestCost - (clonedPool.getAvgCost() + swapedPool.getAvgCost())) > 10.0
												)
										{
											poolToRemove = i;
											pool1 = clonedPool;
											pool2 = swapedPool;
											improvedSwapedPool = true;
											
										}
										else
										{
											swapedUser = (User) clonedPool.getListOfUsers().get(k).clone();
											clonedPool.getListOfUsers().set(k, swapedPool.getListOfUsers().get(j));
											swapedPool.getListOfUsers().set(j, swapedUser);
											clonedPool.buildRoutes();
											swapedPool.buildRoutes();
											
										}
										//improvedSwapedPool = true;
										//poolToRemove = i;
										//pool1 = clonedPool;
										//pool2 = swapedPool;
											
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
					//nbFails=0;
					habitat.addPool(pool1);
					habitat.addPool(pool2);
					tabooPoolList.remove(pool);
					
				}
				else
				{
					tabooPoolList.add(pool);
					
				}
		//	}
			
			return improvedSwapedPool;
		
	}
	
	public boolean merge(Habitat habitat, Pool pool)
	{
		//ArrayList<Pool> tabooList = new ArrayList<Pool>();
		//ArrayList<Integer> tabooHabitatList = new ArrayList<Integer>();
		//double bestCost = 0.0;
		boolean improvedMerge = false;
	
		
			//Sort according to pure pool cost
			//Collections.sort(habitat.getSivs(),Pool.ComparatorCost);
			//Collections.sort(habitat.getSivs(), Pool.ComparatorSize);
			//Collections.reverseOrder();
			//int loops = 0;
			//Pool pool = habitat.getSivs().get((new Random()).nextInt(habitat.getSivs().size()));
			//Pool pool = habitat.getSivs().get((new Random()).nextInt(10));
			//while((pool.getRestCarCap() == 0 || 
			//	  (pool.getListOfUsers().size()==1 && Arrays.stream(Utilisateurs.getAuthorizedUsers()[pool.getListOfUsers().get(0).getNumUser()]).sum()==1)
			//	  ) && loops<5)
			//{
				//pool = habitat.getSivs().get((new Random()).nextInt(habitat.getSivs().size()));
			//	pool = habitat.getSivs().get((new Random()).nextInt(10));
			//	loops++;
			//}
			
			if(pool.getRestCarCap() == 0 || 
			   (pool.getListOfUsers().size()==1 && Arrays.stream(Utilisateurs.getAuthorizedUsers()[pool.getListOfUsers().get(0).getNumUser()]).sum()==1)
			  )
			{
				return improvedMerge;
			}
			//double poolAvgCost = pool.getAvgCost();
			//ArrayList<Pool> poolsList = new ArrayList<Pool>();
				
			//Select randomly a number of pools
			//int nbPools = 5;
			//if(pool.getListOfUsers().size()==1)
				
			//Get the potential list of the pool to merge 
			PoolUtilities.setPool(pool);
			Collections.sort(habitat.getSivs(), PoolUtilities.ComparatorGC);
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
			
			int selectedMergedPool =-1;
			Pool mergedPool = new Pool();
			//for(int i=0;i<poolsList.size() && !improvedMerge;i++)
			//for(int i = Math.max(0, posPool -10);i<Math.min(posPool+10, habitat.getSivs().size()) && !improvedMerge;i++)
			for(int i =0;(i<habitat.getSivs().size() && !improvedMerge);i++)
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
									if((pool.getAvgCost() + habitat.getSivs().get(i).getAvgCost())> clonedPool.getAvgCost())
									{
										improvedMerge = true;
										selectedMergedPool=i;
										mergedPool = clonedPool;
									}
							
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
				
				return improvedMerge;
		
	}
	
	public boolean moveAll(Habitat habitat, Pool pool)
	{
		
			
			//Habitat habitat = habitats.getHabitats().get((new Random()).nextInt(habitats.getHabitats().size()));
			//Pool pool = habitat.getSivs().get((new Random()).nextInt(habitat.getSivs().size()));
			//int pools = 0;
			//while(pool.getRestCarCap() == 0 && pools<5)
			//{
			//	pool = habitat.getSivs().get((new Random()).nextInt(habitat.getSivs().size()));
			//	pools++;
			//}
			//if(pool.getRestCarCap() == 0)
			//{
			//	continue;
			//}
			PoolUtilities.setPool(pool);
			Collections.sort(habitat.getSivs(), PoolUtilities.ComparatorGC);
			// TODO Auto-generated method stub
			boolean improved = false;
			//Random rand2 = new Random();
			Pool poolToMove = (Pool) pool.clone();
			ArrayList<Pool> poolsList = new ArrayList<Pool>();
			ArrayList<Integer> poolsReceived = new ArrayList<Integer>();
			//Sort the pools in habitat
			//PoolUtilities.setPool(pool);
			//Collections.sort(habitat.getSivs(), PoolUtilities.ComparatorGC);
			boolean addedUser = true;
			double oldCost=0.0;
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
								double tmpCost = tempPool.getAvgCost();				
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
									
									oldCost += tmpCost;
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
			double newCost=0.0;	
			for(int i=0;i<poolsList.size();i++)
			{
				newCost += poolsList.get(i).getAvgCost();
			}
			if(newCost <(pool.getAvgCost() + oldCost))
			{
				for(int i=poolsReceived.size()-1;i>=0;i--)
				{
					habitat.getSivs().set(poolsReceived.get(i), poolsList.get(i));
				}
				habitat.getSivs().remove(pool);
				improved = true;
			}	
		}
				
	  return improved;
	}
	
	public boolean move(Habitat habitat, Pool pool)
	{
		
		//ArrayList<Pool> tabooList = new ArrayList<Pool>();
		double bestCost = 0.0;
		
		
		
				//Sort the users of the pool compared to gravity center
				//Collections.sort(pool.getListOfUsers(), User.ComparatorDistG);
		boolean improved = false;
		//int iter = 0;
		//Pool pool = habitat.getSivs().get((new Random()).nextInt(10));
		//Pool pool = habitat.getSivs().get((new Random()).nextInt(habitat.getSivs().size()));
		//	while(pool.getListOfUsers().size() < 2 && iter<5)
		//	{
		//		pool = habitat.getSivs().get((new Random()).nextInt(habitat.getSivs().size()));
				//pool = habitat.getSivs().get((new Random()).nextInt(10));
		//		iter++;
		//	}
					
		//	if(pool.getListOfUsers().size() < 2 )
		//	{
		//		return improved;
		//	}
					
					//PoolUtilities.setPool(pool);
					//Collections.sort(habitat.getSivs(), PoolUtilities.ComparatorGC);
					
					Pool clonedPool=null;
					ArrayList<Integer> tabuSivs = new ArrayList<Integer>();
					tabuSivs.add(-1);
					
					//int k = (new Random()).nextInt(habitat.getSivs().size());
					//for(int k=0;k<habitat.getSivs().size() && nbFails<maxFails;k++)
					for(int k=0;k<habitat.getSivs().size() && !improved;k++)
					{
					
					//int k = (new Random()).nextInt(10);
					//int loops = 0;
					//while((habitat.getSivs().get(k) == pool || habitat.getSivs().get(k).getRestCarCap()==0 || tabuSivs.contains(k)) && loops<10 )
					//{
						//k = (new Random()).nextInt(habitat.getSivs().size());
					//	k = (new Random()).nextInt(10);
					//	loops++;
					//}
					
					if ((habitat.getSivs().get(k) == pool || habitat.getSivs().get(k).getRestCarCap()==0 || tabuSivs.contains(k)))
					{
						continue;	
					}
						
					
					
					//if(habitat.getSivs().get(i) != pool && habitat.getSivs().get(i).getListOfUsers().size()==1) 
					if(habitat.getSivs().get(k) != pool && habitat.getSivs().get(k).getRestCarCap()>0)
					{
						clonedPool = (Pool) pool.clone();
						//for(int j=0;j<pool.getListOfUsers().size() && !moved;j++)
						for(int j=pool.getListOfUsers().size() - 1; j>=0 && !improved;j--)
						{
							if(habitat.getSivs().get(k).areAllUsersAuthorized(clonedPool.getListOfUsers().get(j).getNumUser()))
							{
								bestCost = habitat.getSivs().get(k).getAvgCost() + pool.getAvgCost(); 
								habitat.getSivs().get(k).addUser(clonedPool.getListOfUsers().get(j));
								if(habitat.getSivs().get(k).buildRoutes())
								{
									if(habitat.getSivs().get(k).getAvgCost()<bestCost)
									{
										improved = true;
										clonedPool.getListOfUsers().remove(j);
										clonedPool.buildRoutes();
									}
									else
									{
										habitat.getSivs().get(k).removeUser(clonedPool.getListOfUsers().get(j));
										habitat.getSivs().get(k).buildRoutes();
									}
									
								}
								else
								{
									habitat.getSivs().get(k).removeUser(clonedPool.getListOfUsers().get(j));
									habitat.getSivs().get(k).buildRoutes();
									
								}
							}
						}
					}
					
					if(improved)
					{
						habitat.removePool(pool);
						habitat.addPool(clonedPool);
						//nbFails = 0;
					}
					
					
				}
				
				
		return improved;
		
	}
	
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
					break;
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
			if(candidatPools.get(iMerge).areAllUsersAuthorized(identicalPool.getListOfUsers().get(0).getNumUser())
			  && candidatPools.get(iMerge).isUserAuthorized(identicalPool.getListOfUsers().get(0)) 
			  )
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
		}	
		
		
		
		//move from the potential
		int i = (new Random()).nextInt(candidatPools.size());
		if(!improved)
		{
			//i = (new Random()).nextInt(candidatPools.size());
			//Pool clonedPool;			
			for(int j=candidatPools.get(i).getListOfUsers().size() - 1; j>=0 && !improved;j--)
			{
				double bestCost = identicalPool.getAvgCost() + candidatPools.get(i).getAvgCost();
				if(Utilisateurs.getAuthorizedUsers()[identicalPool.getListOfUsers().get(0).getNumUser()][candidatPools.get(i).getListOfUsers().get(j).getNumUser()]==1)
				{
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
		}
		
		//swap
		if(!improved)
		{
			for(int j=candidatPools.get(i).getListOfUsers().size() - 1; j>=0 && !improved;j--)
			{
				double bestCost = identicalPool.getAvgCost() + candidatPools.get(i).getAvgCost();
				if(Utilisateurs.getAuthorizedUsers()[identicalPool.getListOfUsers().get(0).getNumUser()][candidatPools.get(i).getListOfUsers().get(j).getNumUser()]==1)
				{
					User swapedUser = identicalPool.getListOfUsers().get(0);
					identicalPool.getListOfUsers().set(0,candidatPools.get(i).getListOfUsers().get(j));
					candidatPools.get(i).getListOfUsers().set(j, swapedUser);
					if(identicalPool.buildRoutes() && candidatPools.get(i).buildRoutes())
					{
						//if(pool.getAvgCost() < bestCost)
						//{	
						//improved = true;
						if((identicalPool.getAvgCost() + candidatPools.get(i).getAvgCost()) < bestCost)
						{
							improved=true;
						}
						else
						{
							swapedUser = identicalPool.getListOfUsers().get(0);
							identicalPool.getListOfUsers().set(0,candidatPools.get(i).getListOfUsers().get(j));
							candidatPools.get(i).getListOfUsers().set(j, swapedUser);
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
						swapedUser = identicalPool.getListOfUsers().get(0);
						identicalPool.getListOfUsers().set(0,candidatPools.get(i).getListOfUsers().get(j));
						candidatPools.get(i).getListOfUsers().set(j, swapedUser);
						identicalPool.buildRoutes();
						candidatPools.get(i).buildRoutes();
					}
				}
			}
		}
		
		return improved;
	}
}

