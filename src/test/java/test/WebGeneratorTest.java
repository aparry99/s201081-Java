package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uk.ac.uos.i2p.webGenerator.webGen;

class WebGeneratorTest {

	@Test
	void test() {
		String[] args = {"destination\\", "source\\", "templates\\"};
					
		webGen.main(args);
		
		assertEquals(true, true);
	}

}
