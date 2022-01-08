package com.oshovskii.otus;

import com.oshovskii.otus.domain.FileCsv;
import com.oshovskii.otus.services.FileService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

@SpringBootApplication
public class MainApplication {
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(MainApplication.class, args);
		FileService service = ctx.getBean(FileService.class);
		MessageSource msg = ctx.getBean(MessageSource.class);
		testStudents(service, msg);
	}

	private static void testStudents(FileService service, MessageSource messageSource){
		Scanner scan = new Scanner(System.in);
		System.out.println(translate(messageSource, "name-request"));
		String inputName = scan.nextLine();
		System.out.println(translate(messageSource, "greeting"));
		printQuestions(service, messageSource);
		System.out.println(translate(messageSource, "task"));
		String inputAnswer = scan.nextLine();
		if (inputAnswer.isBlank()) {
			System.out.println(translate(messageSource, "fail"));
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
		if(count == service.numberToCompleteTest("questionsRUS.csv")){
			System.out.println(inputName + " " + translate(messageSource, "completed"));
		} else {
			System.out.println(inputName + " " + translate(messageSource, "failed"));
		}
	}

	private static void printQuestions(FileService service, MessageSource messageSource) {
		List<FileCsv> csvFileCsv = service.parseCsvFile("questionsEN.csv");
		for (int i = 0; i <= 1; i++) {
			System.out.println();
			System.out.println(translate(messageSource, "question") + csvFileCsv.get(i).getQwestion());
			System.out.println("1. " + csvFileCsv.get(i).getRightAnswer());
			System.out.println("2. " + csvFileCsv.get(i).getIncorrectAnswer());
		}
	}

	private static String translate(MessageSource messageSource, String fromString){
		return messageSource.getMessage(
				fromString,
				null,
				Locale.forLanguageTag("ru-RU")
		);
	}
}
