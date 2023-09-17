package com.yaobing.framemvpproject.mylibrary.api;

import com.yaobing.module_apt.ContractFactory;

import java.util.ArrayList;

/**
 * @author : yaobing
 * @date : 2020/10/30 15:21
 * @desc :
 */
@ContractFactory(entites = {ArrayList.class})
public interface RepoAPI {
    void getAllRepoByName(String name);
}
