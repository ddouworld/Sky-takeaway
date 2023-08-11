package com.sky.aspect;

import cn.hutool.core.util.ObjectUtil;
import com.sky.annotations.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author 尹志伟
 * @date 2023/7/3 00:28:45
 * @Description 指定填充切面类
 * @TODO 由于使用了Mybatis-Plus的填充字段机制，故次AOP不使用，由 MybatisPlusMetaObjectHandler 代替
 */
@Component
@Slf4j
@Aspect
public class AutoFillAspect {
    /**
     * 定义切入点
     * com.sky.mapper 包下的所有类 ,以及定义了 @AutoFill 注解的类
     *
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotations.AutoFill)")
    public void autoFillAspectPotionCut() {
    }

    ;

    /**
     * 定义前置通知，在通知中进行公共字段的赋值
     *
     * @param joinPoint
     */
    @Before(value = "autoFillAspectPotionCut()")
    public void autoFill(JoinPoint joinPoint) {
        //获取参数信息，并且获取相对应的方法类
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //获取定义上的注解，
        AutoFill autoFill = method.getAnnotation(AutoFill.class);
        OperationType type = autoFill.type();
        //获取参数
        Object[] args = joinPoint.getArgs();
        if (null == args || args.length == 0) {
            log.info("AutoFillAspect autoFill method not params:{}", args);
        } else {
            log.info("AutoFillAspect autoFill method params:{}", Arrays.toString(args));
            if (ObjectUtil.isNotNull(type)) {
                //当前登录人员ID
                Long adminId = BaseContext.getCurrentId();
                //当前时间
                LocalDateTime now = LocalDateTime.now();
                //参数对象
                try {
                    Object po = args[0];
                    Class<?> poClass = po.getClass();
                    //反射强制获取对应的方法 Method 赋值并且调用
                    Method setCreateTime = poClass.getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME);
                    Method setUpdateTime = poClass.getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME);
                    Method setCreateUser = poClass.getDeclaredMethod(AutoFillConstant.SET_CREATE_USER);
                    Method setUpdateUser = poClass.getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER);
                    switch (type) {
                        case INSERT:
                            //新增操作
                            setCreateTime.invoke(po, now);
                            setUpdateTime.invoke(po, now);
                            setCreateUser.invoke(po, adminId);
                            setUpdateUser.invoke(po, adminId);
                            log.info("AutoFillAspect autoFill INSERT:{}", po.toString());
                            break;
                        case UPDATE:
                            //更新操作
                            setUpdateTime.invoke(po, now);
                            setUpdateUser.invoke(po, adminId);
                            log.info("AutoFillAspect autoFill UPDATE:{}", po.toString());
                            break;
                        default:
                    }
                } catch (Exception e) {
                    log.error("AutoFillAspect autoFill error:{}", e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}
