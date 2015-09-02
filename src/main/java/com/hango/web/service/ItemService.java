package com.hango.web.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.hango.common.service.service.ApiService;
import com.hango.common.service.service.RedisService;
import com.hango.manage.pojo.Item;
import com.hango.manage.pojo.ItemDesc;

@Service
public class ItemService {

    @Autowired
    private ApiService apiService;

    @Value("${HANGO_MANAGE_URL}")
    private String HANGO_MANAGE_URL;

    @Autowired
    private RedisService redisService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static final String REDIS_ITEM_KEY = "HANGO_WEB_ITEM_";

    public Item queryItemById(Long itemId) {
        // 从缓存中命中
        String key = REDIS_ITEM_KEY + itemId;
        try {
            String cacheData = this.redisService.get(key);
            if (StringUtils.isNotBlank(cacheData)) {
                return MAPPER.readValue(cacheData, Item.class);
            }
        } catch (Exception e1) {
            // TODO
            e1.printStackTrace();
        }
        try {
            String url = HANGO_MANAGE_URL + "/rest/item/" + itemId;
            String jsonData = this.apiService.doGet(url);
            Item item = MAPPER.readValue(jsonData, Item.class);

            try {
                // 将结果集写入到缓存中
                this.redisService.set(key, jsonData, 60 * 60 * 24);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return item;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询商品描述
     * 
     * @param itemId
     * @return
     */
    public ItemDesc queryItemDescByItemId(Long itemId) {
        try {
            String url = HANGO_MANAGE_URL + "/rest/item/desc/" + itemId;
            String jsonData = this.apiService.doGet(url);
            ItemDesc itemDesc = MAPPER.readValue(jsonData, ItemDesc.class);
            return itemDesc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
