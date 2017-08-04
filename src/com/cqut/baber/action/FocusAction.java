package com.cqut.baber.action;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cqut.baber.entity.T_Focus;
import com.cqut.baber.service.FocusService;
import com.cqut.genhoo.RM;
import com.cqut.genhoo.annotation.Action;
import com.cqut.genhoo.annotation.RequestMapping;
import com.cqut.genhoo.annotation.RequestParam;
import com.cqut.genhoo.annotation.ResponseBody;

@Action
@RequestMapping("/admin/Focus")
public class FocusAction {
	private FocusService service = new FocusService();

	@RequestMapping("/getFocusInfo")
	@ResponseBody
	public Object getFocusInfo(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		long userId = Long.parseLong(request.getParameter("userId"));
		String result = "";
		String condition = "";
		condition = " and t1.userId='" + userId+"' order by t1.focusTime desc";

		String sql = "select * from t_focus as t1,t_baber as t2 where t1.baberId=t2.baberId";

		//List<T_Focus> focus;
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> focus;
		try {
			System.out.println("----try");
			focus = service.query(sql + condition);
			System.out.println("----query");
			if (focus.size() > 0) {
				map.put("focus", focus);
				result="success";
				System.out.println("----focus" + focus);
			} else {
				result="false";
				map.put("result",result);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("/getFocusStatusInfo")
	@ResponseBody
	public Object getFocusStatusInfo(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		long userId = Long.parseLong(request.getParameter("userId"));
		long baberId = Long.parseLong(request.getParameter("baberId"));
		String condition = "";
		condition = " where userId='" + userId + "'and baberId='" + baberId
				+ "'";

		String sql = "select * from t_focus";

		List<T_Focus> focus;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			System.out.println("----try");
			focus = service.query(sql + condition, T_Focus.class);
			System.out.println("----query");
			if (focus.size() > 0) {
				map.put("focusStatus", "已关注！");
				map.put("focus", focus);
				System.out.println("----focus" + focus);
			} else {
				map.put("focusStatus", "未关注！");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping("/focus")
	@ResponseBody
	public Object focus(T_Focus focus, HttpServletRequest request,
			HttpServletResponse response) {
		// Map<String, Object> map = new HashMap<String, Object>();
		long userId = Long.parseLong(request.getParameter("userId"));
		long baberId = Long.parseLong(request.getParameter("baberId"));
		String focusTime = request.getParameter("focusTime");
		String focusStatus = request.getParameter("focusStatus");
		focus.setUserId(userId);
		focus.setBaberId(baberId);
		focus.setFocusTime(focusTime);
		focus.setFocusStatus(focusStatus);

		try {
			int i = service.add(focus);
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

	@RequestMapping("/ajaxAdd")
	@ResponseBody
	public Object ajaxAdd(T_Focus focus) {
		try {
			int i = service.add(focus);
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
	public void delete(@RequestParam("focusId") long focusId)
			throws SQLException {
		//long deleteId=Long.parseLong(focusId);
		System.out.println("----focusId:--"+focusId);
		// 取消关注
		service.deleteByID(T_Focus.class, focusId);
		// 取消关注对应的码表
	}
}
