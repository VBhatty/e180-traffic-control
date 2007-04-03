package com.e180.vo;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

public class TrafficObjectVO implements IJsonSerializable, JSONString{
	
	private String id;
	private String type;
	private Double speedLimit;
	private String nodeId;
	
	public String Serialize() {
		JSONObject currentObj = new JSONObject();
		//attempt to serialize
		try{
			currentObj.put("id", this.id );
			currentObj.put( "type", this.type);
			currentObj.put( "nodeId", this.nodeId);
			currentObj.put( "speedLimit", this.speedLimit);
		} catch( JSONException e ) {
			e.printStackTrace();
			return null;
		}
		return currentObj.toString();
	}
	
	public String toJSONString() {
		return this.Serialize();
	}
	
	// desieralized the object
	public Object unSerialize(String data) {
		JSONObject currentObj;
		try{
			currentObj = new JSONObject(data);
			this.type = currentObj.getString("type");
			this.nodeId = currentObj.getString("nodeId");
			this.speedLimit = currentObj.getDouble("speedLimit");
			this.id = currentObj.getString("id");
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

	public Double getSpeedLimit() {
		return speedLimit;
	}

	public void setSpeedLimit(Double speedLimit) {
		this.speedLimit = speedLimit;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
}
