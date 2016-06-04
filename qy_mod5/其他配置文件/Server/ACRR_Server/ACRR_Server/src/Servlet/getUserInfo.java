package Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.enterprise.inject.New;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ENTITY.Entity_Token;
import ENTITY.Entity_UserInfo;
import ENTITY.RespData.ResponseObeject;
import MOD.CONST;

import com.google.gson.GsonBuilder;

public class getUserInfo extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userID = request.getParameter(CONST.USERID_STRING);
		String token = request.getParameter(CONST.TOKEN_STRING);
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		if(userID!=null && token != null){
			ResponseObeject responseObeject = new ResponseObeject(1, "");
			if(new Entity_Token().getTokenIsEnable(userID, token) == true){
				responseObeject.setMsg("获取成功");
				responseObeject.setObject(new Entity_UserInfo().getUserInfo(userID));
			}else{
				responseObeject.setMsg("获取失败,身份已过期");
			}
			out.write(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(responseObeject));
		}else{
			out.write("数据不完整");
		}
		out.flush();
		out.close();
	}
}
