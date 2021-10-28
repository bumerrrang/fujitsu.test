package andrei.lunin.fujitsu.test.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;

import org.junit.jupiter.api.Test;

class CustomerTest {
    @Test
    void testEquals() {
        assertThrows(NullPointerException.class, () -> (new Customer()).equals(null));
        assertFalse((new Customer()).equals("Different type to Customer"));
    }

    @Test
    void testEquals2() {
        Customer customer = new Customer();
        assertTrue(customer.equals(customer));
        int expectedHashCodeResult = customer.hashCode();
        assertEquals(expectedHashCodeResult, customer.hashCode());
    }

    @Test
    void testEquals3() {
        Customer customer = new Customer();
        Customer customer1 = new Customer();
        assertTrue(customer.equals(customer1));
        int notExpectedHashCodeResult = customer.hashCode();
        assertFalse(Objects.equals(notExpectedHashCodeResult, customer1.hashCode()));
    }

    @Test
    void testEquals4() {
        Customer customer = new Customer(123L, 1);
        assertFalse(customer.equals(new Customer()));
    }

    @Test
    void testConstructor() {
        assertEquals("id:null, bonus:0", (new Customer()).toString());
    }
}

