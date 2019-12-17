/*
 * Copyright 2018-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import org.assertj.core.api.BDDAssertions;
import org.awaitility.Awaitility;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
// remove::start[]
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.kafka.test.context.EmbeddedKafka;
// remove::end[]
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
// remove::start[]
@AutoConfigureStubRunner
@EmbeddedKafka(topics = "holly-ilm-service")
// remove::end[]
@ActiveProfiles("test")
public class ApplicationTests {

	// remove::start[]
	@Autowired
	StubTrigger trigger;
	@Autowired
	App application;

	@Test
	public void contextLoads() {
		trigger.trigger("hollyIlmDatasetPurgedMax");

		Awaitility.await().untilAsserted(() -> {
			BDDAssertions.then(this.application.storedFoo).isNotNull();
			BDDAssertions.then(this.application.storedFoo.getFoo()).contains("holly");
		});
	}
	// remove::end[]

}
