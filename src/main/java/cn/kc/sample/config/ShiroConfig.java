package cn.kc.sample.config;

import cn.kc.platform.account.service.AccountService;
import cn.kc.sample.shiro.KcAuthenticationListener;
import cn.kc.sample.shiro.ShiroDbRealm;
import com.google.common.collect.Lists;
import org.apache.shiro.authc.AuthenticationListener;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.util.List;

/**
 * Created by dhm on 14-12-13.
 */

@Configuration
@ImportResource(value = "classpath:spring-shiro-context.xml")
public class ShiroConfig {



    @Bean
    public AccountService accountService() {
        return new AccountService();
    }

    @Bean
    public PasswordService passwordService(){
        return new DefaultPasswordService();
    }

    @Bean
    public CredentialsMatcher passwordMatcher(){
        return new PasswordMatcher();
    }
    @Bean
    public Realm shiroDbRealm() {
        ShiroDbRealm shiroFilterFactoryBean = new ShiroDbRealm();
        shiroFilterFactoryBean.setAccountService(accountService());
        shiroFilterFactoryBean.setCredentialsMatcher(passwordMatcher());
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
        webSecurityManager.setRealm(shiroDbRealm());
        ModularRealmAuthenticator realmAuthenticator = new ModularRealmAuthenticator();
        List<AuthenticationListener> listeners = Lists.newArrayList();
        listeners.add(new KcAuthenticationListener());
        realmAuthenticator.setAuthenticationListeners(listeners);
        webSecurityManager.setAuthenticator(realmAuthenticator);
        return webSecurityManager;
    }

}
