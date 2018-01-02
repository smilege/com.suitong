package com.st.entity;

import java.util.List;
import java.util.Map;

/** 
 * @author smile 
 * @version 创建时间：2017年12月29日 上午9:19:12 
 * 类说明  保存模块
 */
public class SaveField extends ParentField{
	private Map<Object,Object> regexMap;
	private List<String> regex;
	
	public Map<Object, Object> getRegexMap() {
		return regexMap;
	}
	public void setRegexMap(Map<Object, Object> regexMap) {
		this.regexMap = regexMap;
	}
	public List<String> getRegex() {
		return regex;
	}
	public void setRegex(List<String> regex) {
		this.regex = regex;
	}
	
	
}
