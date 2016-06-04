package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import ENTITY.Entity_NearBy;
import ENTITY.Entity_Orders;
import ENTITY.RespData.ResponseObeject;
import Tools.MyDataBase;

public class getNearBy extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String min_lat = request.getParameter("min_lat");
		String max_lat = request.getParameter("max_lat");
		String min_lon = request.getParameter("min_lon");
		String max_lon = request.getParameter("max_lon");
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		ResponseObeject responseObeject = new ResponseObeject(1, new Entity_NearBy().getNearByShops(new String[]{min_lat, max_lat, min_lon, max_lon}));
		out.write(new Gson().toJson(responseObeject));
		out.flush();
		out.close();
	}
}
