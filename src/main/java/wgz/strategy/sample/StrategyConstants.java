package wgz.strategy.sample;

/**
 * 封装服务层次一些常量值
 * 
 * @author wanggongzheng
 *
 */
public class StrategyConstants {
  // 返回相关
  public final static int CONTROL_GROUP_CODE = 100;
  public final static int OK = 200;
  public final static int ERROR = 201;
  public final static int PARAM_ERROR = 202;
  public final static int FEATURE_ERROR = 203;
  public final static int LOSS_MODEL_NULL_ERROR = 204;
  public static final double DEFAULT_PREDICT_SCORE = 0.0;

  // 请求参数相关

  // 请求的时间
  public final static String REQUEST_DATE_TIME = "request_time";
}
