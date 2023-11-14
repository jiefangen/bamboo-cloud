package org.panda.support.cloud.example.service.tcc;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @LocalTCC 标记这是一个TCC资源，缺少该注解事务会以AT模式运行，不会调用到commit和rollback方法
 * @TwoPhaseBusinessAction 注解标记这是个TCC接口，同时指定commitMethod，rollbackMethod的名称
 * @BusinessActionContext 是TCC事务中的上下文对象
 * @BusinessActionContextParameter 注解标记的参数会在上下文中传播，即能通过BusinessActionContext对象在commit方法及rollback方法中取到该参数值
 */
@LocalTCC
public interface DeductTccAction {

    @TwoPhaseBusinessAction(name = "DeductTccAction")
    boolean prepare(BusinessActionContext actionContext,
                    @BusinessActionContextParameter(paramName = "uid") int uid,
                    @BusinessActionContextParameter(paramName = "amount")double amount);

    boolean commit(BusinessActionContext actionContext);

    boolean rollback(BusinessActionContext actionContext);

}
