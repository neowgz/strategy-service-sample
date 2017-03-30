package wgz.strategy.sample;

import java.util.HashMap;
import java.util.Map;

import wgz.strategy.sample.thrift.StrategyRequest;
import wgz.strategy.sample.thrift.StrategyResponse;

/**
 * 封装响应日志信息中包含的信息，便于在一行信息中封装后续分析所需所有信息
 * 
 * @author wanggongzheng
 *
 */
public class ResponseLogParams {
  // 响应对象
  private StrategyResponse response;
  // 请求处理耗时
  private long costTime;
  // 请求参数对象
  private StrategyRequest request;

  // 需要收集并打印到响应日志的各类中间信息
  private Map<String, Object> extInfoMap = new HashMap<String, Object>();


  public long getCostTime() {
    return costTime;
  }

  public void setCostTime(long costTime) {
    this.costTime = costTime;
  }


  public Map<String, Object> getExtInfoMap() {
    return extInfoMap;
  }

  public void setExtInfoMap(Map<String, Object> extInfoMap) {
    this.extInfoMap = extInfoMap;
  }

  public StrategyResponse getResponse() {
    return response;
  }

  public void setResponse(StrategyResponse response) {
    this.response = response;
  }

  public StrategyRequest getRequest() {
    return request;
  }

  public void setRequest(StrategyRequest request) {
    this.request = request;
  }
}
