/**
 * 
 */
package la.niub.abcapi.invest.indicatordatamigration.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import la.niub.abcapi.invest.indicatordatamigration.dao.invest.InvestnewIndexMigrationTestMapper;
import la.niub.abcapi.invest.indicatordatamigration.dao.oa.OAUserMapper;
import la.niub.abcapi.invest.indicatordatamigration.model.invest.InvestnewIndexMigrationTest;
import la.niub.abcapi.invest.indicatordatamigration.model.response.Response;

/**
 * @author zhairp createDate: 2019-05-28
 */
@RestController
@RequestMapping("/test")
public class TestController {
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private OAUserMapper oaUserMapper;

	@Autowired
	private InvestnewIndexMigrationTestMapper investnewIndexMigrationTestMapper;

	@GetMapping("/migration")
	Response migration() {
		long startTime = System.currentTimeMillis();
		List<Map<String, Object>> users = oaUserMapper.queryOAUser();
		if (!CollectionUtils.isEmpty(users)) {
			investnewIndexMigrationTestMapper.deleteUsers();
			for (Map<String, Object> user : users) {
				InvestnewIndexMigrationTest migration = new InvestnewIndexMigrationTest();
				String employeeId = (String) user.get("EMPLOYEEID");
				String empName = (String) user.get("EMPNAME");
				String sex = (String) user.get("SEX");
				migration.setEmployeeid(employeeId);
				migration.setEmpname(empName);
				migration.setSex(sex);
				investnewIndexMigrationTestMapper.insertSelective(migration);
			}
		}
		long totalTime = System.currentTimeMillis() - startTime;
		return new Response("耗时[" + totalTime + "]毫秒");
	}

}