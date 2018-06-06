package com.myself.wwk.mychat.models

/**
 * Created by wwk on 2018/6/5.
 */
class FriendlyMessage() {

    var id: String? = null
    var text: String? = null
    var name: String? = null

    constructor(id: String, text: String, name: String): this() {
        this.id = id
        this.text = text
        this.name = name
    }

}