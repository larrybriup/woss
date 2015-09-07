package com.eagle.main;

import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import com.briup.util.BIDR;
import com.briup.util.BackUP;
import com.briup.util.Configuration;
import com.briup.util.Logger;
import com.briup.woss.client.Client;
import com.briup.woss.client.Gather;
import com.eagle.util.ConfigurationImp;

public class ClientMain {

	static Configuration conf = null;
	static Collection<BIDR> c = null;
	static Collection<BIDR> cc = null;
	static Collection<BIDR> ccc = null;
	static Logger logger = null;
	static BackUP backUp;
	static Client client = null;
	static Gather gi = null;

	public static void main(String[] args) {
		try {
			conf = new ConfigurationImp();
			backUp = (BackUP) conf.getBackup();
			logger = (Logger) conf.getLogger();
			client = (Client) conf.getClient();
			gi = (Gather) conf.getGather();

			cc = (Collection<BIDR>) backUp.load("clientStore.txt", backUp.LOAD_REMOVE);
			if (cc != null) {
				logger.info("备份文件加载成功!开始发送...");
				client.send(cc);
				logger.info("备份发送完毕!");
			}

			logger.info("正在读取...");
			if ((c = gi.gather()) != null) {
				client.send(c);
			}

			Timer t = new Timer();
			TimerTask tt = new TimerTask() {

				public void run() {
					try {
						if ((ccc = gi.gather()) != null && ccc.size() != 0) {
							client.send(ccc);
						} else {
							logger.info("日志文件没有增加,不重复获取!");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			t.schedule(tt, 4000, 10000);// 10秒执行一次gather
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (c.size() != 0) {
					BackUP bak = (BackUP) conf.getBackup();
					bak.store("clientStore.txt", c, false);
					logger.error(e.getMessage() + ",系统已经为您备份成功!\n共备份了" + c.size() + "条数据.");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
	}
}
