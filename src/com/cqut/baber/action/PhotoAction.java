package com.cqut.baber.action;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cqut.baber.entity.T_Photo;
import com.cqut.baber.service.PhotoService;
import com.cqut.genhoo.RM;
import com.cqut.genhoo.annotation.Action;
import com.cqut.genhoo.annotation.RequestMapping;
import com.cqut.genhoo.annotation.RequestParam;
import com.cqut.genhoo.annotation.ResponseBody;
import com.cqut.genhoo.bean.ErrorPage;
import com.cqut.genhoo.bean.Page;

 

@Action
@RequestMapping("/admin/Photo")
public class PhotoAction {
	private PhotoService service = new PhotoService();
	
	@RequestMapping("/GetPhotoInfo")
	@ResponseBody
	public Object GetPhotoInfo(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

		String condition = "";

		String sql = "select * from t_photo";

		List<T_Photo> photo;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			photo = service.query(sql + condition, T_Photo.class);
			map.put("photo", photo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("/index")
	public Page index(){
		return new Page("/WEB-INF/jsp/baberMIS/photo/PhotoList.jsp");
	}	
	
	@RequestMapping("/ajaxSearch")
	@ResponseBody
	public Map<String,Object>  Searchlist(@RequestParam("page") String pageIndexStr,@RequestParam("rows") String rowsStr,HttpServletRequest request,HttpServletResponse response) throws SQLException, UnsupportedEncodingException{
		String realName = request.getParameter("realName");
		int pageIndex = Integer.parseInt(pageIndexStr);
		int pageSize = Integer.parseInt(rowsStr);
		
		Map<String,Object> responseMessage = new HashMap<String,Object>();
		
		com.cqut.genhoo.Page<Map<String,Object>> page = service.queryOnePagePhotos(pageIndex, pageSize,realName);
		
		responseMessage.put("total", page.getTotal());
		responseMessage.put("rows", page.getDataList());
		 
		return responseMessage;
	}
	
	
	@RequestMapping("/initAdd")
	public Page initAdd1(){
		return new Page("/WEB-INF/jsp/baberMIS/photo/PhotoAdd.jsp");
	}
	
	@RequestMapping("/add")
	public Page add(T_Photo photo){
		//System.out.println("解析的结果:"+JSON.toJSONString(Photo));
		//service.add(Photo);
		return new Page("/Photo/index");
	}
	
	@RequestMapping("/ajaxAdd")
	@ResponseBody
	public Object ajaxAdd(T_Photo photo){
		try {
			int i = service.add(photo);
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
	public void delete(@RequestParam("PhotoId") Long PhotoId) throws SQLException{
	 
			//删除学生
			service.deleteByID(T_Photo.class, PhotoId);
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
				service.deleteByID(T_Photo.class,Ids[i]);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("/initEdit")
	public Page initEdit(@RequestParam("photoId") Long photoId){
		System.out.println("into edit");
		try {
			T_Photo photo = service.getEntityByID(T_Photo.class, photoId);
			System.out.println("ID="+photo.getPhotoId());
			return new Page("/WEB-INF/jsp/baberMIS/photo/PhotoEdit.jsp").data("photo",photo);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ErrorPage(e);
		}
	}
			
	@RequestMapping("/ajaxEdit")
	@ResponseBody
	public Object ajaxEdit(T_Photo photo){
		try {
			service.modify(photo, new String[]{"hairPhotoUrl","hairPhotoName","hairPhotoDescribe"});
			return RM.createResponseMesage(true, RM.UPDATE_SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			return RM.createResponseMesage(false, e.getMessage());
		}
	}
}
