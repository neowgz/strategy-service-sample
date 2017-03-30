package wgz.strategy.sample.xgboost;

import java.util.HashMap;
import java.util.Map;

import wgz.strategy.sample.RequestHolder;
import wgz.strategy.sample.thrift.StrategyRequest;

/**
 * 特征提取的工具类，用于特征解析、预处理等
 *
 * @author wanggongzheng
 * @date 2016/05/17
 */
public class FeatureUtils {
  // 特征测试配置
  private final static String TEST_MODE = "testMode";
  private final static String BUBBLE_TIME = "bubbleTime";

  /**
   * 从请求参数中提取模型所需的特征
   *
   * @return 返回float类型的映射表
   */
  public static Map<String, Float> extractFromRequest(RequestHolder requestHolder)
      throws Exception {
    StrategyRequest request = requestHolder.getRequest();
    Map<String, Float> resFeatures = new HashMap<String, Float>();
    // 特征提取处理，自己的逻辑
    return resFeatures;
  }
}
