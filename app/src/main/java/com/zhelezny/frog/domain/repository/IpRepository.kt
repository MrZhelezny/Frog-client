package com.zhelezny.frog.domain.repository

interface IpRepository {

    fun getIp(): String

    fun saveIp(ip: String)
}