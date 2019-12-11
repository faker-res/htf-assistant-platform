package la.niub.abcapi.invest.indicatordatamigration.component.health;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import la.niub.abcapi.invest.indicatordatamigration.model.response.Response;

/**
 * 健康监测
 * 
 * @author zhairp createDate: 2019-05-10
 */
@RestController
public class HealthCheckController {
	private Logger log = LoggerFactory.getLogger(getClass());

	@GetMapping(value = "/healthCheck")
	public Response healthCheck() {
		return new Response<>("<Invest IndicatorManage Service>系统健康");
	}

}
