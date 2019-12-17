package test;

import static org.junit.jupiter.api.Assertions.*;

import webGenerator.webGen;

import org.junit.jupiter.api.Test;

class WebGeneratorTest {

	@Test
	void test() {
		String[] args = {"C:\\Users\\Andy\\Desktop\\Java\\s201081-Java\\destination\\", "C:\\Users\\Andy\\Desktop\\Java\\s201081-Java\\source", "C:\\Users\\Andy\\Desktop\\Java\\s201081-Java\\templates"};
					
		webGen.main(args);
		
		assertEquals(true, true);
	}

}
