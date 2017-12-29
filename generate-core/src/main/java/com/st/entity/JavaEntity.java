package com.st.entity;
/** 
 * @author smile 
 * @version 创建时间：2017年12月29日 下午1:18:57 
 * 类说明 
 */
public class JavaEntity extends ParentField {
	private String dataType;
	private String columnName;
	private String formatterCode;

	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getColumnNameS() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getFormatterCode() {
		return formatterCode;
	}
	public void setFormatterCode(String formatterCode) {
		this.formatterCode = formatterCode;
	}
}
