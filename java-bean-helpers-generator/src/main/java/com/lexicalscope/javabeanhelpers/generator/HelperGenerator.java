package com.lexicalscope.javabeanhelpers.generator;

import java.io.IOException;

public interface HelperGenerator {
	void generateHelpers(Class<?> klass) throws IOException;
}
