package com.lebaoxun.pay.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.pay.domain.AlipayConfig;
import com.lebaoxun.pay.service.IAlipayConfigService;
import com.lebaoxun.soa.core.redis.IRedisCache;

@Service
public class AlipayConfigServiceImpl implements IAlipayConfigService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private IRedisCache redisCache;
	
	private static final String CACHEKEY = "alipay:config:";
	
	@Override
	public List<AlipayConfig> getAlipayConfig() {
		// TODO Auto-generated method stub
		List<AlipayConfig> list = new ArrayList<AlipayConfig>();
		String key = CACHEKEY + "*";
		Set<String> sets = redisCache.searchKey(key);
		if(sets != null && !sets.isEmpty()){
			for(String account : sets){
				list.add((AlipayConfig) redisCache.getSys(account));
			}
		}
		return list;
	}

	@Override
	public AlipayConfig getAlipayConfig(String account){
		String key = CACHEKEY + account;
		if(!redisCache.exists(key)){
			logger.error("alipay account '{}' config no exist!",account);
			throw new I18nMessageException("80001","支付账号不存在");
		}
		return (AlipayConfig) redisCache.getSys(key);
	}

	@Override
	public AlipayConfig getAlipayConfigByAppid(String appid) {
		// TODO Auto-generated method stub
		String key = CACHEKEY + "*";
		Set<String> sets = redisCache.searchKey(key);
		if(sets != null && !sets.isEmpty()){
			for(String account : sets){
				AlipayConfig config = (AlipayConfig) redisCache.getSys(account);
				if(config.getAppid().equals(appid)){
					return config;
				}
			}
		}
		logger.error("alipay appid '{}' config no exist!",appid);
		throw new I18nMessageException("80001","支付账号不存在");
	}

	@Override
	public AlipayConfig setAlipayConfigByAppid(AlipayConfig config) {
		// TODO Auto-generated method stub
		String key = CACHEKEY + config.getAccount();
		config.setCreateTime(new Date());
		redisCache.setSys(key, config);
		return config;
	}
	
	@Override
	public boolean deleteAlipayConfig(String account) {
		// TODO Auto-generated method stub
		String key = CACHEKEY + account;
		redisCache.del(key);
		return !redisCache.exists(key);
	}

}
