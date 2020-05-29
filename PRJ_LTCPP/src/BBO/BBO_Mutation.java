package BBO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import Model.CarPool;
import Model.Pool;
import Model.PoolUtilities;
import Model.User;
import Model.UserCandidat;
import Model.Users;
import Model.Utilisateurs;

public class BBO_Mutation {
	private Habitats habitats;
	private BBO_Parameters bboParameters;
	
	public BBO_Mutation(Habitats habitats, BBO_Parameters bboParameters)
	{
		this.habitats = habitats;
		this.bboParameters = bboParameters;
	}
	
	///////////////////////////////////////////////////////
	
	public void getMutatedHabitat(int minMutations, int maxMutations)
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
			//mutationProbability = 0.19 * (new Random()).nextDouble() * mutationProbability * (1.0 - bboParameters.getSpeciesProbabilties()[indProb]/bboParameters.getSpeciesProbabilties()[bboParameters.getSpeciesProbabilties().length - 1]);
			mutationProbability = 0.1 * mutationProbability * (1.0 - bboParameters.getSpeciesProbabilties()[indProb]/bboParameters.getSpeciesProbabilties()[bboParameters.getSpeciesProbabilties().length - 1]);
			//int k = Math.max((new Random()).nextInt(5),3);
			
			//int k = Math.max((new Random()).nextInt(7),3);//habitats.getHabitats().get(i).getSivs().size();
			int k = Math.max((new Random()).nextInt(maxMutations),minMutations);
			//if(restriction==1)
			//{
				//mutationProbability = 0.19 * (new Random()).nextDouble() * mutationProbability * (1.0 - bboParameters.getSpeciesProbabilties()[indProb]/bboParameters.getSpeciesProbabilties()[bboParameters.getSpeciesProbabilties().length - 1]);
			//	k= Math.max((new Random()).nextInt(4),2);
			//}
			
			//int k = 5;
			//for(int j=0;j<habitats.getHabitats().get(i).getSivs().size();j++)
			
						
			for(int j=0;j<k;j++)
			{	
				//if(rand.nextDouble() < mutationProbability)
				int randomNum=0;
				if(rand.nextDouble() < mutationProbability)
				{
					Random rand2 = new Random();
					
					
					//randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
					
					ArrayList<Pool> poolsToSwap = new ArrayList<Pool>();
					int maxPoolToSwap = Math.max((new Random()).nextInt(5), 3);
					//int maxPoolToSwap = Math.max((new Random()).nextInt(6), 4);
					int iterate = 0;
					while(iterate<maxPoolToSwap)
					{	
						randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
						if(!poolsToSwap.contains(habitats.getHabitats().get(i).getSivs().get(randomNum))
						  && habitats.getHabitats().get(i).getSivs().get(randomNum).getListOfUsers().size() > 1 
						  )
						{
							poolsToSwap.add(habitats.getHabitats().get(i).getSivs().get(randomNum));
							iterate++;
						}
					}
					
					for(int l=0;l<poolsToSwap.size();l++)
					{
						//swap3(habitats.getHabitats().get(i),poolsToSwap.get(l));
						swap4(habitats.getHabitats().get(i),poolsToSwap.get(l));
					}
					//while(habitats.getHabitats().get(i).getSivs().get(randomNum).getListOfUsers().size()==1)
					//{
					//	randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
						//randomNum = rand2.nextInt(10);
					//}
					
					//if(habitats.getHabitats().get(i).getSivs().get(randomNum).getListOfUsers().size()>=2)
					//{
						//swap2(habitats.getHabitats().get(i),habitats.getHabitats().get(i).getSivs().get(randomNum));
					//	swap3(habitats.getHabitats().get(i),habitats.getHabitats().get(i).getSivs().get(randomNum));
					//}
					
					ArrayList<Pool> poolsToMove = new ArrayList<Pool>();
					//Collections.sort(habitats.getHabitats().get(i).getSivs(), Pool.ComparatorDistG);
					//Collections.reverseOrder();
					int maxPoolToMove = 2;
					//int maxPoolToMove = Math.max((new Random()).nextInt(3), 2);
					iterate = 0;
					int loops=0;
					while(loops<habitats.getHabitats().get(i).getSivs().size() && iterate<maxPoolToMove)
					{
						randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
						if(!poolsToMove.contains(habitats.getHabitats().get(i).getSivs().get(randomNum))
								  && habitats.getHabitats().get(i).getSivs().get(randomNum).getListOfUsers().size() >= 2 
								  )
								{
									poolsToMove.add(habitats.getHabitats().get(i).getSivs().get(randomNum));
									iterate++;
								}
						loops++;
						
					}
					//while(iterate<maxPoolToMove)
					//{	
						//randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
					//	if(!poolsToMove.contains(habitats.getHabitats().get(i).getSivs().get(randomNum))
					//	  && habitats.getHabitats().get(i).getSivs().get(randomNum).getListOfUsers().size() > 2 
					//	  )
					//	{
					//		poolsToMove.add(habitats.getHabitats().get(i).getSivs().get(randomNum));
					//		iterate++;
					//	}
					//}
					
					for(int l=0;l<poolsToMove.size();l++)
					{
						move3(habitats.getHabitats().get(i),poolsToMove.get(l));
						//ReinsertFurther(habitats.getHabitats().get(i), poolsToMove.get(l));
					}
					//randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
					
					//while(habitats.getHabitats().get(i).getSivs().get(randomNum).getListOfUsers().size() < 3)
					//{
					//	randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
						//randomNum = rand2.nextInt(10);
					//}
					
					//if(habitats.getHabitats().get(i).getSivs().get(randomNum).getListOfUsers().size() >= 3)
					//{
					//		move3(habitats.getHabitats().get(i),habitats.getHabitats().get(i).getSivs().get(randomNum));
					//}
										
					
					//Collections.sort(habitats.getHabitats().get(i).getSivs(), Pool.ComparatorCost);
					//Collections.reverseOrder();
					
					
					
					randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
					
					//if(habitats.getHabitats().get(i).getSivs().get(randomNum).getRestCarCap() == 0) 
					   //&& (new Random()).nextDouble()<0.10)
					//if(habitats.getHabitats().get(i).getSivs().get(randomNum).getListOfUsers().size() > 2)
					//if(habitats.getHabitats().get(i).getSivs().get(randomNum).getRestCarCap() == 0)
					
					//if(habitats.getHabitats().get(i).getSivs().get(randomNum).getRestCarCap() == 0 
					
					if(habitats.getHabitats().get(i).getSivs().get(randomNum).getListOfUsers().size()>=3	
						&& (new Random()).nextDouble()<0.10)
					{
						//ArrayList<Pool> pools = new ArrayList<Pool>();
						if((new Random()).nextDouble()<0.90)
						{
							if(habitats.getHabitats().get(i).getSivs().get(randomNum).getRestCarCap() == 0 ) 
							{
								divide(habitats.getHabitats().get(i),habitats.getHabitats().get(i).getSivs().get(randomNum));
							}
						}
						else
						{
							divideTo3(habitats.getHabitats().get(i),habitats.getHabitats().get(i).getSivs().get(randomNum));
						}
						//divideWorst(habitats.getHabitats().get(i));
					}
					
					if((new Random().nextDouble())<0.001)
					{
						//brokeAlone_2(habitats.getHabitats().get(i));
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
					
									
					ArrayList<Pool> poolsToMerge = new ArrayList<Pool>();
					int iteration = 0;
					int maxPoolToMerge = (int) Math.ceil(habitats.getHabitats().get(i).getSivs().size()/5.0);
					while(iteration<maxPoolToMerge)
					{	
						randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
						if(!poolsToMerge.contains(habitats.getHabitats().get(i).getSivs().get(randomNum))
						  && habitats.getHabitats().get(i).getSivs().get(randomNum).getRestCarCap() > 0 )
						{
							poolsToMerge.add(habitats.getHabitats().get(i).getSivs().get(randomNum));
							iteration++;
						}
					}
					
					for(int l=0;l<poolsToMerge.size();l++)
					{
						merge2(habitats.getHabitats().get(i),poolsToMerge.get(l));
					}
					
					
					
					//MoveAll (Reinsertion of users)
					ArrayList<Pool> poolsToMoveAll = new ArrayList<Pool>();
					iterate = 0;
					int maxPoolToMoveAll = (int) Math.ceil(habitats.getHabitats().get(i).getSivs().size()/5.0);
					while(iterate<maxPoolToMoveAll)
					{	
						randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
						if(!poolsToMoveAll.contains(habitats.getHabitats().get(i).getSivs().get(randomNum))
						  //&& habitats.getHabitats().get(i).getSivs().get(randomNum).getListOfUsers().size() > 1 //&& 
						  //habitats.getHabitats().get(i).getSivs().get(randomNum).getRestCarCap() > 0
						  )
						{
							poolsToMoveAll.add(habitats.getHabitats().get(i).getSivs().get(randomNum));
							iterate++;
						}
					}
					
					for(int l=0;l<poolsToMoveAll.size();l++)
					{
						moveAll(habitats.getHabitats().get(i),poolsToMoveAll.get(l));
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
					
					//if(listPoolPen.size()>0)
					//{
					//	randomNum = rand2.nextInt(listPoolPen.size());
					//	affectAlonePool_2(habitats.getHabitats().get(i),listPoolPen.get(randomNum));
					//}
					if(listPoolPen.size()>0)
					{
						for(int l=0;l<listPoolPen.size();l++)
						{
							if(listPoolPen.get(l)!=null && listPoolPen.get(l).getListOfUsers().size()==1)
							{
								affectAlonePool_2(habitats.getHabitats().get(i),listPoolPen.get(l)); 
								
							}
							
						}
					}
					
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
			if((habitat.getSivs().get(i).getListOfUsers().size()>=3) && habitat.getSivs().get(i) != pool)
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

	private void swap(Habitat habitat,Pool pool) {
		// TODO Auto-generated method stub
		boolean improvedSwapedPool = false;
		Pool pool1 = null;
		Pool pool2 = null; 
		int poolToRemove=-1;
		double bestCost = 1000.0;
		boolean firstValidSwap=true;
		for(int i=0;i<habitat.getSivs().size() && !improvedSwapedPool;i++)
		{
			
			if(habitat.getSivs().get(i) != pool)
			{
				Pool clonedPool = (Pool) pool.clone();
				Pool swapedPool = (Pool) habitat.getSivs().get(i).clone();
				for(int j=0;j<swapedPool.getListOfUsers().size() && !improvedSwapedPool;j++)
				{
					
					for(int k=0;k<clonedPool.getListOfUsers().size() && !improvedSwapedPool;k++)
					{
						User swapedUser = (User) clonedPool.getListOfUsers().get(k).clone();
						clonedPool.getListOfUsers().set(k, swapedPool.getListOfUsers().get(j));
						swapedPool.getListOfUsers().set(j, swapedUser);
						if(clonedPool.buildRoutes() && swapedPool.buildRoutes())
						{
							if(firstValidSwap)
							{
								bestCost = pool.getAvgCost() + habitat.getSivs().get(i).getAvgCost();
								firstValidSwap = false;		
							}
							//if(pool.getAvgCost() + habitat.getSivs().get(i).getAvgCost() > clonedPool.getAvgCost() + swapedPool.getAvgCost())
							if(bestCost > clonedPool.getAvgCost() + swapedPool.getAvgCost())
							{
								//improvedSwapedPool = true;
								poolToRemove = i;
								pool1 = clonedPool;
								pool2 = swapedPool;
								
								bestCost = clonedPool.getAvgCost() + swapedPool.getAvgCost();
								
							}
							else
							{
								swapedUser = (User) clonedPool.getListOfUsers().get(k).clone();
								clonedPool.getListOfUsers().set(k, swapedPool.getListOfUsers().get(j));
								swapedPool.getListOfUsers().set(j, swapedUser);
							}
							
							
						}
						else
						{
							swapedUser = (User) clonedPool.getListOfUsers().get(k).clone();
							clonedPool.getListOfUsers().set(k, swapedPool.getListOfUsers().get(j));
							swapedPool.getListOfUsers().set(j, swapedUser);
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

	/////////////////////////////////////////////
	//Implemete a swap3 with random select
	private void swap2(Habitat habitat,Pool pool) {
		// TODO Auto-generated method stub
		boolean improvedSwapedPool = false;
		Pool pool1 = null;
		Pool pool2 = null; 
		int poolToRemove=-1;
		
		for(int i=0;i<habitat.getSivs().size() && !improvedSwapedPool;i++)
		{
			
			if(habitat.getSivs().get(i) != pool)
			{
				Pool clonedPool = (Pool) pool.clone();
				Pool swapedPool = (Pool) habitat.getSivs().get(i).clone();
				for(int j=0;j<swapedPool.getListOfUsers().size() && !improvedSwapedPool;j++)
				{
					
					for(int k=0;k<clonedPool.getListOfUsers().size() && !improvedSwapedPool;k++)
					{
						User swapedUser = (User) clonedPool.getListOfUsers().get(k).clone();
						clonedPool.getListOfUsers().set(k, swapedPool.getListOfUsers().get(j));
						swapedPool.getListOfUsers().set(j, swapedUser);
						if(clonedPool.buildRoutes() && swapedPool.buildRoutes())
						{
							improvedSwapedPool = true;
							poolToRemove = i;
							pool1 = clonedPool;
							pool2 = swapedPool;
								
						}
						else
						{
							swapedUser = (User) clonedPool.getListOfUsers().get(k).clone();
							clonedPool.getListOfUsers().set(k, swapedPool.getListOfUsers().get(j));
							swapedPool.getListOfUsers().set(j, swapedUser);
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
		//Collections.sort(habitat.getSivs(), PoolUtilities.ComparatorUserGC);
		//Collections.reverseOrder();
		//int posPool = habitat.getSivs().indexOf(pool);
		
		for(int i=0;i<habitat.getSivs().size() && !improvedSwapedPool;i++)
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
	
	/////////////// SWAP 3
	private void swap4(Habitat habitat,Pool pool) {
	// TODO Auto-generated method stub
	boolean improvedSwapedPool = false;
	//Pool pool1 = null;
	//Pool pool2 = null; 
	//int poolToRemove=-1;
	
	ArrayList<Integer> tabuSivs = new ArrayList<Integer>();
	tabuSivs.add(habitat.getSivs().indexOf(pool));
	
	//ArrayList<Pool>
	//PoolUtilities.setPool(pool);
	//Collections.sort(habitat.getSivs(), PoolUtilities.ComparatorUserGC);
	//Collections.reverseOrder();
	//int posPool = habitat.getSivs().indexOf(pool);
	
	for(int i=0;i<habitat.getSivs().size() && !improvedSwapedPool;i++)
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
			//Pool clonedPool = (Pool) pool.clone();
			//Pool swapedPool = (Pool) habitat.getSivs().get(randNumPool).clone();
			for(int j=0;j<habitat.getSivs().get(randNumPool).getListOfUsers().size() && !improvedSwapedPool;j++)
			{
				
				for(int k=0;k<pool.getListOfUsers().size() && !improvedSwapedPool;k++)
				{
					if((pool.areAllUsersAuthorizedSwap(habitat.getSivs().get(randNumPool).getListOfUsers().get(j).getNumUser(),pool.getListOfUsers().get(k).getNumUser())
					&& habitat.getSivs().get(randNumPool).areAllUsersAuthorizedSwap(pool.getListOfUsers().get(k).getNumUser(),habitat.getSivs().get(randNumPool).getListOfUsers().get(j).getNumUser()))
					//&& (clonedPool.isUserAuthorized(swapedPool.getListOfUsers().get(j))
					//	&& swapedPool.isUserAuthorized(clonedPool.getListOfUsers().get(k)))
					)
					{
						//User swapedUser = (User) clonedPool.getListOfUsers().get(k).clone();
						User swapedUser = pool.getListOfUsers().get(k);
						pool.getListOfUsers().set(k, habitat.getSivs().get(randNumPool).getListOfUsers().get(j));
						habitat.getSivs().get(randNumPool).getListOfUsers().set(j, swapedUser);
						if(pool.buildRoutes() && habitat.getSivs().get(randNumPool).buildRoutes())
						{
							improvedSwapedPool = true;
							//poolToRemove = randNumPool;
							//pool = clonedPool;
							//habitat.getSivs().set(randNumPool,swapedPool);
							//pool1 = clonedPool;
							//pool2 = swapedPool;
						}
						else
						{
							//swapedUser = (User) clonedPool.getListOfUsers().get(k).clone();
							swapedUser = pool.getListOfUsers().get(k);
							pool.getListOfUsers().set(k, habitat.getSivs().get(randNumPool).getListOfUsers().get(j));
							habitat.getSivs().get(randNumPool).getListOfUsers().set(j, swapedUser);
							pool.buildRoutes();
							habitat.getSivs().get(randNumPool).buildRoutes();
						}
					}
				}
			}
		}
	}
	
	//if(poolToRemove >=0)
	//{
	//	habitat.removePool(habitat.getSivs().get(poolToRemove));
	//	habitat.removePool(pool);
		
	//	habitat.addPool(pool1);
	//	habitat.addPool(pool2);
	//}
	}
	///////////////////////////////////////
	
	
	public void merge2(Habitat habitat,Pool pool)
	{
		//double poolAvgCost = pool.getAvgCost();
		ArrayList<Pool> poolsList = new ArrayList<Pool>();
		
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
		//Pool clonedPool=null;
		ArrayList<Integer> tabuSivs = new ArrayList<Integer>();
		tabuSivs.add(-1);
		
		//Sort the users of the pool compared to gravity center
		//Collections.sort(pool.getListOfUsers(), User.ComparatorDistG);
		
		for(int i =0;i<habitat.getSivs().size() && !moved;i++)
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
				//clonedPool = (Pool) pool.clone();
				//for(int j=0;j<pool.getListOfUsers().size() && !moved;j++)
				for(int j=pool.getListOfUsers().size() - 1; j>=0 && !moved;j--)
				{
					//if(habitat.getSivs().get(k).areAllUsersAuthorized(clonedPool.getListOfUsers().get(j).getNumUser()))
					if(habitat.getSivs().get(k).areAllUsersAuthorized(pool.getListOfUsers().get(j).getNumUser()))
					{
						//habitat.getSivs().get(k).addUser(clonedPool.getListOfUsers().get(j));
						habitat.getSivs().get(k).addUser(pool.getListOfUsers().get(j));
						if(habitat.getSivs().get(k).buildRoutes())
						{
							moved = true;
							//clonedPool.getListOfUsers().remove(j);
							//clonedPool.buildRoutes();
							pool.getListOfUsers().remove(j);
							pool.buildRoutes();
						}
						else
						{
							//habitat.getSivs().get(k).removeUser(clonedPool.getListOfUsers().get(j));
							habitat.getSivs().get(k).removeUser(pool.getListOfUsers().get(j));
							habitat.getSivs().get(k).buildRoutes();
						}
					}
				}
			}
		}
		
		//if(moved)
		//{
		//	habitat.removePool(pool);
		//	habitat.addPool(clonedPool);
		//}
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
		Random rand2 = new Random();
		
		
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
	
	
	////////  Broke pool with one user //////////////////////
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
						if(habitat.getSivs().get(j).getListOfUsers().get(k).getNumUser()==candidatUsers.get(i)
						  && !candidatPools.contains(habitat.getSivs().get(j)))
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
						if(habitat.getSivs().get(j).getListOfUsers().get(k).getNumUser()==candidatUsers.get(i)
							&& !candidatPools.contains(habitat.getSivs().get(j))
						  )
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
		int loops = 0;
		while(candidatPools.get(i).getListOfUsers().size()==1 && loops<5)
		{
			i = (new Random()).nextInt(candidatPools.size());
			loops++;
		}
		if(candidatPools.get(i).getListOfUsers().size()==1)
		{
			return improved;
		}
			
			if(!improved)
			{
				//i = (new Random()).nextInt(candidatPools.size());
				//Pool clonedPool;			
				for(int j=candidatPools.get(i).getListOfUsers().size() - 1; j>=0 && !improved;j--)
				{
					//double bestCost = identicalPool.getAvgCost() + candidatPools.get(i).getAvgCost();
					if(Utilisateurs.getAuthorizedUsers()[identicalPool.getListOfUsers().get(0).getNumUser()][candidatPools.get(i).getListOfUsers().get(j).getNumUser()]==1)
					{
						identicalPool.getListOfUsers().add(candidatPools.get(i).getListOfUsers().get(j));
					
						if(identicalPool.buildRoutes())
						{
							//if(pool.getAvgCost() < bestCost)
							//{	
							//improved = true;
							candidatPools.get(i).getListOfUsers().remove(j);
							if(candidatPools.get(i).getListOfUsers().size()==0)
							{
								habitat.removePool(candidatPools.get(i));
							}
							else
							{
								candidatPools.get(i).buildRoutes();
							}
							improved=true;
							
							
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
				//double bestCost = identicalPool.getAvgCost() + candidatPools.get(i).getAvgCost();
					User swapedUser = identicalPool.getListOfUsers().get(0);
					identicalPool.getListOfUsers().set(0,candidatPools.get(i).getListOfUsers().get(j));
					candidatPools.get(i).getListOfUsers().set(j, swapedUser);
					if(identicalPool.buildRoutes() && candidatPools.get(i).buildRoutes())
					{
						
							improved=true;
						
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
		
		return improved;
	}
	
	
	// It's like move
	public boolean ReinsertFurther(Habitat habitat, Pool pool)
	{
		
		//ArrayList<Pool> tabooList = new ArrayList<Pool>();
		//double bestCost = 0.0;
		
		
		
		//Sort the users of the pool compared to gravity center
		//Collections.sort(pool.getListOfUsers(), User.ComparatorDistG);
		PoolUtilities.setPool(pool);
		Collections.sort(habitat.getSivs(), PoolUtilities.ComparatorUserGC);
		boolean improved = false;
		
		//PoolUtilities.setPool(pool);
		//Collections.sort(habitat.getSivs(), PoolUtilities.ComparatorGC);
					
		Pool clonedPool=null;
		ArrayList<Integer> tabuSivs = new ArrayList<Integer>();
		tabuSivs.add(-1);
					
		for(int k=0;k<habitat.getSivs().size() && !improved;k++)
		{
					
			if ((habitat.getSivs().get(k) == pool || habitat.getSivs().get(k).getRestCarCap()==0 || tabuSivs.contains(k)))
			{
						continue;	
			}
						
			if(habitat.getSivs().get(k) != pool && habitat.getSivs().get(k).getRestCarCap()>0)
			{
				clonedPool = (Pool) pool.clone();
				if(habitat.getSivs().get(k).areAllUsersAuthorized(clonedPool.getFartherUser().getNumUser()))
					{
						//bestCost = habitat.getSivs().get(k).getAvgCost() + pool.getAvgCost(); 
						habitat.getSivs().get(k).addUser(clonedPool.getFartherUser());
						if(habitat.getSivs().get(k).buildRoutes())
						{
							
								improved = true;
								clonedPool.removeUser(clonedPool.getFartherUser());;
								clonedPool.buildRoutes();
						
									
						}
						else
						{
							habitat.getSivs().get(k).removeUser(clonedPool.getFartherUser());
							habitat.getSivs().get(k).buildRoutes();
						}
					}
			}
					
			if(improved)
			{
				habitat.removePool(pool);
				habitat.addPool(clonedPool);
			}
					
					
		}
		
		if(!improved)
		{
			move3(habitat, pool);
		}
				
				
		return improved;
		
	}	
	
	public boolean brokeAlone(Habitat habitat)
	{
		boolean improved=false;
		boolean found=false;
		//Construct pool of unique user which must do with one user 
		ArrayList<Pool> poolsList = new ArrayList<Pool>();
		ArrayList<Integer> usersList = new ArrayList<Integer>();
		for(int i=0;i<habitat.getSivs().size();i++)
		{
			if(habitat.getSivs().get(i).getListOfUsers().size()==1
				&& Arrays.stream(Utilisateurs.getAuthorizedUsers()[habitat.getSivs().get(i).getListOfUsers().get(0).getNumUser()]).sum()==2 
				)
			{
				
				poolsList.add(habitat.getSivs().get(i));
				for(int j=1;j<Utilisateurs.getAuthorizedUsers()[habitat.getSivs().get(i).getListOfUsers().get(0).getNumUser()].length && !found;j++)
				{
					if(habitat.getSivs().get(i).getListOfUsers().get(0).getNumUser()!=j && Utilisateurs.getAuthorizedUsers()[habitat.getSivs().get(i).getListOfUsers().get(0).getNumUser()][j]==1) //usersList.add(arg0);
					{
						usersList.add(j);
						found=true;
					}
				}
			}
		}
		
		//affect
		//double bestCost=0.0;
		
		for(int i=0;i<poolsList.size();i++)
		{
			if(poolsList.size()>1)
			{
				continue;
			}
				
			found=false;
			for(int j=0;j<habitat.getSivs().size() && !found;j++)
			{
				if(habitat.getSivs().get(j).isUserInPool(usersList.get(i)))
				{
					//bestCost = poolsList.get(i).getAvgCost() + habitat.getSivs().get(j).getAvgCost();
					for(int k=0;k<habitat.getSivs().get(j).getListOfUsers().size() && !found;k++)
					{
						if(habitat.getSivs().get(j).getListOfUsers().get(k).getNumUser() == usersList.get(i))
						{
							//Case of pool with one user
							if(habitat.getSivs().get(j).getListOfUsers().size()==1)
							{
								poolsList.get(i).getListOfUsers().add(habitat.getSivs().get(j).getListOfUsers().get(k));
								if(poolsList.get(i).buildRoutes())
								{
									habitat.getSivs().remove(j);
									found=true;
									improved=true;
								}
								else
								{
									poolsList.get(i).getListOfUsers().remove(habitat.getSivs().get(j).getListOfUsers().get(k));
									poolsList.get(i).buildRoutes();
								}
							}
							else
							{
								poolsList.get(i).addUser(habitat.getSivs().get(j).getListOfUsers().get(k));
								habitat.getSivs().get(j).getListOfUsers().remove(habitat.getSivs().get(j).getListOfUsers().get(k));
								if(poolsList.get(i).buildRoutes())
								{
									habitat.getSivs().get(j).buildRoutes();
									
										found=true;
										improved=true;
									
								}
								else
								{
									habitat.getSivs().get(j).addUser(poolsList.get(i).getListOfUsers().get(1));
									poolsList.get(i).getListOfUsers().remove(1);
									habitat.getSivs().get(j).buildRoutes();
									poolsList.get(i).buildRoutes();
								}
							}
						}
					}
					//poolsList.addUser(habitat.getSivs().get(j).getListOfUsers()));
				}
			}
		}
		
		
		return improved;
	}
				
	public boolean brokeAlone_2(Habitat habitat)
	{
		boolean improved=false;
		boolean found=false;
		//Construct pool of unique user which must do with one user 
		ArrayList<Pool> poolsList = new ArrayList<Pool>();
		ArrayList<Integer> usersList = new ArrayList<Integer>();
		for(int i=0;i<habitat.getSivs().size();i++)
		{
			if(habitat.getSivs().get(i).getListOfUsers().size()==1
				&& Arrays.stream(Utilisateurs.getAuthorizedUsers()[habitat.getSivs().get(i).getListOfUsers().get(0).getNumUser()]).sum()==2 
				)
			{
				
				poolsList.add(habitat.getSivs().get(i));
				for(int j=1;j<Utilisateurs.getAuthorizedUsers().length && !found;j++)
				{
					if(habitat.getSivs().get(i).getListOfUsers().get(0).getNumUser()!=j && Utilisateurs.getAuthorizedUsers()[habitat.getSivs().get(i).getListOfUsers().get(0).getNumUser()][j]==1) //usersList.add(arg0);
					{
						usersList.add(j);
						found=true;
					}
				}
			}
		}
		
		//affect
		//double bestCost=0.0;
		
		if(poolsList.size()==0 || usersList.size()==0 || poolsList.size()!=usersList.size())
		{
			return improved;
		}
		
		int i=(new Random()).nextInt(poolsList.size());
		//for(int i=0;i<poolsList.size();i++)
		//{
				
			found=false;
			for(int j=0;j<habitat.getSivs().size() && !found;j++)
			{
				if(habitat.getSivs().get(j).isUserInPool(usersList.get(i)))
				{
					//bestCost = poolsList.get(i).getAvgCost() + habitat.getSivs().get(j).getAvgCost();
					for(int k=0;k<habitat.getSivs().get(j).getListOfUsers().size() && !found;k++)
					{
						if(habitat.getSivs().get(j).getListOfUsers().get(k).getNumUser() == usersList.get(i))
						{
							//Case of pool with one user
							if(habitat.getSivs().get(j).getListOfUsers().size()==1)
							{
								poolsList.get(i).getListOfUsers().add(habitat.getSivs().get(j).getListOfUsers().get(k));
								if(poolsList.get(i).buildRoutes())
								{
									habitat.getSivs().remove(j);
									found=true;
									improved=true;
								}
								else
								{
									poolsList.get(i).getListOfUsers().remove(habitat.getSivs().get(j).getListOfUsers().get(k));
									poolsList.get(i).buildRoutes();
								}
							}
							else
							{
								poolsList.get(i).addUser(habitat.getSivs().get(j).getListOfUsers().get(k));
								habitat.getSivs().get(j).getListOfUsers().remove(habitat.getSivs().get(j).getListOfUsers().get(k));
								if(poolsList.get(i).buildRoutes())
								{
									habitat.getSivs().get(j).buildRoutes();
									found=true;
									improved=true;
									
								}
								else
								{
									habitat.getSivs().get(j).addUser(poolsList.get(i).getListOfUsers().get(1));
									poolsList.get(i).getListOfUsers().remove(1);
									habitat.getSivs().get(j).buildRoutes();
									poolsList.get(i).buildRoutes();
								}
							}
						}
					}
					//poolsList.addUser(habitat.getSivs().get(j).getListOfUsers()));
				}
			}
		
		
		
		return improved;
	}
}
