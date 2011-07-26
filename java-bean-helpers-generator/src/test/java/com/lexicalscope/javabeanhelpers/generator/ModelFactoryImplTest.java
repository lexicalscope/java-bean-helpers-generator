package com.lexicalscope.javabeanhelpers.generator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class ModelFactoryImplTest {
	private final StringTemplate nameTemplate = mock(StringTemplate.class);
	private final StringTemplateFactory nameTemplateFactory = mock(StringTemplateFactory.class);

	private final StringTemplate contentTemplate = mock(StringTemplate.class);
	private final StringTemplateFactory contentTemplateFactory = mock(StringTemplateFactory.class);

	private BeanModel beanModel;

	@Test
	public void templateContentAndNameAreRendered() throws Exception {

		when(nameTemplateFactory.create(beanModel)).thenReturn(nameTemplate);
		when(contentTemplateFactory.create(beanModel)).thenReturn(contentTemplate);
		when(nameTemplate.render()).thenReturn("My Name");
		when(contentTemplate.render()).thenReturn("My Content");

		final BeanModel output = new ModelFactoryImpl().createModel(ExampleBeanOne.class);

		assertThat(output.getPackage(), equalTo("com.lexicalscope.javabeanhelpers.generator"));
	}
}
