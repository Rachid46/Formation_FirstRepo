package Model;


public abstract class Optimization {
	
	protected Users users;
	protected User workplace;
	
	public void setUsers(Users users)
	{
		this.users = users; 
	}
	
	public User getWorkplace() {
		return workplace;
	}

	public void setWorkplace(User workplace) {
		this.workplace = workplace;
	}

	public Users getUsers()
	{
		return users;
	}
	
	public abstract void treatment();
	
	public abstract void initRandPopulation();
	
	public abstract double getBestSol();
	
}
