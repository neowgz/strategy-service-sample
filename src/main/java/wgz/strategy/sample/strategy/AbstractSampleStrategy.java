package wgz.strategy.sample.strategy;

import wgz.strategy.sample.RequestHolder;

/**
 * 策略的抽象类，用于提供所有策略共同的属性以及逻辑
 * 
 *
 */
public abstract class AbstractSampleStrategy implements SampleStrategy {

  /**
   * 策略抽象类包装实现具体策略计算前后通用处理逻辑
   */
  @Override
  public StrategyResult compute(RequestHolder requestHolder) throws Exception {
    // TODO Auto-generated method stub
    double score = computeLogic(requestHolder);
    // 将score约束在特定范围内，样例模板操作
    double limitScore = limitScore(score);

    StrategyResult result = new StrategyResult();
    result.setScore(limitScore);

    // 记录中间信息，便于后面系统分析
    requestHolder.getExtInfoMap().put("rawScore", score);
    requestHolder.getExtInfoMap().put("limitScore", limitScore);
    return result;
  }

  private double limitScore(double rawScore) {
    if (rawScore < 0) {
      return 0;
    }
    if (rawScore > 100) {
      return 100;
    }
    return rawScore;
  }

  /**
   * 策略抽象类添加，具体策略类都应该重写该方法以实现自己的策略
   * 
   * @param requestHolder
   * @return 基于自己策略计算出的分数
   */
  protected abstract double computeLogic(RequestHolder requestHolder) throws Exception;

  /**
   * 默认策略名字为类名，设计内涵一种策略就对应一个类，且类名不重复
   */
  @Override
  public String getStrategyName() {
    // TODO Auto-generated method stub
    String simpleName = this.getClass().getSimpleName();
    return simpleName;
  }

}
