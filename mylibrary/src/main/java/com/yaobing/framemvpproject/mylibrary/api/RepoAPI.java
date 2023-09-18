package com.yaobing.framemvpproject.mylibrary.api;

import com.yaobing.module_apt.ContractFactory;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Single;

/**
 * @author : yaobing
 * @date : 2020/10/30 15:21
 * @desc :
 */
@ContractFactory(entites = {ArrayList.class,ArrayList.class})
public interface RepoAPI {
    void getAllRepoByName(String name);
    Object getGithubReposByPages(String name, HashMap<String,Object> page_data);
}
