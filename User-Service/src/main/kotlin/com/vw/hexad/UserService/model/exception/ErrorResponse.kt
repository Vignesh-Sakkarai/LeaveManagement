package com.vw.hexad.UserService.model.exception

class ErrorResponse{
    var errorCode: Int = 0
    var errorMessage: String = ""

    constructor(errorCode:Int, errorMessage: String?){
        this.errorCode = errorCode
        this.errorMessage = errorMessage!!
    }
}