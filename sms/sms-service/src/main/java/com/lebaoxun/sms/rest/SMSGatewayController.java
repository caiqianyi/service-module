package com.lebaoxun.sms.rest;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.commons.utils.Assert;
import com.lebaoxun.sms.core.RedisKeyConstant;
import com.lebaoxun.soa.core.redis.IRedisCache;
import com.lebaoxun.soa.core.redis.IRedisHash;

@RestController
@RequestMapping("/sms/install")
public class SMSGatewayController {
	
	@Resource
	private IRedisHash redisHash;
	
	@Resource
	private IRedisCache redisCache;
	
	@RequestMapping(method = RequestMethod.GET, value = "/clearFreeze/{mobile}/")
	ResponseMessage clearFreeze(@PathVariable("mobile")String mobile){
		if("*".equals(mobile)){
			redisCache.del("sms:send:count*");
		}else{
			redisCache.del(String.format(RedisKeyConstant.SMS_SEND_COUNT_SECOND_MOBILE,"*",mobile));
		}
		redisCache.del(String.format(RedisKeyConstant.SMS_FREEZE_LIST_MOBILES,mobile));
		return new ResponseMessage();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/clearBlack/{mobile}/")
	ResponseMessage clearBlack(@PathVariable("mobile")String mobile){
		redisHash.hDel(RedisKeyConstant.HASH_SMS_BLACKLIST_MOBILES,mobile);
		Assert.isTrue(redisHash.hExists(RedisKeyConstant.HASH_SMS_BLACKLIST_MOBILES, mobile), "500" , "黑名单删除失败");
		return new ResponseMessage();
	}
}
