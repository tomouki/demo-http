package com.example.demohttp.service

import ch.qos.logback.core.testUtil.RandomUtil
import com.example.demohttp.config.HazelcastConfig
import com.example.demohttp.dto.DummyDto
import com.fasterxml.jackson.databind.ObjectMapper
import com.hazelcast.core.IMap
import com.hazelcast.core.Member
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.stereotype.Service

@Service
class StoreDataService(private val hzConfig: HazelcastConfig) {
    fun store(prefixMapName: String?): DummyDto {
        println("====================================================")
        val text = RandomStringUtils.randomAlphabetic(1000);
        val dummyDto = DummyDto(
                            RandomStringUtils.randomAlphabetic(10),
                            RandomStringUtils.randomAlphabetic(10),
                            RandomStringUtils.randomAlphabetic(10),
                            RandomStringUtils.randomAlphabetic(10),
                            RandomStringUtils.randomAlphabetic(10),
                            RandomStringUtils.randomAlphabetic(500),
                            RandomUtil.getPositiveInt(),
                            RandomStringUtils.randomAlphabetic(10),
                            RandomStringUtils.randomAlphabetic(10),
                            RandomStringUtils.randomAlphabetic(1000)
                        )

        val maaper = ObjectMapper()
        val stringData = maaper.writeValueAsString(dummyDto);
        println("map key is ${dummyDto.username}, value is ${stringData}")

        val dummydataMap = getIMap(prefixMapName)
        dummydataMap.put(dummyDto.username, stringData)
        return dummyDto
    }

    fun getData(prefixMapName: String?): String? {
        println("====================================================")
        val hzInstance = hzConfig.hazelcastInstance()
        println("hazelcast instance name = ${hzInstance.name}")

        val dummydataMap: IMap<String, String> = getIMap(prefixMapName)

        val keys = dummydataMap.keys
        println("Total key count = ${keys.size}")
        var value: String? = null
            if (keys.size > 0) {
            val oneKey = keys.iterator().next()
            value = dummydataMap.get(oneKey)
        }
        println(value)

        return value
    }

    fun getData(prefixMapName: String?, key: String): String? {
        println("====================================================")
        val hzInstance = hzConfig.hazelcastInstance()
        println("hazelcast instance name = ${hzInstance.name}")

        val dummydataMap = getIMap(prefixMapName)

        val value = dummydataMap.get(key)
        println(value)

        return value
    }

    fun checkCluster(): Set<Member> {
        println("====================================================")
        val hzInstance = hzConfig.hazelcastInstance()
        println("hazelcast instance name = ${hzInstance.name}")
        val members: Set<Member> = hzInstance.getCluster().getMembers()
        println("Member size = ${members.size}")
        members.forEach {
            println("  :::: ${it.address}")
        }
        return members
    }

    private fun getIMap(prefixMapName: String?): IMap<String, String> {
        val hzInstance = hzConfig.hazelcastInstance()
        println("hazelcast instance name = ${hzInstance.name}")

        var mapname = "test-data"
        if (prefixMapName?.isEmpty() == false) {
            mapname =  "${prefixMapName}-test-data"
        }
        println("map ns is ${mapname}")
        return hzInstance.getMap(mapname)
    }
}

