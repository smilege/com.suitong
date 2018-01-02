package com.st.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

/**
 * 字符串相关的常用方法
 */
public class StringUtil {
    /**
     * 把一个对象数组用分隔字符串连接成一个字符串。
     * @param objs 对象数组
     * @param splitString 分割字符串
     * @return 连接后的字符串
     */
    public static <T> String join(T[] objs, String splitString) {
        return join(objs, 0, objs.length, splitString);
    }

	/**
	 * 把一个对象数组用分隔字符串连接成一个字符串。
	 * @param objs 对象数组
	 * @param start 对象数组的开始位置（包含）
	 * @param end 对象数组的结束位置（不包含）
	 * @param splitString 分割字符串
	 * @return 连接后的字符串
	 */
	public static <T> String join(T[] objs, int start, int end, String splitString){
		StringBuilder s = new StringBuilder();
		for (int i = start; i < end; i++){
			if (i != start){
				s.append(splitString);
			}
			s.append(objs[i]);
		}
        return s.toString();
	}
	
    /**
     * 把一个对象列表用分隔字符串连接成一个字符串。
     * @param objList 对象列表
     * @param splitString 分割字符串
     * @return 连接后的字符串
     */
    public static String join(List<?> objList, String splitString) {
        return join(objList, 0, objList.size(), splitString);
    }

    /**
     * 把一个对象列表用分隔字符串连接成一个字符串。
     * @param objList 对象列表
     * @param start 对象列表的开始位置（包含）
     * @param end 对象列表的结束位置（不包含）
     * @param splitString 分割字符串
     * @return 连接后的字符串
     */
    public static String join(List<?> objList, int start, int end, String splitString){
    	StringBuilder s = new StringBuilder();
    	for (int i = start; i < end; i++){
			if (i != start){
				s.append(splitString);
			}
			s.append(objList.get(i));
		}
    	return s.toString();
    }
    
    /**
     * 把一个对象数组的列表的某一列数据用分隔字符串连接成一个字符串。
     * @param objList 对象数组的列表
     * @param splitString 分割字符串
     * @return 连接后的字符串
     */
    public static <T> String join(List<T[]> objList, int columnIndex, String splitString) {
        StringBuilder s = new StringBuilder();
        if (objList.size() > 0) {
            s.append(objList.get(0)[columnIndex]);
            for (int i = 1, ii = objList.size(); i < ii; i++){
                s.append(splitString).append(objList.get(i)[columnIndex]);
            }
        }

        return s.toString();
    }

    
    /**
	 * 获取文件名
	 */
	public static String getFilePathAndName(String template, String className, String packageName){
		String packagePath = "server"+ File.separator +"main" + File.separator + "java" + File.separator;
		if(StringUtils.isNotBlank(packageName)){
			packagePath += packageName.replace(".", File.separator) + File.separator;
		}
		//后端模板
		if(template.contains("Entity.java.vm")){
			return packagePath + "entity" + File.separator + className + ".java";
		}
		
		if(template.contains("Dao.java.vm")){
			return packagePath + "dao" + File.separator + className + "Dao.java";
		}
		
		if(template.contains("Service.java.vm")){
			return packagePath + "service" + File.separator + className + "Service.java";
		}
		
		if(template.contains("ServiceImpl.java.vm")){
			return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
		}
		
		if(template.contains("Controller.java.vm")){
			return packagePath + "controller" + File.separator + className + "Controller.java";
		}
		
		//前端模板
		if(template.contains("html.vm")){
			return "client"+ File.separator +"html" + File.separator + StringUtil.lowCaseByFirst(className) + ".html";
		}
/*		if(template.contains("save.html.vm")){
			return "client"+ File.separator +"html" + File.separator + StringUtil.lowCaseByFirst(className) + ".html";
		}
		
		if(template.contains("detail.html.vm")){
			return "client"+ File.separator +"html" + File.separator + StringUtil.lowCaseByFirst(className) + ".html";
		}*/
		
		if(template.contains("js.vm")){
			return "client"+ File.separator +"js" + File.separator + StringUtil.lowCaseByFirst(className) + ".js";
		}
		
		/*if(template.contains("save.controller.js.vm")){
			return "client"+ File.separator +"js" + File.separator + StringUtil.lowCaseByFirst(className) + ".js";
		}
		
		if(template.contains("detail.controller.js.vm")){
			return "client"+ File.separator +"js" + File.separator + StringUtil.lowCaseByFirst(className) + ".js";
		}
		
		if(template.contains("service.js.vm")){
			return "client"+ File.separator +"js" + File.separator + StringUtil.lowCaseByFirst(className) + ".js";
		}
		
		if(template.contains("state.js.vm")){
			return "client"+ File.separator +"js" + File.separator + StringUtil.lowCaseByFirst(className) + ".js";
		}*/

		return null;
	}

	/**
	 * 修正路径，将 \\ 或 / 等替换为 File.separator
	 * @param path 待修正的路径
	 * @return 修正后的路径
	 */
	public static String path(String path){
		String p = StringUtils.replace(path, "\\", "/");
		p = StringUtils.join(StringUtils.split(p, "/"), "/");
		if (!StringUtils.startsWithAny(p, "/") && StringUtils.startsWithAny(path, "\\", "/")){
			p += "/";
		}
		if (!StringUtils.endsWithAny(p, "/") && StringUtils.endsWithAny(path, "\\", "/")){
			p = p + "/";
		}
		if (path != null && path.startsWith("/")){
			p = "/" + p; // linux下路径
		}
		return p;
	}
    
    //首字母转大写,例如：user-->User(用于方法名，例如模块名为user，则方法名toSaveUser,toDeleteUser,get/setUser等)
	public static String upperCaseByFirst(String str) {
		if(str == null) {
			return "";
		}
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	//首字母转小写
	public static String lowCaseByFirst(String str) {
		if(str == null) {
			return "";
		}
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}
	
	//表名转换成Java名  例如：t_sys_user 转为  SysUser
	public static String tableNameAsClassName(String tableName){
		String table = convertColumnName(tableName);
		return upperCaseByFirst(table);
	}
	
	//列名转换成Java属性名,去掉前缀， 例如：c_user_name 或者 C_USER_NAME 转为  userName
	public static String convertColumnName(String column) {
    	column = column.toLowerCase();
    	if("_".equals(column.substring(1, 2))) {
    		column = column.substring(2);
    	}
    	String[] names = column.split("_");
    	StringBuffer newName = new StringBuffer();
    	for(int i=0;i<names.length;i++) {
    		if (i>0) {
    			String start = names[i].substring(0, 1).toUpperCase();
        		String newNx = start + names[i].substring(1);
        		newName.append(newNx);
			}
    		
    	}
    	return names[0]+newName.toString();
    }
    
	 /**
	  * 下划线转驼峰法,例如 t_sys_user-->tSysUser(true),TSysUser(false)
	  * @param line 源字符串
	  * @param smallCamel 大小驼峰,是否为小驼峰
	  * @return 转换后的字符串
	  */
	 public static String underlineCamel(String line,boolean smallCamel){
		  if(line==null||"".equals(line)){
		   return "";
		  }
		  StringBuffer sb=new StringBuffer();
		  Pattern pattern=Pattern.compile("([A-Za-z\\d]+)(_)?");
		  Matcher matcher=pattern.matcher(line);
		  while(matcher.find()){
		   String word=matcher.group();
		   sb.append(smallCamel&&matcher.start()==0?Character.toLowerCase(word.charAt(0)):Character.toUpperCase(word.charAt(0)));
		   int index=word.lastIndexOf('_');
		   if(index>0){
		    sb.append(word.substring(1, index).toLowerCase());
		   }else{
		    sb.append(word.substring(1).toLowerCase());
		   }
		  }
		  return sb.toString();
	 }
	
	 /**
	  * 驼峰法转下划线,例如 userName 转为 USER_NAME
	  * @param line 源字符串
	  * @return 转换后的字符串
	  */
	 public static String camelUnderline(String line){
		return camelUnderline(line,null);
	 }
	 
	 /**
	  * 根据类型加前缀， 驼峰法转下划线，例如String userName 转为 C_USER_NAME, DATE date 转为 D_DATE
	  * @param line 源字符串
	  * @param dataType 类型
	  * @return 转换后的字符串
	  */
	 public static String camelUnderline(String line,String dataType){
		 PropertiesUtil pu = new PropertiesUtil("config");
		 String StringType = pu.getValue("StringType");
		 String DateType = pu.getValue("DateType");
		 String otherType = pu.getValue("otherType");
		 
		 if(StringUtils.isBlank(line)){
			   return "";
		}
		  line=String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
		  StringBuffer sb=new StringBuffer();
		  if (StringUtils.isNotBlank(dataType)) {
			  if ("String".equalsIgnoreCase(dataType)) {
				  	sb.append(StringType);
			  }else if ("Date".equalsIgnoreCase(dataType)) {
				  sb.append(DateType);
			 }else{
				  sb.append(otherType);
			  	}
			  }
		  Pattern pattern=Pattern.compile("[A-Z]([a-z\\d]+)?");
		  Matcher matcher=pattern.matcher(line);
		  while(matcher.find()){
			   String word=matcher.group();
			   sb.append(word.toUpperCase());
			   sb.append(matcher.end()==line.length()?"":"_");
		  }
		  return sb.toString();
	 }
	
	 
	 public static void main(String[] args) {
		  String line1="t_sys_user";
		  String line2="userName";
		  System.out.println(tableNameAsClassName(line1));
		  System.out.println(upperCaseByFirst(line2));
		  System.out.println(camelUnderline(line2,"String"));
	 }
	
	
	/**
	 * 表名转换成Java类名,传递有前缀名
	 */
	public static String tableToJava(String tableName, String tablePrefix) {
		if(StringUtils.isNotBlank(tablePrefix)){
			tableName = tableName.replace(tablePrefix, "");
		}else {
			tablePrefix = tableName.split("_")[0];
			tableName = tableName.replace("t", "");
			
		}
		return columnToJava(tableName);
	}
	
	/**
	 * 例如：c_user_name-->CUserName,  _user_name-->-->UserName
	 */
	public static String columnToJava(String columnName) {
		return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
	}
    /**
     * 读取txt文件的内容
     * @param file 读取的文件对象
     */
    public static String readFileToString(File file){
        StringBuilder result = new StringBuilder();
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator() + s);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
        	if(br != null) {
        		try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
        return result.toString();
    }
    
    /**
     * 保存为文件
     * @param filePath	保存的文件地址
     * @param fileName	保存的文件名
     * @param content	内容
     */
	public static void createFile(String filePath, String fileName, String content) {
		File dir = new File(filePath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(filePath +  File.separator + fileName);
		FileOutputStream fos = null;
		try {
			file.createNewFile();
			fos = new FileOutputStream(file);
			fos.write(content.getBytes(Constant.CHAR_SET));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
