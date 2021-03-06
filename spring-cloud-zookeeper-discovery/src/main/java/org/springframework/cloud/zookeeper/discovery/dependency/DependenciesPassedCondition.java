/*
 * Copyright 2013-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.cloud.zookeeper.discovery.dependency;

import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Condition that verifies if the Dependencies have been passed in an appropriate
 * place in the application properties.
 *
 * @author Marcin Grzejszczak
 * @since 1.0.0
 */
public class DependenciesPassedCondition extends SpringBootCondition {

	private static final String ZOOKEEPER_DEPENDENCIES_PROP = "spring.cloud.zookeeper.dependencies";

	@Override
	public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Map<String, Object> subProperties = new RelaxedPropertyResolver(context.getEnvironment()).getSubProperties(ZOOKEEPER_DEPENDENCIES_PROP);
		return subProperties.isEmpty() ?
				ConditionOutcome.noMatch("No dependencies have been passed for the service") :
				ConditionOutcome.match();
	}

}
