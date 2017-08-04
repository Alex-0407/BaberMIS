package com.cqut.baber.action;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cqut.baber.entity.T_Admin;
import com.cqut.baber.service.AdminService;
import com.cqut.genhoo.RM;
import com.cqut.genhoo.annotation.Action;
import com.cqut.genhoo.annotation.RequestMapping;
import com.cqut.genhoo.annotation.RequestParam;
import com.cqut.genhoo.annotation.ResponseBody;
import com.cqut.genhoo.bean.ErrorPage;
import com.cqut.genhoo.bean.Page;
 

@Action
@RequestMapping("/admin/Admin")
public class AdminAction {
	private AdminService service = new AdminService();
	
	@RequestMapping("/index")
	public Page index(){
		return new Page("/WEB-INF/jsp/baberMIS/admin/AdminList.jsp");
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public Object login(HttpServletRequest request,HttpServletResponse response) {
		// TODO Auto-generated method stub
        String msg = "0";		
		Boolean success = false;
		
		String tel=request.getParameter("tel");
		String pwd=request.getParameter("pwd");
		System.out.println("tel:"+tel+"  pwd"+pwd);
		
		String condition="";
		condition = " where adminTel = '"+tel+"' and adminPwd='"+pwd+"'";
		System.out.println("------tel---"+tel);
		
		String sql="select * from t_admin";
		
		List<T_Admin> admin;
		
		try {
			System.out.println("------try---");
			admin = service.query(sql+condition,T_Admin.class);
			System.out.println("------sql+condition---"+sql+condition);
			if(admin.size()>0){
				success=true;
				msg="登录成功！";
				System.out.println("------loginResult---"+success);
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
		map.put("msg", msg);
		
		return map;

	}
	
	@RequestMapping("/register")
	@ResponseBody
	public Object register(HttpServletRequest request,HttpServletResponse response,T_Admin admin) {
		// TODO Auto-generated method stub
		
		String name=request.getParameter("name");
		String tel=request.getParameter("tel");
		String pwd=request.getParameter("pwd");
		System.out.println("name:"+name+"tel:"+tel+"  pwd"+pwd);
		
		admin.setAdminName(name);
		admin.setAdminTel(tel);
		admin.setAdminPwd(pwd);
		
		try {
			int i = service.add(admin);
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
	
	@RequestMapping("/GetAdminInfo")
	@ResponseBody
	public Object GetAdminInfo(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

		String condition = "";

		String sql = "select * from admin";

		List<T_Admin> admin;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			admin = service.query(sql + condition, T_Admin.class);
			map.put("admin", admin);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	
	@RequestMapping("/ajaxSearch")
	@ResponseBody
	public Map<String,Object>  Searchlist(@RequestParam("page") String pageIndexStr,@RequestParam("rows") String rowsStr,HttpServletRequest request,HttpServletResponse response) throws SQLException, UnsupportedEncodingException{
		String realName = request.getParameter("realName");
		int pageIndex = Integer.parseInt(pageIndexStr);
		int pageSize = Integer.parseInt(rowsStr);
		
		Map<String,Object> responseMessage = new HashMap<String,Object>();
		
		com.cqut.genhoo.Page<Map<String,Object>> page = service.queryOnePageAdmins(pageIndex, pageSize,realName);
		
		responseMessage.put("total", page.getTotal());
		responseMessage.put("rows", page.getDataList());
		 
		return responseMessage;
	}
	
	
	@RequestMapping("/initAdd")
	public Page initAdd1(){
		return new Page("/WEB-INF/jsp/baberMIS/admin/AdminAdd.jsp");
	}
	
	@RequestMapping("/add")
	public Page add(T_Admin admin){
		return new Page("/Admin/index");
	}
	
	@RequestMapping("/ajaxAdd")
	@ResponseBody
	public Object ajaxAdd(T_Admin admin){
		try {
			int i = service.add(admin);
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
	public void delete(@RequestParam("adminId") Long adminId) throws SQLException{
	 
			//删除学生
			service.deleteByID(T_Admin.class, adminId);
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
				service.deleteByID(T_Admin.class,Ids[i]);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("/initEdit")
	public Page initEdit(@RequestParam("adminId") Long adminId){
		System.out.println("into edit");
		try {
			T_Admin admin = service.getEntityByID(T_Admin.class, adminId);
			System.out.println("ID="+admin.getAdminId());
			return new Page("/WEB-INF/jsp/baberMIS/admin/AdminEdit.jsp").data("admin",admin);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ErrorPage(e);
		}
	}
			
	@RequestMapping("/ajaxEdit")
	@ResponseBody
	public Object ajaxEdit(T_Admin admin){
		try {
			service.modify(admin, new String[]{"adminName","adminTel","adminAddress"});
			return RM.createResponseMesage(true, RM.UPDATE_SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			return RM.createResponseMesage(false, e.getMessage());
		}
	}
}
