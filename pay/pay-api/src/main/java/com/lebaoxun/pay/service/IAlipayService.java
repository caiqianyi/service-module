package com.lebaoxun.pay.service;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.pay.domain.AlipayConfig;
import com.lebaoxun.pay.service.hystrix.AlipayServiceHystrix;

@FeignClient(value="pay-service",fallback=AlipayServiceHystrix.class)
public interface IAlipayService {
	
	@RequestMapping(value = "/alipay/config/list", method = RequestMethod.GET)
	List<AlipayConfig> configList();
	
	@RequestMapping(value = "/alipay/config/info", method = RequestMethod.GET)
	ResponseMessage configInfo(@RequestParam("account")String account);
	
	@RequestMapping(value = "/alipay/config/save", method = RequestMethod.POST)
	ResponseMessage configSave(@RequestBody AlipayConfig config);
	
	@RequestMapping(value = "/alipay/config/delete", method = RequestMethod.POST)
	ResponseMessage configDelete(@RequestParam("account")String account);
	
	@RequestMapping(value = "/alipay/payment", method = RequestMethod.POST)
	ResponseMessage payment(@RequestParam("account")String account,
			@RequestParam("outTradeNo")String outTradeNo,
			@RequestParam("subject")String subject,
			@RequestParam("totalAmount")String totalAmount,
			@RequestParam(value="body",required=false)String body);
	
	@RequestMapping(value = "/alipay/notify")
	String aliNotify(@RequestParam Map<String, String> params);
}
