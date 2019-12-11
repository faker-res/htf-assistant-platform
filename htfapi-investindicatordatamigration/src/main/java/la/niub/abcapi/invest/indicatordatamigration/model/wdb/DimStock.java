/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Tue May 28 13:16:59 CST 2019
 * Description:
 */
package la.niub.abcapi.invest.indicatordatamigration.model.wdb;

import lombok.Data;

/**
 * DimStock dim_stock
 */
@Data
public class DimStock {
	/**
	 * 
	 * dim_stock.stockcode
	 */
	private String stockcode;

	/**
	 * 
	 * dim_stock.stockname
	 */
	private String stockname;

	/**
	 * 
	 * dim_stock.market
	 */
	private String market;

}