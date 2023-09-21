package cn.zw.icore.ap.mapper.seq;

import cn.zw.icore.ap.po.seq.MspSequence;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
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
public interface MspSequenceMapper extends BaseMapper<MspSequence> {

}
