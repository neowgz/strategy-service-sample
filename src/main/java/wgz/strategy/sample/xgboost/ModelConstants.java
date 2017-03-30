package wgz.strategy.sample.xgboost;

/**
 * 模型中一些常量，例如缺失值，特征名等
 * 
 * @author wanggongzheng
 *
 */
public class ModelConstants {

  public final static int OK = 200;
  public final static int ERROR = 201;
  public final static int PARAM_ERROR = 202;
  public final static int FUSIION_FEATURE_ERROR = 203;
  public static final double DEFAULT_PREDICT_SCORE = 0.0;
  public final static String DEGRADE = "degrade";
  public static final float FLOAT_TRUE = 1.0f;
  public static final float FLOAT_FALSE = 0.0f;
  public static final float MISSING_VALUE = -9999f;
}
