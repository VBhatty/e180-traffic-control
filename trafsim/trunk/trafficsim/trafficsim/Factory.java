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

	public RoadVO roadToRoadVO(Road road) {
		RoadVO newRoad = new RoadVO();
		
		newRoad.setId(road.getID());
		newRoad.setFromNodeId(road.getStartNode().getID());
		newRoad.setToNodeId(road.getEndNode().getID());
		newRoad.setSpeedLimit(road.getSpeedLimit());
		
		return newRoad;
	}
	
	public SinkVO sinkToSinkVO(Sink sink) {
		SinkVO newSink = new SinkVO();
		
		newSink.setId(sink.getID());
		newSink.setNodeId(sink.getNodeID());
		
		return newSink;
	}
	
	public SourceVO sourceToSouceVO(Source source) {
		SourceVO newSource = new SourceVO();
		
		newSource.setId(source.getID());
		newSource.setNodeId(source.getSourceID());
		//newSource.setFrequency(source.getFrequency());
		
		return newSource;
	}
	
}

