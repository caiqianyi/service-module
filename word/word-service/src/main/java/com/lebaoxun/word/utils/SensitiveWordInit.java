package com.lebaoxun.word.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.lebaoxun.word.domain.LocalWordType;

/**
 * @Description: 初始化敏感词库，将敏感词加入到HashMap中，构建DFA算法模型
 * @Author : caiqianyi
 * @Date ： 2017年10月17日 下午 14:00:06
 * @version 1.0
 */
public class SensitiveWordInit {
	
	private LocalWordType wordType;
	
	@SuppressWarnings("rawtypes")
	private HashMap sensitiveWordMap;
	
	public SensitiveWordInit(LocalWordType wordType){
		this.wordType = wordType;
		this.initKeyWord();
	}
	
	public HashMap getSensitiveWordMap() {
		return sensitiveWordMap;
	}

	public Set<String> wordLoad(LocalWordType wordType){
		Set<String> set = new TreeSet<String>();
		InputStream in = null;
		BufferedReader bf = null;
		try {
			in = SensitiveWordInit
					.class.getResourceAsStream( 
							"/" + wordType.getName() + "词库.txt");
			
			bf = new BufferedReader(new InputStreamReader(in, "GBK"));
			String line = "";
			while ((line = bf.readLine()) != null) {
				set.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try{
				if(in != null){
					in.close();
				}
				if(bf != null){
					bf.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return set;
	}
	
	@SuppressWarnings("rawtypes")
	private Map initKeyWord(){
		try {
			//读取敏感词库
			Set<String> keyWordSet = wordLoad(wordType);
			//将敏感词库加入到HashMap中
			addSensitiveWordToHashMap(keyWordSet);
			//spring获取application，然后application.setAttribute("sensitiveWordMap",sensitiveWordMap);
			System.out.println(sensitiveWordMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sensitiveWordMap;
	}
	
	/**
	 * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：<br>
	 * 中 = {
	 *      isEnd = 0
	 *      国 = {<br>
	 *      	 isEnd = 1
	 *           人 = {isEnd = 0
	 *                民 = {isEnd = 1}
	 *                }
	 *           男  = {
	 *           	   isEnd = 0
	 *           		人 = {
	 *           			 isEnd = 1
	 *           			}
	 *           	}
	 *           }
	 *      }
	 *  五 = {
	 *      isEnd = 0
	 *      星 = {
	 *      	isEnd = 0
	 *      	红 = {
	 *              isEnd = 0
	 *              旗 = {
	 *                   isEnd = 1
	 *                  }
	 *              }
	 *      	}
	 *      }
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addSensitiveWordToHashMap(Set<String> keyWordSet) {
		sensitiveWordMap = new HashMap(keyWordSet.size());     //初始化敏感词容器，减少扩容操作
		String key = null;  
		Map nowMap = null;
		Map<String, String> newWorMap = null;
		//迭代keyWordSet
		Iterator<String> iterator = keyWordSet.iterator();
		while(iterator.hasNext()){
			key = iterator.next();    //关键字
			nowMap = sensitiveWordMap;
			for(int i = 0 ; i < key.length() ; i++){
				char keyChar = key.charAt(i);       //转换成char型
				Object wordMap = nowMap.get(keyChar);       //获取
				
				if(wordMap != null){        //如果存在该key，直接赋值
					nowMap = (Map) wordMap;
				}
				else{     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
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

}
