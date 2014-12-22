package com.coupang.c4.step14.beanfactory;


import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * 전제 조건은 기본 생성자
 * <p/>
 * getInstance 할 때 항상 동일한 Instance를 리턴하도록 (싱글톤)
 * 맴버변수 map (class, instance)으로 캐싱
 * 지금은 Bean은 싱글톤 scope인데 …
 * Step17의 패턴을 이용해서 scope를 구분
 * Step 16의 xml을 보면 프로토타입 or 싱글톤 타입을 추가해야되는데 쉽게 추가하는 방법
 * Factory가 설정파일을 읽고 instance를 리턴하는 일을 모두 할 필요없다 다른 클래스에 위임해도된다
 * 그리고 thread safe하게 코드를 작성해라
 * Bean Factory
 * SingleTonFactory
 * PrototypeFactory
 * <p/>
 * 1. singleton instance 관리 - 생성된 bean 캐싱
 * 2. 고려 내용 추후 다른 scope 생성이 용이한 구조가 되도록.
 * <p/>
 * *** 점수의 기준
 * 감동 - 200점
 * 스펙 완성 - 90점
 * 일부 완성 - 70점
 * 하나도 못하면 - 30점
 * *** 유연성과 확장성을 잘 고려해서 SimpleBeanFactory를 잘 나누고 추상화를 해주시면 됩니다.
 *
 * <p/>
 * 다음주 금요일(11월 19일)까지
 * Git으로 제출 (repository는 쇼핑몰)
 *


/**
 * 1. singleton instance 관리 - 생성된 bean 캐싱
 * 	1-1. 고려 내용 추후 다른 scope 생성이 용이한 구조가 되도록. <--위에하고 이거만 과제
 * 	2. thread safe하게 구성
 * 	3. 계층 구조가 가능한 bean factory
 *
 *
 */
/*	인터페이스를 만들어서 하는게 유리
	* 유연성과 확장성 잘 고려해서 심플 빈 펙토리를 잘 나누고
	 * 추상화 해주시면 됩니다. 그래서 점수의 기준은 보고나서 감동을 받으면 200점, 스펙 완성 - 90점, 일부 완성 - 70점, 하나도 못하면 - 30점
	 * */
public class SimpleBeanFactory {
	Constructor<?> declaredConstructor;
	private String propertyPath;
	private HashMap<Class<?>,Object> map = new HashMap<Class<?>, Object>();
	private BeanDefinition[] beanDefinitions;
	private PropertyFileBeanDefinitionLoader propertyFileBeanDefinitionLoader ;

	public SimpleBeanFactory(String propertyPath) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, NoSuchMethodException {
		this.propertyPath = propertyPath;

		propertyFileBeanDefinitionLoader= new PropertyFileBeanDefinitionLoader(propertyPath);
		beanDefinitions = propertyFileBeanDefinitionLoader.loadBeans();
		for (int i = 0; i < beanDefinitions.length; i++) {
			addInstance(Class.forName(beanDefinitions[i].getClassFullName()));
		}
	}

	public <T> T getInstance(Class<T> type) {	//Sample1
		return (T)map.get(type);
	}

	public Object getInstance(String beanName) throws ClassNotFoundException {
		for (int i = 0; i < map.size(); i++) {
			if(beanDefinitions[i].getBeanName().equals(beanName)){
				return map.get(Class.forName(beanDefinitions[i].getClassFullName()));
			}
		}
		return null;
	}
	private <T> void addInstance(Class<T> type) throws InstantiationException, NoSuchMethodException{

			try {
				declaredConstructor=type.getDeclaredConstructor();
				declaredConstructor.setAccessible(true);

				map.put(type,declaredConstructor.newInstance());
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}


	}
}
