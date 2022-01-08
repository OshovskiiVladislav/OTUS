package com.oshovskii.otus;

import com.oshovskii.otus.domain.FileCsv;
import com.oshovskii.otus.services.FileService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Scanner;

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class MainApplication {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainApplication.class);
		FileService service = context.getBean(FileService.class);
		testStudents(service);
	}

	private static void testStudents(FileService service){
		Scanner scan = new Scanner(System.in);
		System.out.println("Input your name:");
		String inputName = scan.nextLine();
		System.out.println("Test:");
		printQuestions(service);
		System.out.println("Input the right answers with a comma\n");
		String inputAnswer = scan.nextLine();
		if (inputAnswer.isBlank()) {
			System.out.println("Or next time can do it");
			return;
		}
		String[] splitedText = inputAnswer.split(",");
		int count = 0;
		if (splitedText[0].equals("1")) {
			count++;
		}
		if (splitedText[1].equals("1")) {
			count++;
		}
		if(count == service.numberToCompleteTest("questions.csv")){
			System.out.println(inputName + " completed the test");
		} else {
			System.out.println(inputName + " failed the test");
		}
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
