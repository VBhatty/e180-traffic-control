package com.e180.vo;

public interface IJsonSerializable {
	// return the deserialized object
	public Object unSerialize(String data);
	
	// serialize the object itself now
	public String Serialize();
}
