package cn.zw.icore.ap.util;

import cn.zw.icore.ap.constant.ApConstants;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;
/**
 * <p>
 * 文件功能说明：交易缓冲区处理
 * </p>
 * 
 * @Author zw
 *         <p>
 *         <li>2016年12月12日-上午11:51:55</li>
 *         <li>修改记录</li>
 *         <li>-----------------------------------------------------------</li>
 *         <li>标记：修订内容</li>
 *         <li>20140228 zw：创建注释模板</li>
 *         <li>-----------------------------------------------------------</li>
 *         </p>
 */
@Slf4j
public class ApBuffer {

	private ApBuffer() {
	}

	/**
	 * @Author zw
	 *         <p>
	 *         <li>2016年12月12日-上午11:54:58</li>
	 *         <li>功能说明：将某个数据集加到数据缓冲区。（采用先删除再增加的方式）</li>
	 *         </p>
	 * @param dataMart
	 * @param commObj
	 */
	public static void addData(String dataMart, Map<String, Object> commObj) {
		addData(dataMart, commObj, false);
	}

	/**
	 * @Author zw
	 *         <p>
	 *         <li>2016年03月20日-上午09:54:58</li>
	 *         <li>功能说明：往某个数据集追加数据。</li>
	 *         </p>
	 * @param dataMart
	 * @param commObj
	 */
	public static void appendData(String dataMart, Map<String, Object> commObj) {
		addData(dataMart, commObj, true);
	}

	public static void replaceFieldData(String dataMart, Map<String, Object> commObj) {

		Map<String, Object> ruleBuffer = getBuffer();
		Object data = ruleBuffer.get(dataMart);
		if (data == null) {
			ruleBuffer.put(dataMart, commObj);
		} else {
			if (data instanceof Map) {
				Map<String, Object> dataMap = (Map<String, Object>) data;
				for (String key : commObj.keySet()) {
					dataMap.put(key, commObj.get(key));
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static void addData(String dataMart, Map<String, Object> commObj, boolean appendFlag) {

		Map<String, Object> ruleBuffer = getBuffer();

		// 追加
		if (appendFlag) {
			Object data = ruleBuffer.get(dataMart);
			if (data == null) {
				ruleBuffer.put(dataMart, commObj);
			} else {
				if (data instanceof Map) {
					Map<String, Object> dataMap = (Map<String, Object>) data;
					Iterator<Entry<String, Object>> it = commObj.entrySet().iterator();
					while (it.hasNext()) {
						String key = it.next().getKey();
						// 有不一样的key 才添加
						if (!dataMap.containsKey(key)) {
							dataMap.put(key, commObj.get(key));
						}
					}
				}
			}

		} else {
			ruleBuffer.remove(dataMart);
			ruleBuffer.put(dataMart, commObj);
		}
	}

	/**
	 * @Author tsichang
	 *         <p>
	 *         <li>2017年3月9日-下午4:51:31</li>
	 *         <li>功能说明：获取缓冲区某个字段的值</li>
	 *         </p>
	 * @param dataMart
	 * @param fieldName
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public static String getFieldValue(String dataMart, String fieldName) {

		Map<String, Object> ruleBuffer = getBuffer();
		Object value = null;

		// 如果是公共运行变量，直接去公共运行变量去取
		if (ApConstants.RUN_ENVS.equals(dataMart)) {
			value = getBaseRunEnvsValue(fieldName);
		} else if (ApConstants.ADDITIONAL_DATA_MART.equals(dataMart)) {
			@SuppressWarnings("rawtypes")
			Map customParms = null; //TODO CommTools.getBaseRunEnvs().getCustom_parm();

			if (customParms != null) {
				value = String.valueOf(customParms.get(fieldName));
			}
		} else {// 其他的就从缓冲区取
			Object dataObj = ruleBuffer.get(dataMart);// 数据集
			if (dataObj == null) {
				throw new IllegalArgumentException("unknow dataMart:" + dataMart);
			}

			if (dataObj instanceof Map) {
				Map<String, Object> data = (Map<String, Object>) dataObj;
				value = data.get(fieldName);
			}
		}
		return (value == null) ? null : value.toString();
	}

	/**
	 * @Author ZW
	 *         <p>
	 *         <li>2016年12月13日-下午3:42:26</li>
	 *         <li>功能说明：根据key获取公共运行变量的value</li>
	 *         </p>
	 * @param key
	 * @return value key对应公共运行变量的值 公共运行变量不存在，返回null
	 */
	public static Object getBaseRunEnvsValue(String key) {

		if (CommUtil.isNull(key))
			return null;

		Object value = null;
//TODO 	RunEnvsComm runEnvs = CommTools.getBaseRunEnvs();
		try {
//TODO			Method method = RunEnvsComm.class.getMethod("get" + key.substring(0, 1).toUpperCase() + key.substring(1));
//TODO			value = method.invoke(runEnvs);
		} catch (Exception e) {
			log.error("Get BaseRunEnvs Value failed. [%s]", e, e.getMessage());
		}
		return value;
	}

	/**
	 * 
	 * @Author tsichang
	 *         <p>
	 *         <li>2017年4月25日-上午9:56:15</li>
	 *         <li>功能说明:获取当前的缓存区</li>
	 *         </p>
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public static Map<String, Object> getBuffer() {
		Object bufferObj = null;//CommTools.getBaseRunEnvs().getRule_buffer();

		Stack<Map<String, Object>> ruleBufferStack = null;
		if (bufferObj == null) {
			ruleBufferStack = new Stack<Map<String, Object>>();
//TODO			CommTools.getBaseRunEnvs().setRule_buffer(ruleBufferStack);
		} else if (bufferObj instanceof Stack) {
			ruleBufferStack = (Stack<Map<String, Object>>) bufferObj;
		} else if (bufferObj instanceof List) {
			ruleBufferStack = new Stack<Map<String, Object>>();
			ruleBufferStack.addAll((List) bufferObj);
//TODO			CommTools.getBaseRunEnvs().setRule_buffer(ruleBufferStack);
		} else {
			throw new IllegalArgumentException("unknow class type :" + bufferObj.getClass());
		}

		if (ruleBufferStack.size() == 0) { // 如果栈底服务是直接通过调用服务API，而不是通过"服务类型执行器"调用的，则需要初始化压入第一层缓存。
			Map<String, Object> currentBuffer = new HashMap<String, Object>();
			ruleBufferStack.push(currentBuffer);

			return currentBuffer;
		} else {
			return ruleBufferStack.peek();
		}

	}

}
