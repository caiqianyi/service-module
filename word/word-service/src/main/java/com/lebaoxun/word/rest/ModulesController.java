package com.lebaoxun.word.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ModulesController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping("/modules/{module}/{url}.html")
	public String module(@PathVariable("module") String module, @PathVariable("url") String url){
		logger.debug("module={},url={}",module,url);
		return "/modules/" + module + "/" + url;
	}
}
