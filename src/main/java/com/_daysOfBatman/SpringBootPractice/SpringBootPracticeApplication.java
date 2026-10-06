package com._daysOfBatman.SpringBootPractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com._daysOfBatman.SpringBootPractice.Notifications.NotificationSender;
import com._daysOfBatman.SpringBootPractice.Orders.OrderService;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringBootPracticeApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SpringBootPracticeApplication.class, args);
		System.out.println("Number of beans: " + ctx.getBeanDefinitionCount());
		System.out.println("Bean names: " + String.join(", ", ctx.getBeanDefinitionNames()));

		OrderService first = ctx.getBean(OrderService.class);
		OrderService second = ctx.getBean(OrderService.class);
		System.out.println("Same OrderService instance (==): " + (first == second));

		System.out.println("clock bean: " + ctx.getBean("clock"));
		System.out.println("NotificationSender beans: " + ctx.getBeansOfType(NotificationSender.class).keySet());
		System.out.println("Default : " + first.notifyDefault("Order-1"));
		System.out.println("Qualifier: " + first.notifyBySms("Order-1"));
		System.out.println("All     : " + first.notifyAll("Order-1"));
	}

}
