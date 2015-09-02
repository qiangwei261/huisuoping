package com.hango.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hango.common.service.service.ApiService;
import com.hango.common.service.service.RedisService;

@Service
public class IndexService {

    @Autowired
    private ApiService apiService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Value("${HANGO_MANAGE_URL}")
    private String HANGO_MANAGE_URL;

    @Value("${INDEX_BIG_AD}")
    private String INDEX_BIG_AD;

    @Autowired
    private RedisService redisService;

    public static final String REDIS_INDEX_BIG_AD_KEY = "HANGO_WEB_INDEX_BIG_AD";
    
    public static final String REDIS_INDEX_DATA_KEY = "INDEX_DATA_";

    private static final Integer REDIS_INDEX_BIG_AD_TIME = 60 * 60 * 24;

    
    
    
    /**
     * 获取大广告位数据
     * 
     * @return json数据
     */
    @SuppressWarnings("unchecked")
    public String getIndexBigAd() {
        
        return null;
    }

    /**
     * 首页楼数据
     * 
     * @return
     */
    public String getIndexData() {
		return HANGO_MANAGE_URL;
        
    }

   
}
