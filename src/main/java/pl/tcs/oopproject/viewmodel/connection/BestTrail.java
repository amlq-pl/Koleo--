package pl.tcs.oopproject.viewmodel.findconnection;

import java.util.List;

public class BestTrail implements FindConnectionInterface {
	
		String townA;
		String townB;
		List<ConnectionAbstract> routes; //all routes
		
		public BestTrail(String townA, String townB) {
			this.townA = townA;
			this.townB = townB;
			//IMPLEMENT HERE HOW TO FIND ROUTES
		}
	
	@Override
	public List<ConnectionAbstract> getRoutes() {
		return null;
	}
	
	@Override
	public List<ConnectionAbstract> getCheapRoutes() {
		return null;
	}
	
	@Override
	public List<ConnectionWithTransfers> getRoutesWithoutTransfers() {
		return null;
	}
	
}
