package br.com.microservices.orchestrated.orchestratorservice.core.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import br.com.microservices.orchestrated.orchestratorservice.core.service.OrchestrationService;
import br.com.microservices.orchestrated.orchestratorservice.core.utils.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class SagaOrchestratorConsumer {

    private final JsonUtil jsonUtil;
    private final OrchestrationService orchestrationService;

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${spring.kafka.topic.start-saga}")
    public void consumeStartSagaEvent(String payload) {
        log.info("Receiving event {} from start-saga topic", payload);
        var event = jsonUtil.toEvent(payload);
        orchestrationService.startSaga(event);
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${spring.kafka.topic.orchestrator-saga}")
    public void consumeOrchestratorEvent(String payload) {
        log.info("Receiving event {} from orchestrator topic", payload);
        var event = jsonUtil.toEvent(payload);
        orchestrationService.continueSaga(event);
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${spring.kafka.topic.finish-success}")
    public void consumeFinishSuccessEvent(String payload) {
        log.info("Receiving event {} from finish-success topic", payload);
        var event = jsonUtil.toEvent(payload);
        orchestrationService.finishSagaSuccess(event);
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${spring.kafka.topic.finish-fail}")
    public void consumeFinishFailEvent(String payload) {
        log.info("Receiving event {} from finish-fail topic", payload);
        var event = jsonUtil.toEvent(payload);
        orchestrationService.finishSagaFail(event);
    }
}
