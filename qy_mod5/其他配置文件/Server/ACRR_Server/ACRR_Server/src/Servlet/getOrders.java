package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ENTITY.Entity_Orders;
import ENTITY.RespData.ResponseListObeject;
import MOD.CONST;
import MOD.Mod_Orders;

import com.google.gson.GsonBuilder;

public class getOrders extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*******还没有开始做分页问题*******/
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String FindPage = request.getParameter(CONST.PAGEINDEX_STRING);
		if(FindPage == null) FindPage = 1+"";
		System.out.println("查询页数="+FindPage);
		ArrayList<Mod_Orders> orders = new Entity_Orders().getShops(FindPage);
		ResponseListObeject responseObeject = new ResponseListObeject(1, orders);
		int page = Integer.parseInt(FindPage);
		if(orders.size() == 0){
			page--;
		}
		responseObeject.setMsg(page+""); //页数
		out.write(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(responseObeject));
		out.flush();
		out.close();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}
}
