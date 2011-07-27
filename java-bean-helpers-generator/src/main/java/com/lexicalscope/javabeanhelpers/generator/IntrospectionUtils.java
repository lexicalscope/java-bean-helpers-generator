package com.lexicalscope.javabeanhelpers.generator;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public class IntrospectionUtils {
	private BeanInfo beanPropertyAccess;

	public IntrospectionUtils(final Class<?> klass) {
		try {

			beanPropertyAccess = Introspector.getBeanInfo(klass, Object.class);
		} catch (final IntrospectionException e) {
			throw new RuntimeException("unable to introspect class", e);
		}
	}

	public PropertyDescriptor[] getPropertyDescriptors() {
		return beanPropertyAccess.getPropertyDescriptors();
	}
}
