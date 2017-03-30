package wgz.strategy.sample.exception;

/**
 * 请求参数校验异常
 * 
 * @author wanggongzheng
 * @date 2016/06/30
 */
public class RequestValidationException extends Exception {
  public RequestValidationException() {}

  public RequestValidationException(String info) {
    super(info);
  }
}
