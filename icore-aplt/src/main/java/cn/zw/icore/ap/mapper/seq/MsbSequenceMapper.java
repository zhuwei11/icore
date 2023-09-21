package cn.zw.icore.ap.mapper.seq;

import cn.zw.icore.ap.po.seq.MsbSequence;
import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zw
 * @since 2023-09-20
 */
@Repository
@Mapper
public interface MsbSequenceMapper extends MppBaseMapper<MsbSequence> {

    @Select("select * from msb_sequence where seq_code = ${seqCode} and identifier_segment = ${identifierSegment} for update")
    public MsbSequence selOneWithLock(@Param("seqCode") String seqCode, @Param("identifierSegment") String identifierSegment);

}
