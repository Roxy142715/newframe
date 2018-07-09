package com.gws.newframe.services.test.impl;

import com.gws.newframe.common.cache.CacheModule;
import com.gws.newframe.entity.test.TestUser;
import com.gws.newframe.repositories.dataMaster.test.TestUserMaster;
import com.gws.newframe.repositories.dataSlave.test.TestUserSlave;
import com.gws.newframe.services.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author:wangdong
 * @description:Test的原子层接口
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestUserMaster testUserMaster;

    @Autowired
    private TestUserSlave testUserSlave;
    /**
     * 保存的操作
     *
     * @param testUser
     * @return
     */
    @Cacheable(cacheNames = CacheModule.MIN5,
            key = "T(com.gws.newframe.common.cache.CachePrefix).USER_TESTUSER.concat(T(String).valueOf(#testUser.getUid()))")
    @Override
    public TestUser saveTestUserByMaster(TestUser testUser) {
        if (null == testUser){
            return null;
        }

        return testUserMaster.save(testUser);
    }

    /**
     * 根据Uid查找
     *
     * @param uid
     * @return
     */
    @Cacheable(cacheNames = CacheModule.MIN5,
            key = "T(com.gws.newframe.common.cache.CachePrefix).USER_TESTUSER.concat(T(String).valueOf(#uid))")
    @Override
    public Optional<TestUser> getTestUser(Long uid) {
        if (null == uid){
            return null;
        }

        return testUserMaster.findById(uid);
    }

    @Override
    public TestUser saveTestUserBySlave(TestUser testUser) {
        if (null == testUser){
            return null;
        }

        return testUserSlave.save(testUser);
    }
}
