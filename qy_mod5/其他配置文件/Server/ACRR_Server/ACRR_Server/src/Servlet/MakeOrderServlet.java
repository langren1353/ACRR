package Servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ytx.org.apache.http.protocol.HTTP;
import ENTITY.Entity_AcceptOrder;
import ENTITY.Entity_MakeOrder;
import ENTITY.RespData.ResponseListObeject;
import MOD.Mod_Orders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MakeOrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write("数据不完整");
		out.flush();
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		// 将资料解码
		String reqBody = sb.toString();
		String json = reqBody;

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		if (json != null && !json.equals("")) {
			System.out.println("维修单提交处理");
			int result = new Entity_MakeOrder().MakeOne(gson.fromJson(json, Mod_Orders.class));
			ResponseListObeject responseObeject = new ResponseListObeject(1, result > 0 ? "维修单提交成功" : "维修单提交失败");
			out.write(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(responseObeject));
		} else {
			out.write("数据不完整");
		}
		out.flush();
		out.close();
	}
}
