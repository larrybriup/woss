package com.eagle.test;

import java.io.File;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.briup.util.BIDR;

public class StringTest {

	String loadPath = null;

	public void init() {
		loadPath = "src/com/eagle/backUpFile";
		File f = new File(loadPath + "/" + "serverStore.txt");
		System.out.println(f.exists());
		System.out.println(f.getTotalSpace());
		System.out.println(f.getName());
		System.out.println(f.isFile());
		System.out.println(f.length());
	}

	public static void main(String[] args) {
		Collection<BIDR> c = null;
		List<BIDR> list1 = new LinkedList<BIDR>();
		List<BIDR> list2 = new LinkedList<BIDR>();

		String str = "#briup4596|037:wKgB4596A|7|1239140260|71.98.224.251"
						+ "#|037:wKgB2450A|8|1239256000|71.98.224.251"
						+ "#briup7822|037:wKgB7822A|7|1239172520|38.72.174.112"
						+ "#briup1457|037:wKgB1457A|7|1239108870|104.66.128.6";
		String[] userItems = str.split("#");
		for (int j = 1; j < userItems.length; j++) {
			System.out.println(userItems[j]);
			String[] userInfos = userItems[j].split("[|]");

			if (userInfos[2].equals("7")) {
				String login_name = userInfos[0];
				String NAS_ip = userInfos[1];

				long lin = Long.parseLong(userInfos[3]);
				Timestamp linTimestamp = new Timestamp(lin);

				String login_ip = userInfos[4];

				BIDR b = new BIDR();
				b.setAAA_login_name(login_name);
				b.setLogin_ip(login_ip);
				b.setLogin_date(linTimestamp);
				b.setNAS_ip(NAS_ip);

				list1.add(b);
			}
			if (userInfos[2].equals("8")) {
				for (int k = 0; k < list1.size(); k++) {

					String login_ip = list1.get(k).getLogin_ip();
					if (userInfos[4].equals(login_ip)) {

						String la_login_name = list1.get(k).getAAA_login_name();
						String la_login_ip = list1.get(k).getLogin_ip();
						String la_NAS_ip = list1.get(k).getNAS_ip();
						Timestamp la_InTimestamp = list1.get(k).getLogin_date();

						long la_OutTime = Long.parseLong(userInfos[3]);
						Timestamp la_OutTimestamp = new Timestamp(la_OutTime);

						long li = la_InTimestamp.getTime();
						long lo = la_OutTimestamp.getTime();
						long de = lo - li;
						Integer la_deration = (int) de;

						BIDR b2 = new BIDR();
						b2.setAAA_login_name(la_login_name);
						b2.setLogin_ip(la_login_ip);
						b2.setLogin_date(la_InTimestamp);
						b2.setNAS_ip(la_NAS_ip);
						b2.setLogout_date(la_OutTimestamp);
						b2.setTime_deration(la_deration);

						list2.add(b2);
						// list1.remove(k);
					}

				}
			}
			System.out.println("=============================");
		}
		// System.out.println(list1.get(1).getAAA_login_name());
		System.out.println(list2.get(0).getAAA_login_name());
		System.out.println(list2.get(0).getLogin_ip());
		c = list2;

	}
}
