package com.example.inventory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
//autowired anotation used to inject object repository into this service class
    @Autowired 
    private InventoryRepository repository;

    public InventoryItem addItem(InventoryItem item) {
        item.calculateTotalPrice(); // Calculate total price before saving
        return repository.save(item);
    }

    public List<InventoryItem> getAllItems() {
        return repository.findAll();
    }

    public Optional<InventoryItem> getItemById(Long id) {
        return repository.findById(id);
    }

    public Optional<InventoryItem> updateItem(Long id, InventoryItem updatedItem) {
        return repository.findById(id)
                .map(existingItem -> {
                    existingItem.setProductName(updatedItem.getProductName());
                    existingItem.setQuantity(updatedItem.getQuantity());
                    existingItem.setPrice(updatedItem.getPrice());
                    existingItem.calculateTotalPrice(); // Recalculate total price
                    return repository.save(existingItem);
                });
    }

    public boolean deleteItem(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
