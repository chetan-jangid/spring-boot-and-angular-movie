//package com.gateway;
//
//import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import com.gateway.utils.AdminUsernames;
//
//import reactor.core.publisher.Mono;
//
//@Component
//public class ApplicationReactiveUserDetailsService implements ReactiveUserDetailsService {
//	
//	@Override
//	public Mono<UserDetails> findByUsername(String username) {
//		if (AdminUsernames.getUsernames().contains(username)) {
//			return Mono.just(
//					User.withUsername(username)
//					.password("$2a$10$Z21.LpBETR/doNIoU2.lLuCrF8LrA.wwkgYoQjTnDad8WLPsu8Mo6")
//					.roles("ADMIN")
//					.build()
//					);
//		}
//		return null;
//	}
//
//}
