package com.lebaoxun.word.service.hystrix;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lebaoxun.word.service.IWordService;

@Component
public class WordServiceHystrix implements IWordService {
	
	private Logger logger = LoggerFactory.getLogger(WordServiceHystrix.class);

	@Override
	public boolean isContaintSensitiveWord(String text) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<String> getSensitiveWordByWordType(String text) {
		// TODO Auto-generated method stub
		return null;
	}



}
