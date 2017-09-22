package test.study.test;

import test.tool.db.DBTool;

public class TestExport {


	public static void main(String[] args) {

		export();
		
	}
	
	public static void export(){
		String [] tableNames = {"pub_users"};
		DBTool.exportTableToXls("D:/Desktop/test.xls", tableNames);
		DBTool.exportTableToXml("D:/Desktop/test.xml", tableNames);
	}
}
