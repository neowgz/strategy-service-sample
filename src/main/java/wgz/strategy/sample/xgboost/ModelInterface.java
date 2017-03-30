package wgz.strategy.sample.xgboost;

import wgz.strategy.sample.RequestHolder;

/**
 * 预估模型的接口
 * 
 * @author wanggongzheng
 * @date 2016/06/20
 */
public interface ModelInterface {
  /**
   * 基于请求参数获取相关特征预测分类概率
   * 
   * @param requestHolder
   * @return 返回预估转化概率，0到1之间
   */
  public double predict(RequestHolder requestHolder) throws Exception;

  /**
   * 获取冒泡模型的名称版本
   * 
   * @return
   */
  public String getModelName();
}
