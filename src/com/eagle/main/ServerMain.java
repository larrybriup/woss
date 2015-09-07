package com.eagle.main;

import java.io.File;
import java.util.Collection;

import com.briup.util.BIDR;
import com.briup.util.BackUP;
import com.briup.util.Configuration;
import com.briup.util.Logger;
import com.briup.woss.server.DBStore;
import com.briup.woss.server.Server;
import com.eagle.util.BackUpImpl;
import com.eagle.util.ConfigurationImp;

public class ServerMain {

	public static void main(String[] args) {
		BackUP backUp = null;
		Collection<BIDR> c = null;
		Collection<BIDR> cc = null;
		Logger logger = null;
		try {
			Configuration conf = new ConfigurationImp();
			DBStore db = (DBStore) conf.getDBStore();
			Server server = (Server) conf.getServer();
			backUp = (BackUP) conf.getBackup();
			logger = (Logger) conf.getLogger();

			logger.info("开启服务器...");

			// 加载备份数据并存储到数据库中
			File f = new File(((BackUpImpl) backUp).getStorePath() + "/" + "serverStore.txt");
			logger.info("加载备份文件...");
			cc = (Collection<BIDR>) backUp.load("serverStore.txt", backUp.LOAD_REMOVE);
			if (cc != null) {
				logger.info("备份文件加载成功!开始存储...");
				db.saveToDB(cc);
				logger.info("备份存储完毕!共存储了" + cc.size() + "条记录!");
			} else {
				logger.info("没有备份文件!");
			}
			long before = 0;
			// if((c =server.revicer())!=null) {
			before = System.currentTimeMillis();
			// logger.info("共接收了" + c.size() + "条数据!");
			// logger.info("开始往数据库存储数据...");
			// db.saveToDB(c);
			// }else {
			// logger.info("没有数据!");
			// }
			server.revicer();
			long after = System.currentTimeMillis();

			logger.info("存储成功!耗时 " + (after - before) + "ms,共" + c.size() + "条数据!");

		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (c.size() != 0) {
					backUp.store("serverStore.txt", c, backUp.STORE_OVERRIDE);
					logger.info("数据插入异常!已成功备份!共备份" + c.size() + "条数据!");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}
