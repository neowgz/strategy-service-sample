package wgz.strategy.sample.strategy;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Service;

/**
 * 策略工厂，用于集中存放以及读取各类策略
 * 
 * @author wanggongzheng
 *
 */
@Service
public class StrategyFactory extends ApplicationObjectSupport {

  public SampleStrategy getDefalutCallStrategy() {
    return getStrategyById(DefaultScoreStrategy.STRATEGY_ID);
  }

  /**
   * 根据每个策略strategyId来获取bean，strategyId注入spring容器的id
   * 
   * @param strategyId
   * @return
   */
  public SampleStrategy getStrategyById(String strategyId) {
    return (SampleStrategy) getApplicationContext().getBean(strategyId);
  }
}
