package com.thanhtd.glassstore.config;

import io.lettuce.core.ClientOptions;
import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {
    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    private static final String REDIS_PREFIX = "redis:://";

    private static final String REDIS_SSL_PREFIX = "rediss://";

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Value("${redis.connection.timeout}")
    private int redisConnectionTimeout;

    @Value("${redis.read.timeout}")
    private int redisReadTimeout;

    @Value("${redis.max.wait.millis}")
    private int maxWaitMillis;

    @Value("${redis.max.total}")
    private int redisMaxTotal;

    @Value("${redis.min.idle}")
    private int redisMinIdle;

    @Value("${redis.max.idle}")
    private int redisMaxIdle;

    @Value("${spring.redis.client-type}")
    private String clientType;

    @Value("${spring.redis.cluster.max-redirects}")
    private int clusterMaxDirects;

    private final RedisProperties redisProperties;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        if (redisProperties.getCluster() == null || redisProperties.getCluster().getNodes().isEmpty()) {
            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHost, redisPort);
            redisStandaloneConfiguration.setPassword(RedisPassword.of(redisPassword));
            redisStandaloneConfiguration.setDatabase(redisProperties.getDatabase());
            JedisClientConfiguration jedisClientConfig = getJedisClientConfig();
            JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfig);
            jedisConnectionFactory.afterPropertiesSet();
            return jedisConnectionFactory;
        }

        redisProperties.getCluster().getNodes().forEach(node -> logger.info("Cluster node: {}", node));
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(redisProperties.getCluster().getNodes());
        redisClusterConfiguration.setMaxRedirects(clusterMaxDirects);
        redisClusterConfiguration.setPassword(RedisPassword.of(redisPassword));
        if (!"lettuce".equals(clientType)) {
            JedisClientConfiguration clientConfig = getJedisClientConfig();
            JedisConnectionFactory factory = new JedisConnectionFactory(redisClusterConfiguration, clientConfig);
            factory.afterPropertiesSet();
            return factory;
        }

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration
                .builder()
                .shutdownTimeout(Duration.ofSeconds(redisConnectionTimeout))
                .commandTimeout(Duration.ofSeconds(redisReadTimeout))
                .clientOptions(ClientOptions.builder().build())
                .build();
        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisClusterConfiguration, clientConfig);
        factory.afterPropertiesSet();
        return factory;
    }

    private JedisClientConfiguration getJedisClientConfig() {
        return JedisClientConfiguration
                .builder()
                .connectTimeout(Duration.ofSeconds(redisConnectionTimeout))
                .readTimeout(Duration.ofSeconds(redisReadTimeout))
                .usePooling()
                .poolConfig(jedisPoolConfig())
                .build();
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxWait(Duration.ofSeconds(maxWaitMillis));
        jedisPoolConfig.setMaxTotal(redisMaxTotal);
        jedisPoolConfig.setMinIdle(redisMinIdle);
        jedisPoolConfig.setMaxIdle(redisMaxIdle);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnCreate(true);
        jedisPoolConfig.setTestWhileIdle(true);
        jedisPoolConfig.setTimeBetweenEvictionRuns(Duration.ofSeconds(30));
        jedisPoolConfig.setMinEvictableIdleDuration(Duration.ofSeconds(1800000));
        jedisPoolConfig.setNumTestsPerEvictionRun(3);
        jedisPoolConfig.setBlockWhenExhausted(true);
        return jedisPoolConfig;
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        final RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new GenericToStringSerializer<>(Object.class));
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    @ConditionalOnMissingBean({RedisConnectionFactory.class})
    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
        return new RedissonConnectionFactory(redisson);
    }

//    @Bean(
//            destroyMethod = "shutdown"
//    )
//    @ConditionalOnMissingBean({RedissonClient.class})
//    public RedissonClient redisson() {
//        Config config = new Config();
//        if (redisProperties.getCluster().getNodes() == null || !redisProperties.getCluster().getNodes().isEmpty()) {
//            String[] listNode = this.convert(redisProperties.getCluster().getNodes());
//            config.useClusterServers()
//                    .addNodeAddress(listNode)
//                    .setConnectTimeout(redisConnectionTimeout)
//                    .setPassword(this.redisProperties.getPassword());
//        } else {
//            String prefix = this.redisProperties.getSsl().isEnabled() ? REDIS_SSL_PREFIX : REDIS_PREFIX;
//            config.useSingleServer()
//                    .setAddress(prefix + this.redisProperties.getHost() + ":" + this.redisProperties.getPort())
//                    .setConnectTimeout(redisConnectionTimeout)
//                    .setDatabase(this.redisProperties.getDatabase())
//                    .setPassword(this.redisProperties.getPassword());
//        }
//        return Redisson.create(config);
//    }

//    private String[] convert(List<String> nodesObject) {
//        List<String> listNode = new ArrayList<>(nodesObject.size());
//        Iterator<String> var3 = nodesObject.iterator();
//        while (true) {
//            while (var3.hasNext()) {
//                String node = var3.next();
//                if (!node.startsWith(REDIS_PREFIX) && !node.startsWith(REDIS_SSL_PREFIX)) {
//                    listNode.add(REDIS_PREFIX + node);
//                } else {
//                    listNode.add(node);
//                }
//            }
//            return listNode.toArray(new String[listNode.size()]);
//        }
//    }

}
