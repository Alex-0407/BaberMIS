package com.cqut.baber.action;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cqut.baber.entity.T_Admin;
import com.cqut.baber.entity.T_Baber;
import com.cqut.baber.service.BaberService;
import com.cqut.genhoo.RM;
import com.cqut.genhoo.annotation.Action;
import com.cqut.genhoo.annotation.RequestMapping;
import com.cqut.genhoo.annotation.RequestParam;
import com.cqut.genhoo.annotation.ResponseBody;
import com.cqut.genhoo.bean.ErrorPage;
import com.cqut.genhoo.bean.Page;

@Action
@RequestMapping("/admin/Baber")
public class BaberAction {
	private BaberService service = new BaberService();

	// private BusinessService b_service = new BusinessService();

	@RequestMapping("/login")
	@ResponseBody
	public Object login(@RequestParam("name") String name,
			@RequestParam("password") String password,
			@RequestParam("yanzhengma") String yanzhengma,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		//System.out.println("tel:" + name + "  pwd:" + password);
		//System.out.println("------yanzhengma---" + yanzhengma);
		Map<String, Object> map = new HashMap<String, Object>();
		String type="";
		String result="false";
		
		String bcondition = "";
		bcondition = " where baberName = '" + name + "' and baberPwd='"
				+ password + "'";
		String bsql = "select * from t_baber";
		
		String acondition = "";
		acondition = " where adminName = '" + name + "' and adminPwd='"
				+ password + "'";
		String asql = "select * from t_admin";

		List<T_Baber> baber;
		List<T_Admin> admin;
		
		HttpSession session=request.getSession();
		try {
			System.out.println("------try---");
			baber = service.query(bsql + bcondition, T_Baber.class);
			admin = service.query(asql + acondition, T_Admin.class);
			System.out.println("------sql+condition---" + bsql + bcondition);
			
			
			if (admin.size() > 0) {
				type="管理员";
				result="success";
				map.put("type", type);
				map.put("result", result);
				long loginAdminId=admin.get(0).getAdminId();
				session.setAttribute("loginAdminId", loginAdminId); 
				session.setAttribute("adminLoginName", name); 
				return map;
			}
			else if (baber.size() > 0) {
				type="理发师";
				result="success";
				map.put("type", type);
				map.put("result", result);
				long loginBaberId=baber.get(0).getBaberId();
				session.setAttribute("loginBaberId", loginBaberId); 
				session.setAttribute("baberLoginName", name); 
				System.out.println("------loginName---" + session.getAttribute("baberLoginName"));
				return map;
			} else {
				return RM.createResponseMesage(false, RM.Login_ERROR);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return RM.createResponseMesage(false, e.getMessage());
		}
	}

	@RequestMapping("/register")
	@ResponseBody
	public Object register(HttpServletRequest request,
			HttpServletResponse response, T_Baber baber) {
		// TODO Auto-generated method stub

		String name = request.getParameter("name");
		String tel = request.getParameter("tel");
		String pwd = request.getParameter("pwd");
		System.out.println("name:" + name + "tel:" + tel + "  pwd" + pwd);

		baber.setBaberName(name);
		baber.setBaberTel(tel);
		baber.setBaberPwd(pwd);

		try {
			int i = service.add(baber);
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

	@RequestMapping("/GetBaberDetailInfo_ById")
	@ResponseBody
	public Object GetBaberDetailInfo_ById(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> test;
		List<Map<String, Object>> evaluate;

		String baberId = request.getParameter("baberId");

		String condition = "";
		condition = " and t1.baberId=" + baberId;

		String condition1 = "";
		condition1 = " and t1.baberId=" + baberId;

		String sqlstring = "select * from t_baber as t1,t_photo as t2,t_business as t3 where t1.baberId=t2.baberId and t1.baberId=t3.baberId";
		String sqlstring1 = "select * from t_order as t1,t_evaluate as t2,t_user as t3 where t1.orderId=t2.orderId and t1.userId=t3.userId";

		try {
			test = service.query(sqlstring + condition);
			evaluate = service.query(sqlstring1 + condition1);
			System.out.println("---test-----" + test);
			map.put("test", test);
			map.put("evaluate", evaluate);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return map;
	}

	@RequestMapping("/GetBaberInfo_Male")
	@ResponseBody
	public Object GetBaberInfo_Male(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

		String condition = "";
		condition = " and t1.baberSex ='男'";

		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> test;
		String sqlstring = "select * from t_baber as t1,t_business as t2 where t1.baberId=t2.baberId";
		try {
			test = service.query(sqlstring + condition);
			System.out.println(test.toString());
			map.put("test", test);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return map;
	}

	@RequestMapping("/GetBaberInfo_Female")
	@ResponseBody
	public Object GetBaberInfo_Female(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> test;

		String condition = "";
		condition = " and t1.baberSex ='女'";

		String sqlstring = "select * from t_baber as t1,t_business as t2 where t1.baberId=t2.baberId";

		try {
			test = service.query(sqlstring + condition);
			System.out.println(test.toString());
			map.put("test", test);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return map;
	}

	@RequestMapping("/GetBaberInfo_Male_ByPrice")
	@ResponseBody
	public Object GetBaberInfo_Male_ByPrice(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> test;

		String condition = "";
		condition = " and t1.baberSex ='男' order by t2.servicePrice";

		String sqlstring = "select * from t_baber as t1,t_business as t2 where t1.baberId=t2.baberId";

		try {
			test = service.query(sqlstring + condition);
			System.out.println(test.toString());
			map.put("test", test);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return map;
	}

	@RequestMapping("/GetBaberInfo_Female_ByPrice")
	@ResponseBody
	public Object GetBaberInfo_Female_ByPrice(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> test;

		String condition = "";
		condition = " and t1.baberSex ='女' order by t2.servicePrice";

		String sqlstring = "select * from t_baber as t1,t_business as t2 where t1.baberId=t2.baberId";

		try {
			test = service.query(sqlstring + condition);
			System.out.println(test.toString());
			map.put("test", test);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return map;
	}
	
	
	@RequestMapping("/index")
	public Page index() {
		return new Page("/WEB-INF/jsp/baberMIS/baber/BaberList.jsp");
	}
	
	@RequestMapping("/ajaxSearch")
	@ResponseBody
	public Map<String, Object> Searchlist(
			@RequestParam("page") String pageIndexStr,
			@RequestParam("rows") String rowsStr, HttpServletRequest request,
			HttpServletResponse response) throws SQLException,
			UnsupportedEncodingException {
//		HttpSession session = request.getSession();
//		String loginBaber=(String) session.getAttribute("loginBaber");
		String baberName = request.getParameter("baberName");
		int pageIndex = Integer.parseInt(pageIndexStr);
		int pageSize = Integer.parseInt(rowsStr);

		Map<String, Object> responseMessage = new HashMap<String, Object>();

		com.cqut.genhoo.Page<Map<String, Object>> page = service
				.queryOnePageBabers(pageIndex, pageSize, baberName);

		responseMessage.put("total", page.getTotal());
		responseMessage.put("rows", page.getDataList());

		return responseMessage;
	}

	@RequestMapping("/initAdd")
	public Page initAdd1() {
		return new Page("/WEB-INF/jsp/baberMIS/baber/BaberAdd.jsp");
	}

	@RequestMapping("/ajaxAdd")
	@ResponseBody
	public Object ajaxAdd(T_Baber baber) {
		try {
			int i = service.add(baber);
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
	public void delete(@RequestParam("baberId") Long baberId)
			throws SQLException {

		// 删除学生
		service.deleteByID(T_Baber.class, baberId);
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
				service.deleteByID(T_Baber.class, Ids[i]);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@RequestMapping("/initEdit")
	public Page initEdit(@RequestParam("baberId") Long baberId) {
		try {
			T_Baber baber = service.getEntityByID(T_Baber.class, baberId);
			return new Page("/WEB-INF/jsp/baberMIS/baber/BaberEdit.jsp").data(
					"baber", baber);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ErrorPage(e);
		}
	}

	@RequestMapping("/ajaxEdit")
	@ResponseBody
	public Object ajaxEdit(T_Baber baber) {
		try {
			service.modify(baber, new String[] { "baberName", "baberTel",
					"baberAddress" });
			return RM.createResponseMesage(true, RM.UPDATE_SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			return RM.createResponseMesage(false, e.getMessage());
		}
	}
}
