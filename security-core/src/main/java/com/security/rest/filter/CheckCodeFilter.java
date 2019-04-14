package com.security.rest.filter;

import com.security.rest.cache.CustomerCache;
import com.security.rest.common.CacheConstant;
import com.security.rest.common.SecurityConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CheckCodeFilter implements Filter {

    @Autowired
    private CustomerCache customerCache;

    public void doFilter(final HttpServletRequest request, final HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(SecurityConstant.SYS_ENCODEING);

        //获取code
        String code = ServletRequestUtils.getStringParameter(request, "code");
        String uuid = ServletRequestUtils.getStringParameter(request, "uuid");
//        ServletOutputStream out = response.getOutputStream();//这里不能提前拿，不然SpringSecurity想拿输出对象的时候会报错
        //没有输入验证码则直接返回
        if(StringUtils.isEmpty(code)){
            log.warn("请输入验证码");
            response.getWriter().write("请输入验证码");
            IOUtils.closeQuietly(response.getWriter());
        }else{
            //从缓存获取验证码
            String cacheCode = customerCache.getObject(CacheConstant.VERIFY_CODE_KEY + ":" + uuid, String.class);
            if(!cacheCode.equals(code)){
                response.getWriter().write("验证码错误");
                log.warn("验证码错误");
                IOUtils.closeQuietly(response.getWriter());
            }else{
                chain.doFilter(request, response);
            }
        }
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
