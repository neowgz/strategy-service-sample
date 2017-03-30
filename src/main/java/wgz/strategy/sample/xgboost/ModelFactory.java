package wgz.strategy.sample.xgboost;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import wgz.strategy.sample.selector.ExpFlags;

/**
 * 模型的工厂类，根据实验标识选取对应实验模型
 * 
 * @author wanggongzheng
 *
 */
@Service
public class ModelFactory {
  private Map<String, ModelInterface> modelMap = new HashMap<String, ModelInterface>();
  @Resource(name = "xgboostPredictorV1_0_1")
  private ModelInterface defaultModelV1_0_1;
  @Resource(name = "xgboostPredictor")
  private ModelInterface xgboostModel;

  /**
   * 在服务启动时刻注册实验模型实例，模型实验的扩展点
   */
  @PostConstruct
  private void registerModel() {
    // 需要将一个线上运行稳定发行的模型作为默认模型，当没有命中实验模型时，就选择默认模型
    modelMap.put(ExpFlags.DEFAULT_EXP_FLAG, xgboostModel);
    modelMap.put(ExpFlags.XG_MODLE_1_0_1, defaultModelV1_0_1);
  }

  /**
   * 根据实验号返回对应模型，如果对应实验没有注册模型
   * 
   * @param expFlag
   * @return
   */
  public ModelInterface fetchBubbleModel(String expFlag) {
    if (modelMap.containsKey(expFlag)) {
      return modelMap.get(expFlag);
    } else {
      return modelMap.get("default");
    }
  }
}
