package com.e180.vo;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

public class SinkVO implements IJsonSerializable, JSONString{
	private String id;
	private String nodeId;
	//array of stats
	private Collection<Integer> carsVTime;
	
	public String Serialize() {
		JSONObject currentSink = new JSONObject();
		//attempt to serialize
		try{
			currentSink.put("id", this.id);
			currentSink.put("nodeId", this.nodeId);
		} catch( JSONException e ) {
			e.printStackTrace();
			return null;
		}
		return currentSink.toString();
	}
	
	public String toJSONString() {
		return this.Serialize();
	} 
	
	public Object unSerialize(String data) {
		JSONObject currentSink;
		try{
			currentSink = new JSONObject(data);
			this.id = currentSink.getString("id");
			this.nodeId = currentSink.getString("nodeId");
		} catch( JSONException e ){
			e.printStackTrace();
		}
		return null;
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

	public Collection<Integer> getCarsVTime() {
		return carsVTime;
	}

	public void setCarsVTime(ArrayList<Integer> name) {
		this.carsVTime = name;
	}

	
	
}
