package Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ENTITY.Entity_MyOrders;
import ENTITY.Entity_NeedMeOrders;
import ENTITY.RespData.ResponseListObeject;
import MOD.CONST;

import com.google.gson.GsonBuilder;

public class getNeedMeOrders extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String myID = request.getParameter(CONST.MYTELID_STRING);
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		if(myID!=null){
			ResponseListObeject responseObeject = new ResponseListObeject(1, new Entity_NeedMeOrders().getNeedMeOrders(myID));
			out.write(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(responseObeject));
		}else{
			out.write("数据不完整");
		}
		out.flush();
		out.close();
	}

}
