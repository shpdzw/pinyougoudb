package com.pinyougou.shop.controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import entity.Result;
import util.FastDFSClient;

/**
 * 文件上传 Controller
 * 
 * @author 张巍
 *
 */
@RestController
public class UploadControlle {


	@Value("${FILE_SERVER_URL}")
	private String file_server_url;

	@RequestMapping("/upload")
	public Result upload(MultipartFile file) {// 接受的是上传的文件 前端传递给后端的文件对象
		// 获取文件名 (全名称)
		String originalFilename = file.getOriginalFilename();//
		// 得到扩展名 扩展名不带“.”
		String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

		try {
			// 构建客户端对象
			util.FastDFSClient client = new FastDFSClient("classpath:config/fdfs_client.conf");
			// 上传文件
			String fileId = client.uploadFile(file.getBytes(), extName);
			// 图片完整地址
			String url = file_server_url + fileId;
			// 返回给前端
			return new Result(true, url);

		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "上传失败");
		}

	}
}
