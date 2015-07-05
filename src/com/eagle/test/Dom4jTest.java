package com.eagle.test;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;






public class Dom4jTest {
	public static void main(String[] args) {
		
		try {
			String xmlPath="src/com/eagle/test/Student.xml";
			
			//创建SAXReader类型对象
			SAXReader reader= new SAXReader();
			//读取要解析的xml文件 返回Document类型的对象
			Document document=reader.read(new File(xmlPath));
			
			//获得xml文档中的根元素对象
			Element rootElement = document.getRootElement();
			//获得根元素下面的子元素集合
			List<Element> list1=rootElement.elements();
			
			//获得list中每个之元素对象
			for (Element element1:list1) {
				//获得当前遍历到的这个元素的名字
				String elementName = element1.getName();
				//获得当前遍历到的子元素中id属性的值
				String id = element1.attributeValue("id");
//				//获得当前遍历到的这个子元素的的文本内容
//				String test = element1.getText();
				System.out.println("elementName="+elementName+" id="+id);
				//+" test="+test);
				
				//获得当前遍历到的这个子元素的所有集合
				//因为子元素也是Element类型的,子元素也可以有子元素
				List<Element> list2 = element1.elements();
				for (Element element2:list2) {
					String name2 = element2.getName();
					String text2=element2.getText();
					System.out.println("name2="+name2+" text="+text2);
					
				}
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
