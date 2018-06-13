package com.lebaoxun.pay.service;

import java.util.List;

import com.lebaoxun.pay.domain.AlipayConfig;

/**
 * 支付宝开发者配置
 * @author DELL
 *
 */
public interface IAlipayConfigService {

	/**
	 * 获取所有alipay配置
	 * @return
	 */
	List<AlipayConfig> getAlipayConfig();
	
	/**
	 * 获取单个配置详情
	 * @param account 信息账号别名
	 * @return
	 */
	AlipayConfig getAlipayConfig(String account);
	
	/**
	 * 获取单个配置详情
	 * @param appid 支付宝分配给开发者的应用ID
	 * @return
	 */
	AlipayConfig getAlipayConfigByAppid(String appid);
	
	/**
	 * 设置开发者配置，如存在相同account，则替换，反之则新增
	 * @param config
	 * @return
	 */
	AlipayConfig setAlipayConfigByAppid(AlipayConfig config);
	
	/**
	 * 删除配置
	 * @param account 
	 * @return
	 */
	boolean deleteAlipayConfig(String account);
}
