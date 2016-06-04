package Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ENTITY.Entity_NeedMeOrders;
import ENTITY.Entity_Token;
import ENTITY.Entity_UserInfo;
import ENTITY.RespData.ResponseObeject;
import MOD.CONST;

import com.google.gson.GsonBuilder;

public class OrderFinishServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String orderID = request.getParameter(CONST.ORDERID_STRING);
		String workerID = request.getParameter(CONST.WORKERID_STRING);
		String token = request.getParameter(CONST.TOKEN_STRING);
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		if (workerID != null && token != null && orderID != null) {
			ResponseObeject responseObeject = new ResponseObeject(1, "");
			if (new Entity_Token().getTokenIsEnable(workerID, token) == true) {
				int resultID = new Entity_NeedMeOrders().OrderFinish(orderID);
				String result = resultID > 0 ? "提交成功" : "提交失败";
				responseObeject.setState(resultID);
				responseObeject.setMsg(result);
				// responseObeject.setObject(new Entity_UserInfo().getUserInfo(userID));
			} else {
				responseObeject.setMsg("获取失败,身份已过期");
			}
			out.write(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(responseObeject));
		} else {
			out.write("数据不完整");
		}
		out.flush();
		out.close();
	}

}
