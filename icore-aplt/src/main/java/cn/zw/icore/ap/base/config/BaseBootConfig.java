package cn.zw.icore.ap.base.config;

import cn.zw.icore.ap.base.advice.BaseExceptionHandlerAdvice;
import cn.zw.icore.ap.base.advice.RestResponseBodyAdvice;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * 初使化全局报文和全局异常配置
 */
@AutoConfiguration
@Import({BaseExceptionHandlerAdvice.class, RestResponseBodyAdvice.class})
public class BaseBootConfig {
}
