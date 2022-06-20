package com.example.demo.service.kafka;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhangjun
 * @date 2021/6/30  13:48
 */
@Component
@Slf4j
public class KafkaConsumer3 {

    @KafkaListener(topics = {"test"}, containerFactory = "kafkaListenerContainerFactory")
    public void consumer(List<ConsumerRecord<String, String>> records, Acknowledgment ack) {
        log.info("size:{}", records.size());
        records.forEach(r -> {
            log.info("value:{}", JSON.toJSONString(r.value()));
        });
        ack.acknowledge();
    }
}
