package com.cqut.genhoo;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

@SuppressWarnings("rawtypes")
public class ScriptLoader {
	
	private static String scriptPath = "script.xml";
	private static Map<String,Plagin> scripts = new HashMap<String,Plagin>();
	
	//需要加载的脚本文件
	private String[] needScripts;
	
	public ScriptLoader(String[] palgin){
		this.needScripts = palgin;
	}
	
	public String cssFile(){
		StringBuffer cssBuffer = new StringBuffer();
		for(String str:needScripts){
			cssBuffer.append(scripts.get(str).css);
		}
		return cssBuffer.toString();
	}
	
	public String scriptFile(){
		StringBuffer scriptBuffer = new StringBuffer();
		for(String str:needScripts){
			scriptBuffer.append(scripts.get(str).script);
		}
		return scriptBuffer.toString();
	}
	
	public String cssFile(String id){
		return scripts.get(id).css;
	}
	
	public String scriptFile(String id){
		return scripts.get(id).script;
	}
	static {
		System.out.println("init");
		SAXReader reader = new SAXReader();
		URL url = ScriptLoader.class.getClassLoader().getResource(scriptPath);
		try {
			Document document = reader.read(url);
			Element root = document.getRootElement();
			
			for ( Iterator i = root.elementIterator(); i.hasNext(); ) {
	            Element element = (Element) i.next();
	            String elementName = element.getName();
	            if(elementName.equals("plagin")){
	            	String id = element.attributeValue("id"); 
	            	Plagin plagin = new Plagin();
	            	StringBuffer cssBuffer = new StringBuffer();
	            	StringBuffer scriptBuffer = new StringBuffer();
	            	for ( Iterator ii = element.elementIterator(); ii.hasNext(); ){
	            		Element ele = (Element) ii.next();
	            		if(ele.getName().equals("styleList")){
	            			for ( Iterator iii = ele.elementIterator(); iii.hasNext(); ){
	            				Element elee = (Element) iii.next();
	            				//System.out.println(elee.asXML());
	            				cssBuffer.append(elee.asXML()+"\n");
	            			}
	            		}else if(ele.getName().equals("scriptList")){
		            		for ( Iterator iii = ele.elementIterator(); iii.hasNext(); ){
	            				Element elee = (Element) iii.next();
	            				scriptBuffer.append(elee.asXML()+"\n");
	            			}
		            	}
	            	}
	            	plagin.css = cssBuffer.toString();
	            	plagin.script = scriptBuffer.toString();
	            	scripts.put(id, plagin);
	            }
	        }
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		ScriptLoader loader1 = new ScriptLoader(new String[]{"base","easyui","artDialog"});
		System.out.println(loader1.cssFile());
		System.out.println(loader1.scriptFile());
	}
	
}
class Plagin{
	String css;
	String script;
}
