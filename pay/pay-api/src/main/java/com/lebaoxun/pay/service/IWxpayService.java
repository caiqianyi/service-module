package com.lebaoxun.pay.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.pay.domain.WxpayConfig;
import com.lebaoxun.pay.service.hystrix.WxpayServiceHystrix;

@FeignClient(value="pay-service",fallback=WxpayServiceHystrix.class)
public interface IWxpayService {
	
	@RequestMapping(value = "/wxpay/config/list", method = RequestMethod.GET)
	List<WxpayConfig> configList();
	
	@RequestMapping(value = "/wxpay/config/info", method = RequestMethod.GET)
	ResponseMessage configInfo(@RequestParam("account")String account);
	
	@RequestMapping(value = "/wxpay/config/save", method = RequestMethod.POST)
	ResponseMessage configSave(@RequestBody WxpayConfig config);
	
	@RequestMapping(value = "/wxpay/config/delete", method = RequestMethod.POST)
	ResponseMessage configDelete(@RequestParam("account")String account);
	
	/**
	 * 微信公众号支付付款
	 * 
	 * @return JsonObject
	 * @throws Exception
	 */
	@RequestMapping(value="/wxpay/payment", method = RequestMethod.POST)
	ResponseMessage payment(@RequestParam("spbill_create_ip")String spbill_create_ip, @RequestParam("orderNo")String orderNo, 
			@RequestParam("descr")String descr, @RequestParam("totalFee")Integer totalFee, 
			@RequestParam("attach")String attach, @RequestParam("account")String account, 
			@RequestParam("openid")String openid);
	
	/**
	 * 微信H5付款
	 * @param wapUrl
	 * @param wapName
	 * @param spbill_create_ip
	 * @param orderNo
	 * @param descr
	 * @param totalFee
	 * @param attach
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/wxpay/payment/h5", method = RequestMethod.POST)
	ResponseMessage h5Payment(@RequestParam("wapUrl") String wapUrl, 
			@RequestParam("wapName") String wapName, @RequestParam("spbill_create_ip") String spbill_create_ip, 
			@RequestParam("orderNo") String orderNo, @RequestParam("descr") String descr, 
			@RequestParam("totalFee") Integer totalFee, @RequestParam("attach") String attach, 
			@RequestParam("account") String account);
	
	@RequestMapping(value="/wxpay/payment/qrcode", method = RequestMethod.POST)
	ResponseMessage qrcodePayment(
			@RequestParam("spbill_create_ip") String spbill_create_ip, @RequestParam("orderNo") String orderNo, 
			@RequestParam("descr") String descr, @RequestParam("totalFee") Integer totalFee, 
			@RequestParam("attach") String attach, @RequestParam("account") String account);
	
	@RequestMapping(value="/wxpay/notify")
	String notify(@RequestBody String body);
	
	@RequestMapping(value="/wxpay/h5/notify")
	String h5Notify(@RequestBody String body);
	
	@RequestMapping(value="/wxpay/query", method = RequestMethod.GET)
	ResponseMessage query(@RequestParam("out_trade_no")String out_trade_no, @RequestParam("account")String account,
			@RequestParam(value="send",required=false)String send);
}
