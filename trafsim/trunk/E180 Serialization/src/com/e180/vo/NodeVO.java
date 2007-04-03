package com.e180.vo;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONString;

public class NodeVO implements IJsonSerializable, JSONString {
	
	
	// unique identifier of the node
	private String id;
	
	// coordinate of node
	private double x;
	private double y;
	
	// serialize and deserialie methods
	public String Serialize() {
		JSONObject currentNode = new JSONObject();
		//attempt to serialize
		try{
			currentNode.put("id", this.id );
			currentNode.put( "x", this.x);
			currentNode.put( "y", this.y);
		} catch( JSONException e ) {
			e.printStackTrace();
			return null;
		}
		return currentNode.toString();
	}
	
	public String toJSONString() {
		return this.Serialize();
	}
	
	// desieralized the object
	public Object unSerialize(String data) {
		JSONObject currentNode;
		try{
			currentNode = new JSONObject(data);
			this.x = currentNode.getDouble("x");
			this.y = currentNode.getDouble("y");
			this.id = currentNode.getString("id");
		} catch( JSONException e ){
			e.printStackTrace();
		}
		
		return null;
	}
	


	// getters and setters
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
