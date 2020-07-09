package com.movie;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.movie.document.Movie;
import com.movie.repository.MovieRepository;

@Component
public class StarterData implements CommandLineRunner {
	
	@Autowired
	private MovieRepository movieRepository;

	@Override
	public void run(String... args) throws Exception {
		movieRepository.save(new Movie(null, "Yeh Jawani Hai Deewani", 
				"Kabir, a guy who wants to make it big in the travel industry, "
				+ "meets Naina during a trekking trip. She falls for him but refrains from expressing it. "
				+ "Years later they meet at a friend's wedding.", 
				"hindi", "drama", "Dharma Productions", "Ayan Mukherji", LocalDate.of(2013, 5, 31)));
		movieRepository.save(new Movie(null, "Rockstar", 
				"College student Janardhan is a simpleton who desperately seeks inspiration "
				+ "for the musician inside him. Although heartbreak helps him reach his goal, "
				+ "it also leads him to self-destruction.", 
				"hindi", "drama", "Sunil Lulla and Dhilin Mehta", "Imtiaz Ali", LocalDate.of(2011, 11, 11)));
		movieRepository.save(new Movie(null, "Angrezi Medium", 
				"Though Champak initially disapproves, he eventually does everything in "
				+ "his power while going through a series of hilarious mishaps to fulfil "
				+ "his daughter's dream of going to London to study further.", 
				"hindi", "drama", "Dinesh Vijan, Jyoti Deshpande", "Homi Adajania", LocalDate.of(2020, 3, 13)));
		movieRepository.save(new Movie(null, "Parmanu: The Story of Pokhran", 
				"IAS officer Ashwat is tasked to lead an operation to develop and test nuclear "
				+ "weapons but must ensure that American intelligence agencies do not learn about it.", 
				"hindi", "action", "JA Entertainment; Zee Studios", "Abhishesk Sharma", LocalDate.of(2018, 5, 25)));
		movieRepository.save(new Movie(null, "3 Idiots", 
				"In college, Farhan and Raju form a great bond with Rancho due to his refreshing outlook. "
				+ "Years later, a bet gives them a chance to look for their "
				+ "long-lost friend whose existence seems rather elusive.", 
				"hindi", "comedydrama", "Vidhu Vinod Chopra", "Rajkumar Hirani", LocalDate.of(2009, 12, 25)));
		movieRepository.save(new Movie(null, "Rang De Basanti", 
				"When Sue selects a few students to portray various Indian freedom fighters "
				+ "in her film, she unwittingly awakens their patriotism. "
				+ "The emotional and mental process turns them into rebels for a cause.", 
				"hindi", "comedydrama", "Rakesh Omprakash Mehra", "Rakeysh Omprakash Mehra", LocalDate.of(2006, 1, 26)));
		movieRepository.save(new Movie(null, "Bhaag Milkha Bhaag", 
				"In college, Farhan and Raju form a great bond with Rancho due to his refreshing outlook. "
				+ "Years later, a bet gives them a chance to look for their "
				+ "long-lost friend whose existence seems rather elusive.", 
				"hindi", "motivational", "ROMP Pictures", "Rakeysh Omprakash Mehra", LocalDate.of(2013, 7, 11)));
		
		movieRepository.save(new Movie(null, "Pulp Fiction", 
				"The lives of two mob hitmen, a boxer, a gangster and his wife, "
				+ "and a pair of diner bandits intertwine in four tales of violence and redemption.", 
				"english", "crime", "Quentin Tarantino, Stacey Sher, Lawrence Bender", "Quentin Tarantino", LocalDate.of(1994, 9, 10)));
		movieRepository.save(new Movie(null, "Fight Club", 
				"An insomniac office worker and a devil-may-care soapmaker form an underground "
				+ "fight club that evolves into something much, much more.", 
				"english", "drama", "Cean Chaffin, Ross Grayson Bell, Art Linson", "David Fincher", LocalDate.of(1999, 10, 15)));
		movieRepository.save(new Movie(null, "Into The Wild", 
				"Christopher McCandless, a young graduate, decides to renounce all his possessions "
				+ "and hitchhike across America. During his journey, he encounters several "
				+ "situations which change him as a person.", 
				"english", "motivational", "Sean Penn; Art Linson", "Sean Penn", LocalDate.of(2007, 9, 21)));
		movieRepository.save(new Movie(null, "Cast Away", 
				"After a deadly plane crash, Chuck Nolan finds himself marooned on a "
				+ "desolate island. With no way to escape, "
				+ "Chuck must find ways to survive in his new home.", 
				"english", "motivational", "Steve Starkey; Tom Hanks", "Robert Zemeckis", LocalDate.of(2000, 12, 7)));
	}

}
