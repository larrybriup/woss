package com.eagle.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;

import com.briup.util.BackUP;

public class BackUpImpl implements BackUP {
	String storePath = null;

	public String getStorePath() {
		return storePath;
	}

	public void init(Properties p) {
		storePath = p.getProperty("back_temp");
	}

	public void store(String storeName, Object obj, boolean flag)
			throws Exception {
		
		File file = new File(this.storePath + File.separator + storeName);
		
		if (!file.exists())
			file.createNewFile();
		
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(file, flag));

			oos.writeObject(obj);

			oos.flush();

		} finally {
				if (oos != null)
					oos.close();
		}
	}

	public Object load(String loadName, boolean flag) throws Exception {
		StringBuilder sb = new StringBuilder();
		Object object = null;
		ObjectInputStream ois = null;
		File file = new File(storePath + File.separator + loadName);
		try {
			if (file.exists() && file.isFile() && file.length() > 0) {
				ois = new ObjectInputStream(new FileInputStream(file));

//				while ((object = ois.readObject()) != null) {
//					sb.append(object);
//				}
				
				object = ois.readObject();
				if (flag) {	//如果flag是true
					file.deleteOnExit();	//那么在退出时把加载的文件删除
				}
			}
		} finally {
			if (ois != null) {
				ois.close();
			}
		}

		return object;
	}

}
