package com.yule.querydb.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 反射工具类
 *
 * @author yule
 * @date 2018/10/5 17:36
 */
public class ReflectUtil {

    private static final Logger log = LoggerFactory.getLogger(ReflectUtil.class);

    public static void setFieldValue(Object object, String fieldName, Object value) {
        Field field = getDeclaredField(object, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
        }

        makeAccessible(field);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            log.error(null, e);
        }
    }

    public static Object getFieldValue(Object object, String fieldName) {
        Field field = getDeclaredField(object, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
        }

        makeAccessible(field);

        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            log.error(null, e);
        }

        return result;
    }

    private static Field getDeclaredField(Object object, String filedName)
    {
        for (Class superClass = object.getClass(); superClass != Object.class; )
        {
            try {
                return superClass.getDeclaredField(filedName);
            }
            catch (NoSuchFieldException e)
            {
            }
            superClass = superClass.getSuperclass();
        }

        return null;
    }

    private static void makeAccessible(Field field)
    {
        if (!Modifier.isPublic(field.getModifiers())) {
            field.setAccessible(true);
        }
    }

}
