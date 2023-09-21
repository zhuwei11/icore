package cn.zw.icore;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;


public class DaoGen {
    private static final DataSourceConfig DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://localhost:3306/demo?serverTimezone=Asia/Shanghai", "root", "Weiwei111")
            .schema("demo")
            .build();

    public static void main(String[] args) {
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.global(new GlobalConfig.Builder()
                .outputDir("/Users/zhuwei/Desktop/IDEA/idea-workespace")
                .author("zw")
                .build());
        generator.packageInfo(new PackageConfig.Builder()
                .parent("cn.zw.icore.ap.po.seq")
                .moduleName("seq")
                .entity("po")
                .mapper("mapper")
                .build());
        generator.strategy(new StrategyConfig.Builder()
                .addFieldPrefix("dataVersion")
                .addFieldPrefix("dataCreateTime")
                .addFieldPrefix("dataUpdateTime")
                .addFieldPrefix("dataCreateUser")
                .addFieldPrefix("dataUpdateTime")
//                .addTablePrefix("ms")
                .entityBuilder()
                .superClass("cn.zw.icore.base.entity.BasePo").build());
        generator.execute();
    }
}
