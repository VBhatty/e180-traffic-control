package com.e180.vo;

import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONString;
import org.json.JSONException;

public class RoadVO implements IJsonSerializable, JSONString{
	
	private String id;
	private String fromNodeId;
	private String toNodeId;
	private double speedLimit;
	private ArrayList<Double> avgSpeedvTime;
	private ArrayList<Double> totalCarsvTime;
	
	public String Serialize() {
		JSONObject currentNode = new JSONObject();
		//attempt to serialize
		try{
			currentNode.put("id", this.id );
			currentNode.put( "fromNodeId", this.fromNodeId);
			currentNode.put( "toNodeId", this.toNodeId);
			currentNode.put("speedLimit", this.speedLimit);
		} catch( JSONException e ) {
			e.printStackTrace();
			return null;
		}
		return currentNode.toString();
	}
	
	public String toJSONString() {
		return this.Serialize();
	}
	
	public Object unSerialize(String data) {
		JSONObject currentNode;
		try{
			currentNode = new JSONObject(data);
			this.fromNodeId = currentNode.getString("fromNodeId");
			this.toNodeId = currentNode.getString("toNodeId");
			this.speedLimit = currentNode.getDouble("speedLimit");
			this.id = currentNode.getString("id");
		} catch( JSONException e ){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String getFromNodeId() {
		return fromNodeId;
	}
	public void setFromNodeId(String fromNodeId) {
		this.fromNodeId = fromNodeId;
	}
	public double getSpeedLimit() {
		return speedLimit;
	}
	public void setSpeedLimit(double speedLimit) {
		this.speedLimit = speedLimit;
	}
	public String getToNodeId() {
		return toNodeId;
	}
	public void setToNodeId(String toNodeId) {
		this.toNodeId = toNodeId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<Double> getAvgSpeedvTime() {
		return avgSpeedvTime;
	}

	public void setAvgSpeedvTime(ArrayList<Double> avgSpeedvTime) {
		this.avgSpeedvTime = avgSpeedvTime;
	}

	public ArrayList<Double> getTotalCarsvTime() {
		return totalCarsvTime;
	}

	public void setTotalCarsvTime(ArrayList<Double> totalCarsvTime) {
		this.totalCarsvTime = totalCarsvTime;
	}
	
}
