package com.lexicalscope.javabeanhelpers.generator;

import static org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang.builder.HashCodeBuilder.reflectionHashCode;

class GeneratedOutputImpl implements GeneratedOutput {
	private final String name;
	private final String content;

	public GeneratedOutputImpl(final String name, final String content) {
		this.name = name;
		this.content = content;
	}

	public String qualifiedName() {
		return name;
	}

	public String content() {
		return content;
	}

	@Override
	public int hashCode() {
		return reflectionHashCode(this);
	}

	@Override
	public boolean equals(final Object that) {
		return reflectionEquals(this, that);
	}
}
