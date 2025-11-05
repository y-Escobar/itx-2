package com.escobar.itx_2;

import com.escobar.itx_2.product.config.MongoTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(MongoTestConfig.class)
class Itx2ApplicationTests {

	@Test
	void contextLoads() {
	}

}
