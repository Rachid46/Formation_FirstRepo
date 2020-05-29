package BBO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import Model.CarPool;
import Model.InitPopulation;
import Model.Optimization;
import Model.Pool;
import Model.PoolUtilities;
import Model.User;
import Model.Utilisateurs;

public class BBO_Optimization extends Optimization {

	private Habitats habitats;
	private BBO_Parameters bboParameters;
	private BestCost bestCost = null;
	private EmigrationRates emigrationRates = null;
	private ImmigrationRates immigrationRates = null;
	private EliteSolutions eliteSolutions = null;
	
	private Habitat sol1=null;
	private int nbMutation=0;
	private int nbRenew=0;
	private ArrayList<Double> solAtMut = new ArrayList<Double>();

	public BBO_Optimization() {
		habitats = new Habitats(new ArrayList<Habitat>());
	}

	@Override
	public void initRandPopulation() {
		System.out.println("initPopulation");
		InitPopulation initPop = new InitPopulation(users);
		// a structured portion
		// sweep
		// a part randomly
		for (int i = 0; i < bboParameters.getPopulationSize(); i++) {
			// TODO : cast is incorrect. We can't cast a child in a father class
			Habitat habitat = new Habitat();
			
			
			//if((i+1)/bboParameters.getPopulationSize()>0.15)
			if((i+1)/bboParameters.getPopulationSize()<0.19 )
			{
				initPop.randPop(habitat,0);
			}
			else
			{
				initPop.randPop(habitat,1); //1
			}
				
			habitats.addHabitat(habitat);
		}

		/*
		 * for(int i=0;i<popSize;i++) { //Manage the habitats initPop.randPop(); }
		 */
	}

	// principal treatment function
	@Override
	public void treatment() {

		preprocessingBBO();

		processBBO();
		
		System.out.println("solution initial : " + sol1.getHSI()); 

	}

	private void processBBO() {
		// TODO Auto-generated method stub
		Thread thread = new Thread() {
			@Override
			public void run() {
				// while(terminateRule.isSatisfyRule() != true)
				int j = 0;
				int nbFailsToImprove=0;
				double oldBestSol=10000.0;
				int nb_iter = 1000;
				//int nbTraps = 0;
				//boolean first=true;
				while (j < nb_iter) {
					// System.out.println(terminateRule.getCurrentSituation());
					//for (int i = 0; i < habitats.getHabitats().size(); i++) {
						// System.out.print(Arrays.toString(habitats.getHabitats().get(i).getSivs()) +
						// "\t");
					//	System.out.println("Fit:" + habitats.getHabitats().get(i).getHSI());
					//}

					// System.out.print("Best cost: " +
					// Arrays.toString(bestCost.getHabitat().getSivs()));
					//System.out.println("\tFit: " + bestCost.getHSIFromBestCost());

					// TODO
					saveEliteSolutions();
					
					if(j==0)
					{
						sol1= (Habitat) eliteSolutions.getElites().get(0).clone();
					}
										
					//replace some individuals with others
					//renewPopulation(j, 1000);
					

					/*
					 * for(int i=0;i<eliteSolutions.getElites().size();i++) {
					 * System.out.println("Elite-" + (i+1) + " Hsi : " +
					 * eliteSolutions.getElites().get(i).getHSI()); }
					 */

					// TODO
					migrationBetweenHabitats();

					// Mutate the solutions that don't belong to the elite
					if(nbFailsToImprove > 40 && (new Random()).nextDouble()< 0.019) //10.0/(j+1))
					{
						evaluateHSI();
						
						sortPopulation();
						solAtMut.add(habitats.getHabitats().get(0).getHSI());
						
						//if(first)
						//{ 
						//	oldBestSol = habitats.getHabitats().get(0).getHSI();
						//	first=false;
						//}
						
					
						if( //Math.abs(habitats.getHabitats().get(10).getHSI() - bestCost.getHSIFromBestCost()) < 10.0 // 0.001
							Math.abs(habitats.getHabitats().get(10).getHSI() - habitats.getHabitats().get(0).getHSI())/habitats.getHabitats().get(0).getHSI() < 0.01	
							&& (j+1)/nb_iter < 0.30 && nbMutation>2 && nbRenew<2) 	
						//	&& (new Random()).nextDouble()<0.45)
						//if(nbTraps>2  && nbMutation>2 && nbRenew<2 )	
						{
							
							//mutationAndReplace(0);
							mutationAndReplace(5,7);
							
							//evaluateHSI();

							//sortPopulation();
							//renewPopulation(0, 0);
							nbRenew++;
							nbMutation=0;
							//nbMutation = Math.max(0, nbMutation - 1);
							//nbMutation--;
							//nbMutation--;
						}
						else
						{
							
							//nbTraps
							//if(Math.abs(oldBestSol - habitats.getHabitats().get(0).getHSI())<0.01 )
							//{
							//	oldBestSol = bestCost.getHSIFromBestCost();
							//	nbTraps = Math.min(nbTraps + 1,3);
							//}
							//else
							//{
							//	oldBestSol = bestCost.getHSIFromBestCost();
							//	nbTraps=0;
							//}
							
							mutationAndReplace(3,5);
							nbMutation++;
							nbRenew = Math.max(0, nbRenew - 1);
							
							
						}
						
						
																	
						//vnsOperate();
						
						evaluateHSI();

						sortPopulation();
						
						//mutationAndReplace(j);
						//mutationAndReplace(2,4);
					
						//evaluateHSI();

						//sortPopulation();
						migrationBetweenHabitats();
						vnsOperate();
						
												
						nbFailsToImprove=0;
					}
					else
					{
						//mutationAndReplace(j);
					
						//mutationAndReplace_2(j);
						
						//mutationAndReplace_3(j);
						//vnsOperate();
						
						evaluateHSI();
	
						sortPopulation();
						
						// TODO
						replaceWorstIndividuals();
						
						evaluateHSI();
						
						sortPopulation();
						
						//if(Math.abs(habitats.getHabitats().get(10).getHSI() - habitats.getHabitats().get(0).getHSI()) < 0.01)
						if(Math.abs(habitats.getHabitats().get(10).getHSI() - habitats.getHabitats().get(0).getHSI())/habitats.getHabitats().get(0).getHSI()<0.01)
						{
							nbFailsToImprove++;
						}
						else
						{
							nbFailsToImprove=0;
						}
					}
					
					
					///////// VNS /////
					
					//vnsOperate();
					//swapBestMaster();
						
					//swapBest(); 
					//evaluateHSI();
	
					//sortPopulation();
					
					
					///////////////////////////
					
					

					//evaluateHSI();
					
					//sortPopulation();
					
									
					//
					//Renew if nb fails to improve
					//if(Math.abs(eliteSolutions.getElites().get(0).getHSI() - habitats.getHabitats().get(0).getHSI()) < 20.0) 
					//if(Math.abs(Math.min(eliteSolutions.getElites().get(0).getHSI(),habitats.getHabitats().get(0).getHSI()) - oldBestSol) < 1.0)
					
					
					///////////////

					//saveBestHabitat();
					
					//oldBestSol = eliteSolutions.getElites().get(0).getHSI();
					
					
					
					//randomNum = rand2.nextInt(habitats.getHabitats().get(i).getSivs().size());
					//if(habitats.getHabitats().get(i).getSivs().get(randomNum).getListOfUsers().size() >= 4)
					//{
						//ArrayList<Pool> pools = new ArrayList<Pool>();
					//	divideTo3(habitats.getHabitats().get(i),habitats.getHabitats().get(i).getSivs().get(randomNum));
						//divideWorst(habitats.getHabitats().get(i));
					//}
					
					//if(j>980)
					//{
						//mergeSoloPools();
					//}
					
					evaluateHSI();
					
					sortPopulation();
					
					saveBestHabitat();
					
					j++;
				}
			}

			

		};
		thread.run();
		
		for(int i=0;i<habitats.getHabitats().size();i++ )
		{
			int nbUsers = 0;
			for(int j=0;j<habitats.getHabitats().get(i).getSivs().size();j++)
			{
				nbUsers = nbUsers + habitats.getHabitats().get(i).getSivs().get(j).getListOfUsers().size(); 
			}
			
			System.out.println("habitat-" + (i+1) + " nbUsers : " + nbUsers  + " HSI : " + habitats.getHabitats().get(i).getHSI());
		}
		
		for(int i=0;i<habitats.getHabitats().get(0).getSivs().size();i++)
		{
			System.out.println("Habitat 1 : Pool-" + (i+1) + " size : " + habitats.getHabitats().get(0).getSivs().get(i).getListOfUsers().size());
			//if(habitats.getHabitats().get(0).getSivs().get(i).getListOfUsers().size()==1)
			//{
			for(int j=0;j<habitats.getHabitats().get(0).getSivs().get(i).getListOfUsers().size();j++)
			{
				System.out.println("User Num : " + habitats.getHabitats().get(0).getSivs().get(i).getListOfUsers().get(j).getNumUser());
			}
			
			System.out.println("Coût : " + (i+1) + " size : " + habitats.getHabitats().get(0).getSivs().get(i).getAvgCost());
			//}
		}
		
		for(int i=0;i<solAtMut.size();i++)
		{
			System.out.println("solMut-" + (i+1) + " : " + solAtMut.get(i));
		}
		
		System.out.println("nb mutation : " + nbMutation );
		System.out.println("nb renew : " + nbRenew );
		System.out.println("Best Cost is : " + bestCost.getHSIFromBestCost());
		
		//To remove
		int minSumDist = Arrays.stream(Utilisateurs.getAll_users()[1]).sum();//10000000;
		//Arrays.stream(Utilisateurs.getAuthorizedUsers()[habitat.getSivs().get(counter).getListOfUsers().get(0).getNumUser()]).sum()>1
		int numUser = 0;
		for(int i=1;i<Utilisateurs.getAll_users().length;i++)
		{
			if(Arrays.stream(Utilisateurs.getAll_users()[i]).sum()<minSumDist)
			{
				numUser=i;
				minSumDist = Arrays.stream(Utilisateurs.getAll_users()[i]).sum();
			}
		}
		
		System.out.println("Min User : " + numUser);
	}
	
	protected void renewPopulation(int iter, int maxIter) {
		// TODO Auto-generated method stub
		//if(habitats.getHabitats().get(0).getHSI()-habitats.getHabitats().get(bboParameters.getPopulationSize()-1).getHSI()<=10)
		//if(habitats.getHabitats().get(5).getHSI()-habitats.getHabitats().get(0).getHSI()<=50.0)
		//{
			InitPopulation initPop = new InitPopulation(users);
			for(int k=2;k<3;k++)
			{
				Habitat habitat = new Habitat();
				initPop.randPop(habitat,0); //1
				habitats.getHabitats().set(bboParameters.getPopulationSize()-(k-1), habitat);
				//habitats.getHabitats().set(k, habitat);
			}
			
		//}
		
	}

	private void replaceWorstIndividuals() {
		// TODO Auto-generated method stub
		for(int i=(bboParameters.getPopulationSize() - eliteSolutions.getElites().size());i<bboParameters.getPopulationSize();i++)
		{
			habitats.getHabitats().set(i, eliteSolutions.getElites().get((bboParameters.getPopulationSize() - 1) - i));
		}

	}

	private void mutationAndReplace(int minMutations,int maxMutations) {
		// TODO Auto-generated method stub

//			System.out.println("*****************Mutation*********************");
		// habitats = new Mutation(habitats.getHabitats(),
		// basicParameters).getMutatedHabitat();
		(new BBO_Mutation(habitats, bboParameters)).getMutatedHabitat(minMutations,maxMutations);
		
//			for(int i = 0 ; i < habitat.getHabitat().length ; i++)
//			{
//				System.out.println(Arrays.toString(habitat.getHabitat()[i].getVariables()) + "\t");
//			}

	}

	private void mutationAndReplace_2(int iter) {
		// TODO Auto-generated method stub

//			System.out.println("*****************Mutation*********************");
		// habitats = new Mutation(habitats.getHabitats(),
		// basicParameters).getMutatedHabitat();
		(new BBO_Mutation_2(habitats, bboParameters)).getMutatedHabitat(iter);
		
//			for(int i = 0 ; i < habitat.getHabitat().length ; i++)
//			{
//				System.out.println(Arrays.toString(habitat.getHabitat()[i].getVariables()) + "\t");
//			}

	}
	
	private void mutationAndReplace_3(int iter) {
		// TODO Auto-generated method stub

//			System.out.println("*****************Mutation*********************");
		// habitats = new Mutation(habitats.getHabitats(),
		// basicParameters).getMutatedHabitat();
		(new BBO_Mutation_3(habitats, bboParameters)).getMutatedHabitat(iter);
		
//			for(int i = 0 ; i < habitat.getHabitat().length ; i++)
//			{
//				System.out.println(Arrays.toString(habitat.getHabitat()[i].getVariables()) + "\t");
//			}

	}
	private void migrationBetweenHabitats() {
		// TODO Auto-generated method stub
//		System.out.println("*****************decide*********************");

		new Migration(habitats, bboParameters, immigrationRates, emigrationRates).getMigratedHabitats();
		
		
		
		/*
		 * //Random rand = new Random(basicParameters.getRandomSeed()); Random rand =
		 * new Random(0); //for(int i = 0 ; i < basicParameters.getPopulationSize() ;
		 * i++) for(int i = 0 ; i < bboParameters.getPopulationSize() ; i++) {
		 * if(rand.nextDouble() < immigrationRates.getImmigrationRates()[i]) { //Hi is
		 * selected //for(int j = 0 ; j < basicParameters.getProblemDimension() ; j++)
		 * //Select randomly an SIV from Hi to migrate //int selectedSIV =
		 * rand.nextInt((habitats.getHabitats().get(i).getSivs().size()) + 1);
		 * 
		 * for(int j = 0 ; j < habitats.getHabitats().get(i).getSivs().size() ; j++) {
		 * 
		 * double randomNum = rand.nextDouble() * emigrationRates.getSum(); if(randomNum
		 * < emigrationRates.getEmigrationRates()[j]) { //Select Hj the habitat that
		 * will receive the selected SIV int selectedHj =
		 * rand.nextInt((habitats.getHabitats().size()) + 1); while(selectedHj==i) {
		 * selectedHj = rand.nextInt((habitats.getHabitats().size()) + 1); }
		 * 
		 * //Select SIV from Hj int selectedSIV =
		 * rand.nextInt((habitats.getHabitats().get(selectedHj).getSivs().size()) + 1);
		 * habitats.getHabitats().get(i).immigrateSIV(habitats.getHabitats().get(
		 * selectedHj).getSivs().get(selectedSIV)); }
		 * 
		 * if(rand.nextDouble() < immigrationRates.getImmigrationRates()[i]) { double
		 * randomNum = rand.nextDouble() * emigrationRates.getSum(); double select =
		 * emigrationRates.getEmigrationRates()[0]; int selectIndex = 0; //while((
		 * randomNum > select ) && ( selectIndex < basicParameters.getPopulationSize() -
		 * 1 )) while(( randomNum > select ) && ( selectIndex <
		 * bboParameters.getPopulationSize() - 1 )) { selectIndex = selectIndex +1;
		 * select = select + emigrationRates.getEmigrationRates()[selectIndex]; }
		 * //Adapt this operation to the problem //z.getHabitat()[i].getSIVs()[j] =
		 * habitats.getHabitat()[selectIndex].getSIVs()[j]; } else {
		 * z.getHabitat()[i].getSIVs()[j] = habitats.getHabitat()[i].getSIVs()[j]; }
		 * 
		 * } } }
		 */
//		for(int i = 0 ; i < z.getHabitat().length ; i++)
//		{
//			System.out.println(Arrays.toString(z.getHabitat()[i].getVariables()) + "\t");
//		}

	}

	private void saveEliteSolutions() {
		// TODO Auto-generated method stub
		if (eliteSolutions != null) {
			eliteSolutions.saveBestCost(habitats.getHabitats());
		} else {
			eliteSolutions = new EliteSolutions(bboParameters.getNumberOfElites());
			eliteSolutions.saveBestCost(habitats.getHabitats());
		}

	}

	private void preprocessingBBO() {

		// TODO Auto-generated method stub
		initParameters();

		System.out.println("treatment");
		// BBO_Parameters bboParam = new BBO_Parameters();
		// Initiate parameters
		bboParameters.setPopulationSize(50); // get parameters from graphical interface
		
		bboParameters.setSpeciesProbabilties(new double [(int) (Math.ceil((bboParameters.getPopulationSize() + 1)/2))]);
		
		//Init Species probabilities
		bboParameters.initSpeciesProbabilties();

		// Initiate population
		initRandPopulation();

		// Evaluate fitness
		// TODO
		evaluateHSI();

		double avgCost = 0.0;
		for (int i = 0; i < habitats.getHabitats().size(); i++) {
			System.out.println("Habitat-" + (i + 1));
			System.out.println("Cost : " + habitats.getHabitats().get(i).getHSI());
			avgCost = avgCost + habitats.getHabitats().get(i).getHSI();
		}

		System.out.println("avgCost : " + avgCost / habitats.getHabitats().size());

		// TODO
		sortPopulation();

		// TODO : Elit
		saveBestHabitat();

		// System.out.println("Best cost : " + bestCost.getHSIFromBestCost());

		// TODO
		computeMigrationRates();
	}

	private void initParameters() {
		// TODO Auto-generated method stub
		bboParameters = new BBO_Parameters();
	}

	public void evaluateHSI() {
		for (int i = 0; i < habitats.getHabitats().size(); i++) {
			habitats.getHabitats().get(i).evaluateHSI();
		}
	}

	public void sortPopulation() {
		// habitats.sort(habitats.ComparatorHsi());
		Collections.sort(habitats.getHabitats(), Habitat.ComparatorHsi);
	}

	public void saveBestHabitat() {
		if (bestCost != null) {
			bestCost.saveBestCost(Habitat.compareHabitat(bestCost.getHabitat(), habitats.getHabitats().get(0)));
		} else {
			bestCost = new BestCost(habitats.getHabitats().get(0));
		}
	}

	public void computeMigrationRates() {
		computeEmigrationRates();
		computeImmigrationRate();
	}

	private void computeImmigrationRate() {
		// TODO Auto-generated method stub
		immigrationRates = new ImmigrationRates(emigrationRates);
		System.out.println("Emigration rates: " + Arrays.toString(emigrationRates.getEmigrationRates()));
		System.out.println("Immigration Rates: " + Arrays.toString(immigrationRates.getImmigrationRates()));
	}

	private void computeEmigrationRates() {
		// TODO Auto-generated method stub
		// emigrationRates = new EmigrationRates(basicParameters.getPopulationSize());
		emigrationRates = new EmigrationRates(bboParameters.getPopulationSize());
	}

	@Override
	public double getBestSol() {
		// TODO Auto-generated method stub
		return habitats.getHabitats().get(0).getHSI();
	}
	
	
	//TODO : create a tabu list to test different combination
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
					User user=null;
					if(pool1.getListOfUsers().size()>1)
					{
						user=Utilisateurs.getNearest(pool1.getListOfUsers(), pool2.getListOfUsers().get(0));
						if(user!=null)
						{
							pool2.addUser(user);
							pool1.removeUser(user);
						}
						
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
					if(habitat.getSivs().get(i) != pool && (habitat.getSivs().get(i).getListOfUsers().size() + pool.getListOfUsers().size())>=2)
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
								//&& (clonedPool.isUserAuthorized(swapedPool.getListOfUsers().get(j))
								//	&& swapedPool.isUserAuthorized(clonedPool.getListOfUsers().get(k)))
								)
								{
									User swapedUser = (User) clonedPool.getListOfUsers().get(k).clone();
									clonedPool.getListOfUsers().set(k, swapedPool.getListOfUsers().get(j));
									swapedPool.getListOfUsers().set(j, swapedUser);
									if(clonedPool.buildRoutes() && swapedPool.buildRoutes())
									{
										if(bestCost > (clonedPool.getAvgCost() + swapedPool.getAvgCost()) //&& 
											//Math.abs(bestCost - (clonedPool.getAvgCost() + swapedPool.getAvgCost())) > 10.0
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
							(pool.getListOfUsers().size() <= habitat.getSivs().get(i).getRestCarCap())
						  )
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
	
	////////////////// Reinsert further user /////////////////////////
	
	public boolean ReinsertFurther(Habitat habitat, Pool pool)
	{
		
		//ArrayList<Pool> tabooList = new ArrayList<Pool>();
		double bestCost = 0.0;
		
		
		
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
						bestCost = habitat.getSivs().get(k).getAvgCost() + pool.getAvgCost(); 
						habitat.getSivs().get(k).addUser(clonedPool.getFartherUser());
						if(habitat.getSivs().get(k).buildRoutes())
						{
							if(habitat.getSivs().get(k).getAvgCost()<bestCost)
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
				
				
		return improved;
		
	}	
	
	///////////////////////////////////////////////////////////////////
	
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
						for(int j=pool.getListOfUsers().size() - 1; j>=0 && !improved;j--)
						{
							clonedPool = (Pool) pool.clone();
							if(habitat.getSivs().get(k).areAllUsersAuthorized(clonedPool.getListOfUsers().get(j).getNumUser()))
							{
								bestCost = habitat.getSivs().get(k).getAvgCost() + pool.getAvgCost(); 
								habitat.getSivs().get(k).addUser(clonedPool.getListOfUsers().get(j));
								if(habitat.getSivs().get(k).buildRoutes())
								{
									clonedPool.getListOfUsers().remove(j);
									clonedPool.buildRoutes();
									if((clonedPool.getAvgCost() + habitat.getSivs().get(k).getAvgCost())<bestCost)
									{
										improved = true;
										//clonedPool.getListOfUsers().remove(j);
										clonedPool.buildRoutes();
									}
									else
									{
										//habitat.getSivs().get(k).removeUser(clonedPool.getListOfUsers().get(j));
										habitat.getSivs().get(k).getListOfUsers().remove(habitat.getSivs().get(k).getListOfUsers().size() - 1);
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
		if(candidatPools.get(iMerge).getRestCarCap() >= identicalPool.getListOfUsers().size() 
			&& identicalPool.getRestCarCap() >= candidatPools.get(iMerge).getListOfUsers().size())
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
				double bestCost = identicalPool.getAvgCost() + candidatPools.get(i).getAvgCost();
				for(int j=candidatPools.get(i).getListOfUsers().size() - 1; j>=0 && !improved;j--)
				{
					
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
				double bestCost = identicalPool.getAvgCost() + candidatPools.get(i).getAvgCost();
				for(int j=candidatPools.get(i).getListOfUsers().size() - 1; j>=0 && !improved;j--)
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
		
		
		return improved;
	}
	
	public void swapBestMaster()
	{
			
		Habitat habitat = habitats.getHabitats().get((new Random()).nextInt(habitats.getHabitats().size()));
		
		Pool pool = habitat.getSivs().get((new Random()).nextInt(habitat.getSivs().size()));
		int loops=0;
		while((pool.getListOfUsers().size() == 1)
			 && loops<5)
		{
			pool = habitat.getSivs().get((new Random()).nextInt(habitat.getSivs().size()));
			loops++;
		}
		
		if(pool.getListOfUsers().size()>1)
		{
			swapBest(habitat, pool);
		
		}
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
		double bestCost=0.0;
		
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
					bestCost = poolsList.get(i).getAvgCost() + habitat.getSivs().get(j).getAvgCost();
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
									if(poolsList.get(i).getAvgCost() + habitat.getSivs().get(j).getAvgCost() < bestCost)
									{
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
		double bestCost=0.0;
		
		if(poolsList.size()==0)
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
					bestCost = poolsList.get(i).getAvgCost() + habitat.getSivs().get(j).getAvgCost();
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
									if(poolsList.get(i).getAvgCost() + habitat.getSivs().get(j).getAvgCost() < bestCost)
									{
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
	
	public void vnsOperate()
	{
		ArrayList<Integer> tabooHabitatList = new ArrayList<Integer>();
		ArrayList<Pool> tabooPoolList = new ArrayList<Pool>();		
		int iter=  Math.max(1,(new Random()).nextInt(3));//1;
		int maxFails = 5; //1
		int nbFails = 0;
		int loops=0;
		int k=0;
		
		for(int m=0;m<iter;m++)
		{
			nbFails = 0;
			k=0;
			//k=(new Random()).nextInt(6);
			int habitatIndex = (new Random()).nextInt(habitats.getHabitats().size());
			while(tabooHabitatList.contains(habitatIndex))
			{
				habitatIndex = (new Random()).nextInt(habitats.getHabitats().size());
			}
			Habitat habitat = habitats.getHabitats().get(habitatIndex);
			tabooHabitatList.add(habitatIndex);
			while(k < 6)	
			{
				Pool pool;
				switch (k) {
				case 0:
					nbFails=0;
					tabooPoolList.clear();
					while(nbFails<maxFails)
					{
						pool = habitat.getSivs().get((new Random()).nextInt(habitat.getSivs().size()));
						loops=0;
						while((tabooPoolList.contains(pool) || Arrays.stream(Utilisateurs.getAuthorizedUsers()[pool.getListOfUsers().get(0).getNumUser()]).sum()==1)
							 && loops<5)
						{
							pool = habitat.getSivs().get((new Random()).nextInt(habitat.getSivs().size()));
							loops++;
						}
						nbFails++;
						if(!tabooPoolList.contains(pool) && Arrays.stream(Utilisateurs.getAuthorizedUsers()[pool.getListOfUsers().get(0).getNumUser()]).sum()>1)
						{
							if(swapBest(habitat, pool))
							{
								k=0;
								nbFails=0;
								tabooPoolList.remove(pool);
							}
							{
								tabooPoolList.add(pool);
							}
						}
					}
					k++;
					break;
				
				case 1:
					nbFails = 0;
					tabooPoolList.clear();
					while(nbFails<maxFails)
					{
						//Collections.sort(habitat.getSivs(), Pool.ComparatorDistG);
						//Collections.reverseOrder();
						pool = habitat.getSivs().get((new Random()).nextInt(habitat.getSivs().size()));
						//ArrayList<Pool> poolsList = new ArrayList<Pool>();
						
						loops=0;
						while((tabooPoolList.contains(pool) || pool.getListOfUsers().size()<1)
							 &&	loops<5)
						{
							//pool = habitat.getSivs().get((new Random()).nextInt(poolsList.size()));
							pool = habitat.getSivs().get((new Random()).nextInt(habitat.getSivs().size()));
							loops++;
						}
						nbFails++;
						if(pool.getListOfUsers().size() > 1 && !tabooPoolList.contains(pool))
						{
							if(move(habitat,pool))
							//if( ReinsertFurther(habitat, pool))
							{
								k=1;
								nbFails=0;
								tabooPoolList.remove(pool);
							}
							else
							{
								tabooPoolList.add(pool);
							}
						}
					}
					
					k++;
					//k=3;
					break;	
				
				case 2:
					
					nbFails=0;
					tabooPoolList.clear();
					while(nbFails<maxFails)
					{
						pool = habitat.getSivs().get((new Random()).nextInt(habitat.getSivs().size()));
						loops = 0;
						//if(habitat.getSivs().get(i).getRestCarCap() == 0)
						while((tabooPoolList.contains(pool) || pool.getRestCarCap()>0)
							 &&	loops<5)
						{
							pool = habitat.getSivs().get((new Random()).nextInt(habitat.getSivs().size()));
							loops++;
						}
						nbFails++;
						if(!tabooPoolList.contains(pool) && pool.getRestCarCap()==0)
						{
							if(divide(habitat,pool))
							{
								k=2;
								nbFails=0;
								tabooPoolList.remove(pool);
							}
							else
							{
								tabooPoolList.add(pool);
							}
						}
					}
					k++;
					break;
				
				
				case 3:
					
					nbFails = 0;
					tabooPoolList.clear();
					while(nbFails<maxFails)
					{
						pool = habitat.getSivs().get((new Random()).nextInt(habitat.getSivs().size()));
						loops = 0;
						while((pool.getRestCarCap() == 0 && tabooPoolList.contains(pool)) 
							//|| Arrays.stream(Utilisateurs.getAuthorizedUsers()[pool.getListOfUsers().get(0).getNumUser()]).sum()==1)
							&& loops<5)
						{
							pool = habitat.getSivs().get((new Random()).nextInt(habitat.getSivs().size()));
							loops++;
						}
						nbFails++;
						if(pool.getRestCarCap() > 0 && !tabooPoolList.contains(pool))
						{	
							if(merge(habitat,pool))
							{
								k=3;
								nbFails=0;
								tabooPoolList.remove(pool);
							}
							else
							{
								tabooPoolList.add(pool);
							}
						}
					}
					k++;
					break;
				
				case 4:
					
					nbFails = 0;
					tabooPoolList.clear();
					
					while(nbFails<maxFails)
					{
						Collections.sort(habitat.getSivs(), Pool.ComparatorSize);
						int counter = 0;
						ArrayList<Pool> listPoolPen = new ArrayList<Pool>();
						while(habitat.getSivs().get(counter).getListOfUsers().size()==1)
						{
							if(Arrays.stream(Utilisateurs.getAuthorizedUsers()[habitat.getSivs().get(counter).getListOfUsers().get(0).getNumUser()]).sum()>1)
							{
								listPoolPen.add(habitat.getSivs().get(counter));
							}
							counter++;
						}
						
						if(listPoolPen.size()==0)
						{
							k++;
							break;
						}
						
						Collections.sort(listPoolPen,Pool.ComparatorCost);
						Collections.reverseOrder();
						if((new Random()).nextDouble() > 0.001)
						{
							pool = listPoolPen.get((new Random()).nextInt(listPoolPen.size()));//habitat.getSivs().get((new Random()).nextInt(habitat.getSivs().size()));
						}
						else
						{
							pool = listPoolPen.get(0);
						}
						//loops = 0;
						//while((pool.getListOfUsers().size() > 1 || tabooPoolList.contains(pool) 
						//	|| Arrays.stream(Utilisateurs.getAuthorizedUsers()[pool.getListOfUsers().get(0).getNumUser()]).sum()==1)
						//	&& loops<5)
						//{
						//	//pool = habitat.getSivs().get((new Random()).nextInt(habitat.getSivs().size()));
						//	pool = listPoolPen.get((new Random()).nextInt(listPoolPen.size()));
						//	loops++;
						//}
						nbFails++;
						if(pool.getListOfUsers().size() == 1 && !tabooPoolList.contains(pool))
						{
							if(affectAlonePool_2(habitat,pool))
							{
								k=4;
								nbFails=0;
								tabooPoolList.remove(pool);
							}
							else
							{
								tabooPoolList.add(pool);
							}
						}
					}
					k++;
					break;	
					
				case 5:
					
					nbFails = 0;
					tabooPoolList.clear();
					
					while(nbFails<maxFails)
					{
						pool = habitat.getSivs().get((new Random()).nextInt(habitat.getSivs().size()));
						loops = 0;
						while((pool.getRestCarCap() == 0 && tabooPoolList.contains(pool))
							//|| Arrays.stream(Utilisateurs.getAuthorizedUsers()[p1.getListOfUsers().get(0).getNumUser()]).sum()==1)
							&& loops<5)
						{
							pool = habitat.getSivs().get((new Random()).nextInt(habitat.getSivs().size()));
							loops++;
						}
						nbFails++;
						if(pool.getRestCarCap() > 0 && !tabooPoolList.contains(pool))
						{
							if(moveAll(habitat,pool))
							{
								k=5;
								nbFails=0;
								tabooPoolList.remove(pool);
							}
							else
							{
								tabooPoolList.add(pool);
							}
						}
					}
					k++;
					break;
				
				case 6:
					
					nbFails = 0;
					
					while(nbFails<maxFails)
					{
						nbFails++;
						if(brokeAlone(habitat))
						{
							k=6;
							nbFails=0;
						}
					}
					
					k++;
					break;
					
				default:
					break;
				}
				
				//nbFails++;
				//if(move(habitat))
				//{
				//	nbFails=0;
				//}
			}
		}
		//moveAll();
	}

	

}
