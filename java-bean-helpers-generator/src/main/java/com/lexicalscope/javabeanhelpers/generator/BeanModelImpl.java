package com.lexicalscope.javabeanhelpers.generator;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

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

	public List<AttributeModel> getAttributes() {
		final IntrospectionUtils introspectionUtils = new IntrospectionUtils(klass);

		return wrap(introspectionUtils.getPropertyDescriptors());
	}

	private List<AttributeModel> wrap(final PropertyDescriptor[] propertyDescriptors) {
		final List<AttributeModel> result = new ArrayList<AttributeModel>(propertyDescriptors.length);
		for (final PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			result.add(new AttributeModelImpl(this, propertyDescriptor));
		}
		return result;
	}
}
