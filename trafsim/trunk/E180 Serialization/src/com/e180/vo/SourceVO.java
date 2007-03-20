package com.e180.vo;

import org.json.JSONException;
import org.json.JSONObject;

public class SourceVO implements IJsonSerializable {
	private double frequency;
	private String id;
	private String nodeId;
	
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
	
	
	
	
}