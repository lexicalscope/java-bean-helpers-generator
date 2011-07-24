package com.lexicalscope.javabeanhelpers.generator;

import java.io.IOException;
import java.util.List;

interface TemplateFactory {
	List<Template> createTemplates() throws IOException;
}
