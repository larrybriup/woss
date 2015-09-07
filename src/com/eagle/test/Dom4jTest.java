package com.eagle.test;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Dom4jTest {

	public static void main(String[] args) {

		try {
			String xmlPath = "src/com/eagle/test/Student.xml";

			// 创建SAXReader类型对象
			SAXReader reader = new SAXReader();
			// 读取要解析的xml文件 返回Document类型的对象
			Document document = reader.read(new File(xmlPath));

			// 获得xml文档中的根元素对象
			Element rootElement = document.getRootElement();
			// 获得根元素下面的子元素集合
			List<Element> studentList = rootElement.elements();

			// 获得list中每个之元素对象
			for (Element studentNode : studentList) {
				// 获得当前遍历到的这个元素的名字
				String studentNodeName = studentNode.getName();
				// 获得当前遍历到的子元素中id属性的值
				String id = studentNode.attributeValue("id");

				System.out.print("Student" + id + ": ");

				// 获得当前遍历到的这个子元素的所有集合
				// 因为子元素也是Element类型的,子元素也可以有子元素
				List<Element> studentNodeProperties = studentNode.elements();
				for (Element studentProperty : studentNodeProperties) {
					String studentPropertyName = studentProperty.getName();
					String studentPropertyValue = studentProperty.getText();
					System.out.print(studentPropertyName + " = " + studentPropertyValue + "\t");
				}
				System.out.println();

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
