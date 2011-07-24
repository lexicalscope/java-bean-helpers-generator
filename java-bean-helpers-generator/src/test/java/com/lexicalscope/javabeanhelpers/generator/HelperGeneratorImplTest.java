package com.lexicalscope.javabeanhelpers.generator;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.logging.Logger;

import org.junit.Test;

public class HelperGeneratorImplTest {
	public static interface ExampleBean {
	}

	private final Logger logger = mock(Logger.class);
	private final ModelFactory modelFactory = mock(ModelFactory.class);
	private final TemplateFactory templateFactory = mock(TemplateFactory.class);
	private final OutputSink outputSink = mock(OutputSink.class);

	private final BeanModel beanModel = mock(BeanModel.class);
	private final Template template1 = mock(Template.class);
	private final Template template2 = mock(Template.class);

	private final GeneratedOutput templateOutputWithT1 = mock(GeneratedOutput.class);
	private final GeneratedOutput templateOutputWithT2 = mock(GeneratedOutput.class);

	@Test
	public void modelsAreMergedWithTemplates() throws IOException {
		when(modelFactory.createModel(ExampleBean.class)).thenReturn(beanModel);
		when(templateFactory.createTemplates()).thenReturn(asList(template1, template2));
		when(template1.merge(beanModel)).thenReturn(templateOutputWithT1);
		when(template2.merge(beanModel)).thenReturn(templateOutputWithT2);

		final HelperGeneratorImpl helperGenerator =
				new HelperGeneratorImpl(logger, modelFactory, templateFactory, outputSink);
		helperGenerator.generateHelpers(ExampleBean.class);

		verify(outputSink).output(templateOutputWithT1);
		verify(outputSink).output(templateOutputWithT2);
	}
}
