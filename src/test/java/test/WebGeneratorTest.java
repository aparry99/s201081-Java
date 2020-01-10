package test;

import static org.junit.jupiter.api.Assertions.*;

import webGenerator.webGen;

import org.junit.jupiter.api.Test;

class WebGeneratorTest {

	@Test
	void test() {
		String[] args = {"destination\\", "source\\", "templates\\"};
					
		webGen.main(args);
		
		assertEquals(true, true);
	}

}
