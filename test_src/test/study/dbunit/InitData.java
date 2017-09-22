package test.study.dbunit;

import test.tool.db.DBTool;
import test.tool.db.UpdateType;

public class InitData {

	/*
	 * 入口函数
	 */
	public static void main(String[] args) {
		destroy();
		init();
		
	}
	
	/*
	 * 初始化数据
	 */
	public static void init(){
		
		//创建数据库表，并建立主外键约束。
		StringBuffer deptSQL = new StringBuffer();
		deptSQL.append("CREATE TABLE DEPT (PK VARCHAR2(32) NOT NULL,NAME VARCHAR2(100),");
		deptSQL.append("CONSTRAINT pk_dept PRIMARY KEY (pk)");
		deptSQL.append(")");
		DBTool.executeUpdate(deptSQL.toString(),UpdateType.CREATE);
		
		StringBuffer studentSQL = new StringBuffer();
		studentSQL.append("CREATE TABLE student ( pk VARCHAR2(32) NOT NULL,name VARCHAR2(100),age VARCHAR2(3),deptId VARCHAR2(32) NOT NULL,");
		studentSQL.append("CONSTRAINT pk_student PRIMARY KEY (pk),");
		studentSQL.append("CONSTRAINT fk FOREIGN KEY (deptId) REFERENCES dept (pk)");
		studentSQL.append(")");
		DBTool.executeUpdate(studentSQL.toString(),UpdateType.CREATE);
		
		//插入部分初始数据
		DBTool.executeUpdate("insert into dept(pk,name) values('001','质保中心')",UpdateType.INSERT);
		DBTool.executeUpdate("insert into dept(pk,name) values('002','技术中心')",UpdateType.INSERT);
		
		DBTool.executeUpdate("insert into student(pk,name,age,deptId) values('s1','张三','10','001')",UpdateType.INSERT);
		DBTool.executeUpdate("insert into student(pk,name,age,deptId) values('s2','李四','11','002')",UpdateType.INSERT);
	}
	/*
	 * 销毁数据
	 */
	public static void destroy(){
		DBTool.executeUpdate("drop table student",UpdateType.DROP);
		DBTool.executeUpdate("drop table dept",UpdateType.DROP);
	}
}
