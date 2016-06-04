package Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ENTITY.Entity_Token;
import ENTITY.Entity_UpdateUserInfo;
import ENTITY.RespData.ResponseObeject;
import MOD.CONST;
import Tools.MyUtils;

import com.google.gson.GsonBuilder;

public class PwdChangeServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userID = request.getParameter(CONST.USERID_STRING);
		String token = request.getParameter(CONST.TOKEN_STRING);
		String pwdnew = request.getParameter(CONST.PWD_New_STRING);
		String pwdold = request.getParameter(CONST.PWD_Old_STRING);
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String message = "";
		if (userID != null && token != null) {
			int result = -1;
			if (new Entity_Token().getTokenIsEnable(userID, token) == true) {
				try {
					result = new Entity_UpdateUserInfo().reNewPwd(userID, pwdnew, pwdold);
					message = "修改成功";
				} catch (Exception e) {
					e.printStackTrace();
					message = ("修改失败");
				}
			} else {
				message = ("获取失败,身份已过期");
			}
			ResponseObeject responseObeject = new ResponseObeject(result, "");
			responseObeject.setMsg(message);
			System.out.println(message);
			out.write(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
					.create().toJson(responseObeject));
		} else {
			out.write("数据不完整");
		}
		out.flush();
		out.close();
	}

}
