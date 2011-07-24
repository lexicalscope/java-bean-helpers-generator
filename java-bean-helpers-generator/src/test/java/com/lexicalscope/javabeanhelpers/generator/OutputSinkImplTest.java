package com.lexicalscope.javabeanhelpers.generator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.Test;

public class OutputSinkImplTest {
	private final OutputFileWriter outputFileWriter = mock(OutputFileWriter.class);
	private final GeneratedOutput templateOutput = mock(GeneratedOutput.class);

	@Test
	public void contentOfTemplateIsWrittentToFileWithCorrectName() throws Exception {
		final File outputbase = new File(".");

		when(templateOutput.qualifiedName()).thenReturn("test.package.TestClass");
		when(templateOutput.content()).thenReturn("My Content");

		new OutputSinkImpl(outputbase, outputFileWriter).output(templateOutput);

		verify(outputFileWriter).write(new File(outputbase, "test/package/TestClass.java"), "My Content");
	}
}
