package wgz.strategy.sample.xgboost;

/**
 * 请求扩展json信息对应的java对象
 *
 * @author wanggongzheng
 */
public class RequestExtInfo {
  // 是否为测试模型，测试模式中，部分特征从扩展参数提取
  private boolean testMode;
  // 测试模式中指定的冒泡时间
  private String bubbleTime;

  public boolean isTestMode() {
    return testMode;
  }

  public void setTestMode(boolean testMode) {
    this.testMode = testMode;
  }

  public String getBubbleTime() {
    return bubbleTime;
  }

  public void setBubbleTime(String bubbleTime) {
    this.bubbleTime = bubbleTime;
  }

}
