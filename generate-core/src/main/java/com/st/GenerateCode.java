package com.st;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.st.entity.DetailField;
import com.st.entity.JavaEntity;
import com.st.entity.ListField;
import com.st.entity.SaveField;
import com.st.entity.SearchField;
import com.st.entity.TableEntity;
import com.st.util.Constant;
import com.st.util.PropertiesUtil;
import com.st.util.StringUtil;

/**
 * 测试生成代码
 *
 */
public class GenerateCode {
    public static void main(String[] args){
    	PropertiesUtil pu = new PropertiesUtil("config");
		String genaratePath = StringUtil.path(pu.getValue("filePath"));
		File file = new File(genaratePath);
    	String jsonString = StringUtil.readFileToString(file);
    	JSONObject jsonObject = JSONObject.parseObject(jsonString);
    	try {
			generatorCode(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	/**
	 * 生成代码
	 */
	public static void generatorCode(JSONObject prop) throws Exception{
		TableEntity tableEntity = new TableEntity();
		String module = prop.getString("module");
    	String tableName = prop.getString("tableName");
    	String entityName = StringUtil.upperCaseByFirst(prop.getString("entityName"));
    	//类型没有，默认将表名格式化为列名，例如t_user-->User
    	if (StringUtils.isBlank(entityName)) {
    		entityName = StringUtil.tableNameAsClassName(tableName);
    	}
    	String isPage = prop.getString("isPage");
    	if (!Constant.N.equalsIgnoreCase(isPage)) {
    		tableEntity.setIsPage(Constant.Y);
    	}
    	String needPart = prop.getString("needPart");
    	String[] part=null;
    	if (StringUtils.isNotBlank(needPart)) {
    		 part=needPart.split(",");
    	}
		tableEntity.setModule(module);
		tableEntity.setTableName(tableName);
		tableEntity.setEntityName(entityName);
		tableEntity.setNeedPart(part);
		
		//根据模块功能定制数据
		List<SearchField> searchList = new ArrayList<>();
		List<ListField> listList = new ArrayList<>();
		List<SaveField> saveList = new ArrayList<>();
		List<DetailField> detailList = new ArrayList<>();
		List<JavaEntity> javaEntityList = new ArrayList<>();
		JSONArray fieldsArray = prop.getJSONArray("fields");
		for (Object obj : fieldsArray) {
			JSONObject json = (JSONObject) obj;
			String name = json.getString("name");
			String code = json.getString("code");
			String formatterCode = StringUtil.upperCaseByFirst(code);
			String dataType = json.getString("dataType");
			String columnName = StringUtil.camelUnderline(code,dataType);
			String componentType = json.getString("componentType");
			
			//处理查询块，search约定空为不查询，0为精确，1为模糊，2为精确+模糊
			SearchField searchField = new SearchField();
			String search = json.getString("search");
			if (StringUtils.isNotBlank(search) && "012".contains(search)) {
				searchField.setCode(code);
				searchField.setName(name);
				searchField.setComponentType(componentType);
				searchField.setQueryType(search);
				searchList.add(searchField);
			}
			//处理列表块，list约定 空或者其他为输出，N为不输出
			ListField listField = new ListField();
			String list = json.getString("list");
			if (!Constant.N.equalsIgnoreCase(list)) {
				listField.setCode(code);
				listField.setName(name);
				listField.setComponentType(componentType);
				listList.add(listField);
			}
			
			//处理表单块，save约定 空或者其他为输出，N为不输出
			SaveField saveField = new SaveField();
			String save = json.getString("save");
			if (!Constant.N.equalsIgnoreCase(save)) {
				saveField.setCode(code);
				saveField.setName(name);
				saveField.setComponentType(componentType);
				saveList.add(saveField);
			}
			
			//处理详情块，detail约定 空或者其他为输出，N为不输出
			DetailField detailField = new DetailField();
			String detail = json.getString("detail");
			if (!Constant.N.equalsIgnoreCase(detail)) {
				detailField.setCode(code);
				detailField.setName(name);
				detailField.setComponentType(componentType);
				detailList.add(detailField);
			}
			
			//封装java类
			JavaEntity javaEntity = new JavaEntity();
			javaEntity.setCode(code);
			javaEntity.setFormatterCode(formatterCode);
			javaEntity.setName(name);
			javaEntity.setDataType(dataType);
			javaEntity.setColumnName(columnName);
			javaEntityList.add(javaEntity);
		}
		tableEntity.setListList(listList);
		tableEntity.setSaveList(saveList);
		tableEntity.setSearchList(searchList);
		tableEntity.setDetailList(detailList);
		tableEntity.setJavaEntityList(javaEntityList);
		
		PropertiesUtil pu = new PropertiesUtil("config");
		String packageName = pu.getValue("package");
		
		//封装模板数据
		Map<String, Object> map = new HashMap<>();
		map.put("packageName", packageName);
		map.put("module", module);
		map.put("tableName", tableEntity.getTableName());
		map.put("entityName", tableEntity.getEntityName());
		map.put("searchField", tableEntity.getSearchList());
		map.put("saveField", tableEntity.getSaveList());
		map.put("listField", tableEntity.getListList());
		map.put("isPage", tableEntity.getIsPage());
		map.put("javaEntity", tableEntity.getJavaEntityList());
        VelocityContext context = new VelocityContext(map);
        
        //获取模板列表
		List<String> templates = getTemplates(part);
		for(String template : templates){
			//渲染模板
			StringWriter sw = new StringWriter();
			Template tpl = Velocity.getTemplate(template, "UTF-8");
			tpl.merge(context, sw);
			//模板路径和名称处理
			String codePath =  StringUtil.path(pu.getValue("codePath"));
			String filePath = StringUtil.getFilePathAndName(template, entityName, packageName);
			int index = filePath.lastIndexOf(File.separator);
			String fileName = filePath.substring(index + 1);
			String path = filePath.substring(0, index);
			String fullPath = codePath + File.separator + path;
			//保存生成模板
			StringUtil.createFile(fullPath,fileName, sw.toString());
			
		/*	try {
				//添加到zip
				zip.putNextEntry(new ZipEntry(getFileName(template, "类名", packagePath)));  
				IOUtils.write(sw.toString(), zip, "UTF-8");
				IOUtils.closeQuietly(sw);
				zip.closeEntry();
			} catch (IOException e) {
				throw new RRException("渲染模板失败，表名：" + "表名", e);
			}*/
		}
	}
	
	
	public static List<String> getTemplates(String[] part){
		List<String> templates = new ArrayList<String>();
		//根据功能选择对应的前端模板
/*		if(null!=part){
			int len = part.length;
			for (int i = 0; i < len; i++) {
				if (part[i].contains("manage")) {
					templates.add("template/list.html.vm");
					templates.add("template/list.controller.js.vm");
				}
				if (part[i].contains("save")) {
					templates.add("template/save.html.vm");
					templates.add("template/save.controller.js.vm");
				}
				if (part[i].contains("detail")) {
					templates.add("template/detail.html.vm");
					templates.add("template/detail.controller.js.vm");
				}
			}
		}*/
		//后台模板
		templates.add("template/Entity.java.vm");
	/*	templates.add("template/Controller.java.vm");
		templates.add("template/Service.java.vm");
		templates.add("template/ServiceImpl.java.vm");
		templates.add("template/Dao.java.vm");*/
		return templates;
	}
	
}
