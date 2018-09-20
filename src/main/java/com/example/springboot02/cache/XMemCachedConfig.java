package com.example.springboot02.cache;

import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.TextCommandFactory;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.transcoders.SerializingTranscoder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Created by Administrator on 2018/9/20.
 */
@Configuration
public class XMemCachedConfig {

    @Configuration
    @EnableConfigurationProperties(XMemCachedProperties.class)
    @ConditionalOnProperty(prefix = "memcached", name = "enabled", havingValue = "true", matchIfMissing = true)
    public static class XMemcachedClientConfig {
        private XMemCachedProperties properties;
        @Autowired
        public void setProperties(XMemCachedProperties properties) {
            this.properties = properties;
        }

        @Bean
        public XMemcachedClientBuilder xMemcachedClientBuilder() {

            XMemcachedClientBuilder clientBuilder = new XMemcachedClientBuilder(properties.getInetSocketAddresses(), properties.getWeights());
            clientBuilder.setConnectionPoolSize(properties.getPoolSize());
            clientBuilder.setCommandFactory(textCommandFactory());
            clientBuilder.setSessionLocator(ketamaMemcachedSessionLocator());
            clientBuilder.setTranscoder(serializingTranscoder());

            /*
             * 所谓failure模式，当某个节点挂掉的时候，不会从节点列表移除，请求也不会转移到下一个有效节点，
             * 而是直接将请求置 为失败，就刚才的场景来说，在用户更新数据到节点A的时候，
             * 节点A意外断开，那么用户的这次更新请求不会转移到节点B，而是直接告诉用户更新失败，
             * 用户再次查询数据则绕过节点A直接查询后端存储。这种模式很适合这种节点短暂不可用的状况，
             * 请求会穿透缓存到后端，但是避免了新旧数据的问题。
             */
            clientBuilder.setFailureMode(properties.isFailureMode());
            clientBuilder.setConnectTimeout(properties.getConnectTimeout());
            clientBuilder.setOpTimeout(properties.getOpTimeout());
            clientBuilder.setEnableHealSession(properties.isEnableHealSession());
            clientBuilder.setHealSessionInterval(properties.getHealSessionInterval());
            if (StringUtils.isNotBlank(properties.getName())) {
                clientBuilder.setName(properties.getName());
            }
            clientBuilder.setMaxQueuedNoReplyOperations(properties.getMaxQueuedNoReplyOperations());
            clientBuilder.setSanitizeKeys(properties.isSanitizeKeys());


            return clientBuilder;
        }

        @Bean
        public TextCommandFactory textCommandFactory() {
            return new TextCommandFactory();
        }

        @Bean
        public KetamaMemcachedSessionLocator ketamaMemcachedSessionLocator() {
            return new KetamaMemcachedSessionLocator();
        }

        @Bean
        public SerializingTranscoder serializingTranscoder() {
            return new SerializingTranscoder();
        }

        /**
         * XMemCachedClient 配置
         * @return XMemCachedClient
         * @throws IOException IOException
         */
        @Bean(destroyMethod = "shutdown")
        public XMemcachedClient memcachedClient() throws IOException {
            return (XMemcachedClient) xMemcachedClientBuilder().build();
        }
    }

    @Bean(autowire = Autowire.BY_TYPE)
    public MemCachedUtil memCachedUtil () {
        return new MemCachedUtil();
    }

}
