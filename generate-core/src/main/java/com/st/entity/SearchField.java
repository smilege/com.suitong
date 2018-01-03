package com.st.entity;
/** 
 * @author smile 
 * @version 创建时间：2017年12月29日 上午9:12:52 
 * 类说明  查询模块
 */
public class SearchField extends ParentField{
	private String queryType;//0,1,2
	private String dataBaseColunmName; 
	
	
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getDataBaseColunmName() {
		return dataBaseColunmName;
	}
	public void setDataBaseColunmName(String dataBaseColunmName) {
		this.dataBaseColunmName = dataBaseColunmName;
	}
	
	
	
}
