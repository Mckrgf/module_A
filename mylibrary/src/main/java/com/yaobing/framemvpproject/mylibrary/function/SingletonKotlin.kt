package com.yaobing.framemvpproject.mylibrary.function

/**
 * @author : yaobing
 * @date   : 2022/11/11 16:12
 * @desc   :
 */
//object SingletonKotlin  这一行就是饿汉的实现方式
class SingletonKotlin private constructor() {
    companion object {
        private var singleton: SingletonKotlin? = null
            get() {
                if (field == null) {
                    field = SingletonKotlin()
                }
                return field
            }

        fun getSingle () : SingletonKotlin{
            return singleton!!
        }
    }


}