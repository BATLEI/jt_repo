package com.jt.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.vo.ImageVO;

@Service
@PropertySource
("classpath:/properties/image.properties")
//@ConfigurationProperties(prefix = "image")  依赖get/set方法
public class FileServiceImpl implements FileService {
	//定义本地磁盘路径
	@Value("${image.localDirPath}")
	private String localDirPath;
	//定义虚拟路径名称
	@Value("${image.urlPath}")
	private String urlPath;
	
	/**
	 * 1.获取图片名称
	 * 2.校验是否为图片类型  jpg|png|gif
	 * 3. 校验是否为恶意程序 木马.exe.jpg
	 * 4.分文件保存  按照时间存储  yyyy/MM/dd
	 * 5.防止文件重名.  UUID 32位16进制数+毫秒数
	 * 
	 * 正则常用字符:
	 * 	1.^   标识..开始
	 *  2.$   标识以...结束
	 *  3.点.    任意单个字符
	 *  4.*	    表示任意个 0~~~~无穷
	 *  5.+	  表示 1~~~~无穷
	 *  6.\.  标识特殊字符.	
	 *  7.(xxx|xx|xxx)  代表分组  满足其中一个条件即可
	 */

	@Override
	public ImageVO updateFile(MultipartFile uploadFile) {
		ImageVO imageVO = new ImageVO();
		//1.获取图片名称  a.jpg  A.JPG
		String fileName = uploadFile.getOriginalFilename();
		//将字符统一转化为小写
		fileName = fileName.toLowerCase();
		
		//2.校验图片类型  使用正则表达式判断字符串.
		if(!fileName.matches("^.+\\.(jpg|png|gif)$")) {
			imageVO.setError(1); //表示上传有无
			return imageVO;
		}

		//3.判断是否为恶意程序
		try {
			BufferedImage bufferedImage = 
				ImageIO.read(uploadFile.getInputStream());
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			if(width==0 || height ==0) {
				imageVO.setError(1);
				return imageVO;
			}
			
			//4.时间转化为字符串 2019/5/31
			String dateDir = 
					new SimpleDateFormat("yyyy/MM/dd")
					.format(new Date());
			
			//5.准备文件夹  D:/1-jt/image/yyyy/MM/dd
			String localDir = localDirPath + dateDir;
			File dirFile = new File(localDir);
			if(!dirFile.exists()) {
				//如果文件不存在,则创建文件夹
				dirFile.mkdirs();
			}
			//b8a7ff05-8356-11e9-9997-e0d55e0fcfd8
			//6.使用UUID定义文件名称 uuid.jpg
			String uuid = 
			UUID.randomUUID().toString().replace("-","");
			//图片类型 a.jpg 动态获取 ".jpg"
			String fileType = 
			fileName.substring(fileName.lastIndexOf("."));
			
			//拼接新的文件名称
			//D:/1-jt/image/yyyy/MM/dd/文件名称.类型
			String realLocalPath = localDir+"/"+uuid+fileType;
			
			//7.2完成文件上传
			uploadFile.transferTo(new File(realLocalPath));
			
			//8.拼接url路径   http://image.jt.com/yyyy/MM/dd/uuid.jpg
			String realUrlPath = 
				   urlPath+dateDir+"/"+uuid+fileType;
			
			//将文件文件信息回传给页面
			imageVO.setError(0)
				   .setHeight(height)
				   .setWidth(width)
				   .setUrl(realUrlPath);
		} catch (Exception e) {
			e.printStackTrace();
			imageVO.setError(1);
			return imageVO;
		}

		return imageVO;
	}

}
