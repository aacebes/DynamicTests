package testing;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingConsumer;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class FactoriesTesting {

	// Simple example to show that the failure of one test doesn't affect others
	@TestFactory
	Collection<DynamicTest> listOfDynamicTests() {

		DynamicTest testKO = DynamicTest.dynamicTest("Should fail", new Executable() {
			@Override
			public void execute() throws Throwable {
				assertTrue(false);
			}
		});

		DynamicTest testOK = DynamicTest.dynamicTest("Should pass", new Executable() {
			@Override
			public void execute() throws Throwable {
				assertTrue(true);
			}
		});

		return Arrays.asList(testKO, testOK);
	}

	// Simplified version with lambdas of above factory
	@TestFactory
	Collection<DynamicTest> minimizedListOfDynamicTests() {
		return Arrays.asList(dynamicTest("Should fail", () -> assertTrue(false)),
				dynamicTest("Should pass", () -> assertTrue(true)));
	}

	// Example using a stream to go over all inputs
	@TestFactory
	Stream<DynamicTest> containsShouldWorkWhenTargetIsWithinTheStringNoMatterItsPosition() {
		final String myName = "Alex";
		final List<String> stringsWithMyName = Arrays.asList("Alex", "I'm Alex", "Alex Acebes", "I'm Alex, hi!");

		return stringsWithMyName.stream()
				.map(stringUnderTest -> dynamicTest("\"" + stringUnderTest + "\" should contain \"" + myName + "\"", new Executable() {
					@Override
					public void execute() throws Throwable {
						assertThat(stringUnderTest.contains(myName), is(true));
					}
				}));
	}

	// Same example but using an integer stream to access an external collection
	@TestFactory
	Stream<DynamicTest> containsShouldWorkWhenTargetIsWithinTheStringNoMatterItsPositionV2() {
		final String myName = "Alex";
		final List<String> stringsWithMyName = Arrays.asList("Alex", "I'm Alex", "Alex Acebes", "I'm Alex, hi!");

		return IntStream.iterate(0, i -> i + 1).limit(stringsWithMyName.size())
				.mapToObj(i -> dynamicTest("\"" + stringsWithMyName.get(i) + "\" should contain \"" + myName + "\"",
						() -> assertThat(stringsWithMyName.get(i).contains(myName), is(true))));
	}

	// Tests generated with three elements: input, names and test body
	@TestFactory
	Stream<DynamicTest> addOfBigIntegerShouldWorkLikePlusOperatorOfIntegers() {

		// Custom Iterator to see how it works.
		// It will generate 1000 random pairs of integers.
		Iterator<Integer[]> inputGenerator = new Iterator<Integer[]>() {

			int createdElements = 0;
			final Random random = new Random();

			@Override
			public boolean hasNext() {
				return createdElements < 1000;
			}

			@Override
			public Integer[] next() {
				createdElements++;
				return new Integer[] { random.nextInt(), random.nextInt() };
			}
		};

		// Function that will use the current pair to create the name of the
		// test
		Function<Integer[], String> displayNameGenerator = (numbers) -> "Add " + numbers[0] + " to " + numbers[1];

		// Code of the test itself
		ThrowingConsumer<Integer[]> testExecutor = new ThrowingConsumer<Integer[]>() {

			@Override
			public void accept(Integer[] numbers) throws Throwable {
				final BigInteger bigZero = new BigInteger(numbers[0].toString());
				final BigInteger bigOne = new BigInteger(numbers[1].toString());

				final BigInteger bigAddition = bigZero.add(bigOne);

				assertThat(bigAddition.intValue(), is(numbers[0] + numbers[1]));
			}
		};

		return DynamicTest.stream(inputGenerator, displayNameGenerator, testExecutor);
	}

	// Version of contains test with three elements generation
	@TestFactory
	Stream<DynamicTest> containsShouldWorkWhenTargetIsWithinTheStringNoMatterItsPositionV3() {
		final String myName = "Alex";
		final List<String> stringsWithMyName = Arrays.asList("Alex", "I'm Alex", "Alex Acebes", "I'm Alex, hi!");

		Iterator<String> inputGenerator = stringsWithMyName.iterator();

		Function<String, String> displayNameGenerator = (input) -> "\"" + input + "\" should contain \"" + myName
				+ "\"";

		ThrowingConsumer<String> testExecutor = new ThrowingConsumer<String>() {
			@Override
			public void accept(String stringUnderTest) throws Throwable {
				assertThat(stringUnderTest.contains(myName), is(true));
			}
		};

		return DynamicTest.stream(inputGenerator, displayNameGenerator, testExecutor);
	}

	//Minimized version of contains test with three elements generation
	@TestFactory
	Stream<DynamicTest> containsShouldWorkWhenTargetIsWithinTheStringNoMatterItsPositionV4() {
		final String myName = "Alex";
		final List<String> stringsWithMyName = Arrays.asList("Alex", "I'm Alex", "Alex Acebes", "I'm Alex, hi!");

		return DynamicTest.stream(stringsWithMyName.iterator(),
				(input) -> "\"" + input + "\" should contain \"" + myName + "\"",
				(input) -> assertThat(input.contains(myName), is(true)));
	}
}
