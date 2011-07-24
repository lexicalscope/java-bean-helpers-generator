package com.lexicalscope.javabeanhelpers.generator;

import java.io.IOException;
import java.util.logging.Logger;

import com.google.inject.Inject;

class HelperGeneratorImpl implements HelperGenerator {
	private final Logger logger;
	private final ModelFactory modelFactory;
	private final TemplateFactory templateFactory;
	private final OutputSink outputSink;

	@Inject
	HelperGeneratorImpl(
			final Logger logger,
			final ModelFactory modelFactory,
			final TemplateFactory templateFactory,
			final OutputSink outputSink) {
		this.logger = logger;
		this.modelFactory = modelFactory;
		this.templateFactory = templateFactory;
		this.outputSink = outputSink;
	}

	public void generateHelpers(final Class<?> klass) throws IOException {
		final BeanModel beanModel = modelFactory.createModel(klass);
		for (final Template template : templateFactory.createTemplates()) {
			logger.info(String.format("merging %s with %s", beanModel, template));
			outputSink.output(template.merge(beanModel));
		}
	}
}
