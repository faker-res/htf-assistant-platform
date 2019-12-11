package la.niub.abcapi.invest.indicatordatamigration.component.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * 多线程helper
 */
public class ThreadHelper {

    private static ExecutorService fixed_thread_pool;
    static {
        fixed_thread_pool = newFixedThreadPool(100);
    }

    public static Future run(Callable runner){
        Future<?> result = fixed_thread_pool.submit(runner);
        return result;
    }
}
