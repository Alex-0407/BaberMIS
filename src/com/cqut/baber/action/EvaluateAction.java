package com.cqut.baber.action;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cqut.baber.entity.T_Evaluate;
import com.cqut.baber.service.EvaluateService;
import com.cqut.genhoo.RM;
import com.cqut.genhoo.annotation.Action;
import com.cqut.genhoo.annotation.RequestMapping;
import com.cqut.genhoo.annotation.ResponseBody;

@Action
@RequestMapping("/admin/Evaluate")
public class EvaluateAction {
private EvaluateService service = new EvaluateService();
	
	
	@RequestMapping("/GetEvaluateInfo")
	@ResponseBody
	public Object GetEvaluateInfo(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

		String condition = "";

		String sql = "select * from t_evaluate order by t2.evaluateTime";

		List<T_Evaluate> evaluate;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			System.out.println("----try");
			evaluate = service.query(sql + condition, T_Evaluate.class);
			System.out.println("----query");
			map.put("evaluate", evaluate);
			System.out.println("----evaluate"+evaluate);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	
	@RequestMapping("/evaluate")
	@ResponseBody
	public Object evaluate(T_Evaluate evaluate,HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		long orderId=Long.parseLong(request.getParameter("orderId"));
		String orderStatus =request.getParameter("orderStatus");
		
		String condition = "";
		condition = " where orderId='"+orderId+"'";
		
		String sqlstring="update t_order set orderStatus='"+orderStatus+"'";
		try {
			if(!service.isExist(T_Evaluate.class,"orderId",orderId))
			{
				try {
					int i = service.add(evaluate);
					int j=service.update(sqlstring+condition);
					if(i==1&&j==1){
						return RM.createResponseMesage(true, RM.ADD_SUCCESS);
					}else {
						return RM.createResponseMesage(false, RM.ADD_ERROR);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					return RM.createResponseMesage(false, e.getMessage());
				}
			}
			else
			{
				map.put("result","已存在！");
				return map;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return RM.createResponseMesage(false, e.getMessage());
		}
	}	

	@RequestMapping("/ajaxAdd")
	@ResponseBody
	public Object ajaxAdd(T_Evaluate evaluate){
		try {
			int i = service.add(evaluate);
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
}
