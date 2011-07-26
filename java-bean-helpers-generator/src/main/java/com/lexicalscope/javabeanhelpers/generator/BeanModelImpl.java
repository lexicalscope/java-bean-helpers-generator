package com.lexicalscope.javabeanhelpers.generator;

public class BeanModelImpl implements BeanModel {
	private final Class<?> klass;

	public BeanModelImpl(final Class<?> klass) {
		this.klass = klass;
	}

	public String getPackage() {
		return klass.getPackage().getName();
	}

	public String getName() {
		return klass.getSimpleName();
	}
}
