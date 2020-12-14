package com.example.traveldiary.aop;

import com.example.traveldiary.service.UserLastActivityService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Aspect
public class LastActivityAspect {

    @Autowired
    private UserLastActivityService userLastActivityService;

    @AfterReturning("@annotation(LastActivity)")
    public void fixActivity(JoinPoint jp) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String description = String.format("%s(%s)",
                jp.getSignature().getName(),
                getArgsStraing(jp));

        userLastActivityService.save(username, LocalDateTime.now(), description);
    }

    private String getArgsStraing(JoinPoint jp) {
        List<String> argsList = new ArrayList<>();
        for (Object arg : jp.getArgs()) {
            if (arg.getClass().isPrimitive() || arg instanceof Number || String.class.equals(arg.getClass())) {
                argsList.add(arg.toString());
            } else if (arg instanceof Principal) {
                argsList.add(((Principal) arg).getName());
            } else {
                argsList.add(arg.getClass().getSimpleName());
            }
        }
        return String.join(", ", argsList);
    }

}
