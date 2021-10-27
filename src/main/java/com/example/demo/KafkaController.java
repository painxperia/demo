package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.example.demo.service.kafka.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangjun
 * @date 2021/6/30  13:24
 */
@RestController
public class KafkaController {
    @Autowired
    private KafkaProducerService kafkaProducerService;

    @PostMapping("/kafka")
    public void send(@RequestBody User user){
        kafkaProducerService.sendMsg(user.getId(),JSON.toJSONString(user));
    }
}
