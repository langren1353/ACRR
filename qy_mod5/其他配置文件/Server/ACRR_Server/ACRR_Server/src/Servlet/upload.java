package Servlet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ENTITY.Entity_Token;
import ENTITY.Entity_UpdateUserInfo;
import ENTITY.Entity_UserInfo;
import ENTITY.RespData.ResponseObeject;
import MOD.CONST;
import Tools.MyUtils;

import com.google.gson.GsonBuilder;
import com.sun.mail.util.BASE64DecoderStream;

public class upload extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("POST ***************************************");
		String userID = request.getParameter(CONST.USERID_STRING);
		String token = request.getParameter(CONST.TOKEN_STRING);
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String message = "";
		if (userID != null && token != null) {
			ResponseObeject responseObeject = new ResponseObeject(1, "");
			if (new Entity_Token().getTokenIsEnable(userID, token) == true) {
				try {
					String imageSTR = getReqBody(request);
					String url = MyUtils.GetMD5(Math.random()*10000+"ABC"+Math.random()*100);
					String urlString = this.getServletContext().getRealPath(
							"headPort/KKKK.jpg".replace("KKKK", url));
					url = "/headPort/KKKK.jpg".replace("KKKK", url);
					new Entity_UpdateUserInfo().updateIcon(url, userID);
					System.out.println(urlString);
					base64TOIamge(imageSTR, urlString);
					message = "上传成功";
				} catch (Exception e) {
					e.printStackTrace();
					message = ("上传失败");
				}
			} else {
				message = ("获取失败,身份已过期");
			}
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

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("GET ***************************************");
		resp.getWriter().write("this is upLoad server");
	}

	public String getReqBody(HttpServletRequest req) throws IOException {

		InputStream ins = req.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(ins);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		StringBuilder stringBuilder = new StringBuilder();
		String oneString = "";
		while ((oneString = bufferedReader.readLine()) != null) {
			stringBuilder.append(oneString);
		}
		return stringBuilder.toString();
	}

	public int base64TOIamge(String base64String, String fileUrl) {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				base64String.getBytes());
		BASE64DecoderStream base64DecoderStream = new BASE64DecoderStream(
				byteArrayInputStream);
		byte bytes[] = base64DecoderStream.decode(base64String.getBytes());
		File file = new File(fileUrl);
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(bytes);
			fileOutputStream.flush();
			fileOutputStream.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
}
