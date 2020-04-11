package util;

import java.util.Random;

/**
 * 随机生成6位数验证码
 * @author gxl
 */
public class RandomCodeUtils {

  /**
   * 定义生成验证码的位数
   */
  private final static int DIGIT = 6;

  public static String getRandomCode() {
    StringBuilder str = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < DIGIT; i++) {
      str.append(random.nextInt(10));
    }
    return str.toString();
  }

}
