package edu.escuelaing.arep.service;

import edu.escuelaing.arep.model.Delivery;
import edu.escuelaing.arep.repository.DeliveryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeliveryServiceTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private DeliveryService deliveryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDeliveryList() {
        ArrayList<Delivery> deliveries = new ArrayList<>();
        deliveries.add(new Delivery());
        when(deliveryRepository.findAll()).thenReturn(deliveries);

        ArrayList<Delivery> result = deliveryService.getDeliveryList();

        assertEquals(deliveries, result);
        verify(deliveryRepository).findAll();
    }

    @Test
    void getDeliveryById() {
        Long id = 1L;
        Delivery delivery = new Delivery();
        when(deliveryRepository.findById(id)).thenReturn(Optional.of(delivery));

        Optional<Delivery> result = deliveryService.getDeliveryById(id);

        assertTrue(result.isPresent());
        assertEquals(delivery, result.get());
        verify(deliveryRepository).findById(id);
    }

    @Test
    void createDelivery() {
        Delivery delivery = new Delivery();
        when(deliveryRepository.save(delivery)).thenReturn(delivery);

        Delivery result = deliveryService.createDelivery(delivery);

        assertEquals(delivery, result);
        verify(deliveryRepository).save(delivery);
    }

    @Test
    void updateProperty() {
        Long id = 1L;
        Delivery existingDelivery = new Delivery();
        existingDelivery.setId(id);
        existingDelivery.setAddress("Old Address");

        Delivery updatedDelivery = new Delivery();
        updatedDelivery.setAddress("New Address");

        when(deliveryRepository.findById(id)).thenReturn(Optional.of(existingDelivery));
        when(deliveryRepository.save(any(Delivery.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Delivery> result = deliveryService.updateProperty(id, updatedDelivery);

        assertTrue(result.isPresent());
        assertEquals("New Address", result.get().getAddress());
        verify(deliveryRepository).findById(id);
        verify(deliveryRepository).save(any(Delivery.class));
    }

    @Test
    void deleteProperty() {
        Long id = 1L;
        Delivery delivery = new Delivery();
        when(deliveryRepository.findById(id)).thenReturn(Optional.of(delivery));

        boolean result = deliveryService.deleteProperty(id);

        assertTrue(result);
        verify(deliveryRepository).findById(id);
        verify(deliveryRepository).delete(delivery);
    }

    @Test
    void deleteProperty_NotFound() {
        Long id = 1L;
        when(deliveryRepository.findById(id)).thenReturn(Optional.empty());

        boolean result = deliveryService.deleteProperty(id);

        assertFalse(result);
        verify(deliveryRepository).findById(id);
        verify(deliveryRepository, never()).delete(any());
    }
}
