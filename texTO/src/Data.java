

import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

public class Data {
	
	private String LOCALHOST = "file:///";
	private String HANDPHONE = "C:/";
	private String MEMORY = "E:/";
	
	private String FOLDER_DEFAULT = "texTO";

	public Data(){
	}
	
	/*
	 * @nameDir = texTO/
	 * @nameFile = sprti FileOuputStream("n.txt/rb/java..")
	 * @ext = Extension ex(.txt , .css..)
	 * @value = data that get from textbox
	*/ 
	public void createFile(String nameDir,String nameFile,String ext,byte[] value){
		OutputStream out = null;
		FileConnection fc;
		try{
			//if folder default is nothing
			FileConnection defa = (FileConnection)Connector.open(LOCALHOST+MEMORY+FOLDER_DEFAULT);
			if(defa.isDirectory() == false){
				defa.mkdir(); //make dir
			}
			if(ext =="Default"){
				fc = (FileConnection)Connector.open(LOCALHOST+MEMORY+nameDir+nameFile);
			}else{
				fc = (FileConnection)Connector.open(LOCALHOST+MEMORY+nameDir+nameFile+ext);
			}
			//place for file
			
			//create file
			if(fc.exists() == false){
				fc.create();
			}else{
				//if there are, replace
				fc.delete();
				fc.create();
			}
			
			//open file
			out = fc.openOutputStream();
			for(int i=0;i<value.length;i++){
				//input/write to file
				out.write(value[i]);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(out != null){
					//if out not null then file close
			        out.close();
				}
		    } catch (Exception e) {
			  // TODO Auto-generated catch block
		    	e.printStackTrace();
		    }
		}
	}
	
	//@nameDir
	public void createProject(String dirname){
		OutputStream out = null;
		FileConnection fc,dirCSS,dirJS,dirIMAGE,frdme;
		String CSS ="css",JS="js",IMAGE = "images",readme="Readme.txt";
		String thanks = "Thank you for using this App, have fun coding \n!<0-0>!.";
		try{
			fc = (FileConnection)Connector.open(LOCALHOST+MEMORY+FOLDER_DEFAULT+"/"+dirname);
			dirCSS = (FileConnection)Connector.open(LOCALHOST+MEMORY+FOLDER_DEFAULT+"/"+dirname+"/"+CSS);
			dirJS = (FileConnection)Connector.open(LOCALHOST+MEMORY+FOLDER_DEFAULT+"/"+dirname+"/"+JS);
			dirIMAGE = (FileConnection)Connector.open(LOCALHOST+MEMORY+FOLDER_DEFAULT+"/"+dirname+"/"+IMAGE);
			frdme = (FileConnection)Connector.open(LOCALHOST+MEMORY+FOLDER_DEFAULT+"/"+dirname+"/"+readme);
			if(fc.isDirectory() == false){
				fc.mkdir();
				dirCSS.mkdir();dirJS.mkdir();dirIMAGE.mkdir(); //default folder css,js and images
			}
			if(frdme.isDirectory() == false){
				frdme.create(); //make file readme.txt
			}
			out = frdme.openOutputStream();
			byte[] value = thanks.getBytes();
			for(int i=0;i<value.length;i++){
				out.write(value[i]);
			}
		}catch(Exception e){
			//some code here
		}finally{
			try{
				if(out != null){
					out.close();
				}
			}catch(Exception e){
				
			}
		}
	}

	public boolean checkFile(String dir,String file, String ext) {
		// TODO Auto-generated method stub
		FileConnection fc;
		try{
			fc = (FileConnection)Connector.open(LOCALHOST+MEMORY+dir+file+ext);
			if(fc.exists() == true){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			
		}
		return true;
	}
	
}
