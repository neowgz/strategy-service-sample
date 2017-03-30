package wgz.strategy.sample.selector;

import wgz.strategy.sample.RequestHolder;
import wgz.strategy.sample.strategy.SampleStrategy;

/**
 * 策略选择器，根据请求参数以及配置按照特定规则为该请求选择对应策略，用于灵活并行各类实验
 * 
 * @author wanggongzheng
 * @date 2016/06/17
 */
public interface StrategySelector {
  /**
   * 根据请求参数以及配置按照特定规则为该请求选择对应策略
   * 
   * @param requestHolder 请求等各类参数容器
   * @return SubsidyStrategy 具体实验策略
   */
  public SampleStrategy select(RequestHolder requestHolder) throws Exception;

}
