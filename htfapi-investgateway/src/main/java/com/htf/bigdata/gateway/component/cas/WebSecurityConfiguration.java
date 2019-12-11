package com.htf.bigdata.gateway.component.cas;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * cas配置
 * @author wb-wuxiao
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    //登录回调端点
    private static final String casReturnUrl = "/j_spring_cas_security_check";

    //登出回调端点
    private static final String casLogoutUrl = "/j_spring_cas_security_logout";

    //应用地址
    @Value("${security.cas.service.host}")
    private String serviceUrl;

    //cas域名
    @Value("${security.cas.server.host}")
    private String casServerUrl;

    //cas登陆地址
    @Value("${security.cas.server.login}")
    private String casServerLoginUrl;

    //cas登出地址
    @Value("${security.cas.server.logout}")
    private String casServerLogoutUrl;

    @Override
    public void configure(WebSecurity web) throws Exception {
        //配置忽略
        web.ignoring().antMatchers("/static/**", "/templates/**");
//        web.ignoring().antMatchers("/**");
    }

    /**
     * 设置登录回调端点
     * @return
     */
    @Bean
    public ServiceProperties serviceProperties(){
        ServiceProperties sp = new ServiceProperties();
        sp.setService(serviceUrl+casReturnUrl);
        sp.setSendRenew(false);
        return sp;
    }

    @Bean
    public CasAuthenticationProvider casAuthenticationProvider(){
        CasAuthenticationProvider cp = new CasAuthenticationProvider();
        cp.setAuthenticationUserDetailsService(authenticationUserDetailsService());
        cp.setServiceProperties(serviceProperties());
        cp.setTicketValidator(cas20ServiceTicketValidator());
        cp.setKey("casAuthenticationProviderKey");
        return cp;
    }

    @Bean
    public AuthenticationUserDetailsService<CasAssertionAuthenticationToken> authenticationUserDetailsService(){
        return new CustomUserDetailsService();
    }

    @Bean
    public Cas20ServiceTicketValidator cas20ServiceTicketValidator(){
        return new Cas20ServiceTicketValidator(casServerUrl);
    }

    @Bean
    public CasAuthenticationFilter casAuthenticationFilter() throws Exception {
        CasAuthenticationFilter cf = new CasAuthenticationFilter();
        cf.setAuthenticationManager(authenticationManager());
        cf.setFilterProcessesUrl(casReturnUrl);
        return cf;
    }

    @Bean
    public CasAuthenticationEntryPoint casAuthenticationEntryPoint(){
        CasAuthenticationEntryPoint ce = new CasAuthenticationEntryPoint();
        ce.setLoginUrl(casServerLoginUrl);
        ce.setServiceProperties(serviceProperties());
        return ce;
    }

    @Bean
    public SingleSignOutFilter singleSignOutFilter(){
        SingleSignOutFilter sf = new SingleSignOutFilter();
        sf.setCasServerUrlPrefix(casServerUrl);
        sf.setIgnoreInitConfiguration(true);
        return sf;
    }

    /**
     * 配置登出过滤器端点
     * @return
     */
    @Bean
    public LogoutFilter logoutFilter(){
        LogoutFilter lf = new LogoutFilter(casServerLogoutUrl,new SecurityContextLogoutHandler());
        lf.setLogoutRequestMatcher(new AntPathRequestMatcher(casLogoutUrl));
//        lf.setFilterProcessesUrl(serviceUrl);
        return lf;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
        auth.authenticationProvider(casAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilter(casAuthenticationFilter());
        http.exceptionHandling().authenticationEntryPoint(casAuthenticationEntryPoint());
        http.addFilterBefore(singleSignOutFilter(), CasAuthenticationFilter.class)
                .addFilterBefore(logoutFilter(), LogoutFilter.class);

        http.csrf().disable();
        http.headers().frameOptions().disable();
//        http.authorizeRequests().antMatchers("/assets/**").permitAll()
//                .anyRequest().authenticated();
        /**
         * 配置请求过滤规则
         */
        http.authorizeRequests()
                //此地址/cas/getuser需要验证
                .antMatchers("/cas/getuser").authenticated()
                //其他任何请求不需验证
                .anyRequest().permitAll()
                //登出不需验证
                .and().logout().permitAll();

        http.logout().logoutUrl(casLogoutUrl)
//                .logoutSuccessUrl(serviceUrl)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
    }
}
