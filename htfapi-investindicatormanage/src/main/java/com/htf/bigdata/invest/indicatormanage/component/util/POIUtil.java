package com.htf.bigdata.invest.indicatormanage.component.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.htf.bigdata.invest.indicatormanage.model.business.QuotaBean;
import com.htf.bigdata.invest.indicatormanage.model.business.QuotaValue;
import com.memfactory.utils.ExcelUtil;

/**
 * excel工具
 * 
 * @author zhairp createDate: 2019-06-13
 */
public final class POIUtil {
	private POIUtil() {
	}

	/**
	 * 定制化的解析excel
	 * 
	 * @author zhairp createDate: 2019-06-13
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static List<QuotaBean> resolve(InputStream is) throws FileNotFoundException, IOException {
		Workbook workbook = new XSSFWorkbook(is);
		Sheet datatypeSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = datatypeSheet.iterator();
		List<QuotaBean> quotas = new ArrayList<QuotaBean>();

		// 拿表头指标名
		if (iterator.hasNext()) {
			Row currentRow = iterator.next();
			Iterator<Cell> iterCell = currentRow.iterator();
			iterCell.forEachRemaining(currentCell -> {
				if (!"指标名称".equals(currentCell.toString())) {
					quotas.add(new QuotaBean(currentCell.toString()));
				}
			});
		}

		// 解析指标维度信息:指标单位,指标频率,关联行业,关联股票代码
		Row unitRow = iterator.next();
		Iterator<Cell> unitCell = unitRow.iterator();
		unitCell.next();
		while (unitCell.hasNext()) {
			Cell currentCell = unitCell.next();
			quotas.get(currentCell.getColumnIndex() - 1).setUnit(currentCell.toString());
		}
		Row frequencyRow = iterator.next();
		Iterator<Cell> frequencyCell = frequencyRow.iterator();
		frequencyCell.next();
		while (frequencyCell.hasNext()) {
			Cell currentCell = frequencyCell.next();
			quotas.get(currentCell.getColumnIndex() - 1).setFrequency(currentCell.toString());
		}
		Row industryRow = iterator.next();
		Iterator<Cell> industryCell = industryRow.iterator();
		industryCell.next();
		while (industryCell.hasNext()) {
			Cell currentCell = industryCell.next();
			quotas.get(currentCell.getColumnIndex() - 1).setIndustries(currentCell.toString().split(";"));
		}
		Row stockRow = iterator.next();
		Iterator<Cell> stockCell = stockRow.iterator();
		stockCell.next();
		while (stockCell.hasNext()) {
			Cell currentCell = stockCell.next();
			quotas.get(currentCell.getColumnIndex() - 1).setStockCodes(currentCell.toString().split(";"));
		}

		while (iterator.hasNext()) {
			// 拿日期
			Row currentRow = iterator.next();
			Iterator<Cell> iterCell = currentRow.iterator();
			String strDate = "";
			if (iterCell.hasNext()) {
				Cell currentCell = iterCell.next();
				strDate = ExcelUtil.getCellVal(currentCell);
			}
			// 拿指标数据，拼装成"日期:值"
			while (iterCell.hasNext()) {
				Cell currentCell = iterCell.next();
				QuotaValue quotaValue = new QuotaValue();
				quotaValue.setTime(strDate);
				quotaValue.setData(new BigDecimal(currentCell.toString()));
				quotas.get(currentCell.getColumnIndex() - 1).getDatas().add(quotaValue);
			}
		}
		return quotas;
	}
}
