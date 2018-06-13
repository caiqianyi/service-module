package com.lebaoxun.word.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SensitiveWordType implements Serializable{
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 
	 */
	private static final long serialVersionUID = 8993244141943557478L;
	private String id;
	private String name;
	private List<String> words;
	private Long lastUpdateTime;
	private HashMap<?, ?> wordMap;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getWords() {
		return words;
	}

	public void setWords(List<String> words) {
		this.words = words;
	}
	
	public Long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap<?, ?> sensitiveWordToHashMap(Long lastUpdateTime) {
		if(wordMap == null || lastUpdateTime == null || !lastUpdateTime.equals(this.lastUpdateTime)){
			wordMap = new HashMap(words.size());     //初始化敏感词容器，减少扩容操作
			String key = null;  
			Map nowMap = null;
			Map<String, String> newWorMap = null;
			//迭代keyWordSet
			Iterator<String> iterator = words.iterator();
			while(iterator.hasNext()){
				key = iterator.next();    //关键字
				nowMap = wordMap;
				for(int i = 0 ; i < key.length() ; i++){
					char keyChar = key.charAt(i);       //转换成char型
					Object wordMap = nowMap.get(keyChar);       //获取
					
					if(wordMap != null){        //如果存在该key，直接赋值
						nowMap = (Map) wordMap;
					} else{     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
						newWorMap = new HashMap<String,String>();
						newWorMap.put("isEnd", "0");     //不是最后一个
						nowMap.put(keyChar, newWorMap);
						nowMap = newWorMap;
					}
					
					if(i == key.length() - 1){
						nowMap.put("isEnd", "1");    //最后一个
					}
				}
			}
		}
		return wordMap;
	}
	
}
