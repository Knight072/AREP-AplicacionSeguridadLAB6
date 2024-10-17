package edu.escuelaing.arep.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryTest {

    @Test
    void testNoArgsConstructor() {
        Delivery delivery = new Delivery();
        assertNotNull(delivery);
        assertNull(delivery.getId());
        assertNull(delivery.getUserFirstName());
        assertNull(delivery.getUserLastName());
        assertNull(delivery.getAddress());
        assertNull(delivery.getProductName());
        assertNull(delivery.getPrice());
        assertNull(delivery.getAmount());
        assertNull(delivery.getSize());
        assertNull(delivery.getDescription());
    }

    @Test
    void testAllArgsConstructor() {
        Delivery delivery = new Delivery(1L, "John", "Doe", "123 Main St", "Widget", "9.99", "2", "Medium", "A great widget");

        assertEquals(1L, delivery.getId());
        assertEquals("John", delivery.getUserFirstName());
        assertEquals("Doe", delivery.getUserLastName());
        assertEquals("123 Main St", delivery.getAddress());
        assertEquals("Widget", delivery.getProductName());
        assertEquals("9.99", delivery.getPrice());
        assertEquals("2", delivery.getAmount());
        assertEquals("Medium", delivery.getSize());
        assertEquals("A great widget", delivery.getDescription());
    }

    @Test
    void testSettersAndGetters() {
        Delivery delivery = new Delivery();

        delivery.setId(2L);
        delivery.setUserFirstName("Jane");
        delivery.setUserLastName("Smith");
        delivery.setAddress("456 Elm St");
        delivery.setProductName("Gadget");
        delivery.setPrice("19.99");
        delivery.setAmount("1");
        delivery.setSize("Large");
        delivery.setDescription("An amazing gadget");

        assertEquals(2L, delivery.getId());
        assertEquals("Jane", delivery.getUserFirstName());
        assertEquals("Smith", delivery.getUserLastName());
        assertEquals("456 Elm St", delivery.getAddress());
        assertEquals("Gadget", delivery.getProductName());
        assertEquals("19.99", delivery.getPrice());
        assertEquals("1", delivery.getAmount());
        assertEquals("Large", delivery.getSize());
        assertEquals("An amazing gadget", delivery.getDescription());
    }
}
