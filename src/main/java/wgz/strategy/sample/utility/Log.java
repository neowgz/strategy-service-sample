package wgz.strategy.sample.utility;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.NDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类 ，包装本项目的日志输出文件规范以及提供简易的调用方式。 此外，提供输出json格式日志的入口
 * 
 * @author wanggongzheng
 *
 */
public class Log {
  // 信息级别的日志，包括了请求日志参数等分析所需信息
  private static Logger reqeustLogger = LoggerFactory.getLogger("requestlog");
  // warn以及error基本日志输出
  private static Logger errorLogger = LoggerFactory.getLogger("errorlog");

  public static void debug(String msg) {
    reqeustLogger.debug(msg);
  }

  /**
   * 为该请求处理线程产生一个基于uuid
   */
  public static String genertateLogid() {
    UUID uuid = UUID.randomUUID();
    String uuidStr = uuid.toString();
    String logId = uuidStr.replaceAll("-", "");
    logId = "logid:" + logId;
    NDC.remove();
    NDC.push(logId);
    return logId;
  }

  /**
   * 将对象转化为json格式后，以debug日志输出
   * 
   * @param obj 待输出对象
   * @param prefix 前缀以标识，便于后续过滤分析，和json串之间用":"分割。如果为null，则直接输出json串。
   */
  public static void debugJson(Object obj, String prefix) {
    logJsonForObject("debug", obj, prefix);
  }

  public static void info(String msg) {
    reqeustLogger.info(msg);
  }

  /**
   * 将对象转化为json格式后，以info级别日志输出
   * 
   * @param obj 待输出对象
   * @param prefix 前缀以标识，便于后续过滤分析，和json串之间用":"分割。如果为null，则直接输出json串。
   */
  public static void infoJson(Object obj, String prefix) {
    logJsonForObject("info", obj, prefix);
  }

  /**
   * 将对象转化为json格式后，以info级别日志输,去掉excludes属性
   * 
   * @param obj
   * @param prefix
   * @param excludes 不在json中输出的属性
   */
  public static void infoJson(Object obj, String prefix, List<String> excludes) {
    logJsonForObject("info", obj, prefix, excludes);
  }

  public static void warn(String msg) {
    errorLogger.warn(msg);
  }

  /**
   * 将对象转化为json格式后，以warn级别日志输出
   * 
   * @param obj 待输出对象
   * @param prefix 前缀以标识，便于后续过滤分析，和json串之间用":"分割。如果为null，则直接输出json串。
   */
  public static void warnJson(Object obj, String prefix) {
    logJsonForObject("warn", obj, prefix);
  }

  public static void error(String msg) {
    errorLogger.error(msg);
  }

  /**
   * 主要用来输出异常堆栈信息
   * 
   * @param msg
   * @param obj
   */
  public static void error(String msg, Object obj) {
    errorLogger.error(msg, obj);
  }

  /**
   * 将对象转化为json格式后，以warn级别日志输出
   * 
   * @param obj 待输出对象
   * @param prefix 前缀以标识，便于后续过滤分析，和json串之间用":"分割。如果为null，则直接输出json串。
   */
  public static void errorJson(Object obj, String prefix) {
    logJsonForObject("error", obj, prefix);
  }

  private static void logJsonForObject(String level, Object obj, String prefix) {
    logJsonForObject(level, obj, prefix, null);
  }

  /**
   * 用指定级别日志，用json格式输出对象
   * 
   * @param level
   * @param obj
   * @param prefix
   * @param excludes json中忽略的属性
   */
  private static void logJsonForObject(String level, Object obj, String prefix,
      List<String> excludes) {
    String jsonStr;
    if (excludes != null) {
      jsonStr = JsonUtil.getJsonStrFromObj(obj, excludes);
    } else {
      jsonStr = JsonUtil.getJsonStrFromObj(obj);
    }

    String pairStr = jsonStr;
    if (prefix != null) {
      pairStr = prefix + ":" + jsonStr;
    }
    switch (level) {
      case "debug":
        debug(pairStr);
        break;
      case "info":
        info(pairStr);
        break;
      case "warn":
        warn(pairStr);
        break;
      case "error":
        error(pairStr);
        break;
      default:
        break;
    }
  }
}
