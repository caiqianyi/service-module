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
import com.lebaoxun.pay.domain.WxpayConfig;
import com.lebaoxun.pay.service.IWxpayConfigService;
import com.lebaoxun.soa.core.redis.IRedisCache;

@Service
public class WxpayConfigServiceImpl implements IWxpayConfigService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private IRedisCache redisCache;
	
	private static final String CACHEKEY = "wxpay:config:";
	
	@Override
	public List<WxpayConfig> getWxpayConfig() {
		// TODO Auto-generated method stub
		List<WxpayConfig> list = new ArrayList<WxpayConfig>();
		String key = CACHEKEY + "*";
		Set<String> sets = redisCache.searchKey(key);
		if(sets != null && !sets.isEmpty()){
			for(String account : sets){
				list.add((WxpayConfig) redisCache.getSys(account));
			}
		}
		return list;
	}

	@Override
	public WxpayConfig getWxpayConfig(String account) {
		// TODO Auto-generated method stub
		String key = CACHEKEY + account;
		if(!redisCache.exists(key)){
			logger.error("wxpay account '{}' config no exist!",account);
			throw new I18nMessageException("80001","支付账号不存在");
		}
		return (WxpayConfig) redisCache.getSys(key);
	}

	@Override
	public WxpayConfig getWxpayConfigByMchid(String mchid) {
		// TODO Auto-generated method stub
		String key = CACHEKEY + "*";
		Set<String> sets = redisCache.searchKey(key);
		if(sets != null && !sets.isEmpty()){
			for(String account : sets){
				WxpayConfig config = (WxpayConfig) redisCache.getSys(account);
				if(config.getMchid().equals(mchid)){
					return config;
				}
			}
		}
		logger.error("wxpay mchid '{}' config no exist!",mchid);
		throw new I18nMessageException("80001","支付账号不存在");
	}

	@Override
	public WxpayConfig setWxpayConfigByAppid(WxpayConfig config) {
		// TODO Auto-generated method stub
		String key = CACHEKEY + config.getAccount();
		config.setCreateTime(new Date());
		redisCache.setSys(key, config);
		return config;
	}

	@Override
	public boolean deleteWxpayConfig(String account) {
		// TODO Auto-generated method stub
		String key = CACHEKEY + account;
		redisCache.del(key);
		return !redisCache.exists(key);
	}

}
