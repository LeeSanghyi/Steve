package com.coupang.c4.step14.beanfactory;

import java.io.IOException;

public interface BeanDefinitionLoader {
	public BeanDefinition[] loadBeans() throws IOException;
}
