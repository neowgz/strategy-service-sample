package wgz.strategy.sample.utility;

import flexjson.JSONDeserializer;
import wgz.strategy.sample.thrift.StrategyRequest;
import wgz.strategy.sample.xgboost.RequestExtInfo;

/**
 * 项目的通用工具方法
 * 
 * @author wanggongzheng
 *
 */
public class Utils {
  /**
   * 将字符串首字母小写，类似驼峰变量命名
   */
  public static String toCamelName(String source) {
    if (source.length() < 2) {
      return source.toLowerCase();
    } else {
      return Character.toLowerCase(source.charAt(0)) + source.substring(1);
    }
  }

  /**
   * 是否为测试模式
   *
   * @return true为测试模式
   */
  public static boolean isTestMode(StrategyRequest request) {
    String extInfoJson = request.extInfo;
    if (request.isSetExtInfo()) {
      RequestExtInfo extInfo =
          new JSONDeserializer<RequestExtInfo>().deserialize(extInfoJson, RequestExtInfo.class);
      return extInfo.isTestMode();
    } else {
      return false;
    }
  }
}
