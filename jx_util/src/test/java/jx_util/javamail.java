package jx_util;

import cn.ajajaaj.utils.MailUtil;

public class javamail {
	public static void main(String[] args) {
		try {
			MailUtil.sendMail("pokemonfan11@sina.com", "aa", "aa");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
