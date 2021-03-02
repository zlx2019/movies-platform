package com.movies.common.constant;

/**
 * 支付宝支付常量
 * @author lx Zhang.
 * @date 2021/3/2 2:59 下午
 */
public interface PayAli {
    String AliPAY_GATEWAY_URL = "https://openapi.alipay.com/gateway.do";


    /**-------------------响应参数------------------------------*/
    String TRADE_STATUS = "trade_status";                       //交易状态
    String BODY = "alipay_trade_query_response";                //响应数据
    String SEND_PAY_DATE = "send_pay_date";                     //支付成功时间
    String REFUND_BODY = "alipay_trade_refund_response";        //退款响应数据
    String CODE = "code";                                       //响应码
    String MSG = "msg";                                         //响应消息
    String OUT_TRADE_NO = "out_trade_no";                       //内部单号
    String TRADE_NO = "trade_no";                               //支付宝单号
    String BUYER_USER_ID = "buyer_user_id";                     //购买者用户id
    String GMT_REFUND_PAY = "gmt_refund_pay";                   //退款成功时间
    String REFUND_FEE = "refund_fee";                           //退款金额
    String SIGN = "sign";                                       //签名
    /** ----------------结果参数----------------------------------------*/
    String SUCCESS = "Success";                                 //消息
    String SUCCESS_CODE = "10000";                              //码

}
