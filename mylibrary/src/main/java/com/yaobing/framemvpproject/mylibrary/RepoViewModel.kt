package com.yaobing.framemvpproject.mylibrary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaobing.framemvpproject.mylibrary.api.RepoAPI
import com.yaobing.module_middleware.PresenterRouter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepoViewModel : ViewModel() {
    var data = MutableLiveData<Any>()

    fun loadData(presenterRouter: PresenterRouter,name:String,map:java.util.HashMap<String,Any>){
        viewModelScope.launch(Dispatchers.Main) {
            try {
                data.postValue(presenterRouter.create(RepoAPI::class.java).getGithubReposByPages(name,map))
            }catch (e:Exception){
                data.postValue(data.value)
            }
        }
    }
}