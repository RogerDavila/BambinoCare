package com.bambinocare;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ClassPathResourceTest {

	public static void main(String[] args) throws IOException {
		
		Resource resource = new ClassPathResource("/static/img/photos/photo1.jpg");
		System.out.println(resource.getURI());
		File file = resource.getFile();
		
		System.out.println(file.exists());
	}
	
	
}
