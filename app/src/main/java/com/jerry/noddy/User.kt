package com.jerry.noddy

data class User(val userId:String,val title:String,val description:String,val date:String,val color:Int){
    constructor(): this("","","","",0){

    }
}
