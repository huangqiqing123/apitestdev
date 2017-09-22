import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;


public class Test1 {

	@Test
	public void test1(){
		String actual = "张三";
		//Assert.assertEquals("张三", actual);
		assertTrue("张三".equals(actual));
	}
	
	
	
	
}
