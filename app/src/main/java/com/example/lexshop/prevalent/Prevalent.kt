package com.example.lexshop.prevalent

import com.example.lexshop.model.Users

class Prevalent(var users:Users, val userPhoneKey: String, val userPasswordKey: String)
{
    constructor():this(Users(),"UserPhone","UserPassword")
}