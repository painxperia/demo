package com.example.demo.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @author zhangjun
 * @date 2021/6/30  13:48
 */
@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = {"test"},groupId = "test1",containerFactory = "kafkaListenerContainerFactory")
    public void consumer(ConsumerRecord<?, ?> record, Acknowledgment ack) {
        String topic = record.topic();
        int partition = record.partition();
        Object value = record.value();
        long offset = record.offset();
        log.info("topic:{},partition:{},offset:{},value:{}", topic, partition,offset, value);
        ack.acknowledge();
    }
}
