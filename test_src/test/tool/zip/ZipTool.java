package test.tool.zip;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import test.tool.path.PathTool;

/*
 * 本类依赖于ant.jar(ZipEntry/ZipOutputStream)，主要实现zip文件的压缩、解压缩功能。
 */
public class ZipTool {
	
	private static final int BUFFER = 2048;
	private static ZipOutputStream zos;

	/*
	 * 入口函数，执行ant时调用。
	 */
	public static void main(String[] args) {
		
		System.out.println("测试用例执行完毕，执行测试用例zip打包！");
		
		//打包Junit测试报告
		String src_file_Path = PathTool.getRealPath()+"test_report/junit/html";
		
		//压缩后文件名称、存放目录；此处如果使用相对路径，使用ant调用时会报找不到指定路径的错误。
		String dest_file_path = PathTool.getRealPath()+"test_report"+File.separator+"Junit测试报告.zip";
		zip(src_file_Path,dest_file_path);
		
		//打包覆盖率测试报告
		src_file_Path = PathTool.getRealPath()+"test_report/coverage";
		dest_file_path = PathTool.getRealPath()+"test_report"+File.separator+"覆盖率测试报告.zip";
		zip(src_file_Path,dest_file_path);
	}

	/*
	 * 压缩指定路径下的所有文件,本方法可作为对外接口。
	 * src_file_Path:如 D:\\workspace\\APITest\\test_report\\html
	 * dest_file_path:如 BaseTool.getRealPath()+"test_report"+File.separator+"html.zip"
	 */
	public static void zip(String src_file_Path,String dest_file_path) {
		try {
			File file = new File(src_file_Path);
			
			//将所有 \\ 置换成 /，方便统一处理。
			if(src_file_Path.contains("\\")){
				src_file_Path = src_file_Path.replace("\\", "/");
			}
			
			//如果src_file_Path不以 / 结尾，则人为进行追加。
			if(!src_file_Path.endsWith("/")){
				src_file_Path = src_file_Path+"/";
			}
			zos = new ZipOutputStream(new FileOutputStream(dest_file_path));
			recurseFiles(file,src_file_Path);
			zos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 递归压缩所有文件
	 */
	private static void recurseFiles(File file,String src_file_Path) throws IOException {
		if (file.isDirectory()) {
			String[] fileNames = file.list();
			if (fileNames != null) {
				for (int i = 0; i < fileNames.length; i++) {
					recurseFiles(new File(file, fileNames[i]),src_file_Path);
				}
			}
		} else {
			byte[] buf = new byte[BUFFER];
			int len;
			
			String absolutePath = file.getAbsolutePath();//file.getAbsolutePath()与file.toString()的返回值相同。
			ZipEntry zipEntry = null;

			//将absolutePath的 \\ 统一替换成  /
			if(absolutePath.contains("\\")){
				absolutePath = absolutePath.replace("\\", "/");
			}
			
			String temp = absolutePath.replace(src_file_Path, "");
			zipEntry = new ZipEntry(temp);
			System.out.println(temp);
			FileInputStream fin = new FileInputStream(file);
			BufferedInputStream in = new BufferedInputStream(fin);
			zos.putNextEntry(zipEntry);
			while ((len = in.read(buf)) >= 0) {
				zos.write(buf, 0, len);
			}
			in.close();
			zos.closeEntry();
		}
	}
}
