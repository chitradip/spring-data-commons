/*
 * Copyright 2013 the original author or authors.
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
package org.springframework.data.repository.support;

import static org.mockito.Mockito.*;

import java.io.Serializable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.core.CrudInvoker;
import org.springframework.data.repository.core.CrudMethods;

/**
 * Unit tests for {@link ReflectionRepositoryInvoker}.
 * 
 * @author Oliver Gierke
 */
@RunWith(MockitoJUnitRunner.class)
public class ReflectionRepositoryInvokerUnitTests {

	@Mock CrudRepository<Object, Serializable> repo;
	@Mock CrudMethods methods;

	@Test
	public void createsInvokerForRepositoryExposingBothFindAllAndSaveMethod() {

		when(methods.hasFindOneMethod()).thenReturn(true);
		when(methods.hasSaveMethod()).thenReturn(true);

		new ReflectionRepositoryInvoker<Object>(repo, methods);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsNullRepository() {

		when(methods.hasFindOneMethod()).thenReturn(true);
		when(methods.hasSaveMethod()).thenReturn(true);

		new ReflectionRepositoryInvoker<Object>(null, methods);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsRepositoryIfItDoesntExposeAFindOneMethod() {

		when(methods.hasFindOneMethod()).thenReturn(false);
		when(methods.hasSaveMethod()).thenReturn(true);

		new ReflectionRepositoryInvoker<Object>(repo, methods);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsRepositoryIfItDoesntExposeASaveMethod() {

		when(methods.hasFindOneMethod()).thenReturn(true);
		when(methods.hasSaveMethod()).thenReturn(false);

		new ReflectionRepositoryInvoker<Object>(repo, methods);
	}

	/**
	 * @see DATACMNS-410
	 */
	@Test
	public void invokesFindOneCorrectly() throws Exception {

		when(methods.hasFindOneMethod()).thenReturn(true);
		when(methods.hasSaveMethod()).thenReturn(true);
		when(methods.getFindOneMethod()).thenReturn(CrudRepository.class.getMethod("findOne", Serializable.class));

		CrudInvoker<Object> invoker = new ReflectionRepositoryInvoker<Object>(repo, methods);
		invoker.invokeFindOne(1L);

		verify(repo, times(1)).findOne(1L);
	}

	/**
	 * @see DATACMNS-410
	 */
	@Test
	public void invokesSaveCorrectly() throws Exception {

		when(methods.hasFindOneMethod()).thenReturn(true);
		when(methods.hasSaveMethod()).thenReturn(true);
		when(methods.getSaveMethod()).thenReturn(CrudRepository.class.getMethod("save", Object.class));

		CrudInvoker<Object> invoker = new ReflectionRepositoryInvoker<Object>(repo, methods);
		Object object = new Object();
		invoker.invokeSave(object);

		verify(repo, times(1)).save(object);
	}
}
