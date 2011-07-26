package com.lexicalscope.javabeanhelpers.generator.plugintests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class TestExampleBeanOne {
	@Test
	public void testApp() {
		assertThat(
				ExampleBeanOneBuilder.class.getName(),
				equalTo("com.lexicalscope.javabeanhelpers.generator.plugintests.ExampleBeanOneBuilder"));
	}
}
