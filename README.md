# security-rest
security: 主模块

security-core:  核心业务逻辑

security-browser: 浏览器安全特定代码

security-app :  app相关特定代码

security-demo ; 样例程序

问题：

2019-4-14
1. java Web过滤器,优先级高于SpringSecurity中 security过滤器链
    情景：
        在通过SpringSecurity的用户登陆校验的时候,若需要实现进行对校验码争取与否进行过滤。
        
        方法一： 自定义java Web过滤器,针对用户提交时进行该请求URL过滤，判断校验码是否正确，
                通过FilterRegistrationBean进行注册
                
        方法二： 自定义 SpringSecurity过滤器，针对用户提交时进行该请求URL过滤，判断校验码是否正确，
                通过，添加到SpringSecurity过滤器链里面。
                
    出现的问题：
        IllegalStateException: getOutputStream() has already been called for this response
        
    结果：
        由于SpringSecurity中 java Web过滤器优先级高于security过滤器链,
        所以如果配置的java Web过滤器，那么会先走java Web过滤器，如果再java Web过滤器过滤器里面获取了
        response的outPutStream或者writer， 那么轮到 SpringSecurity的过滤器想去拿的时候就会报错