package com.cqut.baber.action;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cqut.baber.entity.T_Service;
import com.cqut.baber.service.ServiceService;
import com.cqut.genhoo.annotation.Action;
import com.cqut.genhoo.annotation.RequestMapping;
import com.cqut.genhoo.annotation.ResponseBody;
 

@Action
@RequestMapping("/admin/Service")
public class ServiceAction {
	
	private ServiceService service = new ServiceService();
	
	@RequestMapping("/GetServiceInfo_Male")
	@ResponseBody
	public Object GetServiceInfo_Male(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

		String condition = "";
		condition = " where serviceName ='型男'";

		String sql = "select * from t_service";

		List<T_Service> t_service;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			System.out.println("----try");
			t_service = service.query(sql + condition, T_Service.class);
			System.out.println("----query");
			map.put("t_service", t_service);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("/GetServiceInfo_Female")
	@ResponseBody
	public Object GetServiceInfo_Female(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

		String condition = "";
		condition = " where serviceName ='靓女'";

		String sql = "select * from t_service";

		List<T_Service> t_service;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			System.out.println("----try");
			t_service = service.query(sql + condition, T_Service.class);
			System.out.println("----query");
			map.put("t_service", t_service);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
}
