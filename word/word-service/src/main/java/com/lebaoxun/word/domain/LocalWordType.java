package com.lebaoxun.word.domain;

public enum LocalWordType {
	
	SeQing("1","色情"),
	BaoKong("2","暴恐"),
	FanDong("3","反动"),
	MinSheng("4","民生"),
	Other("5","其他"),
	SelfDefined("6","自定义"),
	TanFu("7","贪腐");
	
	private String id;
	private String name;
	
	private LocalWordType(String id,String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
