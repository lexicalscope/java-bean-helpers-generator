package com.lexicalscope.javabeanhelpers.generator;

import java.io.IOException;

interface OutputSink {
	void output(GeneratedOutput templateOutput) throws IOException;
}
