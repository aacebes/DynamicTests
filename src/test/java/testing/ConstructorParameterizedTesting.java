package testing;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ConstructorParameterizedTesting {

	// ----------- PARAMETERS INJECTION THROUGH CONSTRUCTOR -----------
	// When injecting with constructor, parameters can be private attributes

	private Integer firstNumber;
	private Integer secondNumber;

	public ConstructorParameterizedTesting(int first, int second) {
		firstNumber = first;
		secondNumber = second;
	}

	// Generation of above parameters is implemented with a @Parameters annotated method
	@Parameters(name = "Test with {0} and {1}")
	public static List<Integer[]> generator() {
		return Arrays.asList(new Integer[][] { { 0, 0 }, { 0, 2 }, { -2, 4 }, { 7, 4 }, { -4, -8 },
				{ Integer.MAX_VALUE, Integer.MAX_VALUE }, { Integer.MIN_VALUE, Integer.MIN_VALUE } });
	}

	// ----------- TESTS IMPLEMENTATION -----------
	// Both tests will use the same combinations of values

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