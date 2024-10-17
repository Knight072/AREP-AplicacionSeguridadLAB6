package edu.escuelaing.arep.controller;

import edu.escuelaing.arep.model.Delivery;
import edu.escuelaing.arep.service.DeliveryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class DeliveryControllerTest {

    @Mock
    private DeliveryService deliveryService;

    @InjectMocks
    private DeliveryController deliveryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDeliveries() {
        // Arrange
        ArrayList<Delivery> deliveries = new ArrayList<>();
        deliveries.add(new Delivery(1L, "John", "Doe", "123 Street", "Product A", "100", "10", "L", "Description A"));
        when(deliveryService.getDeliveryList()).thenReturn(deliveries);

        // Act
        ResponseEntity<ArrayList<Delivery>> response = deliveryController.getAllDeliveries();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(deliveryService, times(1)).getDeliveryList();
    }

    @Test
    void testGetDeliveryById_Found() {
        // Arrange
        Delivery delivery = new Delivery(1L, "John", "Doe", "123 Street", "Product A", "100.0", "1", "L", "Description A");
        when(deliveryService.getDeliveryById(1L)).thenReturn(Optional.of(delivery));

        // Act
        ResponseEntity<Delivery> response = deliveryController.getDeliveryById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John", response.getBody().getUserFirstName());
        verify(deliveryService, times(1)).getDeliveryById(1L);
    }

    @Test
    void testGetDeliveryById_NotFound() {
        // Arrange
        when(deliveryService.getDeliveryById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Delivery> response = deliveryController.getDeliveryById(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(deliveryService, times(1)).getDeliveryById(1L);
    }

    @Test
    void testCreateDelivery() {
        // Arrange
        Delivery delivery = new Delivery(1L, "John", "Doe", "123 Street", "Product A", "100.0", "1", "L", "Description A");
        when(deliveryService.createDelivery(any(Delivery.class))).thenReturn(delivery);

        // Act
        ResponseEntity<Delivery> response = deliveryController.createProperty(delivery);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John", response.getBody().getUserFirstName());
        verify(deliveryService, times(1)).createDelivery(any(Delivery.class));
    }

    @Test
    void testUpdateDelivery_Found() {
        // Arrange
        Delivery delivery = new Delivery(1L, "John", "Doe", "123 Street", "Product A", "100.0", "1", "L", "Description A");
        when(deliveryService.updateProperty(anyLong(), any(Delivery.class))).thenReturn(Optional.of(delivery));

        // Act
        ResponseEntity<Delivery> response = deliveryController.updateDelivery(1L, delivery);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John", response.getBody().getUserFirstName());
        verify(deliveryService, times(1)).updateProperty(anyLong(), any(Delivery.class));
    }

    @Test
    void testUpdateDelivery_NotFound() {
        // Arrange
        Delivery delivery = new Delivery(1L, "John", "Doe", "123 Street", "Product A", "100.0", "1", "L", "Description A");
        when(deliveryService.updateProperty(anyLong(), any(Delivery.class))).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Delivery> response = deliveryController.updateDelivery(1L, delivery);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(deliveryService, times(1)).updateProperty(anyLong(), any(Delivery.class));
    }

    @Test
    void testDeleteDelivery_Found() {
        // Arrange
        when(deliveryService.deleteProperty(anyLong())).thenReturn(true);

        // Act
        ResponseEntity<Void> response = deliveryController.deleteDelivery(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(deliveryService, times(1)).deleteProperty(1L);
    }

    @Test
    void testDeleteDelivery_NotFound() {
        // Arrange
        when(deliveryService.deleteProperty(anyLong())).thenReturn(false);

        // Act
        ResponseEntity<Void> response = deliveryController.deleteDelivery(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(deliveryService, times(1)).deleteProperty(1L);
    }
}
