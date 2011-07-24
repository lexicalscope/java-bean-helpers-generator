package com.lexicalscope.javabeanhelpers.generator;

import java.io.File;
import java.io.IOException;

import com.google.inject.Inject;
import com.google.inject.name.Named;

class OutputSinkImpl implements OutputSink {
	private final File outputbase;
	private final OutputFileWriter outputFileWriterProvider;

	@Inject
	public OutputSinkImpl(
			@Named("outputBase") final File outputbase,
			final OutputFileWriter outputFileWriterProvider) {
		this.outputbase = outputbase;
		this.outputFileWriterProvider = outputFileWriterProvider;
	}

	public void output(final GeneratedOutput templateOutput) throws IOException {
		outputFileWriterProvider.write(
				new File(outputbase, toFileName(templateOutput.qualifiedName())),
				templateOutput.content());
	}

	private String toFileName(final String qualifiedName) {
		return qualifiedName.replace(".", "/") + ".java";
	}
}
