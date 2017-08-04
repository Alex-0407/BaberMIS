package com.cqut.genhoo;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

public class SysConstant {
	
	//系统默认分页大小
	public static final int DEFAULT_LIMT_SIZE = 10;
	
	//用户存储用户上传的头像的文件的路径
	public static String HEAD_PATH = "D:\\recruitPortalFile\\head";
	//缓存目录
	public static String UPLOAD_TEMP = "D:\\recruitPortalFile\\temp";  
	
	static {
		File f = new File(HEAD_PATH);
		if(!f.exists()){
			f.mkdirs();
		}
		
		File ft = new File(UPLOAD_TEMP);
		if(!ft.exists()){
			ft.mkdirs();
		}
	}
	
	private static final String serverName = "recruitPortalGenhoo";
	
	//找到对于客服机来说Servet的绝对路径
	public static String serverBasePath(HttpServletRequest request){
		return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+serverName;
	}
	
}
