package wgz.strategy.sample.strategy;

import org.springframework.stereotype.Service;

import wgz.strategy.sample.RequestHolder;

/**
 * 最高分策略
 * 
 */
@Service
public class MaxScoreStrategy extends AbstractSampleStrategy {
  public static String STRATEGY_ID = "maxScoreStrategy";

  @Override
  protected double computeLogic(RequestHolder requestHolder) throws Exception {
    // TODO Auto-generated method stub
    return 100;
  }

}
