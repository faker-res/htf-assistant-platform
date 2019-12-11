package la.niub.abcapi.invest.indicatordatamigration.component.util;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * bean转换
 * @author xwu.abcft
 */
public class ObjectUtil {

    /**
     * 从map转换为bean
     * @param map
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T map2Bean(Map<String, Object> map, Class<T> clz) {
        T bean = null;
        try {
            bean = clz.newInstance();
            BeanUtils.populate(bean, map);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 从bean转换为map
     * @param bean
     * @param includeParent 是否包含父类属性
     * @return
     */
    public static Map<String,Object> bean2Map(Object bean,Boolean includeParent) {
        Map<String,Object> map = new HashMap<>();
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields){
            try {
                field.setAccessible(true);
                map.put(field.getName(),field.get(bean));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (includeParent){
            Class parent = bean.getClass().getSuperclass();
            while (!parent.equals(Object.class)){
                fields = parent.getDeclaredFields();
                for (Field field : fields){
                    try {
                        field.setAccessible(true);
                        map.put(field.getName(),field.get(bean));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                parent = parent.getClass().getSuperclass();
            }
        }
        return map;
    }

    /**
     * 去除map中为null的项
     * @param map
     * @return
     */
    public static Map removeNull(Map map){
        Iterator iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            if (entry.getValue() == null){
                iterator.remove();
            }
        }
        return map;
    }

    /**
     * 拷贝对象的值为新的对象同时初始化
     * @param bean 待拷贝的原对象
     * @param clz  新对象的类
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> T copy(Object bean, Class<T> clz) {
        Map<String,Object> map = new HashMap<>();
        for (Field field : bean.getClass().getDeclaredFields()){
            try {
                field.setAccessible(true);
                map.put(field.getName(),field.get(bean));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        T newBean = null;
        try {
            newBean = clz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        for (Field field : clz.getDeclaredFields()){
            try {
                field.setAccessible(true);
                if (map.containsKey(field.getName())){
                    Object value = map.get(field.getName());
                    if (value != null && field.getType().isInstance(value)){
                        field.set(newBean,value);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return newBean;
    }
}
