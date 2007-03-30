package trafficsim;

import com.e180.vo.*;

public class Factory {
	
	public CarVO carToCarVO(Car car){
		CarVO newCar = new CarVO();
		newCar.setId(car.getID());
		newCar.setDestNodeId(car.getDestination().getID());
		newCar.setLength(car.getLength());
		newCar.setSourceNodeId(car.getSource().getID());
		newCar.setSpawnTime(car.getSpawnTime());
		newCar.setWeight(car.getWeight());
		newCar.setPercentageAlongRoads(car.getPercentages());
		newCar.setRoadIds(car.getRoads());
		
		return newCar;
	}
	
	public NodeVO nodeToNodeVO(Node node) {
		NodeVO newNode = new NodeVO();
		
		newNode.setId(node.getID());
		newNode.setX(node.getX());
		newNode.setY(node.getY());
		
		return newNode;
	}

}

