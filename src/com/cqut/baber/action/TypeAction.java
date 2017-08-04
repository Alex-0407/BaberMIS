package com.cqut.baber.action;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cqut.baber.entity.T_Type;
import com.cqut.baber.service.TypeService;
import com.cqut.genhoo.RM;
import com.cqut.genhoo.annotation.Action;
import com.cqut.genhoo.annotation.RequestMapping;
import com.cqut.genhoo.annotation.RequestParam;
import com.cqut.genhoo.annotation.ResponseBody;
import com.cqut.genhoo.bean.ErrorPage;
import com.cqut.genhoo.bean.Page;
 

@Action
@RequestMapping("/admin/Type")
public class TypeAction {
	
	private TypeService service = new TypeService();
	
	@RequestMapping("/index")
	public Page index(){
		return new Page("/WEB-INF/jsp/baberMIS/type/TypeList.jsp");
	}
	
	@RequestMapping("/GetTypeInfo")
	@ResponseBody
	public Object GetTypeInfo(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

		String condition = "";

		String sql = "select * from t_type";

		List<T_Type> type;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			System.out.println("----try");
			type = service.query(sql + condition, T_Type.class);
			System.out.println("----query");
			map.put("type", type);
			System.out.println("----type"+type);
			System.out.println("----type"+type.get(0).getTypeName());
			System.out.println("----type"+type.get(1).getTypeName());
			System.out.println("----type"+type.get(2).getTypeName());
			System.out.println("----type"+type.get(3).getTypeName());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("/ajaxSearch")
	@ResponseBody
	public Map<String,Object>  Searchlist(@RequestParam("page") String pageIndexStr,@RequestParam("rows") String rowsStr,HttpServletRequest request,HttpServletResponse response) throws SQLException, UnsupportedEncodingException{
		String realName = request.getParameter("name");
		int pageIndex = Integer.parseInt(pageIndexStr);
		int pageSize = Integer.parseInt(rowsStr);
		
		Map<String,Object> responseMessage = new HashMap<String,Object>();
		
		com.cqut.genhoo.Page<Map<String,Object>> page = service.queryOnePageTypes(pageIndex, pageSize,realName);
		
		responseMessage.put("total", page.getTotal());
		responseMessage.put("rows", page.getDataList());
		 
		return responseMessage;
	}
	
	
	@RequestMapping("/initAdd")
	public Page initAdd1(){
		return new Page("/WEB-INF/jsp/baberMIS/type/TypeAdd.jsp");
	}
	
	@RequestMapping("/add")
	public Page add(T_Type type){
		//System.out.println("解析的结果:"+JSON.toJSONString(type));
		//service.add(type);
		return new Page("/Type/index");
	}
	
	@RequestMapping("/ajaxAdd")
	@ResponseBody
	public Object ajaxAdd(T_Type type){
		try {
			int i = service.add(type);
			if(i==1){
				return RM.createResponseMesage(true, RM.ADD_SUCCESS);
			}else {
				return RM.createResponseMesage(false, RM.ADD_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return RM.createResponseMesage(false, e.getMessage());
		}
	}	
	
	@RequestMapping("/delete")
	public void delete(@RequestParam("typeId") Long typeId) throws SQLException{
	 
			//删除学生
			service.deleteByID(T_Type.class, typeId);
			//删除学生对应的码表
 
	}
	
	@RequestMapping("/deleteAll")
	public void deleteAll(@RequestParam("Ids") String s) {
		System.out.println("进入deletAll方法");
		String Ids[]=s.split(",");
		System.out.println(Ids);
		try {
			for (int i = 0; i < Ids.length; i++) {
				System.out.println(Ids[i]);
				service.deleteByID(T_Type.class,Ids[i]);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/initEdit")
	public Page initEdit(@RequestParam("typeId") Long typeId){
		try {
			T_Type t_type = service.getEntityByID(T_Type.class, typeId);
			return new Page("/WEB-INF/jsp/baberMIS/type/TypeEdit.jsp").data("t_type", t_type);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ErrorPage(e);
		}
	}
			
	@RequestMapping("/ajaxEdit")
	@ResponseBody
	public Object ajaxEdit(T_Type type){
		try {
			service.modify(type, new String[]{"typeName","typeStatus"});
			return RM.createResponseMesage(true, RM.UPDATE_SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			return RM.createResponseMesage(false, e.getMessage());
		}
	}
}
