package com.zhelezny.frog.data.storage

interface IpStorage {

    fun getIp(): String

    fun saveIp(ip: String)
}