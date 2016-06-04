package Servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ENTITY.Entity_MakeOrder;
import ENTITY.Entity_UpdateUserInfo;
import ENTITY.RespData.ResponseListObeject;
import MOD.Mod_UserInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UpdateUserInfoServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		// �����Ͻ���
		String reqBody = sb.toString();
		String json = reqBody;

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		if (json != null && !json.equals("")) {
			System.out.println("�û���Ϣ����");
			Mod_UserInfo userInfo = gson.fromJson(json, Mod_UserInfo.class);
			int result = new Entity_UpdateUserInfo().update(userInfo);
			ResponseListObeject responseObeject = new ResponseListObeject(result, result > 0 ? "�û���Ϣ���³ɹ�" : "�û���Ϣ����ʧ��");
			out.write(new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(responseObeject));
		} else {
			out.write("���ݲ�����");
		}
		out.flush();
		out.close();
	}

}
