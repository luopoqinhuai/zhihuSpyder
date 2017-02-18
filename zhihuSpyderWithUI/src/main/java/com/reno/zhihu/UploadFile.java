package com.reno.zhihu;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class UploadFile {
	/*
     *采用spring提供的上传文件的方法
     */
	
	@Value("${cmd.dir}")
	private String baseURL;
	
    @RequestMapping("/springUpload")
    public ModelAndView  springUpload(HttpServletRequest request) throws IllegalStateException, IOException
    {
    	
         
         String path=null;
         
         //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request))
        {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
           //获取multiRequest 中所有的文件名
            Iterator iter=multiRequest.getFileNames();
             
            while(iter.hasNext())
            {
                //一次遍历所有文件
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                if(file!=null)
                {
                	String basepath=request.getSession().getServletContext().getRealPath("uploadfile");
                	System.out.println(basepath);
                    path=basepath+"/"+System.nanoTime()+file.getOriginalFilename();
                    //上传
                    file.transferTo(new File(path));
                }
                 
            }
           
        }
        String res=null;
        if(path!=null){
        	String execCMD=baseURL+" "+path;
        	Process p = Runtime.getRuntime().exec(execCMD);
    		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
    		
    		StringBuffer sb=new StringBuffer();
    		int index=-1;
    		char[] cache=new char[1024];
    		while((index=in.read(cache))!=-1){
    			sb.append(new String(cache,0,index));
    		}
    		
    		
    		res=new String(sb).replace("\n", "<br>");
    		
    		
        }
    
      
        HashMap<String ,String> mps=new HashMap<String, String>();
        mps.put("times",res);
      
    return new ModelAndView("uploadend",mps);
    }
}
