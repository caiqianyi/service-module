package com.lebaoxun.sms.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.sms.service.hystrix.SMSGatewayServiceHystrix;

@FeignClient(value="sms-service",fallback=SMSGatewayServiceHystrix.class)
public interface ISMSGatewayService {
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/sms/send/{cst_id}/{mobile}/{template_id}/{sign}")
	ResponseMessage send(@PathVariable("mobile") String mobile,
			@PathVariable("template_id") String template_id,
			@PathVariable("cst_id")String cst_id,
			@PathVariable("sign")String sign,
			@RequestParam(value="data")String[] data);
		
	@RequestMapping(method = RequestMethod.GET, value = "/sms/checkVfCode/{mobile}/{vfCode}")
	ResponseMessage checkVfCode(@PathVariable("mobile") String mobile,
			@PathVariable("vfCode") String vfCode);
}
