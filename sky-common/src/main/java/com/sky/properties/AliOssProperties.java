package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
/*
    这个注解自动从yml中读取 sky.alioss.* 的所有值
    而如果不加这个只打Component就必须
    @Value("${sky.alioss.endpoint}")
    private String endpoint;
    采取这种写法。
    使用该注解更规整
 */
@ConfigurationProperties(prefix = "sky.alioss")
@Data
public class AliOssProperties {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

}
