package com.lexicalscope.javabeanhelpers.generator;

import java.io.File;
import java.io.IOException;

interface OutputFileWriter {
	void write(File file, String content) throws IOException;
}
