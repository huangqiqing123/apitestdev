package test.study.swing;

import java.awt.HeadlessException;
import java.io.File;

import javax.swing.JFileChooser;

public class FileChooser {
	
	private static JFileChooser fc=new JFileChooser();
	
	public static void main(String[] args) {
		
		//只能选择目录
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		String path=null;
		File f=null;
		int flag = 0;
		try{     
			flag=fc.showOpenDialog(null);  
			//flag=fc.showSaveDialog(null);  		
		}    
		catch(HeadlessException head){     
			System.out.println("Open File Dialog ERROR!");    
		}        
		if(flag==JFileChooser.APPROVE_OPTION){
			
			//获得该文件    
			f=fc.getSelectedFile();    
			path=f.getPath();
			System.out.println(path);
		}    		
	}
}
