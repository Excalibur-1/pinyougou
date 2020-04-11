package util;

/**
 * 中文与unicode编码互转工具类
 *
 * @author gxl
 */
public class UnicodeUtils {

  /**
   * 中文转Unicode
   *
   * @param gbString 中文字符
   * @return String
   */
  public static String gbEncoding(final String gbString) {
    char[] utfBytes = gbString.toCharArray();
    StringBuilder unicodeBytes = new StringBuilder();
    for (char utfByte : utfBytes) {
      //转换为16进制整型字符串
      String hexString = Integer.toHexString(utfByte);
      if (hexString.length() <= 2) {
        hexString = "00" + hexString;
      }
      unicodeBytes.append("\\u").append(hexString);
    }
    return unicodeBytes.toString();
  }

  /**
   * Unicode转中文
   *
   * @param dataStr unicode字符
   * @return String
   */
  public static String decodeUnicode(final String dataStr) {
    int start = 0;
    int end;
    final StringBuilder stringBuilder = new StringBuilder();
    while (start > -1) {
      end = dataStr.indexOf("\\u", start + 2);
      String charStr;
      if (end == -1) {
        charStr = dataStr.substring(start + 2);
      } else {
        charStr = dataStr.substring(start + 2, end);
      }
      // 16进制parse整形字符串
      char letter = (char) Integer.parseInt(charStr, 16);
      stringBuilder.append(Character.toString(letter));
      start = end;
    }
    return stringBuilder.toString();
  }

}
