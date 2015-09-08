package com.eagle.client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;

import com.briup.util.BIDR;
import com.briup.util.BackUP;
import com.briup.util.Configuration;
import com.briup.util.Logger;
import com.briup.woss.ConfigurationAWare;
import com.briup.woss.client.Gather;
import com.eagle.util.LoggerImpl;

/**
 * 收集用户上下线日期文件,返回BIDR类型的Collection集合， Collection集合中存放: 
 * private String AAA_login_name; 用户登陆名 
 * private String login_ip; 登陆IP 
 * private Timestamp login_date;登陆日期,时间戳
 * private Timestamp logout_date; 推出登陆日期,时间戳 
 * private String NAS_ip; NASIP 
 * private Integer time_deration;上网持续时间,时间戳
 * 
 * #briup1660|037:wKgB1660A|7|1239110900|44.211.221.247
 * #|037:wKgB1660A|8|1239203860|44.211.221.247
 * 
 * @author king
 * @group SuperEagle
 */
public class GatherImpl implements Gather, ConfigurationAWare {

	private FileInputStream fis = null;
	private BufferedInputStream bis = null;
	private BufferedReader br = null;
	private File file = null;
	private String filePath;
	private BackUP backUp;
	private Logger logger;
	private static long newLen = 0;
	private static long oldLen = 0;

	public Collection<BIDR> gather() {

		Map<String, BIDR> map = new HashMap<String, BIDR>(50);// 放上的
		Collection<BIDR> c = new HashSet<BIDR>(50);// 放配对好的

		try {
			// 接受数据
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath))));

			if (backUp.load("map.txt", backUp.LOAD_UNREMOVE) != null) {
				map = (Map<String, BIDR>) backUp.load("map.txt", backUp.LOAD_REMOVE);
				logger.info("加载map成功!");
			}

			// 精华部分,可以实现动态获取
			newLen = new File(filePath).length();// 把现文件的长度赋给newLen
			if (oldLen == newLen) {// 如果现在的文件的长度等于原来的文件的长度,则跳过
				br.skip(newLen);
			} else {// 否则跳过原来的文件长度,并更新oldLen
				br.skip(oldLen);
				oldLen = newLen;
			}

			String userLogs = "";
			while ((userLogs = br.readLine()) != null) {// 读取

				String[] userItems = userLogs.split("[#]");

				for (int j = 1; j < userItems.length; j++) {

					String[] userInfos = userItems[j].split("[|]");

					// 是7就放入list1,是8就和list1中的数据配对,然后放入list2并删除list1中的此行
					if (userInfos[2].equals("7")) {

						Timestamp login_date = new Timestamp(Long.parseLong(userInfos[3]));

						BIDR b = new BIDR(userInfos[0], userInfos[4], login_date, null, userInfos[1], null);

						map.put(userInfos[4], b);

					} else {// 如果是8下线
						for (int k = 0; k < map.size(); k++) {// 遍历map

							BIDR bidr = map.get(userInfos[4]);// 通过key取得BIDR

							Timestamp logout_date = new Timestamp(Long.parseLong(userInfos[3]));

							long login = bidr.getLogin_date().getTime();
							long logout = logout_date.getTime();
							Integer time_deration = (int) (logout - login);

							bidr.setLogout_date(logout_date);
							bidr.setTime_deration(time_deration);

							c.add(bidr);// 把对象放入set集合
						}
						map.remove(userInfos[4]);// 配对成功之后删除map中无用的对象
					}
				}
			}
			if (map != null && map.size() != 0) {
				backUp.store("map.txt", map, BackUP.STORE_OVERRIDE);// 备份未配对成功的上线记录,覆盖已经加载过的map
				logger.info("备份map成功!");
			}
			if (br != null)
				br.close();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (c != null) {
					backUp.store("clientStore.txt", c, false);
					logger.fatal(e.getMessage() + "发生意外!已经为您成功备份!");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return c;
	}

	@Override
	public void init(Properties p) {
		filePath = p.getProperty("src_file");
	}

	@Override
	public void setConfiguration(Configuration conf) {
		try {
			backUp = conf.getBackup();
			logger = conf.getLogger();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
