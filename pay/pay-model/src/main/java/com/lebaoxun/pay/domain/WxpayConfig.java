package com.lebaoxun.pay.domain;

import java.io.Serializable;
import java.util.Date;

public class WxpayConfig implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3033022900682925884L;
	private String account;
	private String appid;
	private String mchid;
	private String secret;
	private String notifyUrl;
	private String queueName;
	private Date createTime;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMchid() {
		return mchid;
	}
	public void setMchid(String mchid) {
		this.mchid = mchid;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
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
}
