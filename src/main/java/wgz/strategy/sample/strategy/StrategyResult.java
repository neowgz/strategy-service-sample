package wgz.strategy.sample.strategy;

/**
 * 策略的处理结果，服务最后根据策略处理结果提取返回给client
 * 
 *
 */
public class StrategyResult {
  private double score;

  public double getScore() {
    return score;
  }

  public void setScore(double score) {
    this.score = score;
  }

}
