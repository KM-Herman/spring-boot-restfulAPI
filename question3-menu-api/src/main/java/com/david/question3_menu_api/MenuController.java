package com.david.question3_menu_api;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final List<MenuItem> menuItems = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public MenuController() {
        // Initialize with 8 items
        addInitialMenuItem(new MenuItem(null, "Spring Rolls", "Vegetable spring rolls", 5.99, "Appetizer", true));
        addInitialMenuItem(new MenuItem(null, "Caesar Salad", "Classic Caesar salad", 8.99, "Appetizer", true));
        addInitialMenuItem(new MenuItem(null, "Grilled Salmon", "Salmon with fresh herbs", 18.99, "Main Course", true));
        addInitialMenuItem(new MenuItem(null, "Steak Frites", "Grilled steak with fries", 22.99, "Main Course", true));
        addInitialMenuItem(new MenuItem(null, "Chocolate Cake", "Rich chocolate cake", 6.99, "Dessert", true));
        addInitialMenuItem(new MenuItem(null, "Cheesecake", "New York style cheesecake", 7.99, "Dessert", false)); // Not
        // available
        addInitialMenuItem(new MenuItem(null, "Iced Tea", "Freshly brewed iced tea", 2.99, "Beverage", true));
        addInitialMenuItem(new MenuItem(null, "Lemonade", "Homemade lemonade", 3.49, "Beverage", true));
    }

    private void addInitialMenuItem(MenuItem item) {
        long id = counter.incrementAndGet();
        item.setId(id);
        menuItems.add(item);
    }

    // GET /api/menu - Get all menu items
    @GetMapping
    public List<MenuItem> getAllMenuItems() {
        return menuItems;
    }

    // GET /api/menu/{id} - Get specific menu item
    @GetMapping("/{id}")
    public MenuItem getMenuItemById(@PathVariable Long id) {
        return menuItems.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElse(null); // Simple null return for now, could throw exception
    }

    // GET /api/menu/category/{category} - Get items by category
    @GetMapping("/category/{category}")
    public List<MenuItem> getItemsByCategory(@PathVariable String category) {
        return menuItems.stream()
                .filter(item -> item.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    // GET /api/menu/available - Get only available items
    @GetMapping("/available")
    public List<MenuItem> getAvailableItems(@RequestParam(defaultValue = "true") boolean available) {
        return menuItems.stream()
                .filter(item -> item.isAvailable() == available)
                .collect(Collectors.toList());
    }

    // GET /api/menu/search?name={name} - Search menu items by name
    @GetMapping("/search")
    public List<MenuItem> searchItems(@RequestParam String name) {
        return menuItems.stream()
                .filter(item -> item.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    // POST /api/menu - Add new menu item
    @PostMapping
    public MenuItem addMenuItem(@RequestBody MenuItem menuItem) {
        addInitialMenuItem(menuItem);
        return menuItem;
    }

    // PUT /api/menu/{id}/availability - Toggle item availability
    @PutMapping("/{id}/availability")
    public MenuItem toggleAvailability(@PathVariable Long id) {
        Optional<MenuItem> itemOpt = menuItems.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();

        if (itemOpt.isPresent()) {
            MenuItem item = itemOpt.get();
            item.setAvailable(!item.isAvailable());
            return item;
        }
        return null; // Or throw 404
    }

    // DELETE /api/menu/{id} - Remove menu item
    @DeleteMapping("/{id}")
    public void deleteMenuItem(@PathVariable Long id) {
        menuItems.removeIf(item -> item.getId().equals(id));
    }
}
