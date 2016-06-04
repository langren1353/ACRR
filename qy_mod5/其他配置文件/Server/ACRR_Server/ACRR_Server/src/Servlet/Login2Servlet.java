package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ENTITY.Entity_Login2Servelt;
import ENTITY.Entity_MyOrders;
import ENTITY.Entity_Token;
import ENTITY.Entity_UserInfo;
import ENTITY.RespData.ResponseListObeject;
import ENTITY.RespData.ResponseObeject;
import MOD.CONST;

import com.google.gson.GsonBuilder;

public class Login2Servlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String tel = request.getParameter("tel");
		String pwd = request.getParameter("pwd");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		
		if(tel!=null && pwd != null){
			boolean result = new Entity_Login2Servelt().login(tel, pwd);
			if(result == true){
				new Entity_Token().setToken(tel);
			}
			ResponseObeject responseObeject = new ResponseObeject(result==true?1:-1, result==true?"登录成功":"登录失败");
			responseObeject.setObject(new Entity_UserInfo().getUserInfo(tel));
			out.write(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(responseObeject));
		}else{
			out.write("数据不完整");
		}
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
