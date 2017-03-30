package wgz.strategy.sample.strategy;

import org.springframework.stereotype.Service;

import wgz.strategy.sample.RequestHolder;

/**
 * 默认策略，返回及格分
 * 
 */
@Service
public class DefaultScoreStrategy extends AbstractSampleStrategy {
  public static String STRATEGY_ID = "defaultScoreStrategy";

  @Override
  protected double computeLogic(RequestHolder requestHolder) throws Exception {
    // TODO Auto-generated method stub
    return 60;
  }

}
