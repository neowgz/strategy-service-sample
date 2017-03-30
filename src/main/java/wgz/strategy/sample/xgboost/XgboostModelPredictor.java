package wgz.strategy.sample.xgboost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ml.dmlc.xgboost4j.java.Booster;
import ml.dmlc.xgboost4j.java.DMatrix;
import ml.dmlc.xgboost4j.java.XGBoost;
import wgz.strategy.sample.RequestHolder;
import wgz.strategy.sample.utility.Log;

/**
 * xgboost模型预测
 *
 * @author wanggongzheng
 * @date 2016/05/17
 */
public class XgboostModelPredictor implements ModelInterface {

  // xgboost 模型
  private Booster booster;
  // 模型版本（暂用用模型的相对路径为代替
  private String modelVersion;
  // 特征索引映射表，特定名字的特征在libsvm格式的特征编号，
  private Map<String, Integer> featureIndexMap = null;

  public XgboostModelPredictor(String modelPath, String indexMapFile) throws Exception {
    booster = XGBoost.loadModel(modelPath);
    modelVersion = modelPath;
    // 自行构造特征到编号的映射
    // featureIndexMap = loadIndexMap(indexMapFile);
  }

  @Override
  public String getModelName() {
    // TODO Auto-generated method stub
    return modelVersion;
  }

  /**
   * 根据实时请求参数提取特征后，使用xgboost模型预测出转化概率
   *
   * @param requestHolder 实时请求参数
   * @return double 预测概率
   */
  @Override
  public double predict(RequestHolder requestHolder) throws Exception {
    // TODO Auto-generated method stub
    // 在线请求特征
    Map<String, Float> reqFeatures = FeatureUtils.extractFromRequest(requestHolder);
    // 其他各类特征提取
    Map<String, Float> pasFeatures = null;
    // 合并feature map
    Map<String, Float> preFeatures = new HashMap<String, Float>();
    preFeatures.putAll(pasFeatures);
    preFeatures.putAll(reqFeatures);
    // 打印一行info级别特征日志
    Log.infoJson(preFeatures, "pre_feature");
    // 基于xgboost 模型预测转化概率
    double score = xgboostPredict(preFeatures);
    // 如果是测试模式,则把pre_feature加入到requestHolder中的extInfoMap中
    if (requestHolder.isTestMode()) {
      Map<String, Object> testInfoMap = requestHolder.getTestInfoMap();
      testInfoMap.put("pre_feature", preFeatures);
      testInfoMap.put("prob", score);
      requestHolder.setTestInfoMap(testInfoMap);
    }

    return score;

  }

  private double xgboostPredict(Map<String, Float> preFeatures) throws Exception {
    // 生成模型所需的DMatrix
    float[] featureArray = generatePredictSet(preFeatures);
    Log.debugJson(featureArray, "model_feature");
    DMatrix matrix =
        new DMatrix(featureArray, 1, featureArray.length, ModelConstants.MISSING_VALUE);
    // 预测
    float[][] predProb = booster.predict(matrix);
    // 只有1行，第一列是预测概率
    float score = predProb[0][0];
    return score;
  }

  /**
   * 根据模型的特征顺序表，从特征映射表产出有序的特征数组
   */
  private float[] generatePredictSet(Map<String, Float> features) throws Exception {
    int size = featureIndexMap.size();
    float[] predictArray = new float[size + 1];
    List<String> missingKeys = new ArrayList<String>();
    for (Map.Entry<String, Integer> entry : featureIndexMap.entrySet()) {
      // libsvm的index是从1开始的，所以减1
      int index = entry.getValue();
      String key = entry.getKey();
      if (features.containsKey(key)) {
        predictArray[index] = features.get(key);
      } else {
        // 缺失，赋值为缺失值
        predictArray[index] = ModelConstants.MISSING_VALUE;
        missingKeys.add(key);
      }
    }
    Log.debugJson(missingKeys, "missing_key");
    return predictArray;
  }



}
