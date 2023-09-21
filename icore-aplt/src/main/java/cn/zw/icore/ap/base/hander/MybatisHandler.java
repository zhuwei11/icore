package cn.zw.icore.ap.base.hander;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.Instant;
import java.util.Date;

@Slf4j
public class MybatisHandler implements MetaObjectHandler {
    /**
     * 获取当前交易的用户，为空返回默认system
     *
     * @return CurrentUsername
     */
    private String getCurrentUsername() {
       //TODO return StringUtils.defaultIfBlank(UserContextHolder.getInstance().getUsername(), BasePo.DEFAULT_USERNAME);
        return "";
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "dataCreateUser", String.class, getCurrentUsername());
        this.strictInsertFill(metaObject, "dataCreateTime", Date.class, Date.from(Instant.now()));
        this.strictUpdateFill(metaObject, "dataVersion", Integer.class, 1);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "dataUpdateUser", String.class, getCurrentUsername());
        this.strictUpdateFill(metaObject, "dataUpdateTime", Date.class, Date.from(Instant.now()));
        this.strictUpdateFill(metaObject, "dataVersion", Integer.class, Integer.parseInt(metaObject.getValue("data_version").toString()) + 1);
    }
}