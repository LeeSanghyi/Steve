package com.coupang.c4.step14.beanfactory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class BeanDefinition {
	@XmlAttribute(name = "name")
	private String beanName;
	@XmlAttribute(name = "class")
	private String classFullName;

	BeanDefinition() {
	}

	public BeanDefinition(String beanName, String classFullName) {
		this.classFullName = classFullName;
		this.beanName = beanName;
	}

	public String getBeanName() {
		return beanName;
	}

	public String getClassFullName() {
		return classFullName;
	}

	@Override
	public String toString() {
		return "BeanDefinition [beanName=" + beanName + ", classFullName=" + classFullName + "]";
	}
}
