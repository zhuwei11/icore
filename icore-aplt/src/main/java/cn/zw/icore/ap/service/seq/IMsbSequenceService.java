package cn.zw.icore.ap.service.seq;

import cn.zw.icore.ap.po.seq.MsbSequence;
import com.github.jeffreyning.mybatisplus.service.IMppService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zw
 * @since 2023-09-20
 */
public interface IMsbSequenceService extends IMppService<MsbSequence> {

    public MsbSequence selOneWithLock(String seqCode, String identifierSegment);
}
