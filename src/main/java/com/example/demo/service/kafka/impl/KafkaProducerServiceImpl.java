package com.example.demo.service.kafka.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.service.kafka.KafkaProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @author zhangjun
 * @date 2021/6/30  13:26
 */
@Service
@Slf4j
public class KafkaProducerServiceImpl implements KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendMsg(Long id, String message) {
        log.info("sendMsg:{}", message);
        try {
            ListenableFuture<SendResult<String, String>> test = kafkaTemplate.send("test", message);
            log.info("test:{}", JSON.toJSONString(test));
        } catch (Exception e) {
            log.error("error:", e);
        }
    }
}
