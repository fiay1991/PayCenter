package com.park.paycenter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liuyang
 * 时间：2016年4月25日
 * 功能：
 * 备注：
 */

/**
 * 在方法上使用，用于指定使用哪个数据源
 *
 * @author  
 * 
 * 
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    String name();
}