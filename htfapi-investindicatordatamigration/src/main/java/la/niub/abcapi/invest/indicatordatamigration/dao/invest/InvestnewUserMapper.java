/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Mon Jun 03 08:59:21 CST 2019
 * Description:
 */
package la.niub.abcapi.invest.indicatordatamigration.dao.invest;

import la.niub.abcapi.invest.indicatordatamigration.model.invest.InvestnewUser;

public interface InvestnewUserMapper {
    /**
     * deleteByPrimaryKey
     * @param Integer id
     * @return int 
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * insert
     * @param InvestnewUser record
     * @return int 
     */
    int insert(InvestnewUser record);

    /**
     * insertSelective
     * @param InvestnewUser record
     * @return int 
     */
    int insertSelective(InvestnewUser record);

    /**
     * selectByPrimaryKey
     * @param Integer id
     * @return InvestnewUser 
     */
    InvestnewUser selectByPrimaryKey(Integer id);

    /**
     * updateByPrimaryKeySelective
     * @param InvestnewUser record
     * @return int 
     */
    int updateByPrimaryKeySelective(InvestnewUser record);

    /**
     * updateByPrimaryKey
     * @param InvestnewUser record
     * @return int 
     */
    int updateByPrimaryKey(InvestnewUser record);
}