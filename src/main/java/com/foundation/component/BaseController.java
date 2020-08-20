package com.foundation.component;

import org.springframework.transaction.TransactionSystemException;

import javax.annotation.Resource;

public class BaseController {

    @Resource(name="foundationTransaction")
    private TransactionManager transactionManager;

    public void beginTransaction (){
        this.transactionManager.beginTransaction();
    }

    public void commit() throws TransactionSystemException {
        this.transactionManager.commit();
    }

    public void rollback() throws TransactionSystemException {
        this.transactionManager.rollback();
    }
}
