package com.eagle.server;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Collection;

import com.briup.util.BIDR;
import com.briup.util.BackUP;
import com.briup.util.Logger;
import com.briup.woss.server.DBStore;

public class ServerThread extends Thread{
	private Socket client = null;
	private ObjectInputStream ois=null;
	private DBStore db=null;
	private BackUP backUP=null;
	private Logger logger=null;
	private String storeName=null;
	private Collection<BIDR> bidrs=null;
	
	public ServerThread(Socket client,DBStore db,BackUP backUP,
					Logger logger,String storeName) {
		this.client=client;
		this.db=db;
		this.backUP=backUP;
		this.logger=logger;
		this.storeName=storeName;
	}
	
	public void run() {
		
			try {
				ois = new ObjectInputStream(client.getInputStream());
				 bidrs = (Collection<BIDR>) ois.readObject();
				if(bidrs!=null&&bidrs.size()!=0) {
					db.saveToDB(bidrs);
					logger.info("存储成功!共存储"+bidrs.size()+"条数据!");
				}else {
					logger.error("没有数据,不能存储!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				try {
					if(bidrs!=null&&bidrs.size()!=0) {
						backUP.store(storeName, bidrs, backUP.STORE_OVERRIDE);
						logger.error("大哥啊!"+e.getMessage()+"已经成功为您备份!");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} 
	}
}
