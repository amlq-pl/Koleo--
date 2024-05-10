package pl.tcs.oopproject.viewmodel.findconnection;

import java.util.List;

public class BestTrail implements FindConnection {
	
		String townA;
		String townB;
		List<Connection> routes; //all routes
		
		public BestTrail(String townA, String townB) {
			this.townA = townA;
			this.townB = townB;
			//IMPLEMENT HERE HOW TO FIND ROUTES
		}
	
	@Override
	public List<Connection> getRoutes() {
		return null;
	}
	
	@Override
	public List<Connection> getCheapRoutes() {
		return null;
	}
	
	@Override
	public List<Connection> getRoutesWithoutTranfers() {
		return null;
	}
	
}
