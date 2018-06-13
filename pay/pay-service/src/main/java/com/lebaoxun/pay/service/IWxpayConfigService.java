package com.lebaoxun.pay.service;

import java.util.List;

import com.lebaoxun.pay.domain.WxpayConfig;

public interface IWxpayConfigService {
	/**
	 * 获取所有wxpay配置
	 * @return
	 */
	List<WxpayConfig> getWxpayConfig();
	
	/**
	 * 获取单个配置详情
	 * @param account 信息账号别名
	 * @return
	 */
	WxpayConfig getWxpayConfig(String account);
	
	/**
	 * 获取单个配置详情
	 * @param mchid 支付宝分配给开发者的应用ID
	 * @return
	 */
	WxpayConfig getWxpayConfigByMchid(String mchid);
	
	/**
	 * 设置开发者配置，如存在相同account，则替换，反之则新增
	 * @param config
	 * @return
	 */
	WxpayConfig setWxpayConfigByAppid(WxpayConfig config);
	
	/**
	 * 删除配置
	 * @param account 
	 * @return
	 */
	boolean deleteWxpayConfig(String account);
}
