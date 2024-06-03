package com.ideage.ams.config.handler;

import com.ideage.ams.entity.AdminUser;
import com.ideage.ams.service.AdminUserService;
import com.ideage.ams.config.annotation.TokenToUser;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @description アノテーション解析クラス
 * @author zhen.cheng
 */
@Component
public class TokenToUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private AdminUserService adminUserService;

    public TokenToUserMethodArgumentResolver() {
    }

    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(TokenToUser.class)) {
            return true;
        }
        return false;
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        if (parameter.getParameterAnnotation(TokenToUser.class) instanceof TokenToUser) {
            AdminUser adminUser = null;
            String token = webRequest.getHeader("token");
            if (null != token && !"".equals(token) && token.length() == 32) {
                adminUser = adminUserService.getAdminUserByToken(token);
            }
            return adminUser;
        }
        return null;
    }

}