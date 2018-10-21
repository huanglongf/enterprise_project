package common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.stream.FileImageInputStream;

import com.alibaba.fastjson.JSON;
import com.bt.vims.utils.AESUtil;
import com.bt.vims.utils.BASE64UtilStrong;
import com.bt.vims.utils.SignProcess;

import net.sf.json.JSONArray;

public class SocketData {
	/**
	 * 客户端造数据
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<String> getJsonArray() {
		List<String> socketData = new ArrayList<>();
		Map<String, String> map = new LinkedHashMap();
		String encPhoto = producePhoto("D://123.jpg");
		String str = "[{content:'张三',body:{content:'123',visitor_name:'李四',check_in_plcae:'南京',visitor_type:'团体',visitor_num:'11',photo:'"
				+ encPhoto + "'}}]";
		JSONArray jsonArray = JSONArray.fromObject(str);
		map.put("contactCode", Contents.CONTACT_CODE);
		map.put("method", Contents.METHOD);
		map.put("systemKey", Contents.SYSTEM_KEY);
		String body = AESUtil.encrypt(JSON.toJSONString(jsonArray), Contents.SECRET_KEY);
		map.put("body", body);
		map.put("version", Contents.VERSION);
		String newSign = SignProcess.makeMd5Sign(map, Contents.SECRET_KEY); // 加密传入的数据
		socketData.add(newSign);
		socketData.add(body);
		return socketData;
	}

	/**
	 * 解析图片并且加密的方法
	 * 
	 * @return 加密后的图片数据
	 */
	private static String producePhoto(String path) {
		byte[] data = null;
		FileImageInputStream input = null;
		try {
			input = new FileImageInputStream(new File(path));
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int numBytesRead = 0;
			while ((numBytesRead = input.read(buf)) != -1) {
				output.write(buf, 0, numBytesRead);
			}
			data = output.toByteArray();
			output.close();
			input.close();
		} catch (FileNotFoundException ex1) {
			ex1.printStackTrace();
		} catch (IOException ex1) {
			ex1.printStackTrace();
		}
		String encPhoto = BASE64UtilStrong.encode(data);
		return encPhoto;
	}

}
