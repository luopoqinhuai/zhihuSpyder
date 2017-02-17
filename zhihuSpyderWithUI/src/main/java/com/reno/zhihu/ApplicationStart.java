package com.reno.zhihu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ApplicationStart {
	
	 public ApplicationStart() {
         System.out.println("===============\n\n\n");
     }
	
	@RequestMapping("/")
	public String Index(){
		return "index2";
	}
}
