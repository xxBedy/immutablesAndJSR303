package poc;

import org.immutables.value.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * User: mbednar
 * Date: 27.09.16
 * Time: 10:40
 */
@Value.Immutable
public interface Customer {
    @Size(min=2)
    String getFirstname();

    @Size(max=5)
    String getLastname();

    @NotNull
    String getAddress();

    @Pattern(regexp = "[0-9]5")
    String getZip();

    List<String> getPhoneNumbers();
}
