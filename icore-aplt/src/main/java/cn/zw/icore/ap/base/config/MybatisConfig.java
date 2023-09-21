package cn.zw.icore.ap.base.config;

import cn.zw.icore.ap.base.hander.MybatisHandler;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.IllegalSQLInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@AutoConfiguration
@EnableTransactionManagement
public class MybatisConfig {
    /**
     * 初使化Mybatis审计字段自动赋值
     */
    @Bean
    public MybatisHandler myBatisHandler() {
        return new MybatisHandler();
    }

    /**
     * mybatis插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        //防止全表更新与删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        //sql 性能规范插件
        interceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());
        return interceptor;
    }
}
