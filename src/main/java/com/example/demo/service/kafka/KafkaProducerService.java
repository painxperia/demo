package com.example.demo.service.kafka;

/**
 * @author zhangjun
 * @date 2021/6/30  13:25
 */
public interface KafkaProducerService {

    void sendMsg(Long id,String message);

}
