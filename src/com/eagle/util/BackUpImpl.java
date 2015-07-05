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

	public void store(String storeName, Object paramObject, boolean flag)
			throws Exception {
		File sFile = new File(this.storePath + "/" + storeName);
		if (!sFile.exists())
			sFile.createNewFile();
		ObjectOutputStream oos = null;

		try {
			oos = new ObjectOutputStream(new FileOutputStream(sFile, flag));

			oos.writeObject(paramObject);

			oos.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null)
					oos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Object load(String loadName, boolean flag) throws Exception {
		Object object = null;
		ObjectInputStream ois = null;
		File lFile = new File(storePath + "/" + loadName);
		try {
			if (lFile.exists() && lFile.isFile() && lFile.length() > 0) {
				ois = new ObjectInputStream(new FileInputStream(lFile));

				object = ois.readObject();
				if (flag) {//如果flag是true
					lFile.deleteOnExit();//那么在退出时把加载的文件删除
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				ois.close();
			}
		}

		return object;
	}

}
