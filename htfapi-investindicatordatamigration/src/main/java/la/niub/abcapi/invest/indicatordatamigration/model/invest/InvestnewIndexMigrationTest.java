/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Wed May 29 17:30:25 CST 2019
 * Description:
 */
package la.niub.abcapi.invest.indicatordatamigration.model.invest;

import java.util.Date;

import lombok.Data;

/**
 * InvestnewIndexMigrationTest investnew_index_migration_test
 */
@Data
public class InvestnewIndexMigrationTest {
	/**
	 * 主键id investnew_index_migration_test.id
	 */
	private Long id;

	/**
	 * 员工工号 investnew_index_migration_test.employeeid
	 */
	private String employeeid;

	/**
	 * 姓名 investnew_index_migration_test.empname
	 */
	private String empname;

	/**
	 * 性别 investnew_index_migration_test.sex
	 */
	private String sex;

	/**
	 * 创建者 investnew_index_migration_test.creator
	 */
	private String creator;

	/**
	 * 更新者 investnew_index_migration_test.editor
	 */
	private String editor;

	/**
	 * 创建时间 investnew_index_migration_test.create_time
	 */
	private Date createTime;

	/**
	 * 更新时间 investnew_index_migration_test.update_time
	 */
	private Date updateTime;

}