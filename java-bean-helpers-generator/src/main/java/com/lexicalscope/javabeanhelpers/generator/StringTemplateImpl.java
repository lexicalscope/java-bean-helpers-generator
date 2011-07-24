package com.lexicalscope.javabeanhelpers.generator;

import org.stringtemplate.v4.ST;

class StringTemplateImpl implements StringTemplate {
	private final ST st;

	public StringTemplateImpl(final ST st) {
		this.st = st;
	}

	public String render() {
		return st.render();
	}
}
