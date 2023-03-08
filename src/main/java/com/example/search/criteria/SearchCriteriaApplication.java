package com.example.search.criteria;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SearchCriteriaApplication {

	@Autowired
	private UserDetailRepository userDetailRepository;

	public static void main(String[] args) {
		SpringApplication.run(SearchCriteriaApplication.class, args);
	}

	@PostConstruct
	public void addtestData() {
		List<UserDetail> data = Arrays.asList(new UserDetail(1, "Sagar", "Kalra"),
				new UserDetail(2, "Deepti", "Madaan"), new UserDetail(3, "Swati", "Bhatia"));
		userDetailRepository.saveAll(data);
	}

}
