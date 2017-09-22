package test.tool.properties;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import test.tool.path.PathTool;

public class PropertiesTool {

	/*
	 *  解析.properties配置文件, 返回一个Key_Value对应的 Properties对象
	 */
	public static Properties load_property_file(String property_file_path) {
		
		Properties prop = new Properties();
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(property_file_path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	 /**  
     * 修改或添加键值对 如果key存在，修改， 反之，添加。
     * 暂时只支持写英文，如果是中文字符会显示乱码，日后改进。 
     * 本接口只用于classes文件夹下的配置文件()。   
     */  
    public static void writeData(String key, String value, String PROPERTY_FILE) {
		try {
			Properties prop = load_property_file(PROPERTY_FILE);
			OutputStream fos = new FileOutputStream(PathTool.getClassesPath()+PROPERTY_FILE);//此处需要绝对路径
			prop.setProperty(key, value);
			prop.store(fos, "Update '" + key + "' value by hqq");
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
