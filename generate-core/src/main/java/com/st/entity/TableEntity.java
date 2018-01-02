package com.st.entity;

import java.util.List;

/** 
 * @author smile 
 * @version 创建时间：2017年12月29日 上午9:11:07 
 * 类说明  模板数据
 */
public class TableEntity {
	
	private String module;
	private String tableName;
	private String entityName;
	private String isPage;
	private List<SearchField> searchList;
	private List<SaveField> saveList;
	private List<ListField> listList;
	private List<DetailField> detailList;
	private List<JavaEntity> javaEntityList;
	private List<String> needPart;
	
	
	
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getIsPage() {
		return isPage;
	}
	public void setIsPage(String isPage) {
		this.isPage = isPage;
	}
	public List<SearchField> getSearchList() {
		return searchList;
	}
	public void setSearchList(List<SearchField> searchList) {
		this.searchList = searchList;
	}
	public List<SaveField> getSaveList() {
		return saveList;
	}
	public void setSaveList(List<SaveField> saveList) {
		this.saveList = saveList;
	}
	public List<ListField> getListList() {
		return listList;
	}
	public void setListList(List<ListField> listList) {
		this.listList = listList;
	}
	public List<JavaEntity> getJavaEntityList() {
		return javaEntityList;
	}
	public void setJavaEntityList(List<JavaEntity> javaEntityList) {
		this.javaEntityList = javaEntityList;
	}
	
	public List<String> getNeedPart() {
		return needPart;
	}
	public void setNeedPart(List<String> needPart) {
		this.needPart = needPart;
	}
	public List<DetailField> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<DetailField> detailList) {
		this.detailList = detailList;
	}
	
}
