package test.tool.db;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import test.tool.constant.Conf;
import test.tool.constant.DataSourceConf;

public class DBTool {
	
	private static Connection conn = null;
	
	/*
	 * 获取数据库连接
	 */
	public static Connection getConnection(){
		try {
			if(conn == null||conn.isClosed()){		
				try {
					Class.forName(DataSourceConf.driverClassName);
					conn = DriverManager.getConnection(DataSourceConf.url, DataSourceConf.username,DataSourceConf.password);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	/*
	 * 启用当前用户指定tableName的所有外键约束
	 * 入参使用可变参数（jdk5新特性）
	 * 调用方式：
	 * 1、enableFK("pub_organ")
	 * 2、enableFK("pub_organ","pub_stru")
	 * 3、enableFK(new String[]{"pub_organ","pub_stru"})
	 */
	public static void enableFK(String...tableNames){
		disableORenbaleFK(true,tableNames);
	}
	/*
	 * 启用指定表的子表的外键约束
	 * 入参使用可变参数（jdk5新特性）
	 * 调用方式：
	 * 1、enableChildrenTableFK("pub_organ")
	 * 2、enableChildrenTableFK("pub_organ","pub_stru")
	 * 3、enableChildrenTableFK(new String[]{"pub_organ","pub_stru"})
	 */
	public static void enableChildrenTableFK(String...tableNames){	
		String[] childrenTableNames = getChildrenTables(tableNames);
		if(childrenTableNames.length>0){				
			enableFK(childrenTableNames);
		}
	}
	/*
	 * 禁用指定表的子表的外键约束
	 */
	public static void disableChildrenTableFK(String...tableNames){
		String[] childrenTableNames = getChildrenTables(tableNames);
		if(childrenTableNames.length>0){				
			disableFK(childrenTableNames);
		}
	}
	/*
	 * 查询指定表的子表
	 */
	private static String[] getChildrenTables(String...tableNames){
		String dbType = getDBType();
		List<Map<String,Object>> childMapList = null;//用于接收查询子表返回的结果list
		if(dbType.contains("ORACLE")){	
			String temp = "select distinct child.table_name as CHILD_TABLE_NAME from user_constraints parent,user_constraints child " +
			"where parent.constraint_name=child.r_constraint_name " +
			"and child.constraint_type='R' and parent.table_name in (";
			StringBuilder getchildrenSQL = new StringBuilder(temp);
			for (int i = 0; i < tableNames.length; i++) {
				getchildrenSQL.append("'");
				getchildrenSQL.append(tableNames[i].toUpperCase());
				getchildrenSQL.append("'");
				getchildrenSQL.append(",");
				}
			getchildrenSQL.deleteCharAt(getchildrenSQL.length()-1);
			getchildrenSQL.append(")");
			childMapList = executeQuery(getchildrenSQL.toString());
			}else if(dbType.contains("DB2")){	
				String temp = "select distinct TABNAME as CHILD_TABLE_NAME from syscat.references WHERE REFTABNAME in(";
				StringBuilder getchildrenSQL = new StringBuilder(temp);
				for (int i = 0; i < tableNames.length; i++) {
					getchildrenSQL.append("'");
					getchildrenSQL.append(tableNames[i].toUpperCase());
					getchildrenSQL.append("'");
					getchildrenSQL.append(",");
					}
				getchildrenSQL.deleteCharAt(getchildrenSQL.length()-1);
				getchildrenSQL.append(")");
				childMapList = executeQuery(getchildrenSQL.toString());
			}else{
				throw new RuntimeException("暂不支持的数据库类型："+dbType);
			}
		String childrenTableNames[] = new String[childMapList.size()];
		for(int j=0;j<childMapList.size();j++){
			childrenTableNames[j]=childMapList.get(j).get("CHILD_TABLE_NAME").toString();
		}
		return childrenTableNames;
	}

	/*
	 * 禁用当前用户指定tableName的所有外键约束
	 */
	public static void disableFK(String...tableNames){
		disableORenbaleFK(false,tableNames);
	}
	/*
	 * 启用当前用户所有表的外键约束
	 * 考虑性能问题，该方法一般不建议使用，推荐使用 enableFK(String...tableNames)
	 */
	@Deprecated
	public static void enableAllFK(){
		disableORenableAllConstraint(true);
	}
	/*
	 * 禁用当前用户所有表的外键约束
	 * 考虑性能问题，该方法一般不建议使用，推荐使用 disableFK(String...tableNames)
	 */
	@Deprecated
	public static void disableAllFK(){	
		disableORenableAllConstraint(false);
	}
	/*
	 * 导出数据库表，以xml文件的格式备份至指定文件目录
	 * destPathAndFileName格式：D:/Desktop/test.xml
	 */
	public static File exportTableToXml(String destPathAndFileName,String...tableNames){
		
		if(destPathAndFileName==null||"".equals(destPathAndFileName)){
			throw new RuntimeException("入参destPathAndFileName不能为空！");
		}
		
		File xml_file_back = null;
		Connection conn = DBTool.getConnection();
		IDatabaseConnection connection = null;
		FileOutputStream out = null;
		try {
			// 获取数据库连接，创建时，需要指定模式名，否则在oracle下会出错。
			connection = new DatabaseConnection(conn,DBTool.getSchemaName());
			
			// 解析需要备份的表
			QueryDataSet backupDataSet = new QueryDataSet(connection);
			for(int i=0;i<tableNames.length;i++){	
				backupDataSet.addTable(tableNames[i]);
			}
			if(!destPathAndFileName.toLowerCase().endsWith(".xml")){
				destPathAndFileName = destPathAndFileName + ".xml";
			}
			xml_file_back= new File(destPathAndFileName);
			out = new FileOutputStream(xml_file_back);
			FlatXmlDataSet.write(backupDataSet, out);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(out != null){				
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		return xml_file_back;
	}
	/*
	 * 从xml文件中导入测试数据至数据库表，如果导入过程中出错，则回滚事务。
	 * 须保证每个表的第一行所有字段都存在，否则会导致下面所有行，该字段为null。
	 */
	public static boolean importXmlToTable(String xml_file_path,OperationType operationType){

		boolean isSuccess = true;
		IDatabaseConnection connection = null;
		Connection conn = DBTool.getConnection();
		try {
			conn.setAutoCommit(false);
			connection = new DatabaseConnection(conn,DBTool.getSchemaName());
			FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
			IDataSet dataSet = flatXmlDataSetBuilder.build(new FileInputStream(xml_file_path));
			if(operationType == OperationType.INSERT){		
				DatabaseOperation.INSERT.execute(connection, dataSet);
			}else if(operationType == OperationType.CLEAN_INSERT){
				DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
			}
			conn.commit();
		} catch (Exception e) {
			isSuccess = false;
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		return isSuccess;
	}
	/*
	 * 备份数据库表，以xls文件的格式备份至系统临时文件目录中。
	 * 入参String数组
	 */
	public static File backUpTable(String...tableNames){
		
		File xls_file_back = null;
		FileOutputStream out = null;
		Connection conn = DBTool.getConnection();
		IDatabaseConnection connection = null;
		try {
			// 获取数据库连接，创建时，需要指定模式名，否则在oracle下会出错。
			connection = new DatabaseConnection(conn,DBTool.getSchemaName());
			
			// 解析需要备份的表
			QueryDataSet backupDataSet = new QueryDataSet(connection);
			for(int i=0;i<tableNames.length;i++){	
				backupDataSet.addTable(tableNames[i].toUpperCase());
			}
			xls_file_back= File.createTempFile("temp", ".xls");//生成临时文件时，会自动在文件名后追加一个随机数。
			out = new  FileOutputStream(xls_file_back);
			XlsDataSet.write(backupDataSet, out);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		return xls_file_back;
	}
	/*
	 * 备份数据库表，以excel文件的格式备份至系统指定文件目录。
	 * destPathAndFileName格式：D:/Desktop/test.xls
	 */
	public static File exportTableToXls(String destPathAndFileName,String...tableNames){
		
		if(destPathAndFileName==null||"".equals(destPathAndFileName)){
			throw new RuntimeException("入参destPathAndFileName不能为空！");
		}
		Connection conn = DBTool.getConnection();
		IDatabaseConnection connection = null;
		File xlsfile = null;
		FileOutputStream out = null;
		try {
			// 获取数据库连接，创建时，需要指定模式名，否则在oracle下会出错。
			connection = new DatabaseConnection(conn,DBTool.getSchemaName());

			// 解析需要备份的表
			QueryDataSet backupDataSet = new QueryDataSet(connection);
			for(int i=0;i<tableNames.length;i++){	
				backupDataSet.addTable(tableNames[i]);
			}
			if(!destPathAndFileName.toLowerCase().endsWith(".xls")){
				destPathAndFileName = destPathAndFileName + ".xls";
			}
			xlsfile= new File(destPathAndFileName);
			out = new FileOutputStream(xlsfile);
			XlsDataSet.write(backupDataSet, out);
		} catch (Exception e) {
			e.printStackTrace();
			xlsfile.delete();
		}finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return xlsfile;
	}
	/*
	 * 从xls文件中导入测试数据至数据库表
	 * 如果导入过程中出错，则回滚事务。
	 */
	public static boolean importXlsToTable(String xls_file_path,OperationType operationType){
		
		Connection conn = DBTool.getConnection();
		IDatabaseConnection connection = null;
		File xlsfile = null;
		boolean isSuccess = true;
		try {
			conn.setAutoCommit(false);
			connection = new DatabaseConnection(conn,DBTool.getSchemaName());
			xlsfile = new File(xls_file_path);
			IDataSet dataSet = new XlsDataSet(xlsfile);
			if(operationType == OperationType.INSERT){		
				DatabaseOperation.INSERT.execute(connection, dataSet);
			}else if(operationType == OperationType.CLEAN_INSERT){
				DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
			}
			conn.commit();
		} catch (Exception e) {//出现异常，则回滚事务。
			isSuccess = false;
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isSuccess;
	}
	/*
	 * 恢复数据库表
	 * 查询数据表的子表---禁用子表的外键---对数据表执行Clean-Insert操作---启用子表的外键
	 */
	public static void recover(File file) {

		Connection conn = DBTool.getConnection();
		IDatabaseConnection connection = null;
		String fileName = file.getName();
		try {
			connection = new DatabaseConnection(conn, DBTool.getSchemaName());
			if (fileName.toLowerCase().endsWith("xls")) {
				IDataSet dataSet = new XlsDataSet(file);
				String tableNames[]=dataSet.getTableNames();		
				String childrenTableNames[] = getChildrenTables(tableNames);
				
				//禁用相关子表外键约束
				if(childrenTableNames.length>0){			
					disableFK(childrenTableNames);
				}
				
				//执行CLEAN_INSERT，恢复数据库表
				DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
				
				//启用相关子表外键约束
				if(childrenTableNames.length>0){				
					enableFK(childrenTableNames);
				}
			}else{
				throw new RuntimeException("不合法的文件类型："+file.getAbsolutePath());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//DBTool.closeDBunitConnection(connection);
			//DBTool.closeConnection(null, null, conn);
		}
	}	
	/*
	 * 获取当前所连接的数据库实例的模式名schema，从datasource.properties中读取，一般模式名与用户名相同。
	 */
	public static String getSchemaName(){
		return DataSourceConf.username.toUpperCase();
	}
	/*
	 * getDBType，返回当前所使用的数据库类型（大写）。
	 */
	public static String getDBType(){
		
		Connection conn = null;
		String pruductName = null;
		try {
			conn = getConnection();
			DatabaseMetaData metaData = conn.getMetaData();
			pruductName = metaData.getDatabaseProductName();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			//DBTool.closeConnection(null, null, conn);
		}
		return pruductName==null?null:pruductName.toUpperCase();
	}
	/*
	 * 执行查询操作，返回List<Map<String columnName, Object columnValue>>形式的结果集
	 */
	public static List<Map<String, Object>> executeQuery(String sql){
		
		if(Conf.isPrintSql){
			System.out.println(sql);			
		}
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String, Object>> dataSet = new ArrayList<Map<String,Object>>();//结果集
		try {
			ps = conn.prepareStatement(sql);
			rs =  ps.executeQuery();
			List<String> columnNameList = new ArrayList<String>();//列名称list
			ResultSetMetaData metadata = rs.getMetaData();//获取数据表元数据
			int columnCount = metadata.getColumnCount();//列数
			for(int i=1;i<=columnCount;i++){
				String columnName = metadata.getColumnName(i);
				columnNameList.add(columnName);
			}
			while(rs.next()){	
				Map<String, Object> record = new HashMap<String, Object>();//对应数据库表中的一条记录
				for(int j=0;j<columnNameList.size();j++){
					String columnName = columnNameList.get(j);
					Object columnValue = rs.getObject(columnName);//全部以Object的方式读取
					record.put(columnName, columnValue);
				}
				dataSet.add(record);
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeConnection(rs, ps, null);
		}
		return dataSet;
	}
	/*
	 * 执行更新数据操作
	 */
	public static int executeUpdate(String sql) {
		return executeUpdate(sql,UpdateType.NONE);
	}
	/*
	 * 执行更新数据操作
	 */
	public static int executeUpdate(String sql,UpdateType type) {

		if(Conf.isPrintSql){
			System.out.println(sql);			
		}

		int affectLines = 0;// 影响记录的行数
		Connection conn = getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			affectLines = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(null, ps, null);
		}
		return affectLines;
}
	/*
	 * 关闭数据库连接
	 * 关闭顺序：ResultSet——PreparedStatement——Connection
	 */
	public static synchronized void closeConnection(ResultSet rs,PreparedStatement ps,Connection conn){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(ps!=null){
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
//		if(conn!=null){
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
	}
	/*
	 * 关闭DBunit数据库连接
	 * (关闭dbunit连接，会导致connection一同关闭)
	 */
//	public static void closeDBunitConnection(IDatabaseConnection connection){
//		try {
//			if (connection != null){		
//				connection.close();
//			}
//			} catch (SQLException e) {
//				e.printStackTrace();	
//			}
//	}
	/*
	 * 启用or禁止指定表的约束，本接口只针对oracle/DB2数据库。
	 */
	private static void disableORenbaleFK(boolean isEnable,String...tableNames){
		if(tableNames==null||tableNames.length==0){
			throw new RuntimeException("入参tableNames不能为空！");
		}
		//查询指定表的外键约束
		String sql = null;
		String dbType = getDBType();
		if(dbType.contains("ORACLE")){
			sql = "select 'alter table ' || table_name || ' disable constraint ' || constraint_name  from user_constraints where constraint_type='R' and TABLE_NAME in(";
			if(isEnable){
				sql = sql.replace("disable", "enable");
			}		
		}else if(dbType.contains("DB2")){
			sql = "select 'ALTER TABLE '  || TBNAME ||  ' ALTER FOREIGN KEY ' || NAME ||' NOT ENFORCED ' FROM SYSIBM.SYSTABCONST WHERE CONSTRAINTYP='F' and TBNAME in(";
			if(isEnable){
				sql = sql.replace("NOT ENFORCED", "ENFORCED");
			}		
		}else{
			throw new RuntimeException("数据库类型无效(仅支持Oracle和DB2)，dbType="+dbType);	
		}	
		StringBuffer generateSQL = new StringBuffer(sql);
		for(int i=0;i<tableNames.length;i++){	
			generateSQL.append(" '");
			generateSQL.append(tableNames[i].toUpperCase());//注意须转换成大写
			generateSQL.append("',");
		}
		generateSQL.deleteCharAt(generateSQL.length()-1);
		generateSQL.append(")");
		List<Map<String, Object>> dataSet = DBTool.executeQuery(generateSQL.toString());
		
		//启用or停止查询出的外键约束
		for(int i=0;i<dataSet.size();i++){
			Map<String, Object> record = dataSet.get(i);
		     Iterator<Entry<String, Object>> itor = record.entrySet().iterator();   
		        while(itor.hasNext())   
		        {   
		           Entry<String, Object> e = itor.next(); 
		           DBTool.executeUpdate(e.getValue().toString(),UpdateType.ALTER);
		        }   	       
		}
	}
	/*
	 * 启用or禁用所有外键约束，目前只支持oracle和DB2数据库。
	 */
	private static void disableORenableAllConstraint(boolean isEnable){
		
		String sql = null;
		String dbType = getDBType();
		if(dbType.contains("ORACLE")){
			
			//默认是执行禁止操作
			sql = "select 'alter table ' || table_name || ' disable constraint ' || constraint_name  from user_constraints where constraint_type='R'";//R表示外键		
			if(isEnable){			
				sql = sql.replace("disable", "enable");
			}
		}else if(dbType.contains("DB2")){//SYSIBM.SYSTABCONST是DB2字典表的一个视图，可以查询所有表的约束信息。
			sql = "select 'ALTER TABLE '  || TBNAME ||  ' ALTER FOREIGN KEY ' || NAME ||' NOT ENFORCED ' FROM SYSIBM.SYSTABCONST WHERE CONSTRAINTYP='F'";//F表示外键
			if(isEnable){
				sql = sql.replace("NOT ENFORCED", "ENFORCED");
			}		
		}else{
			throw new RuntimeException("数据库类型无效(仅支持Oracle和DB2)，dbType="+dbType);	
		}	
		
		//查询指定表的外键约束
		List<Map<String, Object>> dataSet = DBTool.executeQuery(sql);
		
		//禁止或启用查询出的外键约束
		for(int i=0;i<dataSet.size();i++){
			Map<String, Object> record = dataSet.get(i);
		     Iterator<Entry<String, Object>> itor = record.entrySet().iterator();   
		        while(itor.hasNext())   
		        {   
		           Entry<String, Object> e = itor.next();   
		           DBTool.executeUpdate(e.getValue().toString(),UpdateType.ALTER);
		        }   	       
		}
	}
}
