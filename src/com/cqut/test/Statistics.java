package com.cqut.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Statistics {
	
	private Pattern pattern;
	
	//文件的个数
	int fileCount;
	
	/** 
	 * @Title: main 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param args 
	 * @return void 
	 */
	public static void main(String[] args) {
		
		//String matcher = ".+\\.((java))$";
		String matcher = ".+\\.((java)|(jsp))$";
		//String matcher = ".*";
		
		Statistics s = new Statistics(matcher);
		
		File f = new File("E:/work/workspace/recruitSimple");
		int line = s.getLine(f);
		System.out.println(line);
		
		System.out.println("匹配的文件个数："+s.fileCount);
	}
	
	
	public Statistics(String matcher){
		pattern = Pattern.compile(matcher);
	}
	
	public int getLine(File file){
		
		if(file.isDirectory()){
			File[] s = file.listFiles();
			int count = 0;
			for(File f:s){
				count += getLine(f); 
			}
			return count;
		}else{
			
			String fileName = file.getName();
			
			if(pattern.matcher(fileName).matches()){
				fileCount ++;
				return getFileLine(file);
			}else{
				return 0;
			}
			
			
		}
		
	}
	
	
	private int getFileLine(File file){
		int count = 0;
		try {
			Scanner sc = new Scanner(new FileInputStream(file));
			while(sc.hasNext()){
				sc.nextLine();
				count++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(file.getName()+":"+count);
		return count;
	}
	
	

}
