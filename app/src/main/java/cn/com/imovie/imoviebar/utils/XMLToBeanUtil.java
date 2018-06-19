package cn.com.imovie.imoviebar.utils;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * XML TO Bean
 *
 * @author 锟斤拷锟斤拷锟斤拷 2012-2-16锟斤拷锟斤拷11:03:58
 */
public class XMLToBeanUtil {
	@SuppressWarnings("rawtypes")
	public static List xmlToBeanList(final Class<?> classobj, NodeList nodeList) {
		int length = nodeList.getLength();
		List<Object> objectList = new LinkedList<Object>();
		Object object = null;
		Map<String, Method> mapMethod = getSetMethodMap(classobj);
		for (int i = 0; i < length; i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE)
				continue;

			object = xmlToBean(classobj, mapMethod, node.getChildNodes());
			objectList.add(object);
		}
		return objectList;

	}

	@SuppressWarnings("rawtypes")
	public static List simpleXmlToBeanList(final Class<?> classobj, NodeList nodeList) {
		int length = nodeList.getLength();
		List<Object> objectList = new LinkedList<Object>();
		Map<String, Method> mapMethod = getSetMethodMap(classobj);
		for (int i = 0; i < length; i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE)
				continue;
			Object object = null;
			try {
				object = classobj.newInstance();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
			if (object == null)
				continue;
			String value = StringHelper.formatNullValue(node.getChildNodes().item(0).getTextContent(), "");
			String field = node.getNodeName();
			Method method = mapMethod.get(getSetMethodName(field));
			if (method == null)
				continue;
			Class<?>[] parameterTypes = method.getParameterTypes();
			if (parameterTypes.length != 1)
				continue;
			Class<?> parameterClass = parameterTypes[0];
			if (parameterClass.equals(String.class)) {
				if(StringHelper.isEmpty(value))continue;
				try {
					method.invoke(object, value);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			objectList.add(object);
		}
		return objectList;

	}

	public static Object xmlToBean(final Class<?> classobj, NodeList nodeList) {
		Map<String, Method> mapMethod = getSetMethodMap(classobj);
		return xmlToBean(classobj, mapMethod, nodeList);
	}

	private static Object xmlToBean(final Class<?> classobj,
									Map<String, Method> mapMethod, NodeList nodeList) {
		if (nodeList == null)
			return null;
		int length = nodeList.getLength();
		Object object =null;
		try {
			object=classobj.newInstance();
			if (object == null)
				return object;
			String value;
			String field;
			Method method;
			Node firstNode;
			for (int i = 0; i < length; i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() != Node.ELEMENT_NODE)
					continue;
				firstNode=node.getFirstChild();
				if(firstNode==null)continue;
				value = StringHelper.formatNullValue(firstNode.getNodeValue(),"");
				field = node.getNodeName();
				method = mapMethod.get(getSetMethodName(field));
				if (method == null)
					continue;
				Class<?>[] parameterTypes = method.getParameterTypes();
				if (parameterTypes.length != 1)
					continue;
				Class<?> parameterClass = parameterTypes[0];
				try{
					if (parameterClass.equals(Date.class)) {// 锟斤拷锟节达拷锟斤拷
						if (!StringHelper.isEmpty(value)) {
							value = value.trim();
							Date date = null;
							if (value.length() > 10) {
								date = DateHelper.toDate(value,
										"yyyy-MM-dd HH:mm:ss");
							} else {
								date = DateHelper.toDate(value,
										DateHelper.DEFAULT_DATE_FORMAT);
							}
							method.invoke(object, date);
						}

					} else if (parameterClass.equals(Integer.class)||
							parameterClass.equals(int.class)) {
						if(StringHelper.isEmpty(value))continue;
						method.invoke(object, new Integer(value));
					} else if (parameterClass.equals(Boolean.class)||
							parameterClass.equals(boolean.class)) {
						if(StringHelper.isEmpty(value))continue;
						if ("1".equals(value)) {
							method.invoke(object, new Boolean(true));
						} else {
							method.invoke(object, new Boolean(false));
						}

					} else if (parameterClass.equals(Long.class)||
							parameterClass.equals(long.class)) {
						if(StringHelper.isEmpty(value))continue;
						method.invoke(object, new Long(value));
					} else if (parameterClass.equals(Double.class)||
							parameterClass.equals(double.class)) {
						if(StringHelper.isEmpty(value))continue;
						method.invoke(object, new Double(value));
					} else if (parameterClass.equals(Float.class) ||
							parameterClass.equals(float.class)) {
						if(StringHelper.isEmpty(value))continue;
						method.invoke(object, new Float(value));
					} else if (parameterClass.equals(String.class)) {
						if(StringHelper.isEmpty(value))continue;
						method.invoke(object, value);
					} else if (parameterClass.equals(List.class)){//锟斤拷锟斤拷锟斤拷锟斤拷
						Type type=method.getGenericParameterTypes()[0];
						//锟斤拷锟斤拷欠锟斤拷筒锟斤拷锟斤拷锟斤拷锟斤拷锟�  
						if(type instanceof ParameterizedType){
							ParameterizedType pt =(ParameterizedType)type;
							//锟矫碉拷锟斤拷锟斤拷锟斤拷锟絚lass锟斤拷锟酵讹拷锟斤拷
							Class<?> genericClazz = (Class<?>)pt.getActualTypeArguments()[0];
							@SuppressWarnings("rawtypes")
							List list=xmlToBeanList(genericClazz,node.getChildNodes());
							method.invoke(object, list);
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	static public String getSetMethodName(String fieldName) {
		fieldName = StringHelper.repLineFirstToUpper(fieldName);
		return "set" + StringHelper.upperFirstChar(fieldName);
	}

	private static Map<String, Method> getSetMethodMap(final Class<?> classobj) {
		Method[] allMethod = classobj.getMethods();
		Map<String, Method> mapMethod = new HashMap<String, Method>();
		for (Method method : allMethod) {
			String methodName = method.getName();
			if (methodName.startsWith("set")) {
				mapMethod.put(methodName, method);
			}
		}
		return mapMethod;
	}

	public static String getValueFromElement(Element element) {
		if (element == null || element.getFirstChild() == null)
			return null;
		return element.getFirstChild().getNodeValue();

	}

	public static String getValueFromNode(Node node) {

		if (node == null || node.getFirstChild() == null)
			return null;
		return node.getFirstChild().getNodeValue();

	}
}
