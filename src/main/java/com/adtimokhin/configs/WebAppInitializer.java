package com.adtimokhin.configs;

import org.springframework.core.annotation.Order;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @author adtimokhin
 * 09.04.2021
 **/

@Order(1)
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class, PersistenceConfig.class, SecurityConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        DispatcherServlet dispatcherServlet = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
//        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        // Todo: когда проект будет +/- готов, то раскомментриую эту линию кода сверху. Благодаря ней ошибка 404 перенапрвляется на controlAdvisor
        return dispatcherServlet;
    }

    // Todo: Разобраться с тем, нужно ли делать delete methods или нет. Если да, то этот можно разкомментить.

//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//        super.onStartup(servletContext);
//    }
//
//    private void registerHiddenFieldFilter(ServletContext aContext){
//        aContext.addFilter("hiddenFiledFilter" , new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null, true, "/*");
//    }
}
