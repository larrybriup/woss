package com.eagle.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import com.briup.util.BIDR;

public class IOTest {
	File file = null;
	File file2 = null;
	String filePath = "src/com/eagle/file/t.txt";
	// + "temp.txt";
	BufferedInputStream bis = null;
	FileInputStream fis = null;
	static long ll = 0;
	BufferedReader br = null;
	BufferedWriter bw = null;
	static long newLen = 0;
	static long oldLen = 0;

	public static void main(String[] args) {
		IOTest t = new IOTest();
		Collection<BIDR> userLog = t.gather();
//		System.out.println(userLog);
//		System.out.println("========================");
//		con:
//		while (System.currentTimeMillis() % 10000 != 0) {
//			Collection<BIDR> userLog2 = t.gather();
//			System.out.println("2=" + userLog2);
//			continue con;
//		}
	}

	public Collection<BIDR> gather() {
		Map<String, BIDR> map = new LinkedHashMap<String, BIDR>();// 放上的
		Collection<BIDR> c = new LinkedHashSet<BIDR>();// 放配对好的

		try {
			// 接受数据

			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					new File(filePath))));
			String userLogs = "";
			long readLen = 0;// 每次读取的长度
//			newLen = new File(filePath).length();// 把现文件的长度付给newLen
//			if (oldLen == newLen) {// 如果现在的文件的长度等于原来的文件的长度,则跳过
//				br.skip(newLen);
//			} else {
//				br.skip(oldLen);// 跳过原来的文件长度
//				oldLen = newLen;
//			}
			br.mark(3);
			br.reset();
			userLogs=br.readLine();
			while ( br.readLine() != null) {// 读取
				System.out.println(userLogs);
				readLen += userLogs.length();
				// System.out.println(jj);
				String[] userItems = userLogs.split("[#]");

				for (int j = 1; j < userItems.length; j++) {
					String[] userInfos = userItems[j].split("[|]");
				}
			}
			System.out.println("readLen=" + readLen);
			System.out.println("newLen=" + newLen);
			if (br != null)
				br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

}
