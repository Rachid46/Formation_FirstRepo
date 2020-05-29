package Model;

import GGA.GA_Optimization;

public class GA_OptimizationFactory extends OptimizationFactory{

	@Override
	protected Optimization createOptimization()
	{
		return new GA_Optimization();
	};
}
