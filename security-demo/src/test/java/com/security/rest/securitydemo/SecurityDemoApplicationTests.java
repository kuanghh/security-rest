package com.security.rest.securitydemo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityDemoApplicationTests {

    @Autowired
    private WebApplicationContext wac;

    /**
     * 可模拟http请求
     */
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        /**
         * set入环境webApplicationContext,则每次发出请求都是到当前wac里面
         */
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testGetUsers() throws Exception{

        /**
         * perform : 模拟请求
         * MockMvcRequestBuilders.get : 制定uri
         * MockMvcRequestBuilders.param : 放入请求参数值
         * MockMvcRequestBuilders.contentType : 制定请求头contentType
         * andExpect : 对返回的请求进行预期值匹配
         * MockMvcResultMatchers.status().isOk() : 返回请求码为200
         * MockMvcResultMatchers.jsonPath("$.length()").value(3) : 返回的json数据中的数组长度为3
         *      补充：这里如果想了解jsonPath的使用，可以通过https://github.com/json-path/JsonPath进行了解
         */
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/user")
                .param("username","Tom")
                .param("age","13")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

}
