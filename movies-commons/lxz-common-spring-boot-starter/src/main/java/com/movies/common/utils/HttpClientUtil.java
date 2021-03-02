package com.movies.common.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.ssl.SSLSocketFactoryBuilder;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import java.io.*;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Http请求工具类
 * @author lx Zhang.
 * @date 2020/4/10
 */
public class HttpClientUtil {



    /**
     * POST请求（带json参数）
     * @param url					请求地址
     * 							如果访问一个接口,多少时间内无法返回数据,就直接放弃此次调用
     * @param jsonParam	json参数
     * @return
     */
    public static String doPostJson(String url, String jsonParam) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);

            // 设置请求超时
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000)						// 设置连接超时时间,单位毫秒。
                    .setConnectionRequestTimeout(1000)  			// 从连接池获取到连接的超时,单位毫秒。
                    .setSocketTimeout(5000).build();    	// 请求获取数据的超时时间,单位毫秒; 如果访问一个接口,多少时间内无法返回数据,就直接放弃此次调用。
            httpPost.setConfig(requestConfig);

            // 创建请求内容
            StringEntity entity = new StringEntity(jsonParam, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);

            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof SocketTimeoutException) {
                System.out.println("响应超时！！！");
            }
        } finally {
            // 释放资源
            try {
                if (null != httpClient) {
                    httpClient.close();
                }
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {

                e.printStackTrace();
            }
        }

        return resultString;
    }


    /**
     * POST请求（带Xml参数）
     * @param url					请求地址
     * 							如果访问一个接口,多少时间内无法返回数据,就直接放弃此次调用
     * @param xml	xml参数
     * @return
     */
    public static String doPostXml(String url, String xml) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Get请求
            HttpPost httpPost = new HttpPost(url);
            // 设置请求超时
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000)						// 设置连接超时时间,单位毫秒。
                    .setConnectionRequestTimeout(1000)  			// 从连接池获取到连接的超时,单位毫秒。
                    .setSocketTimeout(5000).build();    	// 请求获取数据的超时时间,单位毫秒; 如果访问一个接口,多少时间内无法返回数据,就直接放弃此次调用。
            httpPost.setConfig(requestConfig);
            // 创建请求内容
            StringEntity entity = new StringEntity(xml, ContentType.APPLICATION_XML);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            //指定编码,防止中文乱码
            resultString = EntityUtils.toString(response.getEntity(),"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof SocketTimeoutException) {
                System.out.println("响应超时！！！");
            }
        } finally {
            // 释放资源
            try {
                if (null != httpClient) {
                    httpClient.close();
                }
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {

                e.printStackTrace();
            }
        }

        return resultString;
    }

    public static String doPostXml2(String requestUrl, String requestMethod, String output) {
        try {
            URL url = new URL(requestUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod(requestMethod);
            if (null != output) {
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(output.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            connection.disconnect();
            return buffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }




    /**
     * GET请求
     * @param url					请求地址
     * 							如果访问一个接口,多少时间内无法返回数据,就直接放弃此次调用
     * @param   paramMap  传入参数
     * @return
     */
    public static String doGet(String url, Map<String, String> paramMap) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpGet httpGet = new HttpGet();

            // 设置请求超时
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000)						// 设置连接超时时间,单位毫秒。
                    .setConnectionRequestTimeout(1000)  			// 从连接池获取到连接的超时,单位毫秒。
                    .setSocketTimeout(5000).build();    	// 请求获取数据的超时时间,单位毫秒; 如果访问一个接口,多少时间内无法返回数据,就直接放弃此次调用。
            httpGet.setConfig(requestConfig);

            // 创建请求内容
            List<NameValuePair> formparams = setHttpParams(paramMap);
            String param = URLEncodedUtils.format(formparams,  Charset.defaultCharset());
            httpGet.setURI(URI.create(url + "?" + param));
            // 执行http请求
            response = httpClient.execute(httpGet);
            resultString = EntityUtils.toString(response.getEntity(), Charset.defaultCharset());
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof SocketTimeoutException) {
                System.out.println("响应超时！！！");
            }
        } finally {
            // 释放资源
            try {
                if (null != httpClient) {
                    httpClient.close();
                }
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {

                e.printStackTrace();
            }
        }

        return resultString;
    }


    /**
     * 设置请求参数
     *
     * @param
     * @return
     */
    private static List<NameValuePair> setHttpParams(Map<String, String> paramMap) {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        Set<Map.Entry<String, String>> set = paramMap.entrySet();
        for (Map.Entry<String, String> entry : set) {
            formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        return formparams;
    }



    /**
     * 发送post请求
     * @param url 地址
     * @param data 参数
     * @param certPath 证书路径
     * @param certPassword 证书密码
     * @return
     */
    public static String postSsl(String url,String data,String certPath,String certPassword){
        try {
            return HttpRequest.post(url)
                    .setSSLSocketFactory(SSLSocketFactoryBuilder
                            .create()
                            .setProtocol(SSLSocketFactoryBuilder.TLSv1)
                            .setKeyManagers(getKeyManager(certPassword,certPath,null))
                            .setSecureRandom(new SecureRandom())
                            .build())
                    .body(data)
                    .execute()
                    .body();
        } catch (Exception e) {
            throw new RuntimeException("发起退款请求异常!");
        }
    }

    /**
     * 发送post请求
     * @param url 地址
     * @param data 参数
     * @param certFile 证书
     * @param certPassword 证书密码
     * @return
     */
    public static String postSsl(String url,String data,InputStream certFile,String certPassword){
        try {
            return HttpRequest.post(url)
                    .setSSLSocketFactory(SSLSocketFactoryBuilder
                            .create()
                            .setProtocol(SSLSocketFactoryBuilder.TLSv1)
                            .setKeyManagers(getKeyManager(certPassword,null,certFile))
                            .setSecureRandom(new SecureRandom())
                            .build())
                    .body(data)
                    .execute()
                    .body();
        } catch (Exception e) {
            throw new RuntimeException("post 异常!");
        }
    }


    /**
     * 获取证书数据
     * @param certPassword 密码
     * @param certPath 证书路径
     * @param certFile 证书
     * @throws Exception
     */
    private static KeyManager[] getKeyManager(String certPassword, String certPath, InputStream certFile)throws Exception {
        KeyStore clientStore = KeyStore.getInstance("PKCS12");
        if (certFile != null){
            clientStore.load(certFile,certPassword.toCharArray());
        }else{
            clientStore.load(new FileInputStream(certPath),certPassword.toCharArray());
        }
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(clientStore,certPassword.toCharArray());
        return kmf.getKeyManagers();
    }
}
