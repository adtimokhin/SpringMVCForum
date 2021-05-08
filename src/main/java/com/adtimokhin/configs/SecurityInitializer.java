package com.adtimokhin.configs;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * @author adtimokhin
 * 10.04.2021
 **/

@Order(2)
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {
}
