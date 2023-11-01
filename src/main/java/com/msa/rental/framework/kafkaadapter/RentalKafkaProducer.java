package com.msa.rental.framework.kafkaadapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.msa.rental.application.outputport.EventOutputPort;
import com.msa.rental.domain.model.event.ItemRented;
import com.msa.rental.domain.model.event.ItemReturned;
import com.msa.rental.domain.model.event.OverdueCleared;
import com.msa.rental.domain.model.event.PointUseCommand;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@RequiredArgsConstructor
public class RentalKafkaProducer implements EventOutputPort {



    @Value(value = "${producers.topic1.name}")
    private  String TOPIC_RENT;
    @Value(value = "${producers.topic2.name}")
    private  String TOPIC_RETURN;
    @Value(value = "${producers.topic3.name}")
    private  String TOPIC_CLEAR;
    @Value(value = "${producers.topic4.name}")
    private  String TOPIC_POINT;

    private final KafkaTemplate<String, ItemRented> kafkaTemplate1;
    private final KafkaTemplate<String, ItemReturned> kafkaTemplate2;
    private final KafkaTemplate<String, OverdueCleared> kafkaTemplate3;
    private final KafkaTemplate<String, PointUseCommand> kafkaTemplate4;

    @Override
    public void occurRentalEvent(ItemRented itemRented) throws JsonProcessingException {
        ListenableFuture<SendResult<String, ItemRented>> future = kafkaTemplate1.send(TOPIC_RENT, itemRented);

        future.addCallback(new ListenableFutureCallback<SendResult<String, ItemRented>>() {

            private final Logger LOGGER = LoggerFactory.getLogger(RentalKafkaProducer.class);

            @Override
            public void onSuccess(SendResult<String, ItemRented> result) {
                ItemRented g = result.getProducerRecord().value();
                LOGGER.info("Sent message=[" + g.getItem().getNo() + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                LOGGER.error( "Unable to send message=[" + itemRented.getItem().getNo() + "] due to : " + ex.getMessage(), ex);
            }

        });
    }

    @Override
    public void occurReturnEvent(ItemReturned itemReturned) throws JsonProcessingException {
        ListenableFuture<SendResult<String, ItemReturned>> future = this.kafkaTemplate2.send(TOPIC_RETURN, itemReturned);
        future.addCallback(new ListenableFutureCallback<SendResult<String, ItemReturned>>() {
            private final Logger LOGGER = LoggerFactory.getLogger(RentalKafkaProducer.class);

            @Override
            public void onSuccess(SendResult<String, ItemReturned> result) {
                ItemReturned g = result.getProducerRecord().value();
                LOGGER.info("Sent message=[" + g.getItem().getNo() + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable t) {
                // needed to do compensation transaction.
                LOGGER.error( "Unable to send message=[" + itemReturned.getItem().getNo() + "] due to : " + t.getMessage(), t);
            }
        });
    }

    @Override
    public void occurOverdueClearedEvent(OverdueCleared overdueCleared) throws JsonProcessingException {
        ListenableFuture<SendResult<String, OverdueCleared>> future = this.kafkaTemplate3.send(TOPIC_CLEAR, overdueCleared);
        future.addCallback(new ListenableFutureCallback<SendResult<String, OverdueCleared>>() {
            private final Logger LOGGER = LoggerFactory.getLogger(RentalKafkaProducer.class);

            @Override
            public void onSuccess(SendResult<String, OverdueCleared> result) {
                OverdueCleared g = result.getProducerRecord().value();
                LOGGER.info("Sent message=[" + g.getIdName().getId() + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable t) {
                // needed to do compensation transaction.
                LOGGER.error( "Unable to send message=[" + overdueCleared.getIdName().getId() + "] due to : " + t.getMessage(), t);
            }
        });
    }

    @Override
    public void occurPointUseCommand(PointUseCommand pointUseCommand) {
        ListenableFuture<SendResult<String, PointUseCommand>> future = this.kafkaTemplate4.send(TOPIC_POINT, pointUseCommand);

        future.addCallback(new ListenableFutureCallback<SendResult<String, PointUseCommand>>() {
            private final Logger LOGGER = LoggerFactory.getLogger(RentalKafkaProducer.class);

            @Override
            public void onSuccess(SendResult<String, PointUseCommand> result) {
                PointUseCommand g = result.getProducerRecord().value();
                LOGGER.info("Sent message=[" + g.getIdName().getId() + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable t) {
                // needed to do compensation transaction.
                LOGGER.error( "Unable to send message=[" + pointUseCommand.getIdName().getId() + "] due to : " + t.getMessage(), t);
            }
        });

    }
}
