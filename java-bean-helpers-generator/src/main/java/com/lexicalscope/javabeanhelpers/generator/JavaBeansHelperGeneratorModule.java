package com.lexicalscope.javabeanhelpers.generator;

import static com.google.inject.name.Names.named;

import java.io.File;

import com.google.inject.AbstractModule;

public class JavaBeansHelperGeneratorModule extends AbstractModule {
	private final File outputBase;

	public JavaBeansHelperGeneratorModule(final File outputBase) {
		this.outputBase = outputBase;
	}

	@Override
	protected void configure() {
		bind(File.class).annotatedWith(named("outputBase")).toInstance(outputBase);
		bind(String.class).annotatedWith(named("templatesPackage")).toInstance("javaBeansHelpersTemplates");

		bind(HelperGenerator.class).to(HelperGeneratorImpl.class);
		bind(ModelFactory.class).to(ModelFactoryImpl.class);
		bind(OutputSink.class).to(OutputSinkImpl.class);
		bind(OutputFileWriter.class).to(OutputFileWriterImpl.class);
		bind(TemplateFactory.class).to(TemplateFactoryImpl.class);
	}
}
