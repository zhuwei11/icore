package cn.zw.icore.ap.base.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BasePo implements Serializable {
    public final static String DEFAULT_USERNAME = "system";

    @TableField(fill = FieldFill.INSERT)
    private String dataCreateUser;

    @TableField(fill = FieldFill.INSERT)
    private Date dataCreateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String dataUpdateUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date dataUpdateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer dataVersion;
}
