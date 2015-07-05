package com.eagle.util;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import com.briup.util.Logger;


public class LoggerImpl implements Logger{
	private String logPrpt;
	private org.apache.log4j.Logger logger;
	
	@Override
	public void init(Properties p) {
		// TODO Auto-generated method stub
//		logPrpt=p.getProperty("log_properties");
//		PropertyConfigurator.configure(logPrpt);
		this.logger=org.apache.log4j.Logger.getRootLogger();
		
		
	}

	@Override
	public void debug(String paramString) {
		// TODO Auto-generated method stub
		logger.debug(paramString);
	}

	@Override
	public void info(String paramString) {
		// TODO Auto-generated method stub
		logger.info(paramString);
		
	}

	@Override
	public void warn(String paramString) {
		// TODO Auto-generated method stub
		logger.warn(paramString);
		
	}

	@Override
	public void error(String paramString) {
		// TODO Auto-generated method stub
		logger.error(paramString);
		
	}

	@Override
	public void fatal(String paramString) {
		// TODO Auto-generated method stub
		logger.fatal(paramString);
		
	}

}
