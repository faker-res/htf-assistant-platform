/**
 * 
 */
package com.htf.bigdata.invest.indicatormanage.component.util;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Test;

/**
 * @author zhairp createDate: 2019-06-14
 */
public class POIUtilTest {
	private static final String FILE_NAME = "D:\\test.xlsx";

	@Test
	public void resolveTest() throws Exception {
		FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
		System.out.println(POIUtil.resolve(excelFile));
	}

}
