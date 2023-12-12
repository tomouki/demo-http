package com.example.demohttp.config

import com.hazelcast.client.HazelcastClient
import com.hazelcast.client.config.ClientConfig
import com.hazelcast.core.HazelcastInstance
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*
import kotlin.collections.ArrayList

@Configuration
class HazelcastConfig {
    @Value("\${hz.hosts}")
    private lateinit var hzUrls: List<String>

    @Value("\${hz.pool.size ?: 1}")
    private val poolSize = 0

    @Value("\${hz.instance.size ?: 1}")
    private val instanceSize = 0


    val instanceList = ArrayList<HazelcastInstance>()

    fun hazelcastInstance(): HazelcastInstance {
        val random = Random()
        val num = random.nextInt(instanceSize)

        return instanceList[num]
    }

    @Bean
    fun hazelcastInstanceList(): List<HazelcastInstance> {
        for (i in 1..instanceSize) {
            instanceList.add(createHazelInstance())
        }
        return instanceList
    }

    private fun createHazelInstance() : HazelcastInstance {
        val config = ClientConfig()
        config.networkConfig
            .setAddresses(hzUrls)
            .setSmartRouting(false)

        config.setExecutorPoolSize(poolSize)
        return HazelcastClient.newHazelcastClient(config)
    }
}