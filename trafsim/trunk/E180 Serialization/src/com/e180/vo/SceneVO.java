package com.e180.vo;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SceneVO implements IJsonSerializable {
	private String id;
	private Collection<NodeVO> nodes;
	private Collection<RoadVO> roads;
	private Collection<SourceVO> sources;
	private Collection<SinkVO> sinks;
	private Collection<CarVO> cars;
	
	public SceneVO(){
		nodes = new ArrayList<NodeVO>();
		roads = new ArrayList<RoadVO>();
		sources = new ArrayList<SourceVO>();
		sinks = new ArrayList<SinkVO>();
		cars = new ArrayList<CarVO>();
	}
	

	public String Serialize() {
		JSONObject currentScene = new JSONObject();
		//attempt to serialize
		try {
			if(nodes.isEmpty()) {
				return null;
			}
			
			currentScene.put("nodes", this.nodes);
			currentScene.put("roads", this.roads);
			currentScene.put("sources", this.sources);
			currentScene.put("sinks", this.sinks);
			currentScene.put("cars", this.cars);
			
			
		} catch( JSONException e ) {
			e.printStackTrace();
			return null;
		}
		return currentScene.toString();
	}
	public Object unSerialize(String data) {
		JSONObject currentScene;
		try{
			currentScene = new JSONObject(data);
			JSONArray jNodes = currentScene.getJSONArray("nodes");
			JSONArray jRoads = currentScene.getJSONArray("roads");
			JSONArray jSources = currentScene.getJSONArray("sources");
			JSONArray jSinks = currentScene.getJSONArray("sinks");
			JSONArray jCars = currentScene.getJSONArray("cars");
			
			Integer i = 0;
			
			for (i=0; i<jNodes.length(); i++) {
				JSONObject jNode = jNodes.getJSONObject(i);
				NodeVO oNode = new NodeVO();
				oNode.unSerialize(jNode.toString());
			}
			
			for (i=0; i<jRoads.length(); i++) {
				JSONObject jRoad = jRoads.getJSONObject(i);
				RoadVO oRoad = new RoadVO();
				oRoad.unSerialize(jRoad.toString());
			}
			
			for (i=0; i<jSources.length(); i++) {
				JSONObject jSource = jSources.getJSONObject(i);
				SourceVO oSource = new SourceVO();
				oSource.unSerialize(jSource.toString());
			}
			
			for (i=0; i<jSinks.length(); i++) {
				JSONObject jSink = jSinks.getJSONObject(i);
				SinkVO oSink = new SinkVO();
				oSink.unSerialize(jSink.toString());
			}
			
			for (i=0; i<jCars.length(); i++) {
				JSONObject jCar = jCars.getJSONObject(i);
				CarVO oCar = new CarVO();
				oCar.unSerialize(jCar.toString());
			}
			
		} catch( JSONException e ){
			e.printStackTrace();
		}
		return null;
	}
	
	public Collection<CarVO> getCars() {
		return cars;
	}

	public void setCars(Collection<CarVO> cars) {
		this.cars = cars;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Collection<NodeVO> getNodes() {
		return nodes;
	}

	public void setNodes(Collection<NodeVO> nodes) {
		this.nodes = nodes;
	}

	public Collection<RoadVO> getRoads() {
		return roads;
	}

	public void setRoads(Collection<RoadVO> roads) {
		this.roads = roads;
	}

	public Collection<SinkVO> getSinks() {
		return sinks;
	}

	public void setSinks(Collection<SinkVO> sinks) {
		this.sinks = sinks;
	}

	public Collection<SourceVO> getSources() {
		return sources;
	}

	public void setSources(Collection<SourceVO> sources) {
		this.sources = sources;
	}
	
	
}
