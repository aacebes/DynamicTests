package testing;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class RandomAnnotationParameterizedTesting {

	// ----------- PARAMETERS INJECTION THROUGH ANNOTATION -----------
	// When injecting with annotations, parameters must be public attributes

	@Parameter // First parameter doesn't need number
	public Integer firstNumber;

	@Parameter(1) // Starting with second parameter, number is needed
	public Integer secondNumber;

	@Parameters(name = "Test with {0} and {1}")
	public static List<Integer[]> generator() {
		final Random random = new Random();

		final List<Integer[]> testCases = new LinkedList<>();
		for (int i = 0; i < 1000; i++) {
			testCases.add(new Integer[] { random.nextInt(), random.nextInt() });
		}

		return testCases;
	}

	// ----------- TESTS IMPLEMENTATION -----------
	//Both tests will use the same combinations of values, randomly generated

	@Test
	public void addOfBigIntegerShouldWorkLikePlusOperatorOfIntegers() {
		final BigInteger bigFirst = new BigInteger(firstNumber.toString());
		final BigInteger bigSecond = new BigInteger(secondNumber.toString());

		final BigInteger bigAddition = bigFirst.add(bigSecond);

		assertThat(bigAddition.intValue(), is(firstNumber + secondNumber));
	}

	@Test
	public void substractOfBigIntegerShouldWorkLikeMinusOperatorOfIntegers() {
		final BigInteger bigFirst = new BigInteger(firstNumber.toString());
		final BigInteger bigSecond = new BigInteger(secondNumber.toString());

		final BigInteger bigAddition = bigFirst.subtract(bigSecond);

		assertThat(bigAddition.intValue(), is(firstNumber - secondNumber));
	}
}