package com.yaobing.framemvpproject.mylibrary.data

import com.google.gson.annotations.SerializedName

class RepoResponse {
    @SerializedName("items") val items: List<RepoData> = emptyList()
}