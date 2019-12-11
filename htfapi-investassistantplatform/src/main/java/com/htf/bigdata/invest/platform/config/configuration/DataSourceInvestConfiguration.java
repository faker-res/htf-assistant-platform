package com.htf.bigdata.invest.platform.config.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * master主数据源配置类
 * 
 * @author zhairp createDate: 2019-02-27
 */
@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = DataSourceInvestConfiguration.PACKAGE, sqlSessionFactoryRef = "investSqlSessionFactory")
public class DataSourceInvestConfiguration {

	// 精确到 invest 目录，以便跟其他数据源隔离
	// Mapper接口目录
	static final String PACKAGE = "com.htf.bigdata.invest.platform.dao.invest";
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
		return dataSource;
	}

	@Bean(name = "investTransactionManager")
	@Primary
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean(name = "investSqlSessionFactory")
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
	}
}