/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Tue May 28 13:16:59 CST 2019
 * Description:
 */
package la.niub.abcapi.invest.indicatordatamigration.dao.wdb;

import java.util.List;

import la.niub.abcapi.invest.indicatordatamigration.model.wdb.DimStock;

public interface DimStockMapper {

	List<DimStock> queryStock();

}