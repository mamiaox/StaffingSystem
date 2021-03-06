package com.staffing.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opensymphony.xwork2.ActionSupport;
import com.staffing.bean.User;
import com.staffing.dao.UserDao;

@SuppressWarnings("serial")
@Component("getUsers")
@Scope("prototype")
public class GetUsersAction extends ActionSupport {
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	private String start = null;
	private String length = null;
	
	private String userId;
	
	private UserDao ud;
	public UserDao getUd() {
		return ud;
	}
	@Resource(name="userDao")
	public void setUd(UserDao ud) {
		this.ud = ud;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	
	public void get() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		Gson gson = new Gson();
		JsonObject jo = new JsonObject();
		boolean success = false;
		String reason = "";
		try {
			out = response.getWriter();
		} catch(IOException e) {}
		
		if(start == null && length == null) {
			List<User> users = ud.getUsers(-1, -1);
			if(users != null) {
				success = true;
				jo.add("users", gson.toJsonTree(users));
			} else
				reason = "获取数据为空";
		} 
		
		jo.addProperty("success", success);
		jo.addProperty("reason", reason);
		
		out.print(jo.toString());
		
		out.flush();
		out.close();
	}
	
	public void getOne() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		Gson gson = new Gson();
		JsonObject jo = new JsonObject();
		boolean success = false;
		String reason = "";
		try {
			out = response.getWriter();
		} catch(IOException e) {}

		if(userId != null) {
			User user = ud.getUserInId(userId);
			if(user != null) {
				success = true;
				jo.add("user", gson.toJsonTree(user));
			} else
				reason = "获取数据为空";
		} else
			reason = "请选择用户";
		
		jo.addProperty("success", success);
		jo.addProperty("reason", reason);
		
		out.print(jo.toString());
		
		out.flush();
		out.close();
	}
}
