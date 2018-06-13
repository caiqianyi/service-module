package com.lebaoxun.word.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import com.lebaoxun.word.domain.LocalWordType;
import com.lebaoxun.word.utils.SensitiveWordInit;

@Configuration
public class SensitivewordConfig {
	
	private Logger logger = LoggerFactory.getLogger(SensitivewordConfig.class);
	
	@Resource
	private IWordService wordService;
	
	@Bean
	int initWord(){
		LocalWordType[] wts = LocalWordType.values();
		for (LocalWordType wt : wts) {
			if(!wordService.isExistType(wt.getId())){
				wordService.addWord(wt.getId(), wt.getName(), wordLoad(wt));
			}
		}
		wordService.init();
		return 1;
	}
	
	@Scheduled(cron = "0/5 * * * * ?")  
    public void checkAndRefresh() {
		logger.debug("checkAndRefresh={}",new Date());
		wordService.init();
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
}
