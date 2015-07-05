package com.eagle.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;

import com.briup.util.BIDR;
import com.briup.util.Configuration;
import com.briup.util.Logger;
import com.briup.woss.ConfigurationAWare;
import com.briup.woss.client.Client;
import com.eagle.util.BackUpImpl;
import com.eagle.util.ConfigurationImp;
import com.eagle.util.LoggerImpl;

/**
 * @author king
 * 
 */
public class ClientImpl implements Client,ConfigurationAWare{
	private Socket client = null;
	private ObjectOutputStream oos = null;
	private BufferedReader br = null;
	private int port;
	private String ip;
	private String storeName;
	private BackUpImpl backUp = null;
	private Logger logger = null;

	@Override
	public void init(Properties p) {
		ip = p.getProperty("ip");

		String pt = p.getProperty("portOfClient");
		port = Integer.parseInt(pt);

		storeName = p.getProperty("back_file_client");

	}

	@Override
	public void send(Collection<BIDR> c) throws Exception {
		try {
			client = new Socket(ip, port);
			logger.info("连接服务器成功!");

			logger.info("正在准备数据...");
			oos = new ObjectOutputStream(client.getOutputStream());
			logger.info("开始发送数据...");
			oos.writeObject(c);
			logger.info("数据发送完毕!共发送了" + c.size() + "条数据!");
			oos.flush();

			if (oos != null)
				oos.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
			backUp.store(storeName, c, backUp.STORE_OVERRIDE);
			logger.error(e.getMessage() + "!已为您成功备份!");
			System.exit(0);
		}
	}

	@Override
	public void setConfiguration(Configuration conf) {
		try {
			backUp=(BackUpImpl) conf.getBackup();
			logger=(LoggerImpl) conf.getLogger();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
