package com.yyt.UniAuth.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionDetail {
	public static String getTrace(Throwable e){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		StringBuffer sb = sw.getBuffer();
		return sb.toString();
	}
}
