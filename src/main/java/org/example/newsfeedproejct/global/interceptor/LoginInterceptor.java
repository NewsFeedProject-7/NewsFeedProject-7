package org.example.newsfeedproejct.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.newsfeedproejct.global.consts.Const;
import org.example.newsfeedproejct.global.exception.GlobalException;
import org.example.newsfeedproejct.global.exception.errorcode.CommonErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import static org.springframework.http.HttpMethod.OPTIONS;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final String[] WHITE_LIST = { "/", "/signup", "/login" };

    @Override
    public boolean preHandle(
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse,
            Object handler
    ) {

        // CORS 프리플라이트는 통과
        if (OPTIONS.matches(httpRequest.getMethod())) return true;

        String uri = httpRequest.getRequestURI();
        if (isWhiteList(uri)) return true;

        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute(Const.LOGIN_USER) == null) {
            throw new GlobalException(CommonErrorCode.UNAUTHORIZED);
        }

        return true;
    }

    private boolean isWhiteList(String uri) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, uri);
    }
}
