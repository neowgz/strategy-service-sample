package wgz.strategy.sample;

import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;

import wgz.strategy.sample.selector.StrategySelector;
import wgz.strategy.sample.strategy.SampleStrategy;
import wgz.strategy.sample.strategy.StrategyResult;
import wgz.strategy.sample.thrift.StrategyRequest;
import wgz.strategy.sample.thrift.StrategyResponse;
import wgz.strategy.sample.thrift.StrategyService;
import wgz.strategy.sample.utility.JsonUtil;
import wgz.strategy.sample.utility.Log;
import wgz.strategy.sample.utility.Utils;
import wgz.strategy.sample.utility.Validation;

/**
 * 预测服务入口，接收预测请求
 *
 * @author wanggongzheng
 * @date 2016/05/14
 */
public class StrategyServiceImpl implements StrategyService.Iface {
  @Autowired
  private StrategySelector strategySelector;


  /**
   * 计算以及预测分数并响应
   */
  @Override
  public StrategyResponse compute(StrategyRequest request) throws TException {
    // TODO Auto-generated method stub
    StrategyResponse response;
    long st = System.currentTimeMillis();
    Log.genertateLogid();
    Log.debug(request.toString());
    Log.infoJson(request, "request");
    RequestHolder requestHolder = new RequestHolder();
    requestHolder.setRequest(request);
    requestHolder.setTestMode(Utils.isTestMode(request));
    try {
      // 请求参数校验
      Validation.checkRequiredParams(request);

      response = processByStrategy(requestHolder);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      Log.error(e.getMessage(), e);
      response = errorRepsonse(StrategyConstants.ERROR, e.getMessage());
    }
    ResponseLogParams logParams = new ResponseLogParams();
    // 处理耗时
    long serviceCostTime = System.currentTimeMillis() - st;
    // 输出响应日志,区分前缀规范为resp_info，用于后续的日志分析
    logParams.setCostTime(serviceCostTime);
    logParams.setResponse(response);
    logParams.setRequest(request);
    logParams.getExtInfoMap().putAll(requestHolder.getExtInfoMap());
    Log.infoJson(logParams, "resp_info", JsonUtil.getExcludeForLogParams(logParams));

    return response;
  }

  /**
   * 根据该用户命中的策略进行折扣计算
   *
   * @param requestHolder 请求容器
   * @return 预测折扣结果
   * @throws Exception
   */
  private StrategyResponse processByStrategy(RequestHolder requestHolder) throws Exception {
    StrategyResponse response = new StrategyResponse();
    // 选择该请求对应的策略
    SampleStrategy strategy = strategySelector.select(requestHolder);
    if (strategy != null) {
      StrategyResult result = strategy.compute(requestHolder);
      response = new StrategyResponse();
      response.setScore(result.getScore());
      response.setErrorCode(StrategyConstants.OK);
      response.setErrorMsg("success");
      // 如果是测试模式,则将pre_feature加入到response的extInfoMap中.
      if (requestHolder.isTestMode()) {
        Map<String, Object> testInfoMap = requestHolder.getTestInfoMap();
        Map<String, Object> respExtInfo = new HashMap<String, Object>();
        respExtInfo.put("pre_feature", testInfoMap.get("pre_feature"));
        respExtInfo.put("prob", testInfoMap.get("prob"));
        respExtInfo.put("service_info", requestHolder.getExtInfoMap());
        response.setExtInfo(JsonUtil.getJsonStrFromObj(respExtInfo));
      }
    } else {
      // 该用户没有命中策略，？默认降级策略，可以提供默认策略或者返回错误，让调用根据约定处理
      response = errorRepsonse(StrategyConstants.ERROR, "no strategy");
    }
    return response;
  }

  /**
   * 构造错误的响应结果工具函数
   *
   * @param errorCode 错误码
   * @param errorMsg 错误信息
   * @return 错误响应
   */
  private StrategyResponse errorRepsonse(int errorCode, String errorMsg) {
    StrategyResponse response = new StrategyResponse();
    response.setErrorCode(errorCode);
    response.setErrorMsg(errorMsg);
    return response;
  }

}
