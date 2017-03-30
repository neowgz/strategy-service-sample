namespace java wgz.strategy.sample.thrift

# 注意！！不要定义set开头的属性，这类属性会目前在最终的日志输出时，过滤掉。

# 请求输入
struct StrategyRequest {
    1: required string serviceName,  # 调用方名称标识
    2: required string pid, # 乘客ID
    3: i32 cityId
    4: optional string extInfo # 扩展信息，json格式,例如指定测试模式{"testMode":true}
}

# 返回输出
struct StrategyResponse {
    1: double   score,   # 结果分数，1到100之间 
    2: i32 errorCode,   # 预测的错误码，100为对照组，200为正确
    3: string errorMsg,  # 错误信息，可选，对应于错误码的错误信息
    4: optional string extInfo  # 扩展信息,json格式,例如用于测试环境下, 返回输入模型前的特征，用于和离线的特征结果对比等
}

# 服务
service StrategyService {
    StrategyResponse compute(1: StrategyRequest request)
}
