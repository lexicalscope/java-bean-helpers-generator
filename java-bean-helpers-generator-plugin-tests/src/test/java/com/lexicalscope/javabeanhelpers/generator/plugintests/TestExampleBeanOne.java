package com.lexicalscope.javabeanhelpers.generator.plugintests;

import static org.fest.reflect.core.Reflection.method;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Ignore;
import org.junit.Test;

public class TestExampleBeanOne {
	@Test
	public void beanBuilderIsCreated() {
		assertThat(
				ExampleBeanOneBuilder.class.getName(),
				equalTo("com.lexicalscope.javabeanhelpers.generator.plugintests.ExampleBeanOneBuilder"));
	}

	@Test
	@Ignore
	public void readWriteAttributesIsSettableInBuilder() {
		assertThat(method("withSimpleReadWriteProperty").
						withReturnType(ExampleBeanOneBuilder.class).
						withParameterTypes(String.class).
						in(ExampleBeanOneBuilder.class),
					notNullValue());
	}
}
