package com.jt.manage.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import com.jt.common.vo.PicUploadResult;
import com.jt.manage.service.FileService;

@Controller
public class FileController {
	@Autowired
	private FileService fileService;
	
	
	/**
	 * 要求文件上传完成后，再次跳转到文件上传页面
	 * 参数一定要与提交参数保持一致
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	/*@RequestMapping
	private String file(MultipartFile file) throws IllegalStateException, IOException{
		//准备文件上传路径
		String path="F:/upload";
		//判断文件夹是否存在
		File filePath=new File(path);
		if(!filePath.exists()){
			System.out.println("====================filePath.exists.1");
			//如果文件夹不存在，则创建一个文件夹
			filePath.mkdirs();
		}
		//获取文件名称
		String fileName=file.getOriginalFilename();
		
		System.out.println("文件名称:============================"+fileName);
		
		//实现文件上传
		file.transferTo(new File(filePath+"/"+fileName));
		System.out.println("**********************************文件上传成功!");
		return "redirect:/file.jsp";
	}*/
	
	//实现商品图片的上传
	@RequestMapping("/pic/upload")
	@ResponseBody
	public PicUploadResult uploadFile(MultipartFile uploadFile){
		return fileService.upload(uploadFile);
	}
	
	
	@RequestMapping("/file")
	private String file(MultipartFile file) throws IllegalStateException, IOException{
		String path="F:/upload";
		File filePath=new File(path);
		if(!filePath.exists()){
			filePath.mkdirs();
		}
		String fileName=file.getOriginalFilename();
		
		file.transferTo(new File(path+"/"+fileName));
		return "redirect:/file.jsp";
	}
	
	
	
	
	
}






















