package com.htf.bigdata.gateway.component.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 文本日志
 */
public class FileLogger {

    private final static Logger logger = LogManager.getLogger(FileLogger.class);

    public final static void write(String filePath, String text) {
        if (text != null && !text.isEmpty()) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true));
                bw.write(text);
                bw.flush();
                bw.close();
            } catch (IOException e) {
                logger.warn("fileLog write failed! filePath : " + filePath);
            }
        }
    }

}
