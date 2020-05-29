package Model;

import BBO.BBO_Optimization;

public class BBO_OptimizationFactory extends OptimizationFactory{
	
	@Override
	protected Optimization createOptimization()
	{
		return new BBO_Optimization();
	};

}
