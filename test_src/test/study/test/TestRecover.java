package test.study.test;

import java.io.File;

import test.tool.db.DBTool;

public class TestRecover {

	public static void main(String[] args) {
	
	DBTool.disableAllFK();
	String path  = 	"C:\\Users\\Administrator\\AppData\\Local\\Temp\\PUB_STRU&PUB_STRU_EXT&PUB_ORGAN&PUB_ORGAN_TYPE&PUB_STRU_TYPE&20110725-1927&43079.xls";
	DBTool.recover(new File(path));
	DBTool.enableAllFK();
		//System.out.println(BaseTool.getDateTimeWithSeconds().replace(":", "").replace("-", ""));
	
	}

}
