package Servlet;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;

import ENTITY.Entity_CodeCheck;
import ENTITY.Entity_Registe;
import ENTITY.Entity_Token;
import ENTITY.Entity_UserInfo;
import ENTITY.RespData.ResponseObeject;
import MOD.CONST;
import MOD.Mod_CheckCode;
import Tools.SMSSend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CodeCheck extends HttpServlet {
	private boolean isDebug = false; // 真实发送验证码
	private Gson gson;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		
		HttpSession session = request.getSession();
		String type = request.getParameter("checkType");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		Mod_CheckCode modCheckCode = new Mod_CheckCode();
		if (type.equals(CONST.SendCKLoginType)) { // 请求发送验证码，用于登录
			// TODO 1.登录时候的验证码发送
			String Tel = request.getParameter("tel");// 电话号码
			modCheckCode.setTelExist(new Entity_CodeCheck().isTelExist(Tel));
			if (modCheckCode.isTelExist() == false) {
				// TODO 登录->未注册的不发送【存在号码才发送】
				String outSS = "该号码未注册";
				modCheckCode.setErrIndex(CONST.NOTREGISTED);
				modCheckCode.setErrInfo(outSS);
				ResponseObeject responseObeject = new ResponseObeject(1, modCheckCode);
				Writer writer = response.getWriter();
				writer.write(gson.toJson(responseObeject));
				writer.flush();
				writer.close();
				return;
			} else {
				String checkCode = initCheckCode();
				session.setAttribute("checkcode", checkCode);
				session.setAttribute("checkname", checkCode + "-" + Tel);
				session.setAttribute("checkcodeTime", System.currentTimeMillis());
				modCheckCode.setCheckCode(checkCode);
				doSendSMS(Tel, checkCode);
				Writer writer = response.getWriter();
				String outSS = "验证码发送成功 with " + Tel + " :: " + checkCode;
				System.out.println(outSS);
				modCheckCode.setErrIndex(CONST.SENDOK);
				modCheckCode.setErrInfo(outSS);
				ResponseObeject responseObeject = new ResponseObeject(1, modCheckCode);
				writer.write(gson.toJson(responseObeject));
				writer.flush();
				writer.close();
				return;
			}
		} else if (type.equals(CONST.CKLoginType)) { // 请求验证短信登录
			// TODO 3.真实登录情况---短信
			String Tel = request.getParameter("tel");// 手机号码
			String CKcode = request.getParameter("code");// 验证码
			String checkcode = (String) session.getAttribute("checkcode"); // 注意客户端需要保存请求之前的cookieID
			String result = "验证失败，无该验证码";
			if (new ENTITY.Entity_CodeCheck().isTelExist(Tel)) {
				modCheckCode.setTelExist(true);
			} else {
				modCheckCode.setTelExist(false);
				result = "该手机号未注册";
				modCheckCode.setErrIndex(CONST.NOTREGISTED);
			}
			if (checkcode != null) {
				if (CKcode.equals(checkcode)) {
					long oldCheckTime = (Long) session.getAttribute("checkcodeTime");
					if (isInTime(oldCheckTime)) {
						// 验证码成功
						String checkName = (String) session.getAttribute("checkname");
						String code[] = checkName.split("-");
						// System.out.println("服务器数据cookie0:" + code[0]);
						// System.out.println("服务器数据cookie1:" + code[1]);
						// System.out.println("服务器数据cookie:" + (String) session.getAttribute("checkcode"));
						if (code[0].equals(CKcode) && code[1].equals(Tel)) {
							result = "登录成功  with " + Tel;
							modCheckCode.setErrIndex(CONST.OK);
							modCheckCode.setRespObject(new Entity_UserInfo().getUserInfo(Tel));
							new Entity_Token().setToken(Tel);
						} else {
							// 不做任何事，不匹配
							result = "手机号码不正确";
							modCheckCode.setErrIndex(CONST.NOTSAME);
						}
					} else {
						// 验证码超时
						result = "验证码超时，请及时输入验证码";
						modCheckCode.setErrIndex(CONST.NOTINTIME);
						session.removeAttribute("checkcode");
						session.removeAttribute("checkname");
						session.removeAttribute("checkcodeTime");
					}
				} else {
					result = "验证码错误，请确认验证码";
					modCheckCode.setErrIndex(CONST.NOCODE);
				}
			}
			modCheckCode.setErrInfo(result);
			System.out.println(result);
			ResponseObeject responseObeject = new ResponseObeject(1, modCheckCode);
			Writer writer = response.getWriter();
			writer.write(gson.toJson(responseObeject));
			writer.flush();
			writer.close();
		} else if (type.equals(CONST.SendCKRegisteType)) { // 请求注册时候的验证码
			// TODO 2.注册时候的验证码发送
			String Tel = request.getParameter("tel");// 电话号码
			modCheckCode.setTelExist(new Entity_CodeCheck().isTelExist(Tel));
			if (modCheckCode.isTelExist() == false) {
				// TODO 注册->未注册的发送【不存在号码才发送】
				String checkCode = initCheckCode();
				session.setAttribute("checkcode", checkCode);
				session.setAttribute("checkname", checkCode + "-" + Tel);
				session.setAttribute("checkcodeTime", System.currentTimeMillis());
				modCheckCode.setCheckCode(checkCode);
				doSendSMS(Tel, checkCode);
				Writer writer = response.getWriter();
				String outSS = "验证码发送成功 with " + Tel + " :: " + checkCode;
				System.out.println(outSS);
				modCheckCode.setErrIndex(CONST.SENDOK);
				modCheckCode.setErrInfo(outSS);
				ResponseObeject responseObeject = new ResponseObeject(1, modCheckCode);
				writer.write(gson.toJson(responseObeject));
				writer.flush();
				writer.close();
				return;
			}else{
				String outSS = "该号码已经注册";
				modCheckCode.setErrIndex(CONST.ISREGISTED);
				modCheckCode.setErrInfo(outSS);
				ResponseObeject responseObeject = new ResponseObeject(1, modCheckCode);
				Writer writer = response.getWriter();
				writer.write(gson.toJson(responseObeject));
				writer.flush();
				writer.close();
				return;
			}
		} else if (type.equals(CONST.CKRegisterType)) {
			// TODO 4.真实注册情况---短信
			String Tel = request.getParameter("tel");		// 手机号码
			String CKcode = request.getParameter("code");	// 验证码
//			String name = request.getParameter("name"); 		// 密码
			String pwd = request.getParameter("pwd"); 		// 密码
//			if(name == null || name.equals("")) name = Tel;
			String checkcode = (String) session.getAttribute("checkcode"); // 注意客户端需要保存请求之前的cookieID
			String result = "验证失败，无该验证码";
			if (new ENTITY.Entity_CodeCheck().isTelExist(Tel)) {
				modCheckCode.setTelExist(true);
				result = "该手机号已经注册";
				modCheckCode.setErrIndex(CONST.ISREGISTED);
			} else {
				modCheckCode.setTelExist(false);
			}
			if (checkcode != null) {
				if (CKcode.equals(checkcode)) {
					long oldCheckTime = (Long) session.getAttribute("checkcodeTime");
					if (isInTime(oldCheckTime)) {
						// 验证码成功
						String checkName = (String) session.getAttribute("checkname");
						String code[] = checkName.split("-");
						if (code[0].equals(CKcode) && code[1].equals(Tel)) {
							new Entity_Registe().RegisteUser(Tel, pwd);
							result = "注册成功  with " + Tel;
							modCheckCode.setErrIndex(CONST.OK);
							modCheckCode.setRespObject(new Entity_UserInfo().getUserInfo(Tel));
						} else {
							// 不做任何事，不匹配
							result = "手机号码不正确";
							modCheckCode.setErrIndex(CONST.NOTSAME);
						}
					} else {
						// 验证码超时
						result = "验证码超时，请及时输入验证码";
						modCheckCode.setErrIndex(CONST.NOTINTIME);
						session.removeAttribute("checkcode");
						session.removeAttribute("checkname");
						session.removeAttribute("checkcodeTime");
					}
				} else {
					result = "验证码错误，请确认验证码";
					modCheckCode.setErrIndex(CONST.NOCODE);
				}
			}
			System.out.println(result);
			modCheckCode.setErrInfo(result);
			ResponseObeject responseObeject = new ResponseObeject(1, modCheckCode);
			Writer writer = response.getWriter();
			writer.write(new Gson().toJson(responseObeject));
			writer.flush();
			writer.close();
			return;
		}
	}

	public void doSendSMS(String Tel, String Code) {
		if (isDebug == false)
			SMSSend.sendTo(Tel, Code);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public String initCheckCode() {
		return (int) ((Math.random() * 8 + 1) * 1000) + "";
	}

	public boolean isInTime(long oldtime) {
		long diss = System.currentTimeMillis() - oldtime;
		if (diss < 300000)
			return true;
		return false;
	}

	@Test
	public void ttt() {
		System.out.println("abc|123".split("|")[0]);
		System.out.println("abc|123".split("|")[1]);
		String[] a = "abc|123".split("|");
		System.out.println(a.length + "-----------");
		System.out.println("abc-123".split("-")[0]);
		System.out.println("abc-123".split("-")[1]);
	}
}
