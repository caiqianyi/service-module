package com.lebaoxun.word.rest;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashSet;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.word.config.IWordService;
import com.lebaoxun.word.domain.SensitiveWordType;

@RequestMapping
@RestController
public class WordController {

	@Resource
	private IWordService wordService;

	@RequestMapping(value = "/word/export/{id}", method = RequestMethod.GET)
	void exportTxt(@PathVariable("id") String id, HttpServletResponse response)
			throws UnsupportedEncodingException {
		SensitiveWordType swt = wordService.getAllWord(id);

		String fileName = swt.getName();
		response.setContentType("text/plain");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ java.net.URLEncoder.encode(fileName, "UTF-8") + ".txt");// 导出中文名称;
		BufferedOutputStream buff = null;
		StringBuffer write = new StringBuffer();
		ServletOutputStream outSTr = null;
		try {
			outSTr = response.getOutputStream();// 建立
			buff = new BufferedOutputStream(outSTr);
			String tab = "\r\n";
			for(String word : swt.getWords()){
				write.append(word + tab);
			}
			buff.write(write.toString().getBytes("UTF-8"));
			buff.flush();
			buff.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				buff.close();
				outSTr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 增加敏感词
	 * 
	 * @param type
	 * @param word
	 * @return
	 */
	@RequestMapping(value = "/word/add", method = RequestMethod.POST)
	ResponseMessage addWord(@RequestParam("id") String id,
			@RequestParam("name") String name,
			@RequestParam("words") String[] words) {
		wordService
				.addWord(id, name, new HashSet<String>(Arrays.asList(words)));
		return ResponseMessage.ok();
	}

	/**
	 * 删除敏感词
	 * 
	 * @param type
	 * @param word
	 * @return
	 */
	@RequestMapping(value = "/word/remove", method = RequestMethod.GET)
	ResponseMessage removeWord(@RequestParam("type") String type,
			@RequestParam("word") String word) {
		return ResponseMessage.ok(wordService.removeWord(type, word));
	}

	/**
	 * 返回所有敏感词类型
	 * 
	 * @return
	 */
	@RequestMapping(value = "/word/type/getAll", method = RequestMethod.GET)
	ResponseMessage getAllWordType() {
		return ResponseMessage.ok(wordService.getAllWordType());
	}

	/**
	 * 是否在敏感词类型
	 * 
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/word/type/isExist", method = RequestMethod.GET)
	ResponseMessage isExistType(@RequestParam("type") String type) {
		return ResponseMessage.ok(wordService.isExistType(type));
	}

	/**
	 * 
	 * @param types
	 *            待检测词汇类型
	 * @param text
	 *            待检测词汇
	 * @param beginIndex
	 *            起始位置
	 * @param isAll
	 *            是否全盘检查，false = 当遇到敏感词则立即停止，true=检查整个句子，找出所有敏感词之后停止
	 * @return
	 */
	@RequestMapping(value = "/word/check", method = RequestMethod.GET)
	ResponseMessage checkSensitiveWord(
			@RequestParam(value = "types", required = false) String types[],
			@RequestParam("text") String text) {
		return ResponseMessage.ok(wordService.checkSensitiveWord(types, text));
	}
}
