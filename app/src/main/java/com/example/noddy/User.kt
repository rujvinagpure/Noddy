package com.example.noddy

data class User(val UserId:String,val title:String,val description:String,val Date:String,val color:Int){
    constructor(): this("","","","",0){

    }
}
