package Model;

import java.io.BufferedReader;
import java.io.FileReader;

public class BMBufferedReader {
	protected String source;
	  String[][] tableau = new String[101][20];//String[nbLignes][nbColonnes];//le tableau où stocker tes résultats. Tu peux aussi utiliser un ArrayList
	
	public BMBufferedReader(String source) {
	    this.source = source;
	    lecture();
	  }
  

	  private void lecture() {
		  Users users = new Users();
		  
	    try {
	      String ligne ;
	      
	      BufferedReader fichier = new BufferedReader(new FileReader(source));
	      
	      while ((ligne = fichier.readLine()) != null) {
	          System.out.println(ligne);
			  String[] ligneTableau = ligne.split("¡¡¡¡");//transforme par exemple "aaa¡¡¡¡bbb¡¡¡¡ccc" en {"aaa","bbb","ccc"}
			  User user = new User();
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
			  users.addUser(user);
			  
			  
			  
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
	      
	      InitPopulation initPop = new InitPopulation(users);
	      
	      initPop.randPop();
	      
	    } catch (Exception e) {
	      e.printStackTrace();
	    }     
	  }    

}
