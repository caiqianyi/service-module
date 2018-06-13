package com.lebaoxun.pay.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付宝支付  账户信息配置
 * @author Caiqianyi
 *
 */
public class AlipayConfig implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3787807608857629833L;
	/**
	 * 支付宝分配给开发者的应用ID
	 */
	private String account;
	private String appid;
	private String privateKey;
	private String publicKey;
	private String mercNo;
	private String returnUrl;
	private String notifyUrl;
	private String queueName;
	private Date createTime;
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getMercNo() {
		return mercNo;
	}
	public void setMercNo(String mercNo) {
		this.mercNo = mercNo;
	}
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
}