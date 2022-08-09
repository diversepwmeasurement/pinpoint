/*
 * Copyright 2020 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.pinpoint.plugin.kafka;

import com.navercorp.pinpoint.common.Version;
import com.navercorp.pinpoint.pluginit.utils.AgentPath;
import com.navercorp.pinpoint.test.plugin.*;
import com.navercorp.pinpoint.test.plugin.shared.SharedTestLifeCycleClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.pinpoint.plugin.kafka.Kafka2UnitServer;
import test.pinpoint.plugin.kafka.TestProducer;

import java.util.Random;

import static test.pinpoint.plugin.kafka.KafkaITConstants.TRACE_TYPE_MULTI_RECORDS;
import static test.pinpoint.plugin.kafka.KafkaITConstants.TRACE_TYPE_RECORD;


/**
 * @author Younsung Hwang
 */
@RunWith(PinpointPluginTestSuite.class)
@PinpointAgent(AgentPath.PATH)
@PinpointConfig("pinpoint-kafka-client.config")
@ImportPlugin({"com.navercorp.pinpoint:pinpoint-kafka-plugin"})
@Dependency({
        "org.apache.kafka:kafka_2.12:[1.1.0]", "log4j:log4j:[1.2.17]", "commons-io:commons-io:[2.5.0]",
        "org.apache.kafka:kafka-clients:[1.1.0,1.1.max]", "com.navercorp.pinpoint:pinpoint-kafka-it-commons:"+ Version.VERSION
})
@JvmVersion(8)
@SharedTestLifeCycleClass(Kafka2UnitServer.class)
public class KafkaClient_1_1_x_IT extends KafkaClient2ITBase {
    @Test
    public void producerSendTest() throws NoSuchMethodException {
        int messageCount = new Random().nextInt(5) + 1;
        final TestProducer producer = new TestProducer();
        producer.sendMessage(brokerUrl, messageCount);
        KafkaClientITBase.verifyProducerSend(brokerUrl, messageCount);
    }

    @Ignore
    @Test
    public void recordEntryPointTest() throws NoSuchMethodException {
        final TestProducer producer = new TestProducer();
        producer.sendMessage(brokerUrl, 1, TRACE_TYPE_RECORD);
        KafkaClientITBase.verifySingleConsumerEntryPoint(brokerUrl, offset);
    }

    @Test
    public void recordMultiEntryPointTest() throws NoSuchMethodException {
        final TestProducer producer = new TestProducer();
        producer.sendMessage(brokerUrl, 1, TRACE_TYPE_MULTI_RECORDS);
        KafkaClientITBase.verifyMultiConsumerEntryPoint(brokerUrl);
    }

}
