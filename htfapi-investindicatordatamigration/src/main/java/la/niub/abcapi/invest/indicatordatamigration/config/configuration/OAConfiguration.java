package la.niub.abcapi.invest.indicatordatamigration.config.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * OA
 * 
 * @author zhairp createDate: 2019-05-28
 */
@Configuration
@MapperScan(basePackages = OAConfiguration.PACKAGE, sqlSessionFactoryRef = "oaSqlSessionFactory")
public class OAConfiguration {

	// Mapper接口目录
	static final String PACKAGE = "la.niub.abcapi.invest.indicatordatamigration.dao.oa";
	// Mapper文件目录
	static final String MAPPER_LOCATION = "classpath:mapper/oa/*.xml";

	@Value("${spring.datasource.oa.url}")
	private String url;

	@Value("${spring.datasource.oa.username}")
	private String user;

	@Value("${spring.datasource.oa.password}")
	private String password;

	@Value("${spring.datasource.oa.driverClassName}")
	private String driverClass;

	@Value("${spring.datasource.oa.timeoutSeconds}")
	private Integer timeout;

	@Bean(name = "oaDataSource")
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(password);
		dataSource.setLoginTimeout(timeout);
		dataSource.setQueryTimeout(timeout);
		return dataSource;
	}

	@Bean(name = "oaTransactionManager")
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean(name = "oaSqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("oaDataSource") DataSource dataSource) throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
		sessionFactory.getObject().getConfiguration().setCallSettersOnNulls(true);
		sessionFactory.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
		return sessionFactory.getObject();
	}

}