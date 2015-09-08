package com.eagle.test;

import org.junit.Before;
import org.junit.Test;

import com.briup.util.BackUP;
import com.briup.util.Configuration;
import com.eagle.util.ConfigurationImp;

public class JTest {
	
	Configuration config;

	@Before
	public void before() {
		config = new ConfigurationImp();
	}

	@Test
	public void testBackUp_store() throws Exception {
		BackUP backup = config.getBackup();
		backup.store("test_store", "test", BackUP.STORE_APPEND);
	}

	@Test
	public void testBackUp_load() throws Exception {
		BackUP backup = config.getBackup();
		Object load = backup.load("test_store", BackUP.LOAD_UNREMOVE);
		System.out.println(load);
	}
}
