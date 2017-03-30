package wgz.strategy.sample;

import java.util.HashMap;
import java.util.Map;

import wgz.strategy.sample.thrift.StrategyRequest;

/**
 * 请求的包装器，用于存放请求对象以及处理流中的中间信息
 * 
 */
public class RequestHolder {
  private StrategyRequest request;
  // 实验策略标识
  private String expFlag;
  // 需要收集并打印到响应日志的各类中间信息
  private Map<String, Object> extInfoMap = new HashMap<String, Object>();
  // 是否为测试模式
  private boolean isTestMode = false;
  // 需要收集的测试值
  private Map<String, Object> testInfoMap = new HashMap<String, Object>();

  public StrategyRequest getRequest() {
    return request;
  }

  public void setRequest(StrategyRequest request) {
    this.request = request;
  }

  public String getExpFlag() {
    return expFlag;
  }

  public void setExpFlag(String expFlag) {
    this.expFlag = expFlag;
  }



  public Map<String, Object> getExtInfoMap() {
    return extInfoMap;
  }

  public void setExtInfoMap(Map<String, Object> extInfoMap) {
    this.extInfoMap = extInfoMap;
  }

  public boolean isTestMode() {
    return isTestMode;
  }

  public void setTestMode(boolean isTestMode) {
    this.isTestMode = isTestMode;
  }


  public void setTestInfoMap(Map<String, Object> testInfoMap) {
    this.testInfoMap = testInfoMap;
  }

  public Map<String, Object> getTestInfoMap() {
    return testInfoMap;
  }
}
