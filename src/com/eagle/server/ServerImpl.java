package com.eagle.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;

import com.briup.util.BIDR;
import com.briup.util.BackUP;
import com.briup.util.Configuration;
import com.briup.util.Logger;
import com.briup.woss.ConfigurationAWare;
import com.briup.woss.server.DBStore;
import com.briup.woss.server.Server;
import com.eagle.util.BackUpImpl;
import com.eagle.util.LoggerImpl;

public class ServerImpl implements Server, ConfigurationAWare {
	private ServerSocket server = null;
	private Socket client = null;
	private ObjectInputStream ois = null;
	private int port;
	private Collection<BIDR> bidrs = null;
	private BackUP backUp = null;
	private Logger logger = null;
	private String storeName = null;
	private boolean flag;
	private DBStore db;

	public Collection<BIDR> revicer() {
		flag = true;
		try {
			server = new ServerSocket(port);
			while (flag) {
				client = server.accept();
				Thread t = new ServerThread(client,db,backUp,logger,storeName);
				t.start();
			}
//			shutdown();
		} catch ( Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void shutdown() {
		try {
			flag=false;
			ois.close();
			client.close();
			server.close();
			logger.info("服务器关闭!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void init(Properties p) {
		String po = p.getProperty("portOfServer");
		port = Integer.parseInt(po);
		storeName = p.getProperty("back_file_server");
	}

	@Override
	public void setConfiguration(Configuration conf) {
		try {
			db=conf.getDBStore();
			backUp = (BackUpImpl) conf.getBackup();
			logger = (LoggerImpl) conf.getLogger();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
