package com.eagle.util;

import java.io.File;
import java.util.List;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.briup.util.BackUP;
import com.briup.util.Configuration;
import com.briup.util.Logger;
import com.briup.woss.ConfigurationAWare;
import com.briup.woss.client.Client;
import com.briup.woss.client.Gather;
import com.briup.woss.server.DBStore;
import com.briup.woss.server.Server;

public class ConfigurationImp implements Configuration {
	String dbStoreClassPath = null;
	String url = null;
	String driver = null;
	String username = null;
	String password = null;

	String loggerClassPath = null;
	String log_properties = null;

	String backClassPath = null;
	String back_temp = null;

	String gatherClassPath = null;
	String src_file = null;

	String clientClassPath = null;
	String back_file_client = null;
	String ip = null;
	String portOfClient = null;

	String serverClassPath = null;
	String portOfServer = null;
	String back_file_server = null;

	private Properties p;
	public ConfigurationImp() {
		this.parseAndInit();
	}

	private void parseAndInit() {
		String xmlPath = "src/com/eagle/file/conf.xml";
		// 创建SAXReader类型的对象
		SAXReader reader = new SAXReader();
		// 读取要解析的xml文件，返回Document类型的对象。
		Element rootElement = null;
		try {
			Document document = reader.read(new File(xmlPath));
			rootElement = document.getRootElement();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		List<Element> list = rootElement.elements();
		for (Element element1 : list) {
			String name = element1.getName();
			if (name == "dbstore") {
				dbStoreClassPath = element1.attribute("class").getText();
				List<Element> list1 = element1.elements();
				for (Element element2 : list1) {
					if (element2.getName() == "url") {
						url = element2.getText();
					}
					if (element2.getName() == "driver") {
						driver = element2.getText();
					}
					if (element2.getName() == "username") {
						username = element2.getText();
					}
					if (element2.getName() == "password") {
						password = element2.getText();
					}
				}
			}
			if (name == "logger") {
				loggerClassPath = element1.attribute("class").getText();
				List<Element> list1 = element1.elements();
				for (Element element2 : list1) {
					if (element2.getName() == "log-properties") {
						log_properties = element2.getText();
					}
				}
			}
			if (name == "backup") {
				backClassPath = element1.attribute("class").getText();
				List<Element> list1 = element1.elements();
				for (Element element2 : list1) {
					if (element2.getName() == "back-temp") {
						back_temp = element2.getText();
					}
				}
			}
			if (name == "gather") {
				gatherClassPath = element1.attribute("class").getText();
				List<Element> list1 = element1.elements();
				for (Element element2 : list1) {
					if (element2.getName() == "src-file") {
						src_file = element2.getText();
					}
				}
			}
			if (name == "client") {
				clientClassPath = element1.attribute("class").getText();
				List<Element> list1 = element1.elements();
				for (Element element2 : list1) {
					if (element2.getName() == "back_file") {
						back_file_client = element2.getText();
					}
					if (element2.getName() == "ip") {
						ip = element2.getText();
					}
					if (element2.getName() == "port") {
						portOfClient = element2.getText();
					}
				}
			}
			if (name == "server") {
				serverClassPath = element1.attribute("class").getText();
				List<Element> list1 = element1.elements();
				for (Element element2 : list1) {
					if (element2.getName() == "port") {
						portOfServer = element2.getText();
					}
					if (element2.getName() == "back-file") {
						back_file_server = element2.getText();
					}
				}
			}
		}
	}

	public Logger getLogger() throws Exception {
		Logger logger = (Logger) Class.forName(loggerClassPath).newInstance();
		if (logger instanceof ConfigurationAWare) {
			((ConfigurationAWare) logger).setConfiguration(this);
		}
		Properties p = new Properties();
		p.setProperty("log_properties", log_properties);
		logger.init(p);
		return logger;
	}

	public BackUP getBackup() throws Exception {
		BackUP backup = (BackUP) Class.forName(backClassPath).newInstance();
		if (backup instanceof ConfigurationAWare) {
			((ConfigurationAWare) backup).setConfiguration(this);
		}
		Properties p = new Properties();
		p.setProperty("back_temp", back_temp);
		backup.init(p);
		return backup;
	}

	public Gather getGather() throws Exception {
		Gather gather = (Gather) Class.forName(gatherClassPath).newInstance();
		if (gather instanceof ConfigurationAWare) {
			((ConfigurationAWare) gather).setConfiguration(this);
		}
		Properties p = new Properties();
		p.setProperty("src_file", src_file);
		gather.init(p);
		return gather;
	}

	public Client getClient() throws Exception {
		Client client = (Client) Class.forName(clientClassPath).newInstance();
		if (client instanceof ConfigurationAWare) {
			((ConfigurationAWare) client).setConfiguration(this);
		}
		Properties p = new Properties();
		p.setProperty("back_file_client", back_file_client);
		p.setProperty("ip", ip);
		p.setProperty("portOfClient", portOfClient);
		client.init(p);
		return client;
	}

	public Server getServer() throws Exception {
		Server server = (Server) Class.forName(serverClassPath).newInstance();
		if (server instanceof ConfigurationAWare) {
			((ConfigurationAWare) server).setConfiguration(this);
		}
		Properties p = new Properties();
		p.setProperty("back_file_server", back_file_server);
		p.setProperty("portOfServer", portOfServer);
		server.init(p);
		return server;
	}

	public DBStore getDBStore() throws Exception {
		DBStore dbStore = (DBStore) Class.forName(dbStoreClassPath)
				.newInstance();
		if (dbStore instanceof ConfigurationAWare) {
			((ConfigurationAWare) dbStore).setConfiguration(this);
		}
		Properties p = new Properties();
		p.setProperty("url", url);
		p.setProperty("driver", driver);
		p.setProperty("username", username);
		p.setProperty("password", password);
		dbStore.init(p);
		return dbStore;
	}

}
