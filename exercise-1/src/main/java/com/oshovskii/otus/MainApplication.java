package com.oshovskii.otus;

import com.oshovskii.otus.domain.FileCsv;
import com.oshovskii.otus.services.FileService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class MainApplication {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
		FileService service = context.getBean(FileService.class);
		printQuestions(service);
	}

	private static void printQuestions(FileService service) {
		List<FileCsv> csvFileCsv = service.parseCsvFile("questions.csv");
		for (int i = 0; i <= 1; i++) {
			System.out.println();
			System.out.println("Questions: " + csvFileCsv.get(i).getQwestion());
			System.out.println("1. " + csvFileCsv.get(i).getRightAnswer());
			System.out.println("2. " + csvFileCsv.get(i).getIncorrectAnswer());
		}
	}
}
