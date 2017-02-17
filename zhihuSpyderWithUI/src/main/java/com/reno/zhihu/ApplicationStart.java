package com.reno.zhihu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ApplicationStart {
	
	
	@RequestMapping("/")
	public String Index(){
		return "index2";
	}
	
	@RequestMapping("/upload")
	public String upload(){
		return "upload";
	}
}
