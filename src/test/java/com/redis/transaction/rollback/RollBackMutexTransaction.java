package com.redis.transaction.rollback;

import com.redis.transaction.GameTransactionCauseImpl;
import com.redis.transaction.GameTransactionEntityCauseImpl;
import com.redis.transaction.GameTransactionEntityFactoryImpl;
import com.redis.transaction.RedisKey;
import com.redis.transaction.enums.GameTransactionCommitResult;
import com.redis.transaction.service.ConfigService;
import com.redis.transaction.service.RedisService;
import com.redis.transaction.service.TransactionService;
import com.redis.transaction.service.TransactionServiceImpl;

/**
 * Created by jiangwenping on 16/12/28.
 */
public class RollBackMutexTransaction {
    public static void main(String[] args) throws Exception{
        ConfigService configService = new ConfigService();
        RedisService redisService = new RedisService();
        redisService.setJedisPool(configService.initRedis(configService.initRediPoolConfig()));

        TransactionService transactionService = new TransactionServiceImpl();
        String union = "union";
        RollbackMutexEntity rollbackMutexEntity = GameTransactionEntityFactoryImpl.createRollbackMutexEntityy(GameTransactionEntityCauseImpl.rollback, redisService, RedisKey.player, union);
        GameTransactionCommitResult commitResult = transactionService.commitTransaction(GameTransactionCauseImpl.rollback, rollbackMutexEntity);
        System.out.println(commitResult.getReuslt());
    }
}
