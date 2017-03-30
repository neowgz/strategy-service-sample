package wgz.strategy.sample.utility;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import flexjson.JSONSerializer;
import wgz.strategy.sample.ResponseLogParams;
import wgz.strategy.sample.thrift.StrategyRequest;
import wgz.strategy.sample.thrift.StrategyResponse;

/**
 * 将对象转换为json格式，方便日志输出等场景,默认用深度序列化
 * 
 * @author wanggongzheng
 * @date 2016/05/23
 */
public class JsonUtil {
  private static String classExclude = "*.class";
  private static List<String> reponseLogExcludes;

  // Java object to JSON String
  public static String getJsonStrFromObj(Object obj) {
    JSONSerializer serializer = new JSONSerializer();
    serializer.exclude(classExclude);
    return serializer.deepSerialize(obj);
  }

  // Java object to JSON String
  public static String getJsonStrFromObj(Object obj, List<String> excludes) {
    JSONSerializer serializer = new JSONSerializer();
    serializer.exclude(classExclude);
    for (String ex : excludes) {
      serializer = serializer.exclude(ex);
    }
    return serializer.deepSerialize(obj);
  }

  /**
   * 打印响应日志信息，将相关对象转成Json时，忽略thrift的isSet等没信息量但可能有很多自动生成的thrift属性
   *
   * @param params
   * @return
   */
  public static List<String> getExcludeForLogParams(ResponseLogParams params) {
    if (reponseLogExcludes == null) {
      List<String> excludes = new ArrayList<String>();
      excludes.addAll(getExcludeForResponse(params.getResponse()));
      excludes.addAll(getExcludeForRequest(params.getRequest()));
      reponseLogExcludes = excludes;
    }
    return reponseLogExcludes;
  }

  /**
   * 提取reponse对象转化为json丢弃的属性
   * 
   * @param response
   * @return
   */
  private static List<String> getExcludeForResponse(StrategyResponse response) {
    return addKeyForProperty(getSetProperty(response), "response");
  }

  /**
   * 提取request对象转化为json丢弃的属性
   * 
   * @param request
   * @return
   */
  private static List<String> getExcludeForRequest(StrategyRequest request) {
    List<String> excludes = new ArrayList<String>();
    // excludes.addAll(getExcludeForProp(request.order, "request.order"));
    excludes.addAll(addKeyForProperty(getSetProperty(request), "request"));
    return excludes;
  }

  /**
   * 提取某个thrift对象的isSet的属性
   * 
   * @param obj thrift对象
   * @param keyPrefix thrift 对应于响应日志对象的变量索引名，例如request.order
   * @return isSet的属性全名列表
   */
  public static List<String> getExcludeForProp(Object obj, String keyPrefix) {
    return addKeyForProperty(getSetProperty(obj), keyPrefix);
  }

  /**
   * 给列表中所有字符串添加前缀key
   */
  private static List<String> addKeyForProperty(List<String> properties, String key) {
    List<String> res = new ArrayList<String>();
    if (key == null || key.isEmpty()) {
      return properties;
    }
    for (String value : properties) {
      String rValue = key + "." + value;
      res.add(rValue);
    }
    return res;
  }

  /**
   * 提取对象的isSet的属性名列表
   */
  private static List<String> getSetProperty(Object obj) {
    List<String> setProperties = new ArrayList<String>();
    if (obj == null) {
      return setProperties;
    }
    Class clazz = obj.getClass();
    for (Method method : clazz.getDeclaredMethods()) {
      int modifiers = method.getModifiers();
      if (Modifier.isStatic(modifiers))
        continue;

      int numberOfArgs = method.getParameterTypes().length;
      String name = method.getName();
      if (name.length() <= 3 && !name.startsWith("is"))
        continue;

      if (numberOfArgs == 0) {
        if (name.startsWith("get")) {
          String property = uncapitalize(name.substring(3));
          if (property.startsWith("set") && !setProperties.contains(property)) {
            setProperties.add(property);
          }
        } else if (name.startsWith("is")) {
          String property = uncapitalize(name.substring(2));
          if (property.startsWith("set") && !setProperties.contains(property)) {
            setProperties.add(property);
          }
        }
      }
    }
    return setProperties;
  }

  /**
   * 将字符串首字母转换为小写
   */
  private static String uncapitalize(String value) {
    if (value.length() < 2) {
      return value.toLowerCase();
    } else if (Character.isUpperCase(value.charAt(0)) && Character.isUpperCase(value.charAt(1))) {
      return value;
    } else {
      return Character.toLowerCase(value.charAt(0)) + value.substring(1);
    }
  }

}
