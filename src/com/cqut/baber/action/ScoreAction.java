package com.cqut.baber.action;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cqut.baber.entity.T_Score;
import com.cqut.baber.service.ScoreService;
import com.cqut.genhoo.RM;
import com.cqut.genhoo.annotation.Action;
import com.cqut.genhoo.annotation.RequestMapping;
import com.cqut.genhoo.annotation.ResponseBody;

@Action
@RequestMapping("/admin/Score")
public class ScoreAction {
	private ScoreService service = new ScoreService();
	
	
	@RequestMapping("/GetScoreInfo")
	@ResponseBody
	public Object GetScoreInfo(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

		String condition = "";

		String sql = "select * from t_score";

		List<T_Score> score;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			System.out.println("----try");
			score = service.query(sql + condition, T_Score.class);
			System.out.println("----query");
			map.put("score", score);
			System.out.println("----score"+score);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping("/ajaxAdd")
	@ResponseBody
	public Object ajaxAdd(T_Score score){
		try {
			int i = service.add(score);
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
