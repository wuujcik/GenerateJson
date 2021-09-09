package com.wuujcik.generatejson

import com.wuujcik.generator.GenJson

@GenJson
data class ContactDetails(
    val emailAddress: String,
    val phoneNumber: String
)
