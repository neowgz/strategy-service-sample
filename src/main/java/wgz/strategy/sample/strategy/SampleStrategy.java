package wgz.strategy.sample.strategy;

import wgz.strategy.sample.RequestHolder;

/**
 * 策略的接口，所有策略都继承这个接口
 * 
 */
public interface SampleStrategy {
  /**
   * 根据的请求参数、配置等信息计算最终建议的折扣
   * 
   * @param requestHolder 请求对象、各类配置等信息容器
   * @return SubsidyResult，包含了折扣等结果信息
   */
  public StrategyResult compute(RequestHolder requestHolder) throws Exception;

  /**
   * 获取该策略id，该id由实现方定义，但需要具备唯一性
   * 
   * @return 策略id
   */
  public String getStrategyName();
}
