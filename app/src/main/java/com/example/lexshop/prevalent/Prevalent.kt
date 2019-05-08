package com.example.lexshop.prevalent

import com.example.lexshop.model.Users

class Prevalent(val users:Users, val userPhoneKey: String, val userPasswordKey: String)
{
    constructor():this(Users(),"UserPhone","UserPassword")
}