package test.study.dbunit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import test.tool.db.DBTool;
import test.tool.path.PathTool;

/*
 * 本测试类使用  excel 文件作为数据文件
 * 功能点：备份原始表、导入测试数据、junit断言、数据库表恢复
 */
public class Test2 {

	private IDatabaseConnection connection = null;
	private File xlsfile = null;
	
	@Before
	public void before() {

		try {
			// 获取数据库连接，创建时，需要指定模式名，否则在oracle下会出错。
			connection = new DatabaseConnection(DBTool.getConnection(),DBTool.getSchemaName());

			// 备份student表
			QueryDataSet backupDataSet = new QueryDataSet(connection);
			backupDataSet.addTable("student");
			xlsfile = File.createTempFile("student_back", ".xls");
			XlsDataSet.write(backupDataSet, new FileOutputStream(xlsfile));//还有些问题

			// 导入测试数据
			String data_file_path = PathTool.getTestDataPath()+"student_pre.xls";
			IDataSet dataSet = new XlsDataSet((new FileInputStream(data_file_path)));
			DatabaseOperation.INSERT.execute(connection, dataSet);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (connection != null){		
					connection.close();
				}
				} catch (SQLException e) {
					e.printStackTrace();	
				}
		}
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
		
		IDatabaseConnection connection = null;
		try {
			connection = new DatabaseConnection(DBTool.getConnection(),DBTool.getSchemaName());
			IDataSet dataSet = new XlsDataSet(xlsfile);
			DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null){		
					connection.close();
				}
				} catch (SQLException e) {
					e.printStackTrace();	
				}
		}
	}
}
