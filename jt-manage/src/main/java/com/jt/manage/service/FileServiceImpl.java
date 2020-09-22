package com.jt.manage.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;


@Service
public class FileServiceImpl implements FileService{
	//定义文件存储的根目录
	@Value("${image.localPath}")
	private String localPath;
	
	
	//定义虚拟路径的根目录
	@Value("${image.urlPath}")
	private String urlPath;
	
	
	
	
	
	
	
	
	
	
	@Override
	public PicUploadResult upload(MultipartFile uploadFile) {
		PicUploadResult result=new PicUploadResult();
		//1.判断是否是图片,正则表达式
		//获取图片名称
		String fileName=uploadFile.getOriginalFilename();
		fileName=fileName.toLowerCase();//全部转换成小写
		if(!fileName.matches("^.*(jpg|png|gif|bmp)$")){
			result.setError(1);
		}
		//2.判断是否是恶意程序，使用BufferedImage转换图片，能获取宽度和高度
		try {
			BufferedImage bufferedImage=ImageIO.read(uploadFile.getInputStream());
			int height=bufferedImage.getHeight();
			int width=bufferedImage.getWidth();
			if(height==0||width==0){
				result.setError(1);
				return result;
			}
			//3.不能将图片保存到同一个文件夹下，使用分文件夹存储，yyyy/MM/dd
			String DatePath=new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			String picDir=localPath+DatePath;
			File picFile=new File(picDir);
			if(!picFile.exists()){
				picFile.mkdirs();
			}
			//4.图片的重名问题,UUID+三位随机数区分图片
			String uuid=UUID.randomUUID().toString().replaceAll("-", "");
			int randomNum=new Random().nextInt(1000);
			String fileType=fileName.substring(fileName.lastIndexOf("."));
			fileName=uuid+randomNum+fileType;
			
			//实现文件上传
			String realFilePath=picFile+"/"+fileName;
			uploadFile.transferTo(new File(realFilePath));
			//将真实数据回显
			result.setHeight("50");
			result.setWidth("30");
			
			
			
			//实现虚拟路径的拼接
			String realUrl=urlPath+DatePath+"/"+fileName;
			result.setUrl(realUrl);
			
			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
			result.setError(1);
		}
		return result;
	}
	
}
