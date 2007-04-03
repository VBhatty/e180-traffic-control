package com.e180.vo;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class CarVO implements IJsonSerializable {
	private String id;
	private Collection<Double> percentageAlongRoads;
	private Collection<String> roadIds;
	private String sourceNodeId;
	private String destNodeId;
	//time step at which the car is spawned
	private Integer spawnTime;
	private double length;
	private double weight;
	
	public CarVO(){
		this.id = "";
		this.percentageAlongRoads = new ArrayList<Double>();
		this.roadIds = new ArrayList<String>();
		this.sourceNodeId = "";
		this.destNodeId = "";
		this.spawnTime = 0;
		this.length = 0;
		this.weight = 0;
	}
	
	public String Serialize() {
		JSONObject currentCar = new JSONObject();
		//attempt to serialize
		try {
			currentCar.put("id", this.id );
	
			
			currentCar.put("percentageAlongRoads", this.percentageAlongRoads);
			currentCar.put("roadIds", this.roadIds);
			currentCar.put("sourceNodeId", this.sourceNodeId);
			currentCar.put("destNodeId", this.destNodeId);
			currentCar.put("spawnTime", this.spawnTime);
			currentCar.put("length", this.length);
			currentCar.put("weight", this.weight);
		} catch( JSONException e ) {
			e.printStackTrace();
			return null;
		}
		return currentCar.toString();
	}


	public Object unSerialize(String data) {
		JSONObject currentCar;
		try{
			currentCar = new JSONObject(data);
			this.id = currentCar.getString("id");
			this.sourceNodeId = currentCar.getString("sourceNodeId");
			this.destNodeId = currentCar.getString("destNodeId");
			this.spawnTime = currentCar.getInt("spawnTime");
			this.length = currentCar.getDouble("length");
			this.weight = currentCar.getDouble("weight");
			//extra steps to deserialize the collection
			this.percentageAlongRoads = new ArrayList<Double>();
			this.roadIds = new ArrayList<String>();
			
			if( currentCar.has("percentageAlongRoads") ) {
				JSONArray perc = currentCar.getJSONArray("percentageAlongRoads");
				for (int i = 0; i < perc.length(); i++) {
					this.percentageAlongRoads.add( perc.getDouble(i) );
				}
			} 
			
			if( currentCar.has("roadIds") ) {
				JSONArray perc = currentCar.getJSONArray("roadIds");
				for (int i = 0; i < perc.length(); i++) {
					this.roadIds.add( perc.getString(i) );
				}
			} 
			
		} catch( JSONException e ){
			e.printStackTrace();
		}
		return null;
	}
	
	


	// getters and setters


	public String getDestNodeId() {
		return destNodeId;
	}


	public void setDestNodeId(String destNodeId) {
		this.destNodeId = destNodeId;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public double getLength() {
		return length;
	}


	public void setLength(double length) {
		this.length = length;
	}


	public String getSourceNodeId() {
		return sourceNodeId;
	}


	public void setSourceNodeId(String sourceNodeId) {
		this.sourceNodeId = sourceNodeId;
	}


	public Integer getSpawnTime() {
		return spawnTime;
	}


	public void setSpawnTime(Integer spawnTime) {
		this.spawnTime = spawnTime;
	}


	public double getWeight() {
		return weight;
	}


	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Collection<Double> getPercentageAlongRoads() {
		return percentageAlongRoads;
	}

	public void setPercentageAlongRoads(Collection<Double> percentageAlongRoads) {
		this.percentageAlongRoads = percentageAlongRoads;
	}

	public Collection<String> getRoadIds() {
		return roadIds;
	}

	public void setRoadIds(Collection<String> roadIds) {
		this.roadIds = roadIds;
	}
}
