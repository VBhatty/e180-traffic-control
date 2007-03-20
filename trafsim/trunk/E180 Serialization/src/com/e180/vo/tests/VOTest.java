package com.e180.vo.tests;

import junit.framework.TestCase;
import com.e180.vo.*;
import java.util.ArrayList;

public class VOTest extends TestCase {
	public VOTest(String name) {
		super(name);
	}
	
	public void testSourceVO(){
		SourceVO source1 = new SourceVO();
		source1.setFrequency(12.33);
		source1.setId("test id 232");
		source1.setNodeId("dfadf dsfa");
		
		SourceVO source2 = new SourceVO();
		source2.unSerialize(source1.Serialize());
		
		// make sure deserialized values are equal to serialized
		assertEquals(source1.getId(), source2.getId());
		assertEquals(source1.getNodeId(), source2.getNodeId());
		assertEquals(source1.getFrequency(), source2.getFrequency());	
	}
	
	public void testCarVO(){
		CarVO car1 = new CarVO();
		CarVO car2 = new CarVO();
		
		ArrayList<Double> percentages = new ArrayList<Double>();
		ArrayList<String> roadIds = new ArrayList<String>();
		
		for (Integer i = 0; i < 10; i++) {
			percentages.add( Math.random() );
			roadIds.add("roadID:"+i.toString());
		}
		car1.setId("car1");
		car1.setPercentageAlongRoads(percentages);
		car1.setRoadIds(roadIds);
		System.out.println(car1.Serialize());
		car2.unSerialize(car1.Serialize());
		
		assertEquals( car1.getRoadIds(), car2.getRoadIds() );
		assertEquals( car1.getPercentageAlongRoads(), car2.getPercentageAlongRoads() );
	}
	

}
