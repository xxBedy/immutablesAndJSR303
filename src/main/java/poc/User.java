package poc;

import org.immutables.value.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * User: mbednar
 * Date: 29.09.16
 * Time: 9:37
 */
@Value.Immutable
public abstract class User {
    @Size(min = 2)
    abstract String getFirstname();

    @Size(max = 5)
    abstract String getLastname();

    @NotNull
    abstract String getAddress();

    @Pattern(regexp = "[0-9]5")
    abstract String getZip();

    abstract List<String> getPhoneNumbers();
}
