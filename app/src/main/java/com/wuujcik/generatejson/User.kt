package com.wuujcik.generatejson

import com.wuujcik.generator.GenJson

@GenJson
data class User(
    val firstName: String,
    val lastName: String,
    val position: String,
    val company: String
)
