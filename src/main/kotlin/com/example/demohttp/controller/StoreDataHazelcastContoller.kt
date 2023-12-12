package com.example.demohttp.controller

import com.example.demohttp.dto.DummyDto
import com.example.demohttp.service.StoreDataService
import com.hazelcast.core.IMap
import com.hazelcast.core.Member
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/hz")
@Validated
class StoreDataHazelcastContoller(private val storeDataService: StoreDataService) {

    @PostMapping
    fun storeRandomData(@RequestBody prefixMapname: String?): DummyDto {
        return storeDataService.store(prefixMapname)
    }

    @GetMapping
    fun getRandomData(@RequestParam prefixMapname: String?): String? {
        return storeDataService.getData(prefixMapname)
    }

    @GetMapping("/search")
    fun getData(@RequestParam prefixMapname: String?, @RequestParam key: String): String? {
        return storeDataService.getData(prefixMapname, key)
    }

    @GetMapping("/check/cluster")
    fun checkCluster(): Set<Member> {
        return storeDataService.checkCluster()
    }
}