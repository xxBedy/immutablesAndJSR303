package poc;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * User: mbednar
 * Date: 27.09.16
 * Time: 10:43
 */
public class UserTest {

    public static final String ADDRESS = "Jizni 718";
    public static final String LAST_NAME = "Bednar";
    public static final String FIRST_NAME = "Martin";

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        Locale.setDefault(Locale.US);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test(expected = IllegalStateException.class)
    public void shouldFailedWhenRequiredAttributeIsMissing() throws Exception {
        User user = ImmutableUser.builder().
                firstname(FIRST_NAME).
                lastname(LAST_NAME).
                build();


        assertEquals(user.getFirstname(), FIRST_NAME);
        assertEquals(user.getLastname(), LAST_NAME);
    }

    @Test
    public void builderTest() throws Exception {
        User user = ImmutableUser.builder().
                firstname(FIRST_NAME).
                lastname(LAST_NAME).
                address(ADDRESS).
                zip("71000").
                addPhoneNumbers("+420 123 456 789", "+421 111 222 333").
                build();


        assertEquals(user.getFirstname(), FIRST_NAME);
        assertEquals(user.getLastname(), LAST_NAME);
        assertEquals(user.getAddress(), ADDRESS);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void listsShoudBeImmutable() throws Exception {
        User user = ImmutableUser.builder().
                firstname(FIRST_NAME).
                lastname(LAST_NAME).
                address(ADDRESS).
                zip("71000").
                addPhoneNumbers("+420 123 456 789", "+421 111 222 333").
                build();


        user.getPhoneNumbers().add("GRRRR");
    }

    @Test
    public void createNewInstanceWhenAddindgItemToList() throws Exception {
        ImmutableUser user = ImmutableUser.builder().
                firstname(FIRST_NAME).
                lastname(LAST_NAME).
                address(ADDRESS).
                zip("71000").
                addPhoneNumbers("+420 123 456 789", "+421 111 222 333").
                build();


        ImmutableUser newUser = user.withPhoneNumbers("NEW");
        assertNotEquals(user, newUser);
    }

    @Test()
    public void validationShouldFailed() throws Exception {
        User user = ImmutableUser.builder().
                firstname("A").
                lastname(LAST_NAME).
                address(ADDRESS).
                zip("123").
                addPhoneNumbers("+420 123 456 789", "+421 111 222 333").
                build();

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        assertNotNull(constraintViolations);
        assertEquals(3, constraintViolations.size());

        ConstraintViolation[] sortedViolations = constraintViolations.toArray(new ConstraintViolation[]{});
        Arrays.sort(sortedViolations, new Comparator<ConstraintViolation>() {
            public int compare(ConstraintViolation o1, ConstraintViolation o2) {
                return o1.getMessage().compareTo(o2.getMessage());
            }
        });

        assertEquals("123", sortedViolations[0].getInvalidValue());
        assertEquals("must match \"[0-9]5\"", sortedViolations[0].getMessage());

        assertEquals("Bednar", sortedViolations[1].getInvalidValue());
        assertEquals("size must be between 0 and 5", sortedViolations[1].getMessage());

        assertEquals("A", sortedViolations[2].getInvalidValue());
        assertEquals("size must be between 2 and 2147483647", sortedViolations[2].getMessage());

    }

}