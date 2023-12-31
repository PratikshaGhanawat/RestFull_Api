package com.example.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/addItem")
    public ResponseEntity<InventoryItem> addItem(@RequestBody InventoryItem item) {
        InventoryItem addedItem = inventoryService.addItem(item);
        return new ResponseEntity<>(addedItem, HttpStatus.CREATED);
    }

    @GetMapping("/getAllItems")
    public ResponseEntity<List<InventoryItem>> getAllItems() {
        List<InventoryItem> itemList = inventoryService.getAllItems();
        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }

    @GetMapping("/getItem/{id}")
    public ResponseEntity<InventoryItem> getItemById(@PathVariable Long id) {
        Optional<InventoryItem> item = inventoryService.getItemById(id);
        return item.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/updateItem/{id}")
    public ResponseEntity<InventoryItem> updateItem(@PathVariable Long id, @RequestBody InventoryItem updatedItem) {
        Optional<InventoryItem> updatedItemOptional = inventoryService.updateItem(id, updatedItem);
        return updatedItemOptional.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/deleteItem/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        boolean deleted = inventoryService.deleteItem(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}