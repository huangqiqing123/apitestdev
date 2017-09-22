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
 * 主要测试删除、插入记录时，外键约束的影响。
 * 插入测试数据、恢复原数据时，须保证主外键约束（选项卡顺序、xml标签顺序）。
 */
public class Test3 {

	private IDatabaseConnection connection = null;
	private File xlsfile_test3 = null;
	
	@Before
	public void before() {

		try {
			// 获取数据库连接，创建时，需要指定模式名，否则在oracle下会出错。
			connection = new DatabaseConnection(DBTool.getConnection(),DBTool.getSchemaName());

			// 备份student、dept表
			QueryDataSet backupDataSet = new QueryDataSet(connection);
			backupDataSet.addTable("dept");
			backupDataSet.addTable("student");
			xlsfile_test3 = File.createTempFile("test3_back", ".xls");
			XlsDataSet.write(backupDataSet, new FileOutputStream(xlsfile_test3));//excel数据源还有些问题仅支持poi3.2

			// 导入测试数据
			String data_file_path = PathTool.getTestDataPath()+"test3_pre.xls";
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
			IDataSet dataSet = new XlsDataSet(xlsfile_test3);
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
