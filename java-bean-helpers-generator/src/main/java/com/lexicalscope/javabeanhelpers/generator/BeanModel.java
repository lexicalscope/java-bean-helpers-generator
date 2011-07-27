package com.lexicalscope.javabeanhelpers.generator;

import java.util.List;

interface BeanModel {
	String getName();

	String getPackage();

	List<AttributeModel> getAttributes();
}
