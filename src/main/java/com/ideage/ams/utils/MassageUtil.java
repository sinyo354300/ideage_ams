package com.ideage.ams.utils;

import java.text.MessageFormat;

public class MassageUtil {
	public static String format(String msgTemplate,Object... params) {
		return MessageFormat.format(msgTemplate, params);
	}
}
