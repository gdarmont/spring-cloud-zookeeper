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

package org.springframework.cloud.zookeeper.discovery;

import org.springframework.cloud.client.discovery.AbstractDiscoveryLifecycle;
import org.springframework.util.ReflectionUtils;

/**
 * Zookeeper {@link org.springframework.cloud.client.discovery.DiscoveryLifecycle}
 * that uses {@link ZookeeperServiceDiscovery} to register and de-register instances.
 *
 * @author Spencer Gibb
 * @since 1.0.0
 */
public class ZookeeperLifecycle extends AbstractDiscoveryLifecycle {

	private ZookeeperDiscoveryProperties properties;
	private ZookeeperServiceDiscovery serviceDiscovery;

	public ZookeeperLifecycle(ZookeeperDiscoveryProperties properties,
			ZookeeperServiceDiscovery serviceDiscovery) {
		this.properties = properties;
		this.serviceDiscovery = serviceDiscovery;
	}

	@Override
	protected void register() {
		try {
			this.serviceDiscovery.getServiceDiscovery().start();
		}
		catch (Exception e) {
			ReflectionUtils.rethrowRuntimeException(e);
		}
	}

	// TODO: implement registerManagement

	@Override
	protected void deregister() {
		try {
			this.serviceDiscovery.getServiceDiscovery().unregisterService(
					this.serviceDiscovery.getServiceInstance());
		}
		catch (Exception e) {
			ReflectionUtils.rethrowRuntimeException(e);
		}
	}

	// TODO: implement deregisterManagement

	@Override
	protected boolean isEnabled() {
		return this.properties.isEnabled();
	}

	@Override
	protected int getConfiguredPort() {
		return this.serviceDiscovery.getPort();
	}

	@Override
	protected void setConfiguredPort(int port) {
		this.serviceDiscovery.setPort(port);
		this.serviceDiscovery.build();
	}

	@Override
	protected Object getConfiguration() {
		return this.properties;
	}
}
