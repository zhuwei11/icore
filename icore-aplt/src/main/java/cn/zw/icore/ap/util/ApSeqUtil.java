package cn.zw.icore.ap.util;

import cn.zw.icore.ap.base.exception.BaseException;
import cn.zw.icore.ap.base.exception.SystemErrorType;
import cn.zw.icore.ap.base.type.BaseEnumType;
import cn.zw.icore.ap.constant.ApConstants;
import cn.zw.icore.ap.po.seq.MspSequence;
import cn.zw.icore.ap.po.seq.MsbSequence;
import cn.zw.icore.ap.po.seq.MspSequenceBuilder;
import cn.zw.icore.ap.service.seq.IMsbSequenceService;
import cn.zw.icore.ap.service.seq.IMspSequenceBuilderService;
import cn.zw.icore.ap.service.seq.IMspSequenceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhuwei
 * 流水生成工具类
 */
public class ApSeqUtil {
    // 流水缓存
    private static Map<String, Sequence> seqCaches = new ConcurrentHashMap<String, Sequence>();

    // 流水缓存key的连接符
    private final static String SEPARATOR = "^^^^";

    // 默认识别段
    private final static String DEFALUT_RECOGINTION_SEGMENT = "****";

    // 默认补位值
    private final static String DEFAULT_PADDING_VALUE = "0";

    @Resource
    static
    IMspSequenceService mspSequenceService;

    @Resource
    static
    IMspSequenceBuilderService mspSequenceBuilderService;

    @Resource
    static
    IMsbSequenceService msbSequenceService;
    /**
     * @Author zw
     *         <p>
     *         <li>2016年12月8日-下午2:08:43</li>
     *         <li>功能说明：获取一个字符型流水号</li>
     *         <li>当流水组建标志为Y时，调用此函数前需要调用addDataToBuffer方法，
     *         往runEnvs中设置app_sequence_builder对于的rule_buffer</li>
     *         </p>
     * @param seqCode
     * @return
     */
    public static String genSeq(String seqCode) {
        // 获取流水定义
        MspSequence seqDef = mspSequenceService.getById("seqCode");
        if (seqDef == null)
            throw new BaseException(SystemErrorType.DATA_IS_NULL,"TABLE MspSequence 数据为null");

        // 如果序号组建标志为N,则不使用app_sequence_builder组装报文
        if (BaseEnumType.E_YESORNO.NO == seqDef.getSeqBuildInd()) {
            return genSeq(seqCode, DEFALUT_RECOGINTION_SEGMENT);
        }

        StringBuilder recognition = new StringBuilder(); // 识别段
        String sequence = ""; // 序号段
        StringBuilder result = new StringBuilder();

        MspSequenceBuilder seqBuilder = null; // 流水序号组建定义
        int seqStartIndex = 0; // 流水序号在流水中的起始位置

        MspSequenceBuilder checkBuilder = null; // 校验位组建定义
        int checkStartIndex = 0; // 校验位在流水中的起始位置

        int curLength = 0; // 流水当前组装的长度，用于序号段和检验为占位

        QueryWrapper<MspSequenceBuilder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("seq_code",seqCode);
        queryWrapper.orderByAsc("data_sort");
        List<MspSequenceBuilder> seqBuilders = mspSequenceBuilderService.list(queryWrapper);

//        // 全局缓存表排序有问题,主动排序
//        CommTools.listSort(seqBuilders, true, MsDict.Comm.data_sort.getId());

        for (MspSequenceBuilder builder : seqBuilders) {
            switch (builder.getSeqBuildClass()) {
                case RECOGNITION:
                    // 根据build构造字符串
                    String recogintionTemp = buildRecogintion(builder);
                    // 进行截取、补位，
                    recogintionTemp = padding(recogintionTemp, builder);

                    recognition.append(recogintionTemp);
                    curLength += Math.abs(builder.getCutLength());
                    break;
                case SEQUENCE:
                    // 由于序号段需要根据最终的识别段生成，因此这里先标记序号段位置
                    seqBuilder = builder;
                    seqStartIndex = curLength;
                    curLength += Math.abs(builder.getCutLength());
                    break;
                case CHECKBIT:
                    // 由于校验位需要根据最终的识别段和序号段生成，因此这里先标记校验位位置
                    checkBuilder = builder;
                    checkStartIndex = curLength;
                    curLength += Math.abs(builder.getCutLength());
                    // recognition.append(padding("", builder));// 校验位先添加空格，防止下面截取报错
                    break;
                case CONSTANTS:
                    // 增加常量配置
                    curLength += Math.abs(builder.getCutLength());
                    recognition.append(builder.getFieldName());
                    break;
                default:
                    break;
            }
        }

        // 产生序号
        if (seqBuilder != null) {
            sequence = getSequence(seqDef, recognition.toString()) + "";
            sequence = padding(sequence, seqBuilder);

            result.append(recognition.substring(0, seqStartIndex)).append(sequence).append(recognition.substring(seqStartIndex));

        }

        // 产生校验位
        if (checkBuilder != null) {
            // 根据 recognition 和 sequence 算出 check bit;
            String resultTemp = CommUtil.isNull(result.toString()) ? recognition.toString() : result.toString();
            String checkBit = CommUtil.genCardnoCheckBit(resultTemp.trim()) + "";
            checkBit = padding(checkBit, checkBuilder);
            result.setLength(0);
            result.append(resultTemp.substring(0, checkStartIndex)).append(checkBit).append(resultTemp.substring(checkStartIndex));
        }
        // 总长度检查（由于均根据builder定义的长度对每段数据进行 了截取、补位，
        // 除非配置出错,app_sequence里面的seq_build_length和app_sequence_builder各段组成的长度不一致，才可能出现长度不一致）

        if (CommUtil.isNull(result.toString())) {// 全是A
            result.append(recognition.toString().trim());
        }

        return result.toString();
    }


    private static class Sequence {
        private String recognitionSegment; // 序号识别段

        private long currentValue = 1; // 缓存中流水当前值
        private long maxValue = 0;// 缓存中流水限值
        private MspSequence seqDef; // 缓存定义

        public Sequence(MspSequence seqDef, String recognitionSegment) {
            this.recognitionSegment = recognitionSegment;
            this.seqDef = seqDef;
        }

        public synchronized long next() {
            if (this.maxValue > this.currentValue) {
                this.currentValue = currentValue + 1;
                return this.currentValue;
            }

            // 获取最大值
            this.maxValue = getSequenceInfo();
            this.currentValue = this.maxValue - seqDef.getSeqCacheSize() + 1;

            return this.currentValue;
        }

        /**
         * 开启新事物更新
         * @return
         */
        @Transactional(propagation = Propagation.REQUIRES_NEW)
        private Long getSequenceInfo() {
//            return DaoUtil.executeInNewTransation(new RunnableWithReturn<Long>() {
//
//                public Long execute() {
//                    return dbProcess(seqDef, recognitionSegment);
//                }
//            });
            return dbProcess(seqDef, recognitionSegment);
        }
    }

    /**
     * @Author chensy
     *         <p>
     *         <li>2016年12月8日-下午2:09:41</li>
     *         <li>功能说明：获取一个字符型流水号（适用于流水组建标志=N、无流水号识别段的情况）</li>
     *         </p>
     * @param seqCode
     * @param recognitionSegment
     * @return
     */
    public static String genSeq(String seqCode, String recognitionSegment) {

        // 获取流水定义
        // 获取流水定义
        MspSequence seqDef = mspSequenceService.getById("seqCode");
        if (seqDef == null)
            throw new BaseException(SystemErrorType.SEQ_BUILD_ERROR,"TABLE MspSequence 数据为null");

        // 判断流水号组件标志必须=N
        if (BaseEnumType.E_YESORNO.YES == seqDef.getSeqBuildInd()) {
            throw new BaseException(SystemErrorType.SEQ_BUILD_ERROR, "流水号组件标志必须=N");
        }

        if (StringUtils.isBlank(recognitionSegment)) {
            recognitionSegment = DEFALUT_RECOGINTION_SEGMENT;
        }

        String sequence = getSequence(seqDef, recognitionSegment) + "";

        // 返回 recognitionSegment + 默认左补0 的流水号
        int seqLength = seqDef.getSeqLength().intValue();
        // 默认识别段不拼接到流水中
        if (DEFALUT_RECOGINTION_SEGMENT.equals(recognitionSegment)) {
            return CommUtil.lpad(sequence, seqLength, DEFAULT_PADDING_VALUE);
        }
        else {
            return recognitionSegment + CommUtil.lpad(sequence, seqLength, DEFAULT_PADDING_VALUE);
        }
    }

    private static long getSequence(MspSequence seqDef, String recognitionSegment) {

        // 缓存大小为1,表示无缓存,且要确保流水号的连续性,直接用主事物处理
        if ( seqDef.getSeqCacheSize() == 1L){
            return dbProcess(seqDef, recognitionSegment);
        }
        else{
            String sKey = seqDef.getSeqCode() + SEPARATOR + recognitionSegment;
            Sequence seq = seqCaches.get(sKey);
            if (seq == null) {
                synchronized (seqCaches) {
                    seq = seqCaches.get(sKey);
                    if (seq == null) {
                        seq = new Sequence(seqDef, recognitionSegment);
                        seqCaches.put(sKey, seq);
                    }
                }
            }
            return seq.next();
        }
    }

    private static Long dbProcess(MspSequence seqDef, String recognitionSegment) {

        MsbSequence ret = msbSequenceService.selOneWithLock(seqDef.getSeqCode(), recognitionSegment);

        if (ret == null) {
            ret = new MsbSequence();
            ret.setSeqCode(seqDef.getSeqCode());
            ret.setIdentifierSegment(recognitionSegment);
            ret.setSeqDesc(seqDef.getSeqDesc());
            ret.setSeqNo(seqDef.getSeqInitValue() + seqDef.getSeqCacheSize() - 1);
            try {
                msbSequenceService.save(ret);
            }
            catch (Exception e) {
                //bizLog.debug("MsbSequence insert error! [%s]", e, e.getMessage());
                ret = msbSequenceService.selOneWithLock(seqDef.getSeqCode(), recognitionSegment);
                if(ret == null) {
                    throw new BaseException(SystemErrorType.DATA_IS_NULL);
                }
            }
        }

        long newSeqNo = ret.getSeqNo() + seqDef.getSeqCacheSize();
        // 越界复位处理
        if (newSeqNo > seqDef.getSeqMaxValue()) {
            ret.setSeqNo(seqDef.getSeqInitValue() + seqDef.getSeqCacheSize() - 1);
        }
        else {
            ret.setSeqNo(newSeqNo);
        }
        msbSequenceService.updateByMultiId(ret);
        return ret.getSeqNo();
    }

    private static String buildRecogintion(MspSequenceBuilder builder) {
        String dataMart = builder.getDataMart();
        String fieldName = builder.getFieldName();
        Object fieldValue = null;
        String result = ""; // 默认为空字符串

//         数据集如果是RunEnvs则直接从RunEnvs中获取
        if (ApConstants.RUN_ENVS.equals(dataMart)) {
            fieldValue = ApBuffer.getBaseRunEnvsValue(fieldName);
        }
        else {
            // 从runEnvs中获取Rule_buffer
            Map ruleBuffer = ApBuffer.getBuffer();
            // 如果没有调用addDataToBuffer方法，则获取的字段为空字符串
            if (ruleBuffer != null) {
                Map<String, Object> mDataMart = (Map<String, Object>) ruleBuffer.get(dataMart);
                if (mDataMart != null) {
                    fieldValue = mDataMart.get(fieldName);
                }
            }
        }

        if (fieldValue != null) {
            result = fieldValue.toString();
        }

        // 先截取长度，后补位
        if (result.length() > (Math.abs(builder.getStartPosition()) - 1)) {
            if (builder.getStartPosition() > 0) {
                if (builder.getCutLength() > 0) {
                    result = result.substring(builder.getStartPosition().intValue() - 1); // 参数里面配置起始位置从1开始算，因此程序里面必须减1；
                }
                else {
                    result = result.substring(0, builder.getStartPosition().intValue());
                }
            }
            else {
                int start = result.length() - Math.abs(builder.getStartPosition().intValue());
                if (builder.getCutLength() > 0) {
                    result = result.substring(start);
                }
                else {
                    result = result.substring(0, start + 1);
                }
            }
        }

        return result;
    }

    // 根据编码配置规则，进行相应的截取、补位方式、补位值。
    private static String padding(String src, MspSequenceBuilder builder) {

        String ret = (src == null ? "" : src);
        int length = builder.getCutLength().intValue();

        if (ret.length() > Math.abs(length)) {
            if (length > 0) {
                ret = ret.substring(0, length);
            }
            else {
                ret = ret.substring(ret.length() - Math.abs(length));
            }

        }
        else if (ret.length() < Math.abs(length)) {
            String padValue = builder.getPaddingValue();
            if (CommUtil.isNull(padValue)) {
                padValue = DEFAULT_PADDING_VALUE;
            }
            if (BaseEnumType.E_PADDINGMODE.LEFT == builder.getPaddingMode()) {
                ret = CommUtil.lpad(ret, length, padValue);
            }
            else {
                ret = CommUtil.rpad(ret, length, padValue);
            }
        }
        return ret;
    }
}
