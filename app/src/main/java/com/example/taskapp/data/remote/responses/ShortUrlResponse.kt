package com.example.taskapp.data.remote.responses

import com.google.gson.annotations.SerializedName

data class ShortUrlResponse (

  @SerializedName("ok"     ) var ok     : Boolean? = null,
  @SerializedName("result" ) var result : Result?  = Result()

)