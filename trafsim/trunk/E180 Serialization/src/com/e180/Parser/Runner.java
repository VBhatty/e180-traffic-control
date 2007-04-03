package com.e180.Parser;

import com.e180.vo.SceneVO;

public class Runner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileParser myParser = new FileParser();
		SceneVO myVO = myParser.readFileIntoScene("C:\\input.txt");
		//System.out.println(myVO.getId());
		System.out.println(myVO.Serialize());
	}

}
