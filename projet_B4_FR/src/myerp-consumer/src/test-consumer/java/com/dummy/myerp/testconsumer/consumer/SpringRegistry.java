package com.dummy.myerp.testconsumer.consumer;

import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public final class SpringRegistry {

    private static final Logger LOGGER = LogManager.getLogger(SpringRegistry.class);
    private static final SpringRegistry INSTANCE = new SpringRegistry();
    private static final String CONTEXT_APPLI_LOCATION = "classpath:/bootstrapContext.xml";


    private ApplicationContext contextAppli;

    private SpringRegistry() {

        super();

        SpringRegistry.LOGGER.debug("[DEBUT] SpringRegistry() - Initialisation du contexte Spring");
        this.contextAppli = new ClassPathXmlApplicationContext(SpringRegistry.CONTEXT_APPLI_LOCATION);
        SpringRegistry.LOGGER.debug("[FIN] SpringRegistry() - Initialisation du contexte Spring");
    }

    protected static final SpringRegistry getInstance() {
        return SpringRegistry.INSTANCE;
    }



    public static final ApplicationContext init() {
        return getInstance().contextAppli;
    }


    protected static Object getBean(String pBeanId) {
        SpringRegistry.LOGGER.debug("[DEBUT] SpringRegistry.getBean() - Bean ID : " + pBeanId);
        Object vBean = SpringRegistry.getInstance().contextAppli.getBean(pBeanId);
        SpringRegistry.LOGGER.debug("[FIN] SpringRegistry.getBean() - Bean ID : " + pBeanId);
        return vBean;
    }

    public static DaoProxy getDaoProxy() {
        return (DaoProxy) SpringRegistry.getBean("daoProxy");
    }
}
