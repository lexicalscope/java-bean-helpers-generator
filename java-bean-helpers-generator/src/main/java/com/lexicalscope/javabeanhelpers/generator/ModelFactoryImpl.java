package com.lexicalscope.javabeanhelpers.generator;

public class ModelFactoryImpl implements ModelFactory {
	public BeanModel createModel(final Class<?> klass) {
		return new BeanModelImpl(klass);
	}
}
