package com.hango.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hango.common.service.bean.HttpResult;
import com.hango.common.service.service.ApiService;
import com.hango.web.bean.User;
@Service
public class UserService {

    @Autowired
    private ApiService apiService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 调用SSO接口完成注册
     * 
     * @param username
     * @param password
     * @param phone
     * @return
     */
    public Boolean doRegister(String username, String password, String phone) {
        String url = "http://sso.taotao.com/user";
        Map<String, Object> params = new HashMap<String, Object>(3);
        params.put("username", username);
        params.put("password", password);
        params.put("phone", phone);
        try {
            HttpResult httpResult = this.apiService.doPost(url, params);
            if (httpResult.getCode() == 200) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 调用SSO接口完成登录
     * 
     * @param username
     * @param password
     * @return ticket
     */
    public String doLogin(String username, String password, String cartKey) {
        String url = "http://sso.taotao.com/user/login";
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("u", username);
        params.put("p", password);
        params.put("data", cartKey);//扩展SSO接口，接收参数，登录成功后发送消息中的内容
        try {
            HttpResult httpResult = this.apiService.doPost(url, params);
            if (httpResult.getCode() == 200) {
                String content = httpResult.getContent();
                JsonNode jsonNode = MAPPER.readTree(content);
                if (jsonNode.has("ticket")) {
                    // 登录成功
                    return jsonNode.get("ticket").asText();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过ticket查询用户信息
     * @param ticket
     * @return
     */
    public User queryUserByTicket(String ticket) {
        try {
            String url = "http://sso.taotao.com/user/" + ticket;
            String jsonData = this.apiService.doGet(url);
            if (jsonData != null) {
                return MAPPER.readValue(jsonData, User.class);
            }
        } catch (Exception e) {
            //TODO
            e.printStackTrace();
        }
        return null;
    }

}
