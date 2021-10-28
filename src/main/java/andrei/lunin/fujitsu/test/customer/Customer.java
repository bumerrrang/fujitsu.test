package andrei.lunin.fujitsu.test.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private Long id;
    private int bonus;

    @Override
    public String toString() {
        return "id:" + this.id + ", bonus:" + bonus;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() != Customer.class) {
            return false;
        }
        Customer customer = (Customer) o;
        return Objects.equals(this.getId(), customer.getId());
    }
}