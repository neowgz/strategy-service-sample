package wgz.strategy.sample.selector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wgz.strategy.sample.RequestHolder;
import wgz.strategy.sample.strategy.MaxScoreStrategy;
import wgz.strategy.sample.strategy.MinScoreStrategy;
import wgz.strategy.sample.strategy.SampleStrategy;
import wgz.strategy.sample.strategy.ScoreStrategyForAGroup;
import wgz.strategy.sample.strategy.ScoreStrategyForBGroup;
import wgz.strategy.sample.strategy.StrategyFactory;
import wgz.strategy.sample.thrift.StrategyRequest;
import wgz.strategy.sample.utility.ABTestUtil;

/**
 * 服务默认的策略选择器
 *
 */
@Service
public class DefaultStrategySelector implements StrategySelector {
  // 策略工厂
  @Autowired
  private StrategyFactory strategyFactory;

  // 记录新策略的版本号
  protected static String newStrategyVersion = "v1.1.0";
  // 记录旧策略的版本号
  protected static String oldStrategyVersion = "v1.0.0";

  @Override
  public SampleStrategy select(RequestHolder requestHolder) throws Exception {
    // TODO Auto-generated method stub
    SampleStrategy strategy = null;
    // 实验标识
    requestHolder.setExpFlag(ExpFlags.DEFAULT_EXP_FLAG);
    StrategyRequest request = requestHolder.getRequest();

    int cityId = request.getCityId();
    if (cityId == 1) {
      return strategyFactory.getStrategyById(MaxScoreStrategy.STRATEGY_ID);
    } else if (cityId == 2) {
      return strategyFactory.getStrategyById(MinScoreStrategy.STRATEGY_ID);
    } else if (cityId == 3) {
      // 按照userid 随机划分为两组
      if (ABTestUtil.hash(Long.parseLong(request.getPid())) % 2 == 0) {
        return strategyFactory.getStrategyById(ScoreStrategyForAGroup.STRATEGY_ID);
      } else {
        return strategyFactory.getStrategyById(ScoreStrategyForBGroup.STRATEGY_ID);
      }
    }

    // 将记录命中策略名, 策略版本号,以及实验号，用于后续的日志输出
    if (strategy != null) {
      requestHolder.getExtInfoMap().put("strategy_name", strategy.getStrategyName());
    }
    requestHolder.getExtInfoMap().put("exp_flag", requestHolder.getExpFlag());
    return strategy;
  }

}
