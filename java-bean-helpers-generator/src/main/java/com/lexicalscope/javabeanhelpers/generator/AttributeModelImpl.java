package com.lexicalscope.javabeanhelpers.generator;

import java.beans.PropertyDescriptor;

class AttributeModelImpl implements AttributeModel {
	private final BeanModel beanModel;
	private final PropertyDescriptor propertyDescriptor;

	public AttributeModelImpl(final BeanModel beanModel, final PropertyDescriptor propertyDescriptor) {
		this.beanModel = beanModel;
		// TODO Auto-generated constructor stub
		this.propertyDescriptor = propertyDescriptor;
	}

	public String getName() {
		return propertyDescriptor.getName();
	}
}
