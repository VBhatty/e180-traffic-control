package com.e180.vo;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

public class SourceVO implements IJsonSerializable, JSONString {
	private double frequency;
	private String id;
	private String nodeId;
	private ArrayList<Integer> carsCreatedvTime;
	public SourceVO(){
		this.frequency = 0;
		this.id = "";
		this.nodeId = "";
	}
	
	public String Serialize(){
		JSONObject currentSource = new JSONObject();
		//attempt to serialize
		try{
			currentSource.put("frequency", this.frequency );
			currentSource.put("id", this.id);
			currentSource.put("nodeId", this.nodeId);
		} catch( JSONException e ) {
			e.printStackTrace();
			return null;
		}
		return currentSource.toString();
	}
	
	public String toJSONString() {
		return this.Serialize();
	}
	

	public Object unSerialize(String data) {
		JSONObject currentSource;
		try{
			currentSource = new JSONObject(data);
			this.frequency = currentSource.getDouble("frequency");
			this.id = currentSource.getString("id");
			this.nodeId = currentSource.getString("nodeId");
		} catch( JSONException e ){
			e.printStackTrace();
		}
		return null;
	}

	public double getFrequency() {
		return frequency;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public ArrayList<Integer> getCarsCreatedvTime() {
		return carsCreatedvTime;
	}

	public void setCarsCreatedvTime(ArrayList<Integer> carsCreatedvTime) {
		this.carsCreatedvTime = carsCreatedvTime;
	}

	
	
	
	
}
