/*
 * Copyright 2012-2014 the original author or authors.
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
package org.springframework.data.repository.init;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.FactoryBean;

/**
 * {@link FactoryBean} to set up a {@link ResourceReaderRepositoryPopulator} with a {@link JacksonResourceReader}.
 * 
 * @deprecated use {@link Jackson2RepositoryPopulatorFactoryBean} instead
 * @author Oliver Gierke
 */
@Deprecated
public class JacksonRepositoryPopulatorFactoryBean extends AbstractRepositoryPopulatorFactoryBean {

	private ObjectMapper mapper;

	/**
	 * Configures the {@link ObjectMapper} to be used.
	 * 
	 * @param mapper
	 */
	public void setMapper(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.data.repository.init.AbstractRepositoryPopulatorFactoryBean#getResourceReader()
	 */
	@Override
	protected ResourceReader getResourceReader() {
		return new JacksonResourceReader(mapper);
	}
}
