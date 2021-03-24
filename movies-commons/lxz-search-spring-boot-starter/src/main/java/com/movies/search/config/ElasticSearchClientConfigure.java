package com.movies.search.config;

import com.movies.common.properties.ElasticSearchProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Objects;

/**
 * RestHighLevelClient 配置
 * @author lx Zhang.
 * @date 2021/3/24 10:46 下午
 */
@Slf4j
@EnableConfigurationProperties(ElasticSearchProperties.class)
public class ElasticSearchClientConfigure {

    @Autowired
    private ElasticSearchProperties properties;

    /**
     * client 配置
     * @Author lx Zhang.
     * @Date 2021/3/24 11:20 下午
     * @Param []
     * @return RestClientBuilder
     **/
    @Bean
    public RestClientBuilder restClientBuilder(){
        RestClientBuilder restClientBuilder = null;
        HttpHost[] httpHosts;
        if (properties.getNodes().contains(",")){
            String[] nodes = properties.getNodes().split(",");
            httpHosts = Arrays.stream(nodes)
                    .map(this::makeHttpHost)
                    .filter(Objects::nonNull)
                    .toArray(HttpHost[]::new);
            restClientBuilder = RestClient.builder(httpHosts);
        }else {
            if (StringUtils.isNotBlank(properties.getNodes())){
                httpHosts = new HttpHost[1];
                httpHosts[0] = makeHttpHost(properties.getNodes());
                restClientBuilder = RestClient.builder(httpHosts);
            }
        }
        // 设置一个监听器，每次节点出现故障时都会收到通知，以防需要采取措施，
        // 当启用故障嗅探时在内部使用。
        restClientBuilder.setFailureListener(new RestClient.FailureListener(){
            @Override
            public void onFailure(Node node) {
                //TODO
            }
        });
        // 设置允许修改默认请求配置的回调
        //（例如请求超时，身份验证或org.apache.http.client.config.RequestConfig.Builder允许设置的任何内容）
        restClientBuilder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder builder) {
                return builder.setConnectTimeout(properties.getConnectTimeout())
                        .setSocketTimeout(properties.getSocketTimeout())
                        .setConnectionRequestTimeout(properties.getConnectionRequestTimeout());
            }
        });
        //jvm配置
        restClientBuilder.setHttpClientConfigCallback(builder -> builder.setDefaultIOReactorConfig(IOReactorConfig.custom()
                .setIoThreadCount(10) //设置线程数
                .build()));
        return restClientBuilder;
    }

    /**
     * 注入RestHighLevelClient
     * @Author lx Zhang.
     * @Date 2021/3/24 11:30 下午
     * @Param [restClientBuilder]
     * @return RestHighLevelClient
     **/
    @Bean
    public RestHighLevelClient restHighLevelClient(@Autowired RestClientBuilder restClientBuilder){
        log.info("RestHighLevelClient init success------------------------------------");
        return new RestHighLevelClient(restClientBuilder);
    }


    /**
     * 拆解node节点 返回HttpHost
     * @Author lx Zhang.
     * @Date 2021/3/24 11:15 下午
     * @Param [s]
     * @return org.apache.http.HttpHost
     **/
    private HttpHost makeHttpHost(String s) {
        String[] address = s.split(":");
        if (address.length == 2) {
            String ip = address[0];
            int port = Integer.parseInt(address[1]);
            return new HttpHost(ip, port, properties.getScheme());
        } else {
            return null;
        }
    }
}
