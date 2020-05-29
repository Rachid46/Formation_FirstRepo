package Model;

import java.io.BufferedReader;
import java.io.FileReader;

//import BBO.BBO_Optimization;

public class CarPool {
	
	//protected String source; 
	protected static User workplace;
	protected static Users users;
	
	//String[][] tableau = new String[100][20];//String[nbLignes][nbColonnes];//le tableau où stocker tes résultats. Tu peux aussi utiliser un ArrayList

	
	public static User getWorkplace() {
		return workplace;
	}

	public static void setWorkplace(User workplace) {
		CarPool.workplace = workplace;
	}

	public static Users getUsers() {
		return users;
	}

	public static void setUsers(Users users) {
		CarPool.users = users;
	}

	public void lecture(String source) {
		  //Users users = new Users();
		users = new Users();
				  
	    try {
	      String ligne ;
	      
	      BufferedReader fichier = new BufferedReader(new FileReader(source));
	      
	      int k=0;
	      while ((ligne = fichier.readLine()) != null) {
	          System.out.println(ligne);
			  String[] ligneTableau = ligne.split("¡¡¡¡");//transforme par exemple "aaa¡¡¡¡bbb¡¡¡¡ccc" en {"aaa","bbb","ccc"}
			  User user = new User();
			  k++;
			  
			  user.setNumUser(Integer.parseInt(ligneTableau[0]));
			  user.setX(Double.parseDouble((ligneTableau[1])));
			  user.setY(Double.parseDouble((ligneTableau[2])));
				  /*
				   private int carCapacity;
		 		   private double maxTravelTime;
				   private int penalty;
				   private double earliestLeaveTime;
				   private double latestArrivalTime;
				    */
				   
			  user.setCarCapacity(Integer.parseInt(ligneTableau[3]));
			  //user.setMaxTravelTime(Double.parseDouble(ligneTableau[4]));
			  String[] hm_tableau_mtt = ligneTableau[4].split(":");
			  user.setMaxTravelTime(Integer.parseInt(hm_tableau_mtt[0])*60 +  Integer.parseInt(hm_tableau_mtt[1]));
				  
			  user.setPenalty(Integer.parseInt(ligneTableau[5]));
				  
			  String[] hm_tableau_e = ligneTableau[6].split(":");
			  user.setEarliestLeaveTime(Integer.parseInt(hm_tableau_e[0])*60 +  Integer.parseInt(hm_tableau_e[1]));
				  
			  String[] hm_tableau_l = ligneTableau[7].split(":");
			  user.setLatestArrivalTime(Integer.parseInt(hm_tableau_l[0])*60 +  Integer.parseInt(hm_tableau_l[1]));
	  		  //RKA : Replace by the object userList
		      //tableau[i] = ligneTableau;
			  //users.add(ligneTableau);
			 
			 
			  if(k==1) 
			  {
				  //workplace
				  workplace = user;
			  }
			  else
			  {
				  //user.setMaxTravelTime((int) Math.round(Utilisateurs.computeTravelTime(user, workplace)) + (int) Math.round(user.getMaxTravelTime()));
				  users.addUser(user);
			  }
			  
			  
	      }

	      fichier.close();
	      //Affichier les utilisateurs
	      
	      for(int i=0;i<users.getSize();i++)
	      {
	    	  System.out.println("numUser = " + users.getIndexOfUsers(i).getNumUser());
	    	  System.out.println("X = " + users.getIndexOfUsers(i).getX());
	    	  System.out.println("Y = " + users.getIndexOfUsers(i).getY());
	    	  System.out.println("Max Travel Time = " + users.getIndexOfUsers(i).getMaxTravelTime());
	    	  System.out.println("Car capacity = " + users.getIndexOfUsers(i).getCarCapacity());
	    	  System.out.println("Earliest Leave Time = " + users.getIndexOfUsers(i).getEarliestLeaveTime());
	    	  System.out.println("Latest arrival time = " + users.getIndexOfUsers(i).getLatestArrivalTime());
	    	  
	      }
	      
	      
	    } catch (Exception e) {
	      e.printStackTrace();
	    }     
	  }    
	
	public void initOpt()
	{
		//users.distances();
		//users.computeTravelTime();
		Utilisateurs.distances(users.getUsers(),workplace);
		Utilisateurs.computeTravelTime(users.getUsers());
		for(int i=0;i<users.getSize();i++)
		{
			users.getIndexOfUsers(i).setDistToWP(users.getIndexOfUsers(i).computeDistWP(workplace));
			users.getIndexOfUsers(i).setTimToWP(users.getIndexOfUsers(i).computeTravelTimWP(workplace));
		}
		
		for(int i= 0;i<users.getUsers().size();i++)
		{
			users.getUsers().get(i).setMaxTravelTime((int) (Math.round(users.getUsers().get(i).getTimToWP()*1.5)));
			users.getUsers().get(i).setPenalty((int) (Math.round(users.getUsers().get(i).getDistToWP()*2)));
			users.getUsers().get(i).updateEarliestLeaveTime();
		}
		
		//Compute the authorized and not authorized couple of users
		Utilisateurs.computeAuthorizedUsers(users.getUsers());
		
		for(int i=0;i<Utilisateurs.getAuthorizedUsers()[0].length;i++)
		{
			System.out.println("Authorized tableau[" + i + "] :" + Utilisateurs.getAuthorizedUsers()[i]);
		}
		
	}
	
	public static void buildNearestOppositePool(Pool pool, Pool oppPool)
	{
		User user = new User();
		User oppUser = null;
		
		user.setX(workplace.getX() + (workplace.getX() - pool.getGravityCentreX()));
		user.setX(workplace.getY() + (workplace.getY() - pool.getGravityCentreY()));
		user.setNumUser(-1);
		
		oppUser = Users.getNearestInList(user, users.getUsers());
		
		oppPool.addUser(oppUser);
		
		Users clonedUsers = (Users) CarPool.users.clone();
		clonedUsers.removeUser(oppUser.getNumUser());
		//Construct the opposite Pool
		for(int i=0;i<3;i++)
		{
			user = Users.getNearestInList(oppUser, clonedUsers.getUsers());
			oppPool.addUser(user);
			if(!oppPool.buildRoutes())
			{
				oppPool.removeUser(user);
			}
			clonedUsers.getUsers().remove(user.getNumUser());
		}
	}

}
