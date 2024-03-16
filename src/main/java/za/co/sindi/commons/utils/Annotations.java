/**
 * 
 */
package za.co.sindi.commons.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashSet;


/**
 * @author Buhake Sindi
 * @since 11 November 2014
 *
 */
public final class Annotations {

	private Annotations() {
		throw new AssertionError("Private constructor.");
	}
	
	public static <T extends Annotation> T findAnnotation(Class<?> clazz, Class<T> annotationClass) {
		Preconditions.checkArgument(clazz != null, "No class type provided.");
		Preconditions.checkArgument(annotationClass != null, "No annotation class type provided.");
		
		T result = null;
		if (clazz.isAnnotationPresent(annotationClass)) {
			result = clazz.getAnnotation(annotationClass);
		}
		
		if (result == null) {
			for (Class<?> interfaceClass : clazz.getInterfaces()) {
//				if (interfaceClass.isAnnotationPresent(annotationClass)) {
//					return interfaceClass.getAnnotation(annotationClass);
//				}
				result = findAnnotation(interfaceClass, annotationClass);
			}
		}
		
		if (result == null) {
			Class<?> superClass = clazz.getSuperclass();
			if (superClass != null && !superClass.equals(Object.class)) {
				return findAnnotation(superClass, annotationClass);
			}
		}
		
		return result;
	}
	
	public static <T extends Annotation> T findAnnotation(Field field, Class<T> annotationClass) {
		Preconditions.checkArgument(field != null, "No field provided.");
		Preconditions.checkArgument(annotationClass != null, "No annotation class type provided.");
		
		if (field.isAnnotationPresent(annotationClass)) {
			return field.getAnnotation(annotationClass);
		}
		
		Class<?> clazz = field.getDeclaringClass();
		if (clazz == null || clazz.equals(Object.class)) {
			return null;
		}
		
		while (true) {
			clazz = clazz.getSuperclass();
			if (clazz == null || clazz.equals(Object.class)) {
				break;
			}
			
			Field[] fields = clazz.getDeclaredFields();
			if (fields != null) {
				for (Field parentField : fields) {
					if (parentField.isAnnotationPresent(annotationClass)) {
						return parentField.getAnnotation(annotationClass);
					}
				}
			}
		}
		
		return null;
	}
	
	public static <T extends Annotation> T findAnnotation(Method method, Class<T> annotationClass) {
		Preconditions.checkArgument(method != null, "No method provided.");
		Preconditions.checkArgument(annotationClass != null, "No annotation class type provided.");
		
		if (method.isAnnotationPresent(annotationClass)) {
			return method.getAnnotation(annotationClass);
		}
		
		Class<?> clazz = method.getDeclaringClass();
		if (clazz == null || clazz.equals(Object.class)) {
			return null;
		}
		
		while (true) {
			clazz = clazz.getSuperclass();
			if (clazz == null || clazz.equals(Object.class)) {
				break;
			}
			
			try {
				Method equivalentMethod = clazz.getDeclaredMethod(method.getName(), method.getParameterTypes());
				if (equivalentMethod != null && equivalentMethod.isAnnotationPresent(annotationClass)) {
					return equivalentMethod.getAnnotation(annotationClass);
				}
			}
			catch (NoSuchMethodException ex) {
				// We're done...
			}
		}
		
		return null;
	}
	
	public static Field[] findFields(Class<?> clazz, Class<? extends Annotation> annotationClass) {
		Collection<Field> visitedFields = new LinkedHashSet<Field>();
		findFields(clazz, annotationClass, visitedFields);
		return visitedFields.toArray(new Field[visitedFields.size()]);
	}
	
	private static void findFields(Class<?> clazz, Class<? extends Annotation> annotationClass, Collection<Field> visitedFields) {
		Preconditions.checkArgument(clazz != null, "No class type provided.");
		Preconditions.checkArgument(annotationClass != null, "No annotation class type provided.");
		
		Field[] fields = clazz.getDeclaredFields();
		if (fields != null) {
			for (Field field : fields) {
				if (field.isAnnotationPresent(annotationClass)) {
					visitedFields.add(field);
				}
			}
		}
		
		Class<?> superClass = clazz.getSuperclass();
		if (superClass != null && !superClass.equals(Object.class)) {
			findFields(superClass, annotationClass, visitedFields);
		}
	}
	
	public static Method[] findMethods(Class<?> clazz, Class<? extends Annotation> annotationClass) {
		Collection<Method> visitedMethods = new LinkedHashSet<Method>();
		findMethods(clazz, annotationClass, visitedMethods);
		return visitedMethods.toArray(new Method[visitedMethods.size()]);
	}
	
	private static void findMethods(Class<?> clazz, Class<? extends Annotation> annotationClass, Collection<Method> visitedMethods) {
		Preconditions.checkArgument(clazz != null, "No class type provided.");
		Preconditions.checkArgument(annotationClass != null, "No annotation class type provided.");
		
		Method[] methods = clazz.getDeclaredMethods();
		if (methods != null) {
			for (Method method : methods) {
				if (method.isAnnotationPresent(annotationClass)) {
					visitedMethods.add(method);
				}
			}
		}
		
		Class<?> superClass = clazz.getSuperclass();
		if (superClass != null && !superClass.equals(Object.class)) {
			findMethods(superClass, annotationClass, visitedMethods);
		}
	}
}
