package com.example.taskapp.data.remote.responses

import com.google.gson.annotations.SerializedName

data class Result (

  @SerializedName("code"             ) var code           : String? = null,
  @SerializedName("short_link"       ) var shortLink      : String? = null,
  @SerializedName("full_short_link"  ) var fullShortLink  : String? = null,
  @SerializedName("short_link2"      ) var shortLink2     : String? = null,
  @SerializedName("full_short_link2" ) var fullShortLink2 : String? = null,
  @SerializedName("share_link"       ) var shareLink      : String? = null,
  @SerializedName("full_share_link"  ) var fullShareLink  : String? = null,
  @SerializedName("original_link"    ) var originalLink   : String? = null

)