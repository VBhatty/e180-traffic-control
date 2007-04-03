package com.e180.Parser;

import com.e180.vo.SceneVO;
import java.io.*;

public class FileParser {
	public SceneVO readFileIntoScene( String fileDir){
		
		if( fileDir == null ) {
			fileDir = "input.txt";
		}
		
		String contents = "";
		
		try {
			BufferedReader reader = new BufferedReader( new FileReader(fileDir) );
			String line = "";
			while((line = reader.readLine())!=null ) {
				contents += line;
			}
		} catch( FileNotFoundException eF) {
			eF.printStackTrace();
			return null;
		} catch( IOException eIo) {
			eIo.printStackTrace();
			return null;
		}
		
		System.out.println( contents );
		SceneVO sceneVO = new SceneVO();
		sceneVO.unSerialize(contents);
		return sceneVO;
	}
	
	public void writeSceneVOIntoFile( String fileDir, SceneVO sceneVO ){
		if( fileDir == null ){
			fileDir = "output.txt";
		}
		
		try {
			BufferedWriter writer = new BufferedWriter( new FileWriter(fileDir) );
			writer.write(sceneVO.Serialize());
		} catch( IOException eIo ) {
			eIo.printStackTrace();
			return;
		}
		
	}
}
