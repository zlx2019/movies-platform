package com.movies.common.utils;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝支付工具类
 * @author lx Zhang.
 * @date 2021/3/2 3:01 下午
 */
@Slf4j
public class AliPayUtils {

    private static final String ALI_PAY_GATEWAY = "https://openapi.alipay.com/gateway.do";

    /**
     * 支付宝手机网站支付、支付宝电脑网站支付
     * @return
     *
     */
    public static String aliPayBy (String appId, String appPrivateKey, String format, String charset, String appPublicKey
            , String signType, String orderId, String returnUrl, String notifyUrl, BigDecimal realPrice
    ) throws AlipayApiException {
        return null;
    }
//        //获得初始化的AlipayClient
//        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do" , appId
//                , appPrivateKey, format, charset, appPublicKey, signType);
//
//        // 单位元
//        String price = String.valueOf(realPrice);
//        String subject = "飞燕数学商城购物,详情请登录飞燕数学查看";
//
//        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
//        model.setOutTradeNo(orderId);
//        model.setSubject(subject);
//        model.setTotalAmount(price);
//        model.setTimeoutExpress("15m");
//        // 自定义存放字段 异步通知时会带上
//        Map<String, Object> map = new ConcurrentHashMap<>(6);
//        map.put("123", "message");
//        String s = JSONUtil.toJsonStr(map);
//        model.setPassbackParams(s);
//        model.setProductCode(alipayProductCodeEnum.getCode());
//        // 电脑支付
//        if (alipayProductCodeEnum.getCode().equals(AlipayProductCodeEnum.COMPUTER_WAY_PAY.getCode())) {
//            //创建API对应的request
//            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
//            alipayRequest.setBizModel(model);
//            alipayRequest.setReturnUrl(returnUrl);
//            alipayRequest.setNotifyUrl(notifyUrl);
//
//            //调用SDK生成表单
//            String form = alipayClient.pageExecute(alipayRequest).getBody();
//            return form;
//        }
//        if (alipayProductCodeEnum.getCode().equals(AlipayProductCodeEnum.WAP_WAY_PAY.getCode())) {
//            //创建API对应的request
//            AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
//            alipayRequest.setBizModel(model);
//            alipayRequest.setReturnUrl(returnUrl);
//            alipayRequest.setNotifyUrl(notifyUrl);
//
//            //调用SDK生成表单
//            String form = alipayClient.pageExecute(alipayRequest).getBody();
//            return form;
//        }
//
//        log.error("[支付异常]  请选择正确的支付方式参数！");
//        throw new BusinessException("请输入正确的支付参数信息！");
//    }

    /**
     * 支付宝关闭订单
     * @param appId
     * @param appPrivateKey
     * @param format
     * @param charset
     * @param publicKey
     * @param signType
     * @param orderId
     * @return
     * @throws AlipayApiException
     */
    public static Boolean closeOrder (String appId, String appPrivateKey, String format, String charset, String publicKey
            , String signType, String orderId) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appId, appPrivateKey, format, charset, publicKey, signType);
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        request.setBizContent("{" +
                "\"trade_no\":\"" + orderId + "\"," +
                "  }");
        AlipayTradeCloseResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }

    }


    public static AlipayTradeQueryResponse findByOrder (String appId, String appPrivateKey, String format, String charset
            , String publicKey, String signType, String orderId) throws AlipayApiException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do" , appId
                , appPrivateKey, format, charset
                , publicKey, signType);
        AlipayTradeQueryRequest alipayTradeQueryRequest = new AlipayTradeQueryRequest();
        alipayTradeQueryRequest.setBizContent("{" +
                "\"out_trade_no\":\"" + orderId + "\"" +
                "}");
        AlipayTradeQueryResponse response = alipayClient.execute(alipayTradeQueryRequest);
        if (StringUtils.isEmpty(response.getTradeStatus())) {
            throw new RuntimeException("失败！");
        }
        return response;
    }



    /**
     * 根据内部订单号查询支付宝订单信息
     * @author L
     * @date 2020年9月9日 16:04:24
     * @param {appId:应用ID,appPrivateKey:应用私钥,format:传输协议,publicKey:公钥,signType:签名方式,orderNo:订单号}
     * @return com.alipay.api.response.AlipayTradeQueryResponse
     */
    public static AlipayTradeQueryResponse findAliPayIsPayByOrderNo(String appId,String appPrivateKey,String charset,String publicKey,String signType,String orderNo){
        DefaultAlipayClient client = new DefaultAlipayClient(ALI_PAY_GATEWAY, appId, appPrivateKey, "json", charset, publicKey, signType);
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        Map<String, String> params = new HashMap<>();
        params.put("out_trade_no",orderNo);
        request.setBizContent(JSON.toJSONString(params));
        AlipayTradeQueryResponse response = null;
        try {
            response = client.execute(request);
            return response;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            log.error("根据订单号查询支付宝支付信息异常!");
            return null;
        }
    }

    /**
     * 支付宝方 是否已支付
     * 参数同上
     * @return
     */
    public static Boolean findOrderIsPay(String appId,String appPrivateKey,String charset,String publicKey,String signType,String orderNo){
        AlipayTradeQueryResponse response = findAliPayIsPayByOrderNo(appId, appPrivateKey, charset, publicKey, signType, orderNo);
        if (response != null){
            if (response.isSuccess()) {
                if (response.getCode().equals("10000") && response.getMsg().equals("Success")){
                    if (response.getTradeStatus().equals("TRADE_SUCCESS")){
                        //已支付
                        return true;
                    }
                }
            }

            if (response.getSubCode().equals("ACQ.TRADE_NOT_EXIST")){
                //交易不存在
                return false;
            }
        }
        return false;
    }

}
