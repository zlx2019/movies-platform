package com.movies.common.constant;

/**
 * 微信支付接口参数常量命名
 * @author lx Zhang.
 * @date 2021/3/2 2:59 下午
 */
public interface PayWx {

    /** url---------------------------*/
    String REFUND_URL = "https://api.mch.weixin.qq.com/pay/refundquery";  //微信退款接口

    /** 携带参数-----------------------*/
    String APP_ID = "appid";                            //公众账号 | 小程序ID
    String MCH_ID = "mch_id";                           //商户号
    String NONCE_STR = "nonce_str";                     //随机字符串
    String SIGN = "sign";                               //签名
    String SIGN_TYPE = "sign_type";                     //签名类型
    String TRANSACTION_ID = "transaction_id";           //微信订单号
    String OUT_TRADE_NO = "out_trade_no";               //内部订单号
    String OUT_REFUND_NO = "out_refund_no";             //退款单号
    String TOTAL_FEE = "total_fee";                     //订单金额
    String REFUND_FEE = "refund_fee";                   //退款金额(全款退则就是订单金额)
    String RETURN_MONEY_NOTIFY_URL = "notify_url";      //退款成功回调地址


    /** 响应参数-----------------------*/
    String RETURN_CODE = "return_code";                 //返回状态码
    String RETURN_MSG = "return_msg";                   //返回信息
    String RESULT_CODE = "result_code";                 //业务结果
    String ERR_CODE = "err_code";                       //错误代码
    String ERR_CODE_DES = "err_code_des";               //错误代码描述
    String REFUND_ID = "refund_id";                     //微信退款单号
    String REQ_INFO = "req_info";                       //退款加密信息
    String REFUND_STATUS = "refund_status";             //退款状态
    String SUCCESS_TIME = "success_time";               //退款成功时间
    String REFUND_RECV_ACCOUT = "refund_recv_accout";   //退款入账帐户
    String CODE_URL = "code_url";                       //二维码链接
    String TRADE_STATE = "trade_state";                 //支付状态
    String SETTLEMENT_TOTAL_FEE = "settlement_total_fee";//应结订单金额
    String TIME_END = "time_end";                       //支付完成时间
    String TRADE_TYPE = "trade_type";                   //支付类型
    String SETTLEMENT_REFUND_FEE = "settlement_refund_fee"; //退款金额


    /** 结果参数-----------------------------*/
    String SUCCESS = "SUCCESS"; //成功
    String FAIL = "FAIL"; //失败


}
