package la.niub.abcapi.invest.indicatordatamigration.component.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 集合工具
 */
public class ListUtil {

    /**
     * 移除空元素
     * @param list
     * @return
     */
    public static Collection<String> removeEmpty(Collection<String> list){
        Iterator<String> it = list.iterator();
        while(it.hasNext()){
            String x = it.next();
            if(x == null || x.equals("") || x.replaceAll("\\s","").equals("")){
                it.remove();
            }
        }
        return list;
    }

    /**
     * 计算交集
     * @param list1
     * @param list2
     * @param <T>
     * @return
     */
    public static <T> List<T> intersect(List<T> list1, List<T> list2){
        List<T> newList = new ArrayList(list1);
        newList.retainAll(list2);
        return newList;
    }

    /**
     * 计算差集
     * @param list1
     * @param list2
     * @param <T>
     * @return
     */
    public static <T> List<T> diff(List<T> list1, List<T> list2){
        List<T> newList = new ArrayList(list1);
        newList.removeAll(list2);
        return newList;
    }
}
