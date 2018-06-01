package com.myself.wwk.mychat.models

/**
 * Created by wwk on 2018/6/1.
 */
class Users() {
    var user_name: String? = null
    var image: String? = null
    var thumb_image: String? = null
    var status: String? = null

    constructor(user_name: String, image: String, thumb_image: String, status: String): this() {
        this.user_name = user_name
        this.image = image
        this.thumb_image = thumb_image
        this.status = status
    }
}