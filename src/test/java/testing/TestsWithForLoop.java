package testing;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TestsWithForLoop {

	// In this example no exception is caught, so no all cases will be tested.
	@Test
	public void containsShouldWorkWhenTargetIsWithinTheStringNoMatterItsPositionV1() {
		final String myName = "Alex";
		final List<String> stringsWithMyName = Arrays.asList("Alex", "I'm Alex", "I'm Alejandro, don't shorten it",
				"Alex Acebes", "Nobody", "I'm Alex, hi!");

		System.err.println("\n\nTest: containsShouldWorkWhenTargetIsWithinTheStringNoMatterItsPositionV1");
		for (String string : stringsWithMyName) {
			System.err.println(string);
			assertThat(string.contains(myName), is(true));
		}
	}

	// In this example we catch the exception, so even when an assert fails the
	// test will always pass.
	@Test
	public void containsShouldWorkWhenTargetIsWithinTheStringNoMatterItsPositionV2() {
		final String myName = "Alex";
		final List<String> stringsWithMyName = Arrays.asList("Alex", "I'm Alex", "I'm Alejandro, don't shorten it",
				"Alex Acebes", "Nobody", "I'm Alex, hi!");

		System.err.println("\n\nTest: containsShouldWorkWhenTargetIsWithinTheStringNoMatterItsPositionV2");
		for (String string : stringsWithMyName) {
			try {
				assertThat("\"" + string + "\" doesn't contain \"" + myName + "\"", string.contains(myName), is(true));
			} catch (AssertionError error) {
				System.err.println(error.getMessage() + "\n");
			}
		}
	}

	// In this example we catch the exception but throw it at the end. This way
	// we can have all errors in console and the test fails when it has to.
	@Test
	public void containsShouldWorkWhenTargetIsWithinTheStringNoMatterItsPositionV3() {
		final String myName = "Alex";
		final List<String> stringsWithMyName = Arrays.asList("Alex", "I'm Alex", "I'm Alejandro, don't shorten it",
				"Alex Acebes", "Nobody", "I'm Alex, hi!");
		AssertionError failure = null;

		System.err.println("\n\nTest: containsShouldWorkWhenTargetIsWithinTheStringNoMatterItsPositionV3");
		for (String string : stringsWithMyName) {
			try {
				assertThat("\"" + string + "\" doesn't contain \"" + myName + "\"", string.contains(myName), is(true));
			} catch (AssertionError error) {
				failure = error;
				System.err.println(error.getMessage() + "\n");
			}
		}

		if (failure != null) {
			throw failure;
		}
	}

}
