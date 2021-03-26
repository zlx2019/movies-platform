package com.movies.gateway.handler;

import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.movies.common.constant.ServiceConst;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * Java代码实现 Sentinel限流规则
 * @author lx Zhang.
 * @date 2021/3/26 4:03 下午
 */
public class SentinelHandler {

    /**
     * 编写限流规则
     * @Author lx Zhang.
     * @Date 2021/3/26 4:06 下午
     **/
    @PostConstruct
    public void initSentinel(){
        Set<GatewayFlowRule> rules = new HashSet<>();
        GatewayFlowRule rule = new GatewayFlowRule(ServiceConst.ACTOR_SERVICE); //资源名
        rule.setCount(3) //限流阈值
                .setIntervalSec(30); //统计时间窗口,30m

        rules.add(rule);
        GatewayRuleManager.loadRules(rules);
    }
}
