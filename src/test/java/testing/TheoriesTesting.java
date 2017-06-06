package testing;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class TheoriesTesting {

	// ----------- DATA POINTS DECLARATION -----------

	@DataPoints("positive values")
	public static int[] positiveCreation() {
		return new int[] { 1, 2, 3 };
	}

	@DataPoints("negative values")
	public static int[] negativeCreation() {
		return new int[] { -1, -2, -3 };
	}

	@DataPoint
	public static final int MAX_INTEGER = Integer.MAX_VALUE;

	// ----------- TESTS IMPLEMENTATION -----------

	// The first parameter will only take values from DataPoint with positive
	// integers and the second one will take them from the negative integers.
	// So, for this test, a and b will always have different sign.
	@Theory
	public void multiplicationShouldGiveNegativeValueWhenNumbersHasDifferentSigne(
			@FromDataPoints("positive values") final int a, @FromDataPoints("negative values") final int b) {
		System.out.println("Multiply: " + a + " * " + b);
		assertThat(a * b, lessThan(0));
	}

	// The parameter will take values from all generators: positive and
	// negative integers plus the max integer value.
	@Theory
	public void squareShouldGivePositiveResult(final int number) {
		System.out.println("Square: " + number + "^2");
		assertThat(number * number, greaterThan(0));
	}
}