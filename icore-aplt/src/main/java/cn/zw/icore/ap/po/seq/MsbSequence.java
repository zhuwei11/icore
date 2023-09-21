package cn.zw.icore.ap.po.seq;

import cn.zw.icore.ap.base.entity.BasePo;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("msb_sequence")
public class MsbSequence extends BasePo {

    private static final long serialVersionUID = 1L;

    /**
     * sequence code
     */
    @MppMultiId
    private String seqCode;

    /**
     * sequence describe
     */

    private String seqDesc;

    /**
     * identifier_segment
     */
    @MppMultiId
    private String identifierSegment;

    /**
     * sequence no
     */
    private Long seqNo;


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

    public String getIdentifierSegment() {
        return identifierSegment;
    }

    public void setIdentifierSegment(String identifierSegment) {
        this.identifierSegment = identifierSegment;
    }

    public Long getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Long seqNo) {
        this.seqNo = seqNo;
    }

    @Override
    public String toString() {
        return "MsbSequence{" +
        "seqCode=" + seqCode +
        ", seqDesc=" + seqDesc +
        ", identifierSegment=" + identifierSegment +
        ", seqNo=" + seqNo +
        ", dataCreateTime=" + this.getDataCreateTime() +
        ", dataUpdateTime=" + this.getDataUpdateTime() +
        ", dataCreateUser=" + this.getDataCreateUser() +
        ", dataUpdateUser=" + this.getDataUpdateUser() +
        ", dataVersion=" + this.getDataVersion() +
        "}";
    }
}
