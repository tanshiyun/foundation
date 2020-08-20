package com.foundation.component;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionManager implements InitializingBean {

    private final ThreadLocal<TransactionStatus> transactionThread = new ThreadLocal<>();

    private final DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();

    //事务管理器
    private PlatformTransactionManager transactionManager;

    //默认事务隔离级别--读已提交
    private String isolation = "ISOLATION_READ_COMMITTED";

    //默认事务传播行为
    private String propagation = "PROPAGATION_REQUIRED";


    /**
     * 开启事务
     */
    public void beginTransaction() {
        TransactionStatus transactionStatus = this.transactionManager.getTransaction(this.transactionDefinition);
        this.transactionThread.set(transactionStatus);
    }


    /**
     * 提交事务
     */
    public void commit() {
        TransactionStatus transactionStatus = this.transactionThread.get();

        if(null == transactionStatus){
            throw new TransactionSystemException("no available transaction");
        }

        this.transactionManager.commit(transactionStatus);
        this.transactionThread.remove();
    }


    /**
     * 回滚事务
     */
    public void rollback() {
        TransactionStatus transactionStatus = this.transactionThread.get();

        if(null == transactionStatus){
            throw new TransactionSystemException("no available transaction");
        }

        this.transactionManager.rollback(transactionStatus);
        this.transactionThread.remove();
    }


    public void afterPropertiesSet() {
        this.transactionDefinition.setIsolationLevelName(this.isolation);
        this.transactionDefinition.setPropagationBehaviorName(this.propagation);
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setIsolation(String isolation) {
        this.isolation = isolation;
    }

    public void setPropagation(String propagation) {
        this.propagation = propagation;
    }
}
