package cn.zw.icore.ap.base.config;


import cn.zw.icore.ap.base.hander.IcoreStartedEventHandler;
import cn.zw.icore.ap.base.hander.MappingInfoHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * Opensabre Rest信息事件通知配置类
 */
@AutoConfiguration
@Import({IcoreStartedEventHandler.class, MappingInfoHandler.class})
public class BaseServiceConfig {
}
