package com.lexicalscope.javabeanhelpers.generator;

class TemplateImpl implements Template {
	private final StringTemplateFactory nameTemplateFactory;
	private final StringTemplateFactory contentTemplateFactory;

	public TemplateImpl(
			final StringTemplateFactory nameTemplateFactory,
			final StringTemplateFactory contentTemplateFactory) {
		this.nameTemplateFactory = nameTemplateFactory;
		this.contentTemplateFactory = contentTemplateFactory;
	}

	public GeneratedOutput merge(final BeanModel beanModel) {
		return new GeneratedOutputImpl(
				nameTemplateFactory.create(beanModel).render(),
				contentTemplateFactory.create(beanModel).render());
	}
}
