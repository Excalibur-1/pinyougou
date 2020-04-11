package util;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

/**
 * FastDFS工具类
 *
 * @author gxl
 */
public class FastDfsClient {

  private StorageClient1 storageClient;

  public FastDfsClient(String conf) throws Exception {
    String classpath = "classpath:";
    if (conf.contains(classpath)) {
      conf = conf.replace(classpath, this.getClass().getResource("/").getPath());
    }
    ClientGlobal.init(conf);
    TrackerClient trackerClient = new TrackerClient();
    TrackerServer trackerServer = trackerClient.getConnection();
    storageClient = new StorageClient1(trackerServer, null);
  }

  /**
   * 上传文件方法
   * <p>Title: uploadFile</p>
   * <p>Description: </p>
   *
   * @param fileName 文件全路径
   * @param extName  文件扩展名，不包含（.）
   * @param metas    文件扩展信息
   * @return String
   */
  public String uploadFile(String fileName, String extName, NameValuePair[] metas) throws Exception {
    return storageClient.upload_file1(fileName, extName, metas);
  }

  public String uploadFile(String fileName) throws Exception {
    return uploadFile(fileName, null, null);
  }

  public String uploadFile(String fileName, String extName) throws Exception {
    return uploadFile(fileName, extName, null);
  }

  /**
   * 上传文件方法
   * <p>Title: uploadFile</p>
   * <p>Description: </p>
   *
   * @param fileContent 文件的内容，字节数组
   * @param extName     文件扩展名
   * @param metas       文件扩展信息
   * @return String
   */
  public String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas) throws Exception {

    return storageClient.upload_file1(fileContent, extName, metas);
  }

  public String uploadFile(byte[] fileContent) throws Exception {
    return uploadFile(fileContent, null, null);
  }

  public String uploadFile(byte[] fileContent, String extName) throws Exception {
    return uploadFile(fileContent, extName, null);
  }
}
