package test.study.unitils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Ignore;
import org.junit.Test;
//import org.unitils.UnitilsJUnit4;
//import org.unitils.database.DatabaseUnitils;
//import org.unitils.database.annotations.Transactional;
//import org.unitils.database.util.TransactionMode;

import test.tool.db.DBTool;
import test.tool.db.UpdateType;


public class Test1 {//extends UnitilsJUnit4{
//
//	@Transactional(TransactionMode.ROLLBACK) 
//	@Test
//	public void test1() throws Exception{
//
//		MaxValueUtil.nextIntValue("ID1");
//		Test2.nextIntValue();
//
//
//	}
//	@Ignore
//	//@Transactional(TransactionMode.ROLLBACK) 
//	@Test
//	public void test2(){
//		try {
//			DataSource dataSource = DatabaseUnitils.getDataSource();
//			Connection con1 =  dataSource.getConnection();
//			PreparedStatement ps1 = con1.prepareStatement("select * from pub_apps");
//			ResultSet set = ps1.executeQuery();
//			System.out.println("-----首次查询----");
//			while(set.next()){
//				System.out.println(set.getString("APP_NAME"));
//			}
//			if(set!=null){
//				set.close();
//			}
//			if(ps1!=null){
//				ps1.close();
//			}
//			if(con1!=null){
//				con1.close();
//			}
//		
//			//该记录可以回滚
//			Connection con2 =  dataSource.getConnection();
//			PreparedStatement ps2 = con2.prepareStatement("insert into PUB_APPS(APP_CODE,APP_NAME,SEQ) VALUES ('QQ','QQ','11')");
//			ps2.executeUpdate();
//		
//			if(ps2!=null){
//				ps2.close();
//			}
//			if(con2!=null){
//				con2.close();
//			}
//			
//			//该记录回滚不了
//			DBTool.executeUpdate("insert into PUB_APPS(APP_CODE,APP_NAME,SEQ) VALUES ('QQ2','QQ2','22')", UpdateType.INSERT);
//			
//			//可以查询所有插入的记录
//			System.out.println("-----第二次查询(TestDataSource查询结果)-----");
//			
//			Connection con3 =  dataSource.getConnection();
//			PreparedStatement ps3 = con3.prepareStatement("select * from pub_apps");
//			ResultSet set3 = ps3.executeQuery();
//			while(set3.next()){
//				System.out.println(set3.getString("APP_NAME"));
//			}
//			if(set3!=null){
//				set3.close();
//			}
//			if(ps3!=null){
//				ps3.close();
//			}
//			if(con3!=null){
//				con3.close();
//			}
//			
//			Connection con4 = dataSource.getConnection();
//			PreparedStatement ps4 = con4.prepareStatement("delete from pub_apps");
//			ps4.executeUpdate();
//			
//			//只能查询出QQ2
//			System.out.println("非TestDataSource查询返回结果数："+DBTool.executeQuery("select * from pub_apps").size());
//	
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
