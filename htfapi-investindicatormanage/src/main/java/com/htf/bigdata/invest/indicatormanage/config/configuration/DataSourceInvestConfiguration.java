package com.htf.bigdata.invest.indicatormanage.config.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.enums.DBType;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * master主数据源配置类
 * 
 * @author zhairp createDate: 2019-02-27
 */
@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = DataSourceInvestConfiguration.PACKAGE)
public class DataSourceInvestConfiguration {

	// 精确到 invest 目录，以便跟其他数据源隔离
	// Mapper接口目录
	static final String PACKAGE = "com.htf.bigdata.invest.indicatormanage.dao.invest";
	// Mapper文件目录
	static final String MAPPER_LOCATION = "classpath:mapper/invest/*.xml";

	@Value("${spring.datasource.invest.url}")
	private String url;

	@Value("${spring.datasource.invest.username}")
	private String user;

	@Value("${spring.datasource.invest.password}")
	private String password;

	@Value("${spring.datasource.invest.driverClassName}")
	private String driverClass;

	@Value("${spring.datasource.invest.timeoutSeconds}")
	private Integer timeout;

	@Value("${spring.datasource.invest.initSize}")
	private Integer initSize;

	@Value("${spring.datasource.invest.minIdle}")
	private Integer minIdle;

	@Value("${spring.datasource.invest.maxActive}")
	private Integer maxActive;

	@Value("${spring.datasource.invest.maxWait}")
	private Long maxWait;

	@Value("${spring.datasource.invest.timeBetweenEvictionRunsMillis}")
	private Long timeBetweenEvictionRunsMillis;

	@Value("${spring.datasource.invest.validationQuery}")
	private String validationQuery;

	@Value("${spring.datasource.invest.testWhileIdle}")
	private Boolean testWhileIdle;

	@Value("${spring.datasource.invest.testOnBorrow}")
	private Boolean testOnBorrow;

	@Value("${spring.datasource.invest.testOnReturn}")
	private Boolean testOnReturn;

	@Value("${spring.datasource.invest.minEvictableIdleTimeMillis}")
	private Long minEvictableIdleTimeMillis;

	@Value("${spring.datasource.invest.maxEvictableIdleTimeMillis}")
	private Long maxEvictableIdleTimeMillis;

	@Value("${spring.datasource.invest.testOnReturn}")
	private Boolean keepAlive;

	@Bean(name = "investDataSource")
	@Primary
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(password);
		dataSource.setLoginTimeout(timeout);
		dataSource.setQueryTimeout(timeout);
		dataSource.setInitialSize(initSize);
		dataSource.setMinIdle(minIdle);
		dataSource.setMaxActive(maxActive);
		dataSource.setMaxWait(maxWait);
		dataSource.setValidationQuery(validationQuery);
		dataSource.setTestWhileIdle(testWhileIdle);
		dataSource.setTestOnBorrow(testOnBorrow);
		dataSource.setTestOnReturn(testOnReturn);
		dataSource.setTimeBetweenConnectErrorMillis(timeBetweenEvictionRunsMillis);
		dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		dataSource.setMaxEvictableIdleTimeMillis(maxEvictableIdleTimeMillis);
		dataSource.setKeepAlive(keepAlive);
		return dataSource;
	}

	@Bean(name = "investTransactionManager")
	@Primary
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	/*@Bean(name = "investSqlSessionFactory")
	@Primary
	public SqlSessionFactory sqlSessionFactory(@Qualifier("investDataSource") DataSource dataSource) throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
		// mybatis返回resultType="map"时,如果数据为空的字段,则该字段省略不显示.
		// 表示设置结果为Null也返回相应的字段名称
		sessionFactory.getObject().getConfiguration().setCallSettersOnNulls(true);
		sessionFactory.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
		return sessionFactory.getObject();
	}*/

    /**
     * @Description:mybatis分页插件
     * @param []
     * @Return: com.baomidou.mybatisplus.plugins.PaginationInterceptor
     * @Author: huwl
     * @Date: 2019/6/17
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page=new PaginationInterceptor();
        page.setDialectType(DBType.MYSQL.getDb());
        return page;
    }
}