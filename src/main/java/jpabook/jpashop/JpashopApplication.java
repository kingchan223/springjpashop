package jpabook.jpashop;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpashopApplication.class, args);
	}

	@Bean/*컨트롤러에서 엔티티 반환시에 연관관계까 걸린 프록시 객체는 냅두고 제대로 데려온 객체만 반환한다.*/
	Hibernate5Module hibernate5Module(){
		return new Hibernate5Module();
	}
}
