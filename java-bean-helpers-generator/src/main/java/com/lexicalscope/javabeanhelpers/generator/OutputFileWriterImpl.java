package com.lexicalscope.javabeanhelpers.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class OutputFileWriterImpl implements OutputFileWriter {
	public void write(final File file, final String content) throws IOException {
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		final FileWriter fileWriter = new FileWriter(file);
		try {
			fileWriter.write(content);
			fileWriter.flush();
		} finally {
			fileWriter.close();
		}
	}
}
