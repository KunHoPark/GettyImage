package com.konai.cryptox.kotlin.extension


/**
 *  "?id="에 해당하는 value 리턴.
 */
fun String.subStringTagId(tag: String) : String {
    val s = this.indexOf(tag) + tag.length

    return this.substring(s, this.length)
}
