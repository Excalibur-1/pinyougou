package com.pinyougou.shop.controller;

import constants.Constants;
import entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import util.FastDfsClient;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * 文件上传控制器
 *
 * @author gxl
 */
@RestController
@RequestMapping("/upload")
public class UploadController implements Constants {

  @Value("${FILE_SERVER_URL}")
  private String fileServerUrl;

  @RequestMapping("/uploadFile")
  public Result upload(MultipartFile file) {
    //获取文件名
    String originalFilename = file.getOriginalFilename();
    //得到扩展名
    String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    try {
      FastDfsClient client = new FastDfsClient("classpath:fastDFS/fdfs_client.conf");
      String fileId = client.uploadFile(file.getBytes(), extName);
      //图片完整地址
      String url = fileServerUrl + fileId;
      return new Result(TRUE, url);
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(FALSE, UPLOAD_FAIL);
    }
  }

}
