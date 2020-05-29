package Model;

import java.util.ArrayList;
import java.util.Comparator;

public class PoolUtilities {
	
	private ArrayList<Pool> listOfPools;
	private static Pool pool;

	/**
	 * @return the listOfPools
	 */
	public ArrayList<Pool> getListOfPools() {
		return listOfPools;
	}

	/**
	 * @param listOfPools the listOfPools to set
	 */
	public void setListOfPools(ArrayList<Pool> listOfPools) {
		this.listOfPools = listOfPools;
	}

	/**
	 * @return the pool
	 */
	public Pool getPool() {
		return pool;
	}

	/**
	 * @param pool the pool to set
	 */
	public static void setPool(Pool pooli) {
		pool = pooli;
	}
	
	public static Comparator<Pool> ComparatorGC = new Comparator<Pool>() {
	     
        @Override
        public int compare(Pool pool1, Pool pool2) {
            //return (int) Math.round((uc1.getRegret() - uc2.getRegret()));
        	//+ 1/((pool1.getMinMaxTravelTime() - pool1.getMinShortTime())/60.0)
        	//+ 0.05* 1/(Math.abs(pool1.getMinMaxTravelTime() - pool1.getMinShortTime())/60.0)
        	//+ 1/pool1.getMinMaxTravelTime()
        	//+ 1/pool2.getMinMaxTravelTime()
        	if (0.98 * Math.sqrt(Math.pow(pool1.getGravityCentreX() - pool.getGravityCentreX(),2) + Math.pow(pool1.getGravityCentreY() - pool.getGravityCentreY(),2)) 
        				+ 0.02 * (Math.abs(pool1.getMinEarliestLeaveTime() - pool.getMinEarliestLeaveTime())/60.0) < 0.98*Math.sqrt(Math.pow(pool2.getGravityCentreX() - pool.getGravityCentreX(),2) + Math.pow(pool2.getGravityCentreY() - pool.getGravityCentreY(),2))
        				+ 0.02 * (Math.abs(pool2.getMinEarliestLeaveTime() - pool.getMinEarliestLeaveTime())/60.0 )
        				)
        		//pool2.getGravityCentreDist())
        		return -1;
        	else if (0.98 * Math.sqrt(Math.pow(pool1.getGravityCentreX() - pool.getGravityCentreX(),2) + Math.pow(pool1.getGravityCentreY() - pool.getGravityCentreY(),2)) 
    				+ 0.02 * (Math.abs(pool1.getMinEarliestLeaveTime() - pool.getMinEarliestLeaveTime())/60.0) > 0.98*Math.sqrt(Math.pow(pool2.getGravityCentreX() - pool.getGravityCentreX(),2) + Math.pow(pool2.getGravityCentreY() - pool.getGravityCentreY(),2))
    				+ 0.02 * (Math.abs(pool2.getMinEarliestLeaveTime() - pool.getMinEarliestLeaveTime())/60.0 )
    				)
        		return 1;
           	else
        		return 0;
        	
        }
        
    };
    
    public static Comparator<Pool> ComparatorUserGC = new Comparator<Pool>() {
	     
        @Override
        public int compare(Pool pool1, Pool pool2) {
            //return (int) Math.round((uc1.getRegret() - uc2.getRegret()));
        	//+ 1/((pool1.getMinMaxTravelTime() - pool1.getMinShortTime())/60.0)
        	//+ 0.05* 1/(Math.abs(pool1.getMinMaxTravelTime() - pool1.getMinShortTime())/60.0)
        	//+ 1/pool1.getMinMaxTravelTime()
        	//+ 1/pool2.getMinMaxTravelTime()
        	if (Math.sqrt(Math.pow(pool1.getGravityCentreX() - pool.getFartherUser().getX(),2) + Math.pow(pool1.getGravityCentreY() - pool.getFartherUser().getY(),2)) 
        			< Math.sqrt(Math.pow(pool2.getGravityCentreX() -  pool.getFartherUser().getX(),2) + Math.pow(pool2.getGravityCentreY() -  pool.getFartherUser().getY(),2))
        		)
        		//pool2.getGravityCentreDist())
        		return -1;
        	else if (Math.sqrt(Math.pow(pool1.getGravityCentreX() - pool.getFartherUser().getX(),2) + Math.pow(pool1.getGravityCentreY() - pool.getFartherUser().getY(),2)) 
        			> Math.sqrt(Math.pow(pool2.getGravityCentreX() -  pool.getFartherUser().getX(),2) + Math.pow(pool2.getGravityCentreY() -  pool.getFartherUser().getY(),2))
            		)
        		return 1;
           	else
        		return 0;
        	
        }
        
    };

}
