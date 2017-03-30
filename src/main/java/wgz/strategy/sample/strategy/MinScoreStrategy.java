package wgz.strategy.sample.strategy;

import org.springframework.stereotype.Service;

import wgz.strategy.sample.RequestHolder;

/**
 * 最低分策略
 * 
 */
@Service
public class MinScoreStrategy extends AbstractSampleStrategy {
  public static String STRATEGY_ID = "minScoreStrategy";

  @Override
  protected double computeLogic(RequestHolder requestHolder) throws Exception {
    // TODO Auto-generated method stub
    return 0;
  }

}
