package test.study.test;

public class TestMail {

	public static void main(String[] args) {
		
		String t = "D:\\Desktop\\index.html";
		System.out.println(t.substring(t.lastIndexOf(System.getProperty("file.separator"))+1));

	}

}
