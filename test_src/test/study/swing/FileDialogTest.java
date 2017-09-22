package test.study.swing;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileDialogTest {

	Frame frame = new Frame("测试");
	FileDialog fd1 = new FileDialog(frame,"选择需要打开的文件",FileDialog.LOAD);
	FileDialog fd2 = new FileDialog(frame,"选择需要保存文件的路径",FileDialog.SAVE);
	
	Button btn1 = new Button("打开文件");
	Button btn2 = new Button("保存文件");
	
	public void init(){
		btn1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				fd1.setVisible(true);
				 System.out.println(fd1.getDirectory()+"   "+fd1.getFile());
		         // E:\工作\常用文档\   inspur.jpg
			}		
		});
		
		btn2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				fd2.setVisible(true);		
				 System.out.println(fd2.getDirectory()+"   "+fd2.getFile());
			        // D:\Desktop\   121
			}		
		});
		
		frame.add(btn1);
		frame.add(btn2,BorderLayout.SOUTH);
		//frame.add(btn2);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {

		FileDialogTest test = new FileDialogTest();
		test.init();
	}
}
