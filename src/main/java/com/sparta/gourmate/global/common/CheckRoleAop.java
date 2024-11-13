package com.sparta.gourmate.global.common;

import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.global.exception.CustomException;
import com.sparta.gourmate.global.exception.ErrorCode;
import com.sparta.gourmate.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CheckRoleAop {

    @Pointcut("execution(* com.sparta.gourmate.domain.user.controller.UserController.*User(..))")
    private void userController() {
    }

    @Pointcut("execution(* com.sparta.gourmate.domain.*.controller.Admin*.*(..))")
    private void adminController() {
    }

    @Before("userController() && !adminController()")
    public Object execute(JoinPoint joinPoint) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal().getClass() == UserDetailsImpl.class) {
            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
            User loginUser = userDetails.getUser();

            Long userId = Arrays.stream(joinPoint.getArgs())
                    .filter(Long.class::isInstance)
                    .map(Long.class::cast)
                    .findFirst()
                    .orElse(null);
            if (userId == null) {
                return null;
            }
            if (!loginUser.getRole().isAdmin() && !userId.equals(loginUser.getId())) {
                throw new CustomException(ErrorCode.AUTH_AUTHORIZATION_FAILED);
            }
        }
        return null;
    }
}