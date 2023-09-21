package cn.zw.icore.ap.po.seq;

import cn.zw.icore.ap.base.entity.BasePo;
import cn.zw.icore.ap.base.type.BaseEnumType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zw
 * @since 2023-09-20
 */
@TableName("msp_sequence")
public class MspSequence extends BasePo {

    private static final long serialVersionUID = 1L;

    /**
     * sequence code
     */
    @TableId
    private String seqCode;

    /**
     * sequence describe
     */
    private String seqDesc;

    /**
     * sequence initiation value
     */
    private Long seqInitValue;

    /**
     * sequence cache size
     */
    private Long seqCacheSize;

    /**
     * sequence length
     */
    private Long seqLength;

    /**
     * sequence max value
     */
    private Long seqMaxValue;

    /**
     * sequence build indication(Y-YES,N-NO) 
     */
    private BaseEnumType.E_YESORNO seqBuildInd;

    /**
     * module
     */
    private String module;



    public String getSeqCode() {
        return seqCode;
    }

    public void setSeqCode(String seqCode) {
        this.seqCode = seqCode;
    }

    public String getSeqDesc() {
        return seqDesc;
    }

    public void setSeqDesc(String seqDesc) {
        this.seqDesc = seqDesc;
    }

    public Long getSeqInitValue() {
        return seqInitValue;
    }

    public void setSeqInitValue(Long seqInitValue) {
        this.seqInitValue = seqInitValue;
    }

    public Long getSeqCacheSize() {
        return seqCacheSize;
    }

    public void setSeqCacheSize(Long seqCacheSize) {
        this.seqCacheSize = seqCacheSize;
    }

    public Long getSeqLength() {
        return seqLength;
    }

    public void setSeqLength(Long seqLength) {
        this.seqLength = seqLength;
    }

    public Long getSeqMaxValue() {
        return seqMaxValue;
    }

    public void setSeqMaxValue(Long seqMaxValue) {
        this.seqMaxValue = seqMaxValue;
    }

    public BaseEnumType.E_YESORNO getSeqBuildInd() {
        return seqBuildInd;
    }

    public void setSeqBuildInd(BaseEnumType.E_YESORNO seqBuildInd) {
        this.seqBuildInd = seqBuildInd;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }


    @Override
    public String toString() {
        return "MspSequence{" +
        "seqCode=" + seqCode +
        ", seqDesc=" + seqDesc +
        ", seqInitValue=" + seqInitValue +
        ", seqCacheSize=" + seqCacheSize +
        ", seqLength=" + seqLength +
        ", seqMaxValue=" + seqMaxValue +
        ", seqBuildInd=" + seqBuildInd +
        ", module=" + module +
        ", dataCreateTime=" + this.getDataCreateTime() +
        ", dataUpdateTime=" + this.getDataUpdateTime() +
        ", dataCreateUser=" + this.getDataCreateUser() +
        ", dataUpdateUser=" + this.getDataUpdateUser() +
        ", dataVersion=" + this.getDataVersion() +
        "}";
    }
}
