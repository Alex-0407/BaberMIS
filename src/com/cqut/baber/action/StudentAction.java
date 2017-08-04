package com.cqut.baber.action;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cqut.baber.entity.Student;
import com.cqut.baber.service.StudentService;
import com.cqut.genhoo.RM;
import com.cqut.genhoo.annotation.Action;
import com.cqut.genhoo.annotation.RequestMapping;
import com.cqut.genhoo.annotation.RequestParam;
import com.cqut.genhoo.annotation.ResponseBody;
import com.cqut.genhoo.bean.ErrorPage;
import com.cqut.genhoo.bean.Page;
 

@Action
@RequestMapping("/admin/Student")
public class StudentAction {
	private StudentService service = new StudentService();
	
	@RequestMapping("/index")
	public Page index(){
		return new Page("/WEB-INF/jsp/baber/student/StudentList.jsp");
	}
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object>  list(@RequestParam("page") String pageIndexStr,@RequestParam("rows") String rowsStr,HttpServletResponse response) throws SQLException{
		int pageIndex = Integer.parseInt(pageIndexStr);
		int pageSize = Integer.parseInt(rowsStr);	
		
		Map<String,Object> responseMessage = new HashMap<String,Object>();
		
		com.cqut.genhoo.Page<Map<String,Object>> page = service.queryOnePageStudents(pageIndex, pageSize);
		
		responseMessage.put("total", page.getTotal());
		responseMessage.put("rows", page.getDataList());
		
		return responseMessage;
	}
	
	@RequestMapping("/ajaxSearch")
	@ResponseBody
	public Map<String,Object>  Searchlist(@RequestParam("page") String pageIndexStr,@RequestParam("rows") String rowsStr,HttpServletRequest request,HttpServletResponse response) throws SQLException, UnsupportedEncodingException{
		String stuname = request.getParameter("stuname");
		int pageIndex = Integer.parseInt(pageIndexStr);
		int pageSize = Integer.parseInt(rowsStr);
		
		Map<String,Object> responseMessage = new HashMap<String,Object>();
		
		com.cqut.genhoo.Page<Map<String,Object>> page = service.searchOnePageStudents(pageIndex, pageSize,stuname);
		
		responseMessage.put("total", page.getTotal());
		responseMessage.put("rows", page.getDataList());
		/*String Sql="select *  from student where 1=1  ";
		String a1 =request.getParameter("name");
		if(a1!=""){
			Sql+="name="+a1+" and";
		}
		if()*/
		 
		return responseMessage;
	}
	
	
	@RequestMapping("/initAdd")
	public Page initAdd1(){
		return new Page("/WEB-INF/jsp/baber/student/StudentAdd.jsp");
	}
	
	@RequestMapping("/add")
	public Page add(Student student){
		//System.out.println("解析的结果:"+JSON.toJSONString(student));
		//service.add(student);
		return new Page("/Student/index");
	}
	
	@RequestMapping("/delete")
	public void delete(@RequestParam("studentID") Long studentID) throws SQLException{
	 
			//删除学生
			service.deleteByID(Student.class, studentID);
			//删除学生对应的码表
 
	}
	
	@RequestMapping("/deleteAll")
	public void deleteAll(@RequestParam("stuIds") String s) {
		System.out.println("进入deletAll方法");
		String stuIds[]=s.split(",");
		System.out.println(stuIds);
		try {
			for (int i = 0; i < stuIds.length; i++) {
				System.out.println(stuIds[i]);
				service.deleteByID(Student.class,stuIds[i]);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/initEdit")
	public Page initEdit(@RequestParam("studentID") Long studentID){
		try {
			Student student = service.getEntityByID(Student.class, studentID);
			return new Page("/WEB-INF/jsp/baber/student/StudentEdit.jsp").data("student", student);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ErrorPage(e);
		}
	}
			
	@RequestMapping("/ajaxEdit")
	@ResponseBody
	public Object ajaxEdit(Student student){
		try {
			service.modify(student, new String[]{"name","remark"});
			return RM.createResponseMesage(true, RM.UPDATE_SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			return RM.createResponseMesage(false, e.getMessage());
		}
	}
	
	@RequestMapping("/ajaxAdd")
	@ResponseBody
	public Object ajaxAdd(Student student){
		try {
			int i = service.add(student);
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
