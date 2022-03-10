package com;

import java.util.Arrays;

import tools.FileTools;

public class SqlHelper {
	private final static String DEL_SERVICES = "delete from services where name in ";
	private final static String DEL_BUSSSERVICES = "delete from bussservices where serviceid in ";
	private final static String DEL_SERVICESINFO = "delete from serviceinfo where serviceid in ";
	private final static String DEL_BINDMAP = "delete from bindmap where serviceid in";
	private final static String DEL_DATAADAPTOR = "delete from dataadapter where DATAADAPTERID in ";
	private final static String DEL_SERVICESYSTEMMAP = "delete from servicesystemmap where serviceid in ";
	private final static String DEL_DEPLOYMENTS = "delete from deployments where name in ";

	public static void main(String[] args) {
		byte[] bytes = FileTools.readContent("files/services.txt");
		String services = new String(bytes);
		int strLen = services.length();
		if ((strLen % 16) != 0 || !services.startsWith(",")) {
			System.out.println("services.txt文件不符合要求，请检查后再试！");
			System.out.println("要求：1.文件总字符数能整除16；2.以英文半角逗号开始。");
			return;
		}
		String[] serviceArray = services.split(",");
		String[] newArray = {};
		String[] repeated = {};
		for (String service : serviceArray) {
			if (Arrays.asList(newArray).contains(service)) {
				repeated = Arrays.copyOf(repeated, repeated.length + 1);
				repeated[repeated.length - 1] = service;
			} else {
				newArray = Arrays.copyOf(newArray, newArray.length + 1);
				newArray[newArray.length - 1] = service;
			}
		}
		if (repeated.length > 0) {
			System.out.println("services.txt文件不符合要求，请检查后再试！");
			System.out.println("重复元素：" + Arrays.toString(repeated));
			return;
		}
		int serviceNum = strLen / 16;
		System.out.println("serviceNum:[" + serviceNum + "]");
		int groups = (int) Math.ceil((double) serviceNum / 100);
		System.out.println("groups:[" + groups + "]");
		StringBuffer sb = new StringBuffer();
		String[] del_tables = { DEL_SERVICES, DEL_BUSSSERVICES, DEL_SERVICESINFO, DEL_BINDMAP,
				DEL_DATAADAPTOR, DEL_SERVICESYSTEMMAP, DEL_DEPLOYMENTS };
		for (String del_table : del_tables) {
			StringBuffer del = new StringBuffer();
			for (int i = 0; i < groups; i++) {
				int beginIndex = i * 1600, endIndex = beginIndex + 1600;
				if (endIndex > services.length()) {
					endIndex = services.length();
				}
				String str = services.substring(beginIndex, endIndex);
				System.out.println("str:[" + str + "]");
				// System.out.println("i:[" + i + "]");
				if (str.startsWith(",")) {
					str = "(\n" + str.substring(1) + "\n);\n";
				} else {
					System.out.println("services.txt文件不符合要求，请检查后再试！");
					System.out.println("要求：1.文件中13位服务码在单引号中；2.每个服务开始的单引号前加英文半角逗号。");
					return;
				}
				// System.out.println(str);
				del.append(del_table).append(str);
			}
			sb.append(del);
		}
		// System.out.println(sb.toString());
		FileTools.writeFile("result.txt", sb.toString().getBytes());
	}
}
