package com.e180.vo;

import org.json.JSONException;
import org.json.JSONObject;

public class SinkVO implements IJsonSerializable {
	private String id;
	private String nodeId;

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
	
}
