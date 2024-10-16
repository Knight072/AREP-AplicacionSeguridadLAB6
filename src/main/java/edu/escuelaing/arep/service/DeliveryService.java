package edu.escuelaing.arep.service;

import edu.escuelaing.arep.model.Delivery;
import edu.escuelaing.arep.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository) {

        this.deliveryRepository = deliveryRepository;
    }

    public ArrayList<Delivery> getDeliveryList() {
        return (ArrayList<Delivery>) deliveryRepository.findAll();
    }

    public Optional<Delivery> getDeliveryById(Long id) {
        return deliveryRepository.findById(id);
    }

    public Delivery createDelivery(Delivery property) {
        return deliveryRepository.save(property);
    }

    public Optional<Delivery> updateProperty(Long id, Delivery propertyDetails) {
        return deliveryRepository.findById(id)
                .map(property -> {
                    property.setAddress(propertyDetails.getAddress());
                    property.setPrice(propertyDetails.getPrice());
                    property.setSize(propertyDetails.getSize());
                    property.setDescription(propertyDetails.getDescription());
                    return deliveryRepository.save(property);
                });
    }

    public boolean deleteProperty(Long id) {
        return deliveryRepository.findById(id)
                .map(property -> {
                    deliveryRepository.delete(property);
                    return true;
                })
                .orElse(false);
    }
}
