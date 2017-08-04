package com.cqut.baber.action;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cqut.baber.entity.T_Business;
import com.cqut.baber.service.BusinessService;
import com.cqut.genhoo.RM;
import com.cqut.genhoo.annotation.Action;
import com.cqut.genhoo.annotation.RequestMapping;
import com.cqut.genhoo.annotation.RequestParam;
import com.cqut.genhoo.annotation.ResponseBody;
import com.cqut.genhoo.bean.ErrorPage;
import com.cqut.genhoo.bean.Page;
 

@Action
@RequestMapping("/admin/Business")
public class BusinessAction {
	private BusinessService service = new BusinessService();
	
	@RequestMapping("/GetBusinessInfo")
	@ResponseBody
	public Object GetBusinessInfo(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

		String condition = "";

		String sql = "select * from t_business";

		List<T_Business> business;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			business = service.query(sql + condition, T_Business.class);
			map.put("business", business);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("/index")
	public Page index(){
		return new Page("/WEB-INF/jsp/baberMIS/business/BusinessList.jsp");
	}
	
	
	@RequestMapping("/ajaxSearch")
	@ResponseBody
	public Map<String,Object>  Searchlist(@RequestParam("page") String pageIndexStr,@RequestParam("rows") String rowsStr,HttpServletRequest request,HttpServletResponse response) throws SQLException, UnsupportedEncodingException{
		String realName = request.getParameter("Name");
		int pageIndex = Integer.parseInt(pageIndexStr);
		int pageSize = Integer.parseInt(rowsStr);
		
		Map<String,Object> responseMessage = new HashMap<String,Object>();
		
		com.cqut.genhoo.Page<Map<String,Object>> page = service.queryOnePageBusiness(pageIndex, pageSize,realName);
		
		responseMessage.put("total", page.getTotal());
		responseMessage.put("rows", page.getDataList());
		 
		return responseMessage;
	}
	
	
	@RequestMapping("/initAdd")
	public Page initAdd1(){
		return new Page("/WEB-INF/jsp/baberMIS/business/BusinessAdd.jsp");
	}
	
	@RequestMapping("/add")
	public Page add(T_Business business){
		//System.out.println("解析的结果:"+JSON.toJSONString(Business));
		//service.add(Business);
		return new Page("/Business/index");
	}
	
	@RequestMapping("/ajaxAdd")
	@ResponseBody
	public Object ajaxAdd(T_Business business){
		try {
			int i = service.add(business);
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
	public void delete(@RequestParam("businessId") Long businessId) throws SQLException{
	 
			//删除学生
			service.deleteByID(T_Business.class, businessId);
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
				service.deleteByID(T_Business.class,Ids[i]);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("/initEdit")
	public Page initEdit(@RequestParam("businessId") Long businessId){
		System.out.println("into edit");
		try {
			T_Business business = service.getEntityByID(T_Business.class, businessId);
			System.out.println("ID="+business.getBusinessId());
			return new Page("/WEB-INF/jsp/baberMIS/business/BusinessEdit.jsp").data("business",business);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ErrorPage(e);
		}
	}
			
	@RequestMapping("/ajaxEdit")
	@ResponseBody
	public Object ajaxEdit(T_Business business){
		try {
			service.modify(business, new String[]{"baberId","serviceType","servicePrice","serviceLevel","serviceLevel","serviceStatus"});
			return RM.createResponseMesage(true, RM.UPDATE_SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			return RM.createResponseMesage(false, e.getMessage());
		}
	}
}
