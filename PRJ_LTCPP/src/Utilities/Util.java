package Utilities;

import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Collections;

public class Util {
	
	public static ArrayList<ArrayList<Integer>> arrRet;
	
	public static int factorial(int fact)
	{
		int res=1;
		
		for(int i=1 ;i<=fact;i++)
		{
			res = res * i;
		}
		
		return res;
		//return fact;
	}
	
	/*
	 * public static void permute(ArrayList<Integer> arr, int k){ for(int i = k; i <
	 * arr.size(); i++){ Collections.swap(arr, i, k); permute(arr, k+1);
	 * Collections.swap(arr, k, i); }
	 */
        /*
        if (k == arr.size() -1){
            System.out.println(Java.util.Arrays.toString(arr.toArray()));
        }
       
    }
     */
	
	/*
	public static void main(String[] args){
        Permute.permute(Java.util.Arrays.asList(3,4,6,2,1), 0);
    }
    */
	

	public static void permute(ArrayList<Integer> arr, int k){
        
        for(int i = k; i < arr.size(); i++)
		{
			Collections.swap(arr, i, k);
			permute(arr, k+1);
			Collections.swap(arr, k, i);
           
        }
		
        
        if (k == arr.size() -1){
        	ArrayList<Integer> arr1 = new ArrayList<Integer>();
        	for(int l=0;l<arr.size();l++)
        	{
        		arr1.add(arr.get(l));
        	}
        	arrRet.add(arr1);
        }
		
        
    }

}
