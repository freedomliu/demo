package com.example.demo.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class JSONResult {

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;
    
    // 附加数据
    private Object attach;

    public static JSONResult ok() {
        return new JSONResult(null);
    }
    
    public static JSONResult ok(Object data) {
        return new JSONResult(data);
    }
    
    public static JSONResult ok(String msg) {
        return new JSONResult(null,msg);
    }
    
    public static JSONResult ok(Object data,String msg) {
        return new JSONResult(data,msg);
    }
    
    public static JSONResult ok(Object data,String msg,Object attach) {
        return new JSONResult(data,msg,attach);
    }
    
    public static JSONResult build(Integer status, String msg, Object data) {
        return new JSONResult(status, msg, data);
    }
    
    public static JSONResult error(String msg) {
        return new JSONResult(500, msg, null);
    }

    public JSONResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }
    
    public JSONResult(Object data, String msg) {
    	this.status = 200;
    	this.msg =msg;
    	this.data = data;
    }
    
    public JSONResult(Object data, Object attach) {
    	this.status = 200;
    	this.msg = "OK";
    	this.data = data;
    	this.attach=attach;
    }
    
    public JSONResult(Object data, String msg, Object attach) {
    	this.status = 200;
    	this.msg = "OK";
    	this.data = data;
    	this.msg=msg;
    	this.attach=attach;
    }

    public JSONResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
    
	/**
	 * @Decription: 获取异常信息
	 * @Author: liuxiangtao90
	 * @CreateDate: Created in 2019/2/2 14:12
	 * @param:
	 * @param e
	 * @Return: java.lang.String
	 */
	public static String getExceptionString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getAttach() {
		return attach;
	}

	public void setAttach(Object attach) {
		this.attach = attach;
	}


}
