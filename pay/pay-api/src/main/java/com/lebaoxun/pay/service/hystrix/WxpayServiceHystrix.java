package com.lebaoxun.pay.service.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.pay.domain.WxpayConfig;
import com.lebaoxun.pay.service.IWxpayService;
@Component
public class WxpayServiceHystrix implements IWxpayService {

	@Override
	public List<WxpayConfig> configList() {
		// TODO Auto-generated method stub
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage configInfo(String account) {
		// TODO Auto-generated method stub
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage configSave(WxpayConfig config) {
		// TODO Auto-generated method stub
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage configDelete(String account) {
		// TODO Auto-generated method stub
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage payment(String spbill_create_ip, String orderNo,
			String descr, Integer totalFee, String attach, String account,
			String openid) {
		// TODO Auto-generated method stub
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage h5Payment(String wapUrl, String wapName,
			String spbill_create_ip, String orderNo, String descr,
			Integer totalFee, String attach, String account) {
		// TODO Auto-generated method stub
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage qrcodePayment(String spbill_create_ip,
			String orderNo, String descr, Integer totalFee, String attach,
			String account) {
		// TODO Auto-generated method stub
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public String notify(String body) {
		// TODO Auto-generated method stub
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public String h5Notify(String body) {
		// TODO Auto-generated method stub
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage query(String out_trade_no, String account,
			String send) {
		// TODO Auto-generated method stub
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

}
