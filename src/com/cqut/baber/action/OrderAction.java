    package com.cqut.baber.action;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cqut.baber.entity.T_Admin;
import com.cqut.baber.entity.T_Baber;
import com.cqut.baber.entity.T_Evaluate;
import com.cqut.baber.entity.T_Order;
import com.cqut.baber.entity.T_User;
import com.cqut.baber.service.EvaluateService;
import com.cqut.baber.service.OrderService;
import com.cqut.genhoo.RM;
import com.cqut.genhoo.annotation.Action;
import com.cqut.genhoo.annotation.RequestMapping;
import com.cqut.genhoo.annotation.RequestParam;
import com.cqut.genhoo.annotation.ResponseBody;
import com.cqut.genhoo.bean.Page;

@Action
@RequestMapping("/admin/Order")
public class OrderAction {
	private OrderService service = new OrderService();
	private EvaluateService evaluateService = new EvaluateService();

	//user
	@RequestMapping("/GetOrderInfo")
	@ResponseBody
	public Object GetOrderInfo(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

		String condition = "";

		String sql = "select * from t_order";

		List<T_Order> order;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			order = service.query(sql + condition, T_Order.class);
			map.put("order", order);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping("/GetOrderInfo_ByUserId")
	@ResponseBody
	public Object GetOrderInfo_ByUserId(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> test;

		String userId = request.getParameter("userId");

		String condition = "";
		condition = " and t1.userId=" + userId + " order by t2.orderStartTime desc";

		// condition =
		// " and t1.userId="+userId+" and t2.orderStatus='支付成功！'"+" or t2.orderStatus='等待买家付款！'"+" or t2.orderStatus='已取消！'";

		String sqlstring = "select * from t_user as t1,t_order as t2,t_baber as t3 where t1.userId=t2.userId and t3.baberId=t2.baberId";

		try {
			test = service.query(sqlstring + condition);
			System.out.println("---test-----" + test);
			map.put("test", test);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return map;
	}

	@RequestMapping("/order")
	@ResponseBody
	public Object order(T_Order order, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			int i = service.add(order);
			if (i == 1) {
				return RM.createResponseMesage(true, RM.ADD_SUCCESS);
			} else {
				return RM.createResponseMesage(false, RM.ADD_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return RM.createResponseMesage(false, e.getMessage());
		}
	}


	@RequestMapping("/deposit")
	@ResponseBody
	public Object deposit(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		String msg="";
		int odrresult = 0;
		int usrresult = 0;
		//int bbrresult = 0;
		int admresult = 0;
		//List<Map<String, Object>> order;
		//T_Order order;
		List<T_Order> order;

		long orderId = Long.parseLong(request.getParameter("orderId"));
	/*	long userId = Long.parseLong(request.getParameter("userId"));
		long baberId = Long.parseLong(request.getParameter("baberId"));*/
		
		String orderStatus = request.getParameter("orderStatus");

		System.out.println("---orderStatus-----" + orderStatus);

		try {

			String odrcondition = "";
			odrcondition = " where orderId='" + orderId + "'";
			
			String sqlstring1 = "select * from t_order";
			
			order = service.query(sqlstring1 + odrcondition,T_Order.class);
			System.out.println("---order-----" + order);
			
			long userId=order.get(0).getUserId();
			//long baberId=order.getBaberId();
			float orderPrice=order.get(0).getOrderPrice();
			
			String usrsqlstring = "select * from t_user";
			String admsqlstring = "select * from t_admin";
			//String bbrsqlstring = "select * from t_baber";
			String usrcondition = "";
			usrcondition = " where userId='" + userId + "'";
			String admcondition = "";
			admcondition = " where adminId='" + 1 + "'";
			//String bbrcondition = "";
			//bbrcondition = " where baberId='" + baberId + "'";
			
			List<T_User> user;
			user=service.query(usrsqlstring + usrcondition,T_User.class);
			List<T_Admin> admin;
			admin=service.query(admsqlstring + admcondition,T_Admin.class);
			
			//T_Baber baber;
			//baber=(T_Baber) service.query(bbrsqlstring + bbrcondition,T_Baber.class);
			
            Float userAccountAmount=user.get(0).getUserAccountAmount()-orderPrice;
            Float adminAccountAmount=admin.get(0).getAdminAccountAmount()+orderPrice;
            
            //Float baberAccountAmount=baber.getBaberAccountAmount()+orderPrice;
            
            String odrsqlstring = "update t_order set orderStatus='" + orderStatus
					+ "'";
            String usrupdatesqlstring = "update t_user set userAccountAmount='" + userAccountAmount
					+ "'";
            String admupdatesqlstring = "update t_admin set adminAccountAmount='" + adminAccountAmount
					+ "'";
			//String bbrupdatesqlstring = "update t_baber set baberAccountAmount='" + baberAccountAmount
				//	+ "'";
            if(user.get(0).getUserAccountAmount()<orderPrice)
            {
            	msg="用户余额不足！";
            	result=0;
            }
            else
            {
            	odrresult = service.update(odrsqlstring + odrcondition);
    			usrresult = service.update(usrupdatesqlstring + usrcondition);
    			admresult = service.update(admupdatesqlstring + admcondition);
    			//bbrresult = service.update(bbrupdatesqlstring + bbrcondition);
            }
		
			System.out.println("---odrresult-----" + odrresult);
			System.out.println("---usrresult-----" + usrresult);
			System.out.println("---admresult-----" + admresult);
			System.out.println("---msg-----" + msg);
			//System.out.println("---bbrresult-----" + bbrresult);
			
			if(odrresult==1&&usrresult==1&&admresult==1)
			{
				result=1;
			}

			map.put("order", order);
			map.put("result", result);
			map.put("msg", msg);

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return map;
	}
	
	@RequestMapping("/cancelDeposit")
	@ResponseBody
	public Object cancelDeposit(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		String msg="";
		int odrresult = 0;
		int usrresult = 0;
		//int bbrresult = 0;
		int admresult = 0;
		//List<Map<String, Object>> order;
		List<T_Order> order;

		long orderId = Long.parseLong(request.getParameter("orderId"));
	/*	long userId = Long.parseLong(request.getParameter("userId"));
		long baberId = Long.parseLong(request.getParameter("baberId"));*/
		
		String orderStatus = request.getParameter("orderStatus");

		System.out.println("---orderStatus-----" + orderStatus);

		try {

			String odrcondition = "";
			odrcondition = " where orderId='" + orderId + "'";
			
			String sqlstring1 = "select * from t_order";
			
			order =service.query(sqlstring1 + odrcondition,T_Order.class);
			System.out.println("---order-----" + order);
			
			long userId=order.get(0).getUserId();
			//long baberId=order.getBaberId();
			float orderPrice=order.get(0).getOrderPrice();
			
			String usrsqlstring = "select * from t_user";
			String admsqlstring = "select * from t_admin";
			//String bbrsqlstring = "select * from t_baber";
			String usrcondition = "";
			usrcondition = " where userId='" + userId + "'";
			String admcondition = "";
			admcondition = " where adminId='" + 1 + "'";
			//String bbrcondition = "";
			//bbrcondition = " where baberId='" + baberId + "'";
			
			List<T_User> user;
			user=service.query(usrsqlstring + usrcondition,T_User.class);
			List<T_Admin> admin;
			admin=service.query(admsqlstring + admcondition,T_Admin.class);
			
			//T_Baber baber;
			//baber=(T_Baber) service.query(bbrsqlstring + bbrcondition,T_Baber.class);
			
            Float userAccountAmount=user.get(0).getUserAccountAmount()+orderPrice;
            Float adminAccountAmount=admin.get(0).getAdminAccountAmount()-orderPrice;
            
            //Float baberAccountAmount=baber.getBaberAccountAmount()+orderPrice;
            
            String odrsqlstring = "update t_order set orderStatus='" + orderStatus
					+ "'";
            String usrupdatesqlstring = "update t_user set userAccountAmount='" + userAccountAmount
					+ "'";
            String admupdatesqlstring = "update t_admin set adminAccountAmount='" + adminAccountAmount
					+ "'";
			//String bbrupdatesqlstring = "update t_baber set baberAccountAmount='" + baberAccountAmount
				//	+ "'";
            if(admin.get(0).getAdminAccountAmount()<orderPrice)
            {
            	msg="系统余额不足！";
            }
            else
            {
            	odrresult = service.update(odrsqlstring + odrcondition);
    			usrresult = service.update(usrupdatesqlstring + usrcondition);
    			admresult = service.update(admupdatesqlstring + admcondition);
    			//bbrresult = service.update(bbrupdatesqlstring + bbrcondition);
            }
		
			System.out.println("---odrresult-----" + odrresult);
			System.out.println("---usrresult-----" + usrresult);
			System.out.println("---admresult-----" + admresult);
			//System.out.println("---bbrresult-----" + bbrresult);
			
			if(odrresult==1&&usrresult==1&&admresult==1)
			{
				result=1;
			}

			map.put("order", order);
			map.put("result", result);
			map.put("msg", msg);

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return map;
	}
	
	@RequestMapping("/agreePay")
	@ResponseBody
	public Object agreePay(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		String msg="";
		int odrresult = 0;
		//int usrresult = 0;
		int bbrresult = 0;
		int admresult = 0;
		//List<Map<String, Object>> order;
		List<T_Order> order;

		long orderId = Long.parseLong(request.getParameter("orderId"));
	/*	long userId = Long.parseLong(request.getParameter("userId"));
		long baberId = Long.parseLong(request.getParameter("baberId"));*/
		String orderStatus = request.getParameter("orderStatus");

		System.out.println("---orderStatus-----" + orderStatus);

		try {

			String odrcondition = "";
			odrcondition = " where orderId='" + orderId + "'";
			
			String sqlstring1 = "select * from t_order";
			
			order = service.query(sqlstring1 + odrcondition,T_Order.class);
			System.out.println("---order-----" + order);
			
			//long userId=order.getUserId();
			long baberId=order.get(0).getBaberId();
			float orderPrice=order.get(0).getOrderPrice();
			
			//String usrsqlstring = "select * from t_user";
			String admsqlstring = "select * from t_admin";
			String bbrsqlstring = "select * from t_baber";
			//String usrcondition = "";
			//usrcondition = " where userId='" + userId + "'";
			String admcondition = "";
			admcondition = " where adminId='" + 1 + "'";
			String bbrcondition = "";
			bbrcondition = " where baberId='" + baberId + "'";
			
			//T_User user;
			//user=(T_User) service.query(usrsqlstring + usrcondition,T_User.class);
			
			List<T_Admin> admin;
			admin=service.query(admsqlstring + admcondition,T_Admin.class);
			List<T_Baber> baber;
			baber=service.query(bbrsqlstring + bbrcondition,T_Baber.class);
			
           // Float userAccountAmount=user.getUserAccountAmount()-orderPrice;
			
            Float adminAccountAmount=(float) (admin.get(0).getAdminAccountAmount()-orderPrice*0.9);
            Float baberAccountAmount=(float) (baber.get(0).getBaberAccountAmount()+orderPrice*0.9);
            
            String odrsqlstring = "update t_order set orderStatus='" + orderStatus
					+ "'";
            String admupdatesqlstring = "update t_admin set adminAccountAmount='" + adminAccountAmount
            //String usrupdatesqlstring = "update t_user set userAccountAmount='" + userAccountAmount
					+ "'";
			String bbrupdatesqlstring = "update t_baber set baberAccountAmount='" + baberAccountAmount
					+ "'";
            if(admin.get(0).getAdminAccountAmount()<orderPrice*0.9)
            {
            	msg="系统余额不足！";
            }
            else
            {
            	odrresult = service.update(odrsqlstring + odrcondition);
    			//usrresult = service.update(usrupdatesqlstring,usrcondition);
    			bbrresult = service.update(bbrupdatesqlstring + bbrcondition);
    			admresult = service.update(admupdatesqlstring + admcondition);
            }
		
			System.out.println("---odrresult-----" + odrresult);
			//System.out.println("---usrresult-----" + usrresult);
			System.out.println("---admresult-----" + admresult);
			System.out.println("---bbrresult-----" + bbrresult);
			
			if(odrresult==1&&admresult==1&&bbrresult==1)
			{
				result=1;
			}
			
			map.put("result", result);
			map.put("msg", msg);

			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return map;
	}
	
	

	@RequestMapping("/cancelPay")
	@ResponseBody
	public Object cancelPay(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		List<Map<String, Object>> order;

		String orderId = request.getParameter("orderId");
		String orderStatus = request.getParameter("orderStatus");
		System.out.println("---orderStatus-----" + orderStatus);

		String condition = "";
		condition = " where orderId='" + orderId + "'";

		String sqlstring = "update t_order set orderStatus='" + orderStatus
				+ "'";
		String sqlstring1 = "select orderStatus from t_order";

		try {
			order = service.query(sqlstring1 + condition);
			System.out.println("---order-----" + order);

			result = service.update(sqlstring + condition);
			System.out.println("---result-----" + result);

			map.put("order", order);
			map.put("result", result);

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return map;
	}

	@RequestMapping("/removeOrder")
	@ResponseBody
	public Object removeOrder(HttpServletRequest request,
			HttpServletResponse response) {

		String orderId = request.getParameter("orderId");
		// String orderStatus = request.getParameter("orderStatus");

		System.out.println("---orderId-----" + orderId);

		String condition = "";
		condition = " where orderId='" + orderId + "'";

		String sqlstring = "update t_order set orderStatus='用户已移除！'";

		try {
			service.update(sqlstring + condition);
			return RM.createResponseMesage(true, RM.UPDATE_SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			return RM.createResponseMesage(false, e.getMessage());
		}
	}

	@RequestMapping("/ignoreEvaluate")
	@ResponseBody
	public Object ignoreEvaluate(HttpServletRequest request,
			HttpServletResponse response) {

		String orderId = request.getParameter("orderId");
		// String orderStatus = request.getParameter("orderStatus");

		System.out.println("---orderId-----" + orderId);

		String condition = "";
		condition = " where orderId='" + orderId + "'";

		String sqlstring = "update t_order set orderStatus='已完成，未评价！'";

		try {
			service.update(sqlstring + condition);
			return RM.createResponseMesage(true, RM.UPDATE_SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			return RM.createResponseMesage(false, e.getMessage());
		}
	}
	
	//admin
	@RequestMapping("/CheckOrderIndex")
	public Page CheckOrderIndex() {
		return new Page("/WEB-INF/jsp/baberMIS/order/CheckOrderList.jsp");
	}
	
	@RequestMapping("/ajaxSearchCheckOrder")
	@ResponseBody
	public Map<String, Object> ajaxSearchCheckOrder(
			@RequestParam("page") String pageIndexStr,
			@RequestParam("rows") String rowsStr, HttpServletRequest request,
			HttpServletResponse response) throws SQLException,
			UnsupportedEncodingException {
		String orderStatus = request.getParameter("orderStatus");

		System.out.println("----orderStatus------" + orderStatus);

		int pageIndex = Integer.parseInt(pageIndexStr);
		int pageSize = Integer.parseInt(rowsStr);

		Map<String, Object> responseMessage = new HashMap<String, Object>();

		com.cqut.genhoo.Page<Map<String, Object>> page = service
				.queryOnePageCheckOrders(pageIndex, pageSize, orderStatus);

		responseMessage.put("total", page.getTotal());
		responseMessage.put("rows", page.getDataList());

		return responseMessage;
	}
	
	
	@RequestMapping("/publishRow")
	@ResponseBody
	public Object publishRow(HttpServletRequest request,
			HttpServletResponse response) {

		String orderId = request.getParameter("orderId");

		System.out.println("---orderId-----" + orderId);

		String condition = "";
		condition = " where orderId='" + orderId + "'";

		String sqlstring = "update t_order set orderStatus='已托管现金！'";

		try {
			service.update(sqlstring + condition);
			return RM.createResponseMesage(true, RM.UPDATE_SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			return RM.createResponseMesage(false, e.getMessage());
		}
	}
	
	@RequestMapping("/unpublishRow")
	@ResponseBody
	public Object unpublishRow(HttpServletRequest request,
			HttpServletResponse response) {

		String orderId = request.getParameter("orderId");

		System.out.println("---orderId-----" + orderId);

		String condition = "";
		condition = " where orderId='" + orderId + "'";

		String sqlstring = "update t_order set orderStatus='预约未通过审核！'";

		try {
			service.update(sqlstring + condition);
			return RM.createResponseMesage(true, RM.UPDATE_SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			return RM.createResponseMesage(false, e.getMessage());
		}
	}
	
	@RequestMapping("/ApplyDrawbackOrderIndex")
	public Page ApplyDrawbackOrderIndex() {
		return new Page("/WEB-INF/jsp/baberMIS/order/ApplyDrawbackOrderList.jsp");
	}
	
	@RequestMapping("/ajaxSearchApplyDrawbackOrder")
	@ResponseBody
	public Map<String, Object> ajaxSearchApplyDrawbackOrder(
			@RequestParam("page") String pageIndexStr,
			@RequestParam("rows") String rowsStr, HttpServletRequest request,
			HttpServletResponse response) throws SQLException,
			UnsupportedEncodingException {
		String orderStatus = request.getParameter("orderStatus");

		System.out.println("----orderStatus------" + orderStatus);

		int pageIndex = Integer.parseInt(pageIndexStr);
		int pageSize = Integer.parseInt(rowsStr);

		Map<String, Object> responseMessage = new HashMap<String, Object>();

		com.cqut.genhoo.Page<Map<String, Object>> page = service
				.queryOnePageApplyDrawbackOrders(pageIndex, pageSize, orderStatus);

		responseMessage.put("total", page.getTotal());
		responseMessage.put("rows", page.getDataList());

		return responseMessage;
	}

	@RequestMapping("/applyDrawback")
	@ResponseBody
	public Object applyDrawback(@RequestParam("orderId") Long orderId,HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();

		int odrresult = 0;
		int usrresult = 0;
		//int bbrresult = 0;
		int admresult = 0;
		//List<Map<String, Object>> order;
		List<T_Order> order;

		//String orderId = request.getParameter("orderId");
	/*	long userId = Long.parseLong(request.getParameter("userId"));
		long baberId = Long.parseLong(request.getParameter("baberId"));*/
		
		//String orderStatus = request.getParameter("orderStatus");

		//System.out.println("---orderStatus-----" + orderStatus);

		try {

			String odrcondition = "";
			odrcondition = " where orderId='" + orderId + "'";
			
			String sqlstring1 = "select * from t_order";
			
			order = service.query(sqlstring1 + odrcondition,T_Order.class);
			System.out.println("---order-----" + order);
			
			long userId=order.get(0).getUserId();
			//long baberId=order.getBaberId();
			float orderPrice=order.get(0).getOrderPrice();
			
			String usrsqlstring = "select * from t_user";
			String admsqlstring = "select * from t_admin";
			//String bbrsqlstring = "select * from t_baber";
			String usrcondition = "";
			usrcondition = " where userId='" + userId + "'";
			String admcondition = "";
			admcondition = " where adminId='" + 1 + "'";
			//String bbrcondition = "";
			//bbrcondition = " where baberId='" + baberId + "'";
			
			List<T_User> user;
			user=service.query(usrsqlstring + usrcondition,T_User.class);
			List<T_Admin> admin;
			admin=service.query(admsqlstring + admcondition,T_Admin.class);
			
			//T_Baber baber;
			//baber=(T_Baber) service.query(bbrsqlstring + bbrcondition,T_Baber.class);
			
            Float userAccountAmount=(float) (user.get(0).getUserAccountAmount()+orderPrice*0.5);
            Float adminAccountAmount=(float) (admin.get(0).getAdminAccountAmount()-orderPrice*0.5);
            
            //Float baberAccountAmount=baber.getBaberAccountAmount()+orderPrice;
            
            String odrsqlstring = "update t_order set orderStatus='退款成功！退款金额为订单的90% 。'";
            String usrupdatesqlstring = "update t_user set userAccountAmount='" + userAccountAmount
					+ "'";
            String admupdatesqlstring = "update t_admin set adminAccountAmount='" + adminAccountAmount
					+ "'";
			//String bbrupdatesqlstring = "update t_baber set baberAccountAmount='" + baberAccountAmount
				//	+ "'";
            if(admin.get(0).getAdminAccountAmount()<orderPrice*0.9)
            {
            	System.out.println("---getAdminAccountAmount-----" + admin.get(0).getAdminAccountAmount());
            	return RM.createResponseMesage(true, RM.UPDATE_ERROR);
            }
            else
            {
            	odrresult = service.update(odrsqlstring + odrcondition);
    			usrresult = service.update(usrupdatesqlstring + usrcondition);
    			admresult = service.update(admupdatesqlstring + admcondition);
    			//bbrresult = service.update(bbrupdatesqlstring + bbrcondition);
            }
		
			System.out.println("---odrresult-----" + odrresult);
			System.out.println("---usrresult-----" + usrresult);
			System.out.println("---admresult-----" + admresult);
			//System.out.println("---bbrresult-----" + bbrresult);
			
			if(odrresult==1&&usrresult==1&&admresult==1)
			{
				return RM.createResponseMesage(true, RM.UPDATE_SUCCESS);
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return RM.createResponseMesage(false, e1.getMessage());
		}

		return map;
	}
	
	//baber
	@RequestMapping("/PreOrderIndex")
	public Page PreOrderIndex() {
		return new Page("/WEB-INF/jsp/baberMIS/order/PreOrderList.jsp");
	}

	@RequestMapping("/OrderIndex")
	public Page OrderIndex() {
		return new Page("/WEB-INF/jsp/baberMIS/order/OrderList.jsp");
	}
	
	@RequestMapping("/EvalateOrderIndex")
	public Page EvalateOrderIndex() {
		return new Page("/WEB-INF/jsp/baberMIS/order/EvaluateOrderList.jsp");
	}

	@RequestMapping("/FinishOrderIndex")
	public Page FinishOrderIndex() {
		return new Page("/WEB-INF/jsp/baberMIS/order/FinishOrderList.jsp");
	}

	@RequestMapping("/ajaxSearchPreOrder")
	@ResponseBody
	public Map<String, Object> SearchPreOrderlist(
			@RequestParam("page") String pageIndexStr,
			@RequestParam("rows") String rowsStr, HttpServletRequest request,
			HttpServletResponse response) throws SQLException,
			UnsupportedEncodingException {
		
		HttpSession session = request.getSession();
		long loginBaberId=(Long) session.getAttribute("loginBaberId");
		
		System.out.println("----loginBaberId------" + loginBaberId);

		int pageIndex = Integer.parseInt(pageIndexStr);
		int pageSize = Integer.parseInt(rowsStr);

		Map<String, Object> responseMessage = new HashMap<String, Object>();

		com.cqut.genhoo.Page<Map<String, Object>> page = service
				.queryOnePagePreOrders(pageIndex, pageSize,loginBaberId);

		responseMessage.put("total", page.getTotal());
		responseMessage.put("rows", page.getDataList());

		return responseMessage;
	}
	
	@RequestMapping("/checkRow")
	@ResponseBody
	public Object checkRow(HttpServletRequest request,
			HttpServletResponse response) {

		String orderId = request.getParameter("orderId");

		System.out.println("---orderId-----" + orderId);

		String condition = "";
		condition = " where orderId='" + orderId + "'";

		String sqlstring = "update t_order set orderStatus='理发师已接单，请准时来店理发！'";

		try {
			service.update(sqlstring + condition);
			return RM.createResponseMesage(true, RM.UPDATE_SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			return RM.createResponseMesage(false, e.getMessage());
		}
	}
	
	
	@RequestMapping("/uncheckRow")
	@ResponseBody
	public Object uncheckRow(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		String msg="";
		int odrresult = 0;
		int usrresult = 0;
		//int bbrresult = 0;
		int admresult = 0;
		//List<Map<String, Object>> order;
		List<T_Order> order;

		long orderId = Long.parseLong(request.getParameter("orderId"));
	/*	long userId = Long.parseLong(request.getParameter("userId"));
		long baberId = Long.parseLong(request.getParameter("baberId"));*/
		
		/*String orderStatus = request.getParameter("orderStatus");

		System.out.println("---orderStatus-----" + orderStatus);*/

		try {

			String odrcondition = "";
			odrcondition = " where orderId='" + orderId + "'";
			
			String sqlstring1 = "select * from t_order";
			
			order = service.query(sqlstring1 + odrcondition,T_Order.class);
			System.out.println("---order-----" + order);
			
			long userId=order.get(0).getUserId();
			//long baberId=order.getBaberId();
			float orderPrice=order.get(0).getOrderPrice();
			
			String usrsqlstring = "select * from t_user";
			String admsqlstring = "select * from t_admin";
			//String bbrsqlstring = "select * from t_baber";
			String usrcondition = "";
			usrcondition = " where userId='" + userId + "'";
			String admcondition = "";
			admcondition = " where adminId='" + 1 + "'";
			//String bbrcondition = "";
			//bbrcondition = " where baberId='" + baberId + "'";
			
			List<T_User> user;
			user=service.query(usrsqlstring + usrcondition,T_User.class);
			List<T_Admin> admin;
			admin=service.query(admsqlstring + admcondition,T_Admin.class);
			
			//T_Baber baber;
			//baber=(T_Baber) service.query(bbrsqlstring + bbrcondition,T_Baber.class);
			
            Float userAccountAmount=(float) (user.get(0).getUserAccountAmount()+orderPrice);
            Float adminAccountAmount=(float) (admin.get(0).getAdminAccountAmount()-orderPrice);
            
            //Float baberAccountAmount=baber.getBaberAccountAmount()+orderPrice;
            
            String odrsqlstring = "update t_order set orderStatus='理发师很忙，请预约其他时段！系统已全额退款。'";
            String usrupdatesqlstring = "update t_user set userAccountAmount='" + userAccountAmount
					+ "'";
            String admupdatesqlstring = "update t_admin set adminAccountAmount='" + adminAccountAmount
					+ "'";
			//String bbrupdatesqlstring = "update t_baber set baberAccountAmount='" + baberAccountAmount
				//	+ "'";
            if(user.get(0).getUserAccountAmount()<orderPrice*0.9)
            {
            	msg="用户余额不足！";
            }
            else
            {
            	odrresult = service.update(odrsqlstring + odrcondition);
    			usrresult = service.update(usrupdatesqlstring + usrcondition);
    			admresult = service.update(admupdatesqlstring + admcondition);
    			//bbrresult = service.update(bbrupdatesqlstring + bbrcondition);
            }
		
			System.out.println("---odrresult-----" + odrresult);
			System.out.println("---usrresult-----" + usrresult);
			System.out.println("---admresult-----" + admresult);
			//System.out.println("---bbrresult-----" + bbrresult);
			
			if(odrresult==1&&usrresult==1&&admresult==1)
			{
				result=1;
				return RM.createResponseMesage(true, RM.UPDATE_SUCCESS);
			}

			map.put("order", order);
			map.put("result", result);
			map.put("msg", msg);

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return RM.createResponseMesage(false, e1.getMessage());
		}

		return map;
	}


	/*@RequestMapping("/uncheckRow")
	@ResponseBody
	public Object uncheckRow(HttpServletRequest request,
			HttpServletResponse response) {

		String orderId = request.getParameter("orderId");

		System.out.println("---orderId-----" + orderId);

		String condition = "";
		condition = " where orderId='" + orderId + "'";

		String sqlstring = "update t_order set orderStatus='理发师该时段很忙，请预约其他时段！'";

		try {
			service.update(sqlstring + condition);
			return RM.createResponseMesage(true, RM.UPDATE_SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			return RM.createResponseMesage(false, e.getMessage());
		}
	}*/

	@RequestMapping("/ajaxSearchOrder")
	@ResponseBody
	public Map<String, Object> SearchOrderlist(
			@RequestParam("page") String pageIndexStr,
			@RequestParam("rows") String rowsStr, HttpServletRequest request,
			HttpServletResponse response) throws SQLException,
			UnsupportedEncodingException {
		
		HttpSession session = request.getSession();
		long loginBaberId=(Long) session.getAttribute("loginBaberId");
		
		System.out.println("----loginBaberId------" + loginBaberId);

		int pageIndex = Integer.parseInt(pageIndexStr);
		int pageSize = Integer.parseInt(rowsStr);

		Map<String, Object> responseMessage = new HashMap<String, Object>();

		com.cqut.genhoo.Page<Map<String, Object>> page = service
				.queryOnePageOrders(pageIndex, pageSize,loginBaberId);

		responseMessage.put("total", page.getTotal());
		responseMessage.put("rows", page.getDataList());

		return responseMessage;
	}

	@RequestMapping("/ajaxSearchEvaluateOrder")
	@ResponseBody
	public Map<String, Object> ajaxSearchEvaluateOrder(
			@RequestParam("page") String pageIndexStr,
			@RequestParam("rows") String rowsStr, HttpServletRequest request,
			HttpServletResponse response) throws SQLException,
			UnsupportedEncodingException {
		HttpSession session = request.getSession();
		long loginBaberId=(Long) session.getAttribute("loginBaberId");
		
		int pageIndex = Integer.parseInt(pageIndexStr);
		int pageSize = Integer.parseInt(rowsStr);

		Map<String, Object> responseMessage = new HashMap<String, Object>();

		com.cqut.genhoo.Page<Map<String, Object>> page = service
				.queryOnePageEvalateOrders(pageIndex, pageSize,loginBaberId);

		responseMessage.put("total", page.getTotal());
		responseMessage.put("rows", page.getDataList());

		return responseMessage;
	}

	@RequestMapping("/ajaxSearchFinishOrder")
	@ResponseBody
	public Map<String, Object> ajaxSearchFinishOrder(
			@RequestParam("page") String pageIndexStr,
			@RequestParam("rows") String rowsStr, HttpServletRequest request,
			HttpServletResponse response) throws SQLException,
			UnsupportedEncodingException {
		HttpSession session = request.getSession();
		long loginBaberId=(Long) session.getAttribute("loginBaberId");

		int pageIndex = Integer.parseInt(pageIndexStr);
		int pageSize = Integer.parseInt(rowsStr);

		Map<String, Object> responseMessage = new HashMap<String, Object>();

		com.cqut.genhoo.Page<Map<String, Object>> page = service
				.queryOnePageFinishOrders(pageIndex, pageSize,loginBaberId);

		responseMessage.put("total", page.getTotal());
		responseMessage.put("rows", page.getDataList());

		return responseMessage;
	}

	

	@RequestMapping("/applyPayRow")
	@ResponseBody
	public Object applyPayRow(HttpServletRequest request,
			HttpServletResponse response) {

		String orderId = request.getParameter("orderId");

		System.out.println("---orderId-----" + orderId);

		String condition = "";
		condition = " where orderId='" + orderId + "'";

		String sqlstring = "update t_order set orderStatus='理发师申请支付！'";

		try {
			service.update(sqlstring + condition);
			return RM.createResponseMesage(true, RM.UPDATE_SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			return RM.createResponseMesage(false, e.getMessage());
		}
	}

	@RequestMapping("/applyEvaluateRow")
	@ResponseBody
	public Object applyEvaluateRow(HttpServletRequest request,
			HttpServletResponse response) {

		String orderId = request.getParameter("orderId");

		System.out.println("---orderId-----" + orderId);

		String condition = "";
		condition = " where orderId='" + orderId + "'";

		String sqlstring = "update t_order set orderStatus='理发师申请评价！'";

		try {
			service.update(sqlstring + condition);
			return RM.createResponseMesage(true, RM.UPDATE_SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			return RM.createResponseMesage(false, e.getMessage());
		}
	}

	@RequestMapping("/finishRow")
	@ResponseBody
	public Object finishRow(HttpServletRequest request,
			HttpServletResponse response) {

		String orderId = request.getParameter("orderId");

		System.out.println("---orderId-----" + orderId);

		String condition = "";
		condition = " where orderId='" + orderId + "'";

		String sqlstring = "update t_order set orderStatus='已完成，未评价！'";

		try {
			service.update(sqlstring + condition);
			return RM.createResponseMesage(true, RM.UPDATE_SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			return RM.createResponseMesage(false, e.getMessage());
		}
	}

	@RequestMapping("/detailRow")
	@ResponseBody
	public Object detailRow(HttpServletRequest request,
			HttpServletResponse response) {

		String orderId = request.getParameter("orderId");

		System.out.println("---orderId-----" + orderId);

		String condition = "";
		condition = " where orderId='" + orderId + "'";

		String sqlstring = "update t_order set orderStatus='订单完成！'";

		try {
			service.update(sqlstring + condition);
			return RM.createResponseMesage(true, RM.UPDATE_SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			return RM.createResponseMesage(false, e.getMessage());
		}
	}

	@RequestMapping("/initAdd")
	public Page initAdd1() {
		return new Page("/WEB-INF/jsp/baberMIS/order/OrderAdd.jsp");
	}

	@RequestMapping("/add")
	public Page add(T_Order order) {
		// System.out.println("解析的结果:"+JSON.toJSONString(Order));
		// service.add(Order);
		return new Page("/Order/index");
	}

	@RequestMapping("/ajaxAdd")
	@ResponseBody
	public Object ajaxAdd(T_Order order) {
		try {
			int i = service.add(order);
			if (i == 1) {
				return RM.createResponseMesage(true, RM.ADD_SUCCESS);
			} else {
				return RM.createResponseMesage(false, RM.ADD_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return RM.createResponseMesage(false, e.getMessage());
		}
	}

	@RequestMapping("/delete")
	public void delete(@RequestParam("orderId") Long orderId)
			throws SQLException {

		// 删除订单
		service.deleteByID(T_Order.class, orderId);
		// 删除订单对应的码表

		List<T_Evaluate> evaluate;
		// Map<String, Object> map = new HashMap<String, Object>();
		// List<Map<String,Object>> test;
		String condition = "";
		condition = " and t2.orderId=" + orderId + "";

		String sqlstring = "select evaluateId from t_evaluate as t1,t_order as t2 where t1.orderId=t2.orderId";

		evaluate = evaluateService.query(sqlstring + condition,
				T_Evaluate.class);
		Iterator<T_Evaluate> evaluateIterator = evaluate.iterator();
		if (evaluate.size() > 0)
			while (evaluateIterator.hasNext()) {
				T_Evaluate evaluate1 = evaluateIterator.next();
				long evaluateId = evaluate1.getEvaluateId();

				// 删除订单
				evaluateService.deleteByID(T_Evaluate.class, evaluateId);
				// 删除订单对应的码表
			}
	}
	
	@RequestMapping("/deleteOrder")
	public void deleteOrder(@RequestParam("orderId") Long orderId) throws SQLException {

		// 删除学生
		service.deleteByID(T_Order.class, orderId);
		// 删除学生对应的码表

	}
	
	@RequestMapping("/deleteAll")
	public void deleteAll(@RequestParam("Ids") String s) {
		System.out.println("进入deletAll方法");
		String Ids[] = s.split(",");
		System.out.println(Ids);
		try {
			for (int i = 0; i < Ids.length; i++) {
				System.out.println(Ids[i]);
				service.deleteByID(T_Order.class, Ids[i]);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
