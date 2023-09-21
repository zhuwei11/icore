package cn.zw.icore.ap.service.seq.impl;


import cn.zw.icore.ap.mapper.seq.MsbSequenceMapper;
import cn.zw.icore.ap.po.seq.MsbSequence;
import cn.zw.icore.ap.service.seq.IMsbSequenceService;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import org.springframework.stereotype.Service;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zw
 * @since 2023-09-20
 */
@Service
public class MsbSequenceServiceImpl extends MppServiceImpl<MsbSequenceMapper, MsbSequence> implements IMsbSequenceService {

    @Override
    public MsbSequence selOneWithLock(String seqCode, String identifierSegment) {
        return this.selOneWithLock(seqCode, identifierSegment);
    }
}
