package test.tool.transform;

import java.util.ArrayList;
import java.util.List;

public class Transform {
	
	/*
	 * List<String> 转换成 String[]
	 */
	public static String[] List2Array(List<String> list){
		
		if(list==null||list.size()==0){
			return null;
		}else
		{		
			String str[] = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				str[i] = list.get(i);
			}
			return str;
		}
	}
	/*
	 * String[] 转换成 List<String>
	 */
	public static List<String> array2List(String[] array){
		
		if(array==null||array.length==0){
			return null;
		}else{
			List<String> returnList = new ArrayList<String>();
			for(int i=0;i<array.length;i++){
				returnList.add(array[i]);
			}		
			return returnList;
		}
	}
}

