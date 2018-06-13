package com.lebaoxun.pay.service.hystrix;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.pay.domain.AlipayConfig;
import com.lebaoxun.pay.service.IAlipayService;

@Component
public class AlipayServiceHystrix implements IAlipayService {
	
	@Override
	public List<AlipayConfig> configList() {
		// TODO Auto-generated method stub
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage configInfo(String account) {
		// TODO Auto-generated method stub
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage configSave(AlipayConfig config) {
		// TODO Auto-generated method stub
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage configDelete(String account) {
		// TODO Auto-generated method stub
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage payment(String account, String outTradeNo,
			String subject, String totalAmount, String body) {
		// TODO Auto-generated method stub
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}
	
	@Override
	public String aliNotify(Map<String, String> params) {
		// TODO Auto-generated method stub
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

}
