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
 * 外部数据库
 * 
 * @author zhairp createDate: 2019-05-28
 */
@Configuration
@MapperScan(basePackages = WDBConfiguration.PACKAGE, sqlSessionFactoryRef = "wdbSqlSessionFactory")
public class WDBConfiguration {

	// Mapper接口目录
	static final String PACKAGE = "la.niub.abcapi.invest.indicatordatamigration.dao.wdb";
	// Mapper文件目录
	static final String MAPPER_LOCATION = "classpath:mapper/wdb/*.xml";

	@Value("${spring.datasource.wdb.url}")
	private String url;

	@Value("${spring.datasource.wdb.username}")
	private String user;

	@Value("${spring.datasource.wdb.password}")
	private String password;

	@Value("${spring.datasource.wdb.driverClassName}")
	private String driverClass;

	@Value("${spring.datasource.wdb.timeoutSeconds}")
	private Integer timeout;

	@Bean(name = "wdbDataSource")
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

	@Bean(name = "wdbTransactionManager")
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean(name = "wdbSqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("wdbDataSource") DataSource dataSource) throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
		sessionFactory.getObject().getConfiguration().setCallSettersOnNulls(true);
		sessionFactory.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
		return sessionFactory.getObject();
	}

}