package cn.itcast.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.RequestContent;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：hkxia
 * @description：TODO
 * @date ：2021/2/15 02:41
 */
@Component
public class loginFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE; //前置过滤器
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException { // run 中写业务逻辑，判断用户的请求中 access-token
        //获取请求上下文 getCurrentContext() 域的作用范围是请求到达zuul，一直到路由结束返回到客户端流程--->找是否存在access-token
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        //是否存在access-token？
        String token = request.getParameter("access-token");
        //不存在，拦截
        if (StringUtils.isBlank(token)) {
            //未登录，拦截
            ctx.setSendZuulResponse(false);// true放行、false拦截
            ctx.setResponseStatusCode(HttpStatus.SC_FORBIDDEN); //HTTP 状态码 403
        }
        return null;
    }

}
