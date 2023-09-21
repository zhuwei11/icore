package cn.zw.icore.ap.po.seq;

import cn.zw.icore.ap.base.entity.BasePo;
import cn.zw.icore.ap.base.type.BaseEnumType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zw
 * @since 2023-09-20
 */
@TableName("msp_sequence_builder")
public class MspSequenceBuilder extends BasePo {

    private static final long serialVersionUID = 1L;

    /**
     * sequence code
     */
    @MppMultiId
    private String seqCode;

    /**
     * data sort
     */
    @MppMultiId
    private Long dataSort;

    /**
     * sequence build class(A-identification section,B-serial number,C-check bit,S-constants) 
     */
    private BaseEnumType.E_SEQBUILDCLASS seqBuildClass;

    /**
     * data mart
     */
    private String dataMart;

    /**
     * field name
     */
    private String fieldName;

    /**
     * start position
     */
    private Long startPosition;

    /**
     * cut length
     */
    private Long cutLength;

    /**
     * padding mode(L-left padding,R-right padding) 
     */
    private BaseEnumType.E_PADDINGMODE paddingMode;

    /**
     * padding value
     */
    private String paddingValue;


    public String getSeqCode() {
        return seqCode;
    }

    public void setSeqCode(String seqCode) {
        this.seqCode = seqCode;
    }

    public Long getDataSort() {
        return dataSort;
    }

    public void setDataSort(Long dataSort) {
        this.dataSort = dataSort;
    }

    public BaseEnumType.E_SEQBUILDCLASS getSeqBuildClass() {
        return seqBuildClass;
    }

    public void setSeqBuildClass(BaseEnumType.E_SEQBUILDCLASS seqBuildClass) {
        this.seqBuildClass = seqBuildClass;
    }

    public String getDataMart() {
        return dataMart;
    }

    public void setDataMart(String dataMart) {
        this.dataMart = dataMart;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Long getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Long startPosition) {
        this.startPosition = startPosition;
    }

    public Long getCutLength() {
        return cutLength;
    }

    public void setCutLength(Long cutLength) {
        this.cutLength = cutLength;
    }

    public BaseEnumType.E_PADDINGMODE getPaddingMode() {
        return paddingMode;
    }

    public void setPaddingMode(BaseEnumType.E_PADDINGMODE paddingMode) {
        this.paddingMode = paddingMode;
    }

    public String getPaddingValue() {
        return paddingValue;
    }

    public void setPaddingValue(String paddingValue) {
        this.paddingValue = paddingValue;
    }


    @Override
    public String toString() {
        return "MspSequenceBuilder{" +
        "seqCode=" + seqCode +
        ", dataSort=" + dataSort +
        ", seqBuildClass=" + seqBuildClass +
        ", dataMart=" + dataMart +
        ", fieldName=" + fieldName +
        ", startPosition=" + startPosition +
        ", cutLength=" + cutLength +
        ", paddingMode=" + paddingMode +
        ", paddingValue=" + paddingValue +
        ", dataCreateTime=" + this.getDataCreateTime() +
        ", dataUpdateTime=" + this.getDataUpdateTime() +
        ", dataCreateUser=" + this.getDataCreateUser() +
        ", dataUpdateUser=" + this.getDataUpdateUser() +
        ", dataVersion=" + this.getDataVersion() +
        "}";
    }
}
