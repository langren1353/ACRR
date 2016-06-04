package Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ENTITY.Entity_AcceptOrder;
import ENTITY.Entity_Orders;
import ENTITY.RespData.ResponseListObeject;
import MOD.CONST;

import com.google.gson.GsonBuilder;

public class AcceptOrder extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 
		String workerID = request.getParameter(CONST.WORKERID_STRING);
		String orderID = request.getParameter(CONST.ORDERID_STRING);
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		if(workerID!=null && workerID != null){
			ResponseListObeject responseObeject = new ResponseListObeject(1, new Entity_AcceptOrder().AcceptOrder(workerID, orderID)==1?"接单成功":"接单失败");
			out.write(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(responseObeject));
		}else{
			out.write("数据不完整");
		}
		out.flush();
		out.close();
	}

}
