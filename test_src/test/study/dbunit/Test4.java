package test.study.dbunit;

import java.io.File;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import test.tool.db.DBTool;
import test.tool.db.OperationType;
import test.tool.path.PathTool;

/*
 * 功能点：删除、插入记录时，外键约束的影响；多个表时的数据准备；xml作为数据载体
 * 插入测试数据、恢复原数据时，须保证主外键约束（选项卡顺序、xml标签顺序(也可以通过禁止、启用约束的方式)）。
 */
public class Test4 {

	//备份数据库表
	String tableNames[] = new String[2];
	private File xmlfile_test4 = null;
	
	@Before
	public void before() {

		tableNames[0]="student";
		tableNames[1]="dept";
		xmlfile_test4 = DBTool.backUpTable(tableNames);
		
		//如果需要，可在此禁用必要的外键约束，导入测试数据后，再启用外键约束。
		String names[]={"student"};
		DBTool.disableFK(names);
		
		//导入测试数据
		String xml_file_path = PathTool.getTestDataPath()+"test4_pre.xml";
		DBTool.importXmlToTable(xml_file_path,OperationType.INSERT);
		
		//导入测试数据后，重新启用前面禁用的外键约束。
		DBTool.enableFK(names);
	}
	
	@Test
	public void test1(){	
		List<Map<String, Object>> dataset = DBTool.executeQuery("select * from student where pk='s3'");
		Assert.assertEquals(1, dataset.size());
		Assert.assertEquals("s3", dataset.get(0).get("PK"));
		Assert.assertEquals("黄启庆", dataset.get(0).get("NAME"));
	}
	
	/*
	 * 将数据库恢复到测试前状态
	 */
	@After
	public void after() {
		DBTool.disableFK(tableNames);
		DBTool.recover(xmlfile_test4);
		DBTool.enableFK(tableNames);
	}
}
