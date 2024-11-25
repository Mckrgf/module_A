package com.yaobing.framemvpproject.mylibrary.data

data class Student  constructor(var name: String, var age: Int) {
    //生成所有的构造函数

    constructor(name: String) : this(name, 0)
}
