package com.bt.base.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/** 
 * @ClassName: SpringUtil
 * @Description: TODO(spring工具类)
 * @author Ian.Huang
 * @date 2017年4月27日 下午3:16:45 
 * 
 */
public final class SpringUtil implements BeanFactoryPostProcessor {
	
	// Spring应用上下文环境
	private static ConfigurableListableBeanFactory beanFactory;
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory paramConfigurableListableBeanFactory)
			throws BeansException {
		SpringUtil.beanFactory = paramConfigurableListableBeanFactory;
		
	}
	
	/**
	 * @Title: getBean
	 * @Description: TODO(注册名取值)
	 * @param name
	 * @throws BeansException
	 * @return: T
	 * @author: Ian.Huang
	 * @date: 2017年4月27日 下午3:02:54
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		return (T) beanFactory.getBean(name);
		
	}
	
	/**
	 * @Title: getBean
	 * @Description: TODO(类型取值)
	 * @param clz
	 * @throws BeansException
	 * @return: T
	 * @author: Ian.Huang
	 * @date: 2017年4月27日 下午3:04:17
	 */
	public static <T> T getBean(Class<T> clz) throws BeansException {
		return (T) beanFactory.getBean(clz);
		
	}
	
	/**
	 * @Title: containsBean
	 * @Description: TODO(判断是否存在对应注册名的bean)
	 * @param name
	 * @return: boolean
	 * @author: Ian.Huang
	 * @date: 2017年4月27日 下午3:04:53
	 */
	public static boolean containsBean(String name) {
		return beanFactory.containsBean(name);
		
	}
	
	/**
	 * @Title: isSingleton
	 * @Description: TODO(
	 * 判断注册名对应bean定义是一个singleton还是一个prototype，
	 * 如果bean没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException)
	 * @param name
	 * @throws NoSuchBeanDefinitionException
	 * @return: boolean
	 * @author: Ian.Huang
	 * @date: 2017年4月27日 下午3:05:37
	 */
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.isSingleton(name);
		
	}
	
	/**
	 * @Title: getType
	 * @Description: TODO(
	 * 获取注册名对应bean的类型，
	 * 如果bean没有被找到，将会抛出一个异常NoSuchBeanDefinitionException)
	 * @param name
	 * @throws NoSuchBeanDefinitionException
	 * @return: Class<?>
	 * @author: Ian.Huang
	 * @date: 2017年4月27日 下午3:08:00
	 */
	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.getType(name);
		
	}
	
	/**
	 * @Title: getAliases
	 * @Description: TODO(
	 * 获取注册名对应bean的别名，
	 * 如果bean没有被找到，将会抛出一个异常NoSuchBeanDefinitionException)
	 * @param name
	 * @throws NoSuchBeanDefinitionException
	 * @return: String[]
	 * @author: Ian.Huang
	 * @date: 2017年4月27日 下午3:09:35
	 */
	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.getAliases(name);
		
	}

}
