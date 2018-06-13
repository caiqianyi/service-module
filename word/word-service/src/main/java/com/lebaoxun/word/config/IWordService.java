package com.lebaoxun.word.config;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lebaoxun.word.domain.SensitiveOption;
import com.lebaoxun.word.domain.SensitiveWordType;


public interface IWordService {
	
	void init();
	
	/**
	 * 增加敏感词
	 * @param type
	 * @param word
	 * @return
	 */
	SensitiveWordType addWord(String type,String name,Set<String> word);
    
    /**
     * 删除敏感词
     * @param type
     * @param word
     * @return
     */
    String removeWord(String type,String word);
    
    /**
     * 所有某一个分类敏感词
     * @param type
     * @param size
     * @param offset
     * @return
     */
    SensitiveWordType getAllWord(String type);
    
    /**
     * 返回所有敏感词类型
     * @return
     */
    Map<String,Object> getAllWordType();
    
    /**
     * 是否在敏感词类型
     * @param type
     * @return
     */
    boolean isExistType(String type);
    /**
     * 
     * @param types 待检测词汇类型
     * @param text 待检测词汇
     * @param beginIndex 起始位置
     * @param isAll 是否全盘检查，false = 当遇到敏感词则立即停止，true=检查整个句子，找出所有敏感词之后停止
     * @return
     */
    List<SensitiveOption> checkSensitiveWord(String types[],String text);
    
}
