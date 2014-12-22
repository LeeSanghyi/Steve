package com.coupang.c4.step14.beanfactory;

import com.coupang.c4.ResourceUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PropertyFileBeanDefinitionLoader extends AbstractBeanDefinitionLoader {
	protected PropertyFileBeanDefinitionLoader(String configurationPath) {
		super(configurationPath);
	}

	@Override
	BeanDefinition[] loadBeans(InputStream inputStream) throws IOException {
		String[] lines = ResourceUtil.readFully(inputStream);
		List<BeanDefinition> result = new ArrayList<BeanDefinition>();
		if(lines != null){
			for(String line : lines){
				String[] parsed = line.split("=");
				result.add(new BeanDefinition(parsed[0], parsed[1]));
			}
		}
		return result.toArray(new BeanDefinition[0]);
	}
}
