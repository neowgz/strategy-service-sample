package wgz.strategy.sample;

import org.apache.thrift.TProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wgz.strategy.sample.thrift.StrategyService;
import wgz.strategy.sample.utility.Log;

/**
 * 补贴服务的启动主函数
 *
 */
public class StrategyServer {

  private int port;
  private TServerSocket serverTransport;
  private StrategyServiceImpl strategyServiceImpl;

  public static void main(String[] args) {
    try {
      new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

    } catch (Exception e) {
      Log.error(e.getMessage(), e);
    }
  }

  public void setPort(int port) {
    this.port = port;
  }

  public void setSubsidyServiceImpl(StrategyServiceImpl strategyServiceImpl) {
    this.strategyServiceImpl = strategyServiceImpl;
  }

  public void init() throws Exception {
    serverTransport = new TServerSocket(port);
    TProcessor processor =
        new StrategyService.Processor<StrategyService.Iface>(strategyServiceImpl);
    TServer server =
        new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
    server.serve();
  }

  public void close() {
    Log.info("Stopping server ...");
    serverTransport.close();
  }
}
