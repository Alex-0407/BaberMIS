package com.cqut.baber.action;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cqut.baber.entity.T_User;
import com.cqut.baber.service.UserService;
import com.cqut.genhoo.RM;
import com.cqut.genhoo.annotation.Action;
import com.cqut.genhoo.annotation.RequestMapping;
import com.cqut.genhoo.annotation.RequestParam;
import com.cqut.genhoo.annotation.ResponseBody;
import com.cqut.genhoo.bean.ErrorPage;
import com.cqut.genhoo.bean.Page;

@Action
@RequestMapping("/admin/User")
public class UserAction {
	private UserService service = new UserService();

	@RequestMapping("/login")
	@ResponseBody
	public Object login(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String msg = "0";
		Boolean success = false;

		String userName = request.getParameter("userName");
		String pwd = request.getParameter("pwd");
		System.out.println("userName:" + userName + "  pwd:" + pwd);

		String condition = "";
		condition = " where userName = '" + userName + "' and userPwd='" + pwd
				+ "'";

		String sql = "select * from t_user";

		List<T_User> user;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			System.out.println("------try---");
			user = service.query(sql + condition, T_User.class);
			if (user.size() > 0) {
				/*
				 * request.setAttribute("user", user.get(0));
				 * request.setAttribute("str", user.get(0).getUserPwd());
				 */
				map.put("user", user);
			}

			// Iterator<user> userIterator=user.iterator();
			System.out.println("------sql+condition---" + sql + condition);
			System.out.println("------user---" + user);
			if (user.size() > 0) {
				success = true;
				msg = "登录成功！";
				System.out.println("------loginResult---" + success);
			} else {
				success = false;
				msg = "用户名或密码错误！";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.put("success", success);
		map.put("msg", msg);
		return map;
	}

	@RequestMapping("/register")
	@ResponseBody
	public Object register(HttpServletRequest request,
			HttpServletResponse response, T_User user) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();

		String name = request.getParameter("name");
		String tel = request.getParameter("tel");
		String pwd = request.getParameter("pwd");
		String userPhotoUrl = "images/photo/baber/male/male.jpg";
		System.out.println("name:" + name + "tel:" + tel + "  pwd" + pwd);

		user.setUserName(name);
		user.setUserTel(tel);
		user.setUserPwd(pwd);
		user.setUserPhotoUrl(userPhotoUrl);
		user.setUserRealName("");
		user.setUserSex("");
		user.setUserBirthday("");
		user.setUserAddress("");

		try {
			if (!service.isExist(T_User.class, "userName", name)) {
				try {
					int i = service.add(user);
					if (i == 1) {
						return RM.createResponseMesage(true, RM.ADD_SUCCESS);
					} else {
						return RM.createResponseMesage(false, RM.ADD_ERROR);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return RM.createResponseMesage(false, e.getMessage());
				}
			} else {
				map.put("result", "已存在！");
				return map;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return RM.createResponseMesage(false, e.getMessage());
		}
	}

	@RequestMapping("/modefyPwd")
	@ResponseBody
	public Object modefyPwd(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

		String name = request.getParameter("name");
		String pwd = request.getParameter("pwd");
		String newpwd = request.getParameter("newpwd");
		System.out.println("name:" + name + "pwd:" + pwd + "  newpwd" + newpwd);

		String condition = "";
		condition = " where userName = '" + name + "' and userPwd='" + pwd
				+ "'";

		String sql = "update t_user set userPwd='" + newpwd + "'";

		try {
			int i = service.update(sql + condition);
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

	@RequestMapping("/charge")
	@ResponseBody
	public Object charge(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

		String name = request.getParameter("name");
		Float num = Float.parseFloat(request.getParameter("num"));
		String pwd = request.getParameter("pwd");

		System.out.println("name:" + name + "pwd:" + pwd + "  num" + num);

		String condition = "";
		condition = " where userRealName = '" + name + "' and userPwd='" + pwd
				+ "'";

		String sql1 = "select * from t_user";

		List<T_User> user;

		try {
			user = service.query(sql1 + condition, T_User.class);
			if (user.size() > 0) {
				Float userAccountAmount = user.get(0).getUserAccountAmount()
						+ num;

				String sql = "update t_user set userAccountAmount='"
						+ userAccountAmount + "'";
				int i = service.update(sql + condition);
				if (i == 1) {
					return RM.createResponseMesage(true, RM.ADD_SUCCESS);
				} else {
					return RM.createResponseMesage(false, RM.ADD_ERROR);
				}
			} else
				return RM.createResponseMesage(false, RM.ADD_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			return RM.createResponseMesage(false, e.getMessage());
		}
	}

	@RequestMapping("/GetUserInfo")
	@ResponseBody
	public Object GetUserInfo(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String userId = request.getParameter("userId");

		String condition = "";
		condition = " where userId = '" + userId + "'";

		String sql = "select * from t_user";

		List<Map<String, Object>> user;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			user = service.query(sql + condition);
			map.put("user", user);
			System.out.println("------user---" + user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping("/modefyMyInfo")
	@ResponseBody
	public Object modefyMyInfo(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String realName = request.getParameter("realName");
		String sex = request.getParameter("sex");
		String birthday = request.getParameter("birthday");
		String tel = request.getParameter("tel");
		String address = request.getParameter("address");

		System.out.println("userName:" + userName + "userName:" + userName
				+ "  userSex" + sex);

		String condition = "";
		condition = " where userId = '" + userId + "'";

		String sql = "update t_user set userName='" + userName
				+ "',userRealName='" + realName + "',userSex='" + sex
				+ "',userBirthday='" + birthday + "',userTel='" + tel
				+ "',userAddress='" + address + "'";

		try {
			int i = service.update(sql + condition);
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

	@RequestMapping("/ajaxSearch")
	@ResponseBody
	public Map<String, Object> Searchlist(
			@RequestParam("page") String pageIndexStr,
			@RequestParam("rows") String rowsStr, HttpServletRequest request,
			HttpServletResponse response) throws SQLException,
			UnsupportedEncodingException {
		String userName = request.getParameter("userName");
		System.out.println("----userName------" + userName);
		int pageIndex = Integer.parseInt(pageIndexStr);
		int pageSize = Integer.parseInt(rowsStr);

		Map<String, Object> responseMessage = new HashMap<String, Object>();

		com.cqut.genhoo.Page<Map<String, Object>> page = service
				.queryOnePageUsers(pageIndex, pageSize, userName);

		responseMessage.put("total", page.getTotal());
		responseMessage.put("rows", page.getDataList());

		return responseMessage;
	}

	@RequestMapping("/index")
	public Page index() {
		return new Page("/WEB-INF/jsp/baberMIS/user/UserList.jsp");
	}

	@RequestMapping("/initAdd")
	public Page initAdd1() {
		return new Page("/WEB-INF/jsp/baberMIS/user/UserAdd.jsp");
	}

	@RequestMapping("/add")
	public Page add(T_User user) {
		// System.out.println("解析的结果:"+JSON.toJSONString(user));
		// service.add(user);
		return new Page("/User/index");
	}

	@RequestMapping("/ajaxAdd")
	@ResponseBody
	public Object ajaxAdd(T_User user) {
		try {
			int i = service.add(user);
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
	public void delete(@RequestParam("userId") Long userId) throws SQLException {

		// 删除学生
		service.deleteByID(T_User.class, userId);
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
				service.deleteByID(T_User.class, Ids[i]);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@RequestMapping("/initEdit")
	public Page initEdit(@RequestParam("userId") Long userId) {
		try {
			T_User user = service.getEntityByID(T_User.class, userId);
			return new Page("/WEB-INF/jsp/baberMIS/user/UserEdit.jsp").data(
					"user", user);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ErrorPage(e);
		}
	}

	@RequestMapping("/ajaxEdit")
	@ResponseBody
	public Object ajaxEdit(T_User user) {
		try {
			service.modify(user, new String[] { "userName", "userSex",
					"userTel" });
			return RM.createResponseMesage(true, RM.UPDATE_SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			return RM.createResponseMesage(false, e.getMessage());
		}
	}
}
