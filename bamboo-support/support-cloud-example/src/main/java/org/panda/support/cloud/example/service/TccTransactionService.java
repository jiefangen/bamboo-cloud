package org.panda.support.cloud.example.service;

import java.util.Map;

public interface TccTransactionService {

    String doTransactionCommit();

    String doTransactionRollback(Map map);
}
