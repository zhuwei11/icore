package cn.zw.icore.ap.service.seq.impl;


import cn.zw.icore.ap.mapper.seq.MspSequenceBuilderMapper;
import cn.zw.icore.ap.po.seq.MspSequenceBuilder;
import cn.zw.icore.ap.service.seq.IMspSequenceBuilderService;
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
public class MspSequenceBuilderServiceImpl extends MppServiceImpl<MspSequenceBuilderMapper, MspSequenceBuilder> implements IMspSequenceBuilderService {

}
