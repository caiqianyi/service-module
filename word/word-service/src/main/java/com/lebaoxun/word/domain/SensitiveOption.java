package com.lebaoxun.word.domain;

import java.io.Serializable;
/**
 * 敏感项
 * @author Caiqianyi
 *
 */
public class SensitiveOption implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6371902175368021822L;
	private String type;
	private int index;
	private String word;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	
}
