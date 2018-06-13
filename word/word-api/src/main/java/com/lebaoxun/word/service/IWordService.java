package com.lebaoxun.word.service;

import java.util.Set;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lebaoxun.word.service.hystrix.WordServiceHystrix;

@FeignClient(value="word-service",fallback=WordServiceHystrix.class)
public interface IWordService {
	
	
	/**
     * 是否包含敏感词
     * 
     * @param txt 检测文字
     * @param wordType 字库类型
     * @return
     */
	@RequestMapping(value="/word/isContaintSensitiveWord/{text}",method=RequestMethod.GET)
    boolean isContaintSensitiveWord(@PathVariable("text") String text);
    
    /**
     * 获取敏感词内容
     * 
     * @param txt
     * @param wordType 字库类型
     * @return 敏感词内容
     */
	@RequestMapping(value="/word/getSensitiveWordByWordType/{text}",method=RequestMethod.GET)
    Set<String> getSensitiveWordByWordType(@PathVariable("text") String text);
}
