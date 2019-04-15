package com.security.rest.filter;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Producer;
import com.security.rest.cache.CustomerCache;
import com.security.rest.common.CacheConstant;
import com.security.rest.exception.VerifyCodeException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.ServletRequestUtils;

import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.security.rest.common.SecurityConstant.SYS_ENCODEING;
import static com.security.rest.common.SecurityConstant.VERIFY_UUID;

/**
 * 获取登陆验证码的过滤器
 */
@Data
@Slf4j
public class VerifyCodeFilterImpl implements VerifyCodeFilter {

    private Producer producer;

    private CustomerCache customerCache;

    public void doFilter(final HttpServletRequest request, final HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        String code = producer.createText();
        String uuid = ServletRequestUtils.getStringParameter(request, VERIFY_UUID);
        ServletOutputStream outputStream = response.getOutputStream();
        /**
         * 如果请求的时候没有带上UUID
         */
        if(uuid == null){
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(SYS_ENCODEING);
            String message = JSONObject.toJSONString(new VerifyCodeException(HttpStatus.BAD_REQUEST.value(), "请求没带上uuid"));
            outputStream.write(message.getBytes());
        }

        //返回图片
        BufferedImage image = producer.createImage(code);
        //放入缓存
        customerCache.putObject(CacheConstant.VERIFY_CODE_KEY +":"+uuid, code);
        ImageIO.write(image, "jpeg", outputStream);
        try {
            IOUtils.closeQuietly(response.getWriter());
        }catch (Exception e){}
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            // Cast to HTTP
            doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
        } else {
            throw new ServletException("Cannot filter non-HTTP requests/responses");
        }
    }
}
