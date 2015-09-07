package com.eagle.util;

import java.util.Properties;

import com.briup.util.Logger;

public class LoggerImpl implements Logger {

	private String logPrpt;
	private org.apache.log4j.Logger logger;

	@Override
	public void init(Properties p) {
		// logPrpt=p.getProperty("log_properties");
		// PropertyConfigurator.configure(logPrpt);
		this.logger = org.apache.log4j.Logger.getRootLogger();
	}

	@Override
	public void debug(String paramString) {
		logger.debug(paramString);
	}

	@Override
	public void info(String paramString) {
		logger.info(paramString);
	}

	@Override
	public void warn(String paramString) {
		logger.warn(paramString);
	}

	@Override
	public void error(String paramString) {
		logger.error(paramString);
	}

	@Override
	public void fatal(String paramString) {
		logger.fatal(paramString);
	}

}
