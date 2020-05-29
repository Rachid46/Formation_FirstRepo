package Model;

public abstract class OptimizationFactory {
	
	public Optimization getOptimization() {
	    return createOptimization();
	  }

	protected abstract Optimization createOptimization();

}
