package com.lebaoxun.word.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.soa.core.redis.IRedisHash;
import com.lebaoxun.word.config.IWordService;
import com.lebaoxun.word.domain.SensitiveOption;
import com.lebaoxun.word.domain.SensitiveWordType;

@Service
public class WordServiceImpl implements IWordService {

	private final static String SENSITIVE_WORD_TYPE_HASH = "sensitive:word";
	
	private Logger logger = LoggerFactory.getLogger(WordServiceImpl.class);
	
	@Resource
	private IRedisHash redisHash;
	
	private Map<String,SensitiveWordType> map;
	
	private Map<String,Object> getAllTypes(){
		Map<String,Object> map = redisHash.hGetAll(SENSITIVE_WORD_TYPE_HASH);
		if(map.isEmpty()){
			return null;
		}
		return map;
	}
	
	private List<SensitiveWordType> getSensitiveWordTypes(String types[]){
		List<SensitiveWordType> swts = new ArrayList<SensitiveWordType>();
		if(types == null || types.length == 0){
			types = getAllTypes().keySet().toArray(new String[]{});
		}
		if(types != null && types.length != 0){
			for(int i=0;i<types.length;i++){
				String type = types[i];
				if(map.containsKey(type)){
					swts.add(map.get(type));
				}else{
					throw new I18nMessageException("-1","没有定义此敏感词类型");
				}
			}
		}
		return swts;
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		String types[] = getAllTypes().keySet().toArray(new String[]{});
		if(types != null && types.length != 0){
			for(int i=0;i<types.length;i++){
				String type = types[i];
				SensitiveWordType swt = (SensitiveWordType) redisHash.hGet(SENSITIVE_WORD_TYPE_HASH, type);
				if(map == null){
					map = new HashMap<String,SensitiveWordType>();
				}
				if(map.containsKey(type)){
					SensitiveWordType swt2 = map.get(type);
					swt2.setWords(swt.getWords());
					swt2.sensitiveWordToHashMap(swt.getLastUpdateTime());
					swt = swt2;
				}else{
					swt.sensitiveWordToHashMap(null);
				}
				map.put(type, swt);
			}
		}
	}

	@Override
	public SensitiveWordType addWord(String type, String name, Set<String> word) {
		SensitiveWordType swt = null;
		// TODO Auto-generated method stub
		if(!redisHash.hExists(SENSITIVE_WORD_TYPE_HASH, type)){
			swt = new SensitiveWordType();
			swt.setId(type);
			swt.setWords(new ArrayList<String>());
		}else{
			swt = (SensitiveWordType) redisHash.hGet(SENSITIVE_WORD_TYPE_HASH, type);
		}
		swt.setName(name);
		swt.setLastUpdateTime(new Date().getTime());
		swt.getWords().addAll(word);
		redisHash.hSet(SENSITIVE_WORD_TYPE_HASH, type, swt);
		return swt;
	}

	@Override
	public String removeWord(String type, String word) {
		// TODO Auto-generated method stub
		if(!redisHash.hExists(SENSITIVE_WORD_TYPE_HASH, type)){
			throw new I18nMessageException("-1","没有定义此敏感词类型");
		}
		SensitiveWordType swt = (SensitiveWordType) redisHash.hGet(SENSITIVE_WORD_TYPE_HASH, type);
		swt.getWords().remove(word);
		swt.setLastUpdateTime(new Date().getTime());
		redisHash.hSet(SENSITIVE_WORD_TYPE_HASH, type, swt);
		return word;
	}

	@Override
	public SensitiveWordType getAllWord(String type) {
		// TODO Auto-generated method stub
		if(!redisHash.hExists(SENSITIVE_WORD_TYPE_HASH, type)){
			throw new I18nMessageException("-1","没有定义此敏感词类型");
		}
		SensitiveWordType swt = (SensitiveWordType) redisHash.hGet(SENSITIVE_WORD_TYPE_HASH, type);
		return swt;
	}

	@Override
	public Map<String, Object> getAllWordType() {
		// TODO Auto-generated method stub
		return getAllTypes();
	}
	
	@Override
	public boolean isExistType(String type) {
		// TODO Auto-generated method stub
		return redisHash.hExists(SENSITIVE_WORD_TYPE_HASH, type);
	}

	@Override
	public List<SensitiveOption> checkSensitiveWord(String[] types,
			String text) {
		long beginTime = System.currentTimeMillis();

		List<SensitiveOption> result = new ArrayList<SensitiveOption>();
		for (int i = 0; i < text.length(); i++) {
			String r1 = checkSensitiveWord(types, text, i, true);
			if (r1 != null) {
				int length = Integer.parseInt(r1.split(",")[0]);
				String type = r1.split(",")[1];
				
				int end = i + length;
				if(end > text.length()){
					end = text.length();
				}
				// 将检测出的敏感词保存到集合中
				String word = text.substring(i, end);
				SensitiveOption option = new SensitiveOption();
				option.setIndex(i);
				option.setType(type);
				option.setWord(word);
				result.add(option);
				i = end - 1;
			}
		}
		long endTime = System.currentTimeMillis();
		logger.debug("总共消耗时间为：{}",endTime - beginTime);
		return result;
	}
	
	private String checkSensitiveWord(String[] types, String txt,
			int beginIndex, boolean isAll) {
		boolean  flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况
		int matchFlag = 0;     //匹配标识数默认为0
		char word = 0;
		List<SensitiveWordType> wts = getSensitiveWordTypes(types);
		String type = null;
		for(SensitiveWordType wt : wts){
			Map nowMap = wt.sensitiveWordToHashMap(wt.getLastUpdateTime());
			boolean isBreak = false;
			for(int i = beginIndex; i < txt.length() ; i++){
				word = txt.charAt(i);
				nowMap = (Map) nowMap.get(word);     //获取指定key
				logger.debug("word={},i={},txt={}",word,i,txt);
				if(nowMap != null){     //存在，则判断是否为最后一个
					matchFlag++;     //找到相应key，匹配标识+1 
					type = wt.getId();
					if("1".equals(nowMap.get("isEnd"))){       //如果为最后一个匹配规则,结束循环，返回匹配标识数
						flag = true;       //结束标志位为true   
						if(isAll){    //最小规则，直接返回,最大规则还需继续查找
							isBreak = true;
							break;
						}
					}
				} else{     //不存在，直接返回
					break;
				}
			}
			if(isBreak){
				break;
			}
		}
		if(!flag){        //长度必须大于等于1，为词 
			matchFlag = 0;
		}
		if(matchFlag > 0){
			return matchFlag+","+type;
		}
		return null;
	}

}
