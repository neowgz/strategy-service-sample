package wgz.strategy.sample.utility;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;

/**
 * 基于id的hash算法分组工具
 */
public class ABTestUtil {
  /**
   * 将Long类型的ID哈希为Boolean值.
   *
   * @param id 待Hash的ID值
   * @return Boolean类型的Hash值
   */
  public static boolean boolHash(long id) {
    return 0 != (BKDRHash(md5(String.valueOf(id) + "salt")) & 0x01L);
  }

  public static int hash(long id) {
    return BKDRHash(md5(String.valueOf(id) + "salt"));
  }

  /**
   * 字符串Hash函数.
   *
   * @param str 待Hash字符串
   * @return Hash值
   */
  public static int BKDRHash(String str) {
    int seed = 1;
    int hash = 0;
    for (int i = 0; i < str.length(); ++i) {
      hash = hash * seed + str.charAt(i);
    }
    return hash;
  }

  public static String md5(String s) {
    try {
      final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      messageDigest.update(s.getBytes());
      final byte[] resultByte = messageDigest.digest();
      final String result = new String(Hex.encodeHex(resultByte));
      return result;
    } catch (Exception e) {
      return s;
    }
  }
}
