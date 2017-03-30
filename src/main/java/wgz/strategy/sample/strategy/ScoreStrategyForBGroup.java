package wgz.strategy.sample.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wgz.strategy.sample.RequestHolder;
import wgz.strategy.sample.xgboost.ModelFactory;

/**
 * 基于xgbootst模型预测结果来制定策略，例如订单转化率、流失概率等
 * 
 */
@Service
public class ScoreStrategyForBGroup extends AbstractSampleStrategy {
  public static String STRATEGY_ID = "scoreStrategyForBGroup";
  @Autowired
  private ModelFactory modelFactory;

  @Override
  protected double computeLogic(RequestHolder requestHolder) throws Exception {
    // TODO Auto-generated method stub
    //
    double prob = modelFactory.fetchBubbleModel(requestHolder.getExpFlag()).predict(requestHolder);
    if (prob > 0.8) {
      return 95;
    } else if (prob < 0.3) {
      return 30;
    } else {
      return 70;
    }

  }

}
