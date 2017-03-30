package wgz.strategy.sample.utility;

import java.util.ArrayList;
import java.util.List;

import wgz.strategy.sample.exception.RequestValidationException;
import wgz.strategy.sample.thrift.StrategyRequest;


/**
 * 校验类，提供参数校验、特征校验等功能
 *
 * @author wanggongzheng
 * @date 2016/05/27
 */
public class Validation {

  /**
   * 校验必填参数是否设置，如果没有设置，则返回没有参数的名称列表
   */
  public static void checkRequiredParams(StrategyRequest request)
      throws RequestValidationException {
    // 校验必填参数，本期模型使用且IDL接口中为设置为required
    // 考虑到后续变动兼容，大部分参数都未设置为required？
    List<String> missParams = new ArrayList<String>();
    // 检验必选参数
    if (!missParams.isEmpty()) {
      String missStr = JsonUtil.getJsonStrFromObj(missParams);
      throw new RequestValidationException(missStr);
    }
  }

}


