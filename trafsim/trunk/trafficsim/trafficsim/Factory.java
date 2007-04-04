package trafficsim;

import java.util.Iterator;
import java.util.Set;

import com.e180.vo.*;

public class Factory {
	
	public CarVO carToCarVO(Vehicle car){
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
	private TrafficObjectVO trafficObjToTrafficObjVO(trafficController controller) {
		TrafficObjectVO newTO = new TrafficObjectVO();
		
		newTO.setId(controller.getID());
		newTO.setNodeId(controller.getID());
		
		return newTO;
	}
	public SourceVO sourceToSouceVO(Source source) {
		SourceVO newSource = new SourceVO();
		
		newSource.setId(source.getID());
		newSource.setNodeId(source.getID());
		//newSource.setFrequency(source.getFrequency());
		
		return newSource;
	}

	public void setVehicles(Set vehicles,SceneVO myScene) {
		Iterator veh = vehicles.iterator();
		//iterate through and add each vehicle to the scene
		while (veh.hasNext()){
			Vehicle v = (Vehicle)veh.next();
			myScene.addCar(this.carToCarVO(v));
		}
		
	}

	public void setNodes(Set nodes, SceneVO sceneOut) {
		Iterator veh = nodes.iterator();
		//iterate through and add each vehicle to the scene
		while (veh.hasNext()){
			Node v = (Node)veh.next();
			if (v.isSink()){
				sceneOut.addSink(this.sinkToSinkVO((Sink)v));
			}
			if (v.isSource()){
				sceneOut.addSource(this.sourceToSouceVO((Source)v));
			}
			if (v.isTrafCont()){
				sceneOut.addTrafObj(this.trafficObjToTrafficObjVO((trafficController)v));
			}
		}
		Iterator veh1 = nodes.iterator();
		while (veh1.hasNext()){
			Node v = (Node)veh1.next();
			sceneOut.addNode(this.nodeToNodeVO(v));
		}
	}



	public void setRoads(Set Roads, SceneVO sceneOut) {
		Iterator veh = Roads.iterator();
		//iterate through and add each vehicle to the scene
		while (veh.hasNext()){
			Road v = (Road)veh.next();
			sceneOut.addRoad(this.roadToRoadVO(v));
		}
		
	}
	
}

