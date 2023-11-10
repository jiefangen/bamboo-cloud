package org.panda.support.cloud.seata.action;

import io.seata.rm.tcc.api.BusinessActionContext;

/**
 * TCC操作
 */
public interface TccAction {

    boolean commit(BusinessActionContext actionContext);

    boolean rollback(BusinessActionContext actionContext);

}
