package com.restaurant.rms.controller;

import com.restaurant.rms.dto.request.RestaurantDTO;
import com.restaurant.rms.dto.request.RestaurantMenuItemDTO;
import com.restaurant.rms.service.RestaurantMenuItemService.RestaurantMenuItemService;
import com.restaurant.rms.util.error.IdInvalidException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/menu_item")
@SecurityRequirement(name = "api")
public class RestaurantMenuItemController {
    private RestaurantMenuItemService restaurantMenuItemService;

    @PostMapping("/create")
//    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    public ResponseEntity<RestaurantMenuItemDTO> createRestaurantMenuItem(@Valid @RequestBody RestaurantMenuItemDTO restaurantMenuItemDTO) throws IdInvalidException {
        RestaurantMenuItemDTO savedRestaurantMenuItem = restaurantMenuItemService.createRestaurantMenuItem(restaurantMenuItemDTO);
        return ResponseEntity.status(HttpStatus.OK).body(savedRestaurantMenuItem);
    }

    @GetMapping("{menu_item_id}")
    public ResponseEntity<RestaurantMenuItemDTO> getRestaurantById (@PathVariable("menu_item_id") Integer menu_item_id) throws IdInvalidException {
        RestaurantMenuItemDTO restaurantMenuItemDTO = restaurantMenuItemService.getRestaurantMenuItemById(menu_item_id);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantMenuItemDTO);
    }

    @GetMapping("/get")
    public ResponseEntity<List<RestaurantMenuItemDTO>> getRestaurantAll(){
        List<RestaurantMenuItemDTO> restaurantMenuItemDTO = restaurantMenuItemService.getRestaurantMenuItemAll();
        return ResponseEntity.ok(restaurantMenuItemDTO);
    }

    @PutMapping("/{menu_item_id}")
//    @PreAuthorize("hasAnyRole('CONTENT_MANAGER', 'MANAGER')")
    public ResponseEntity<RestaurantMenuItemDTO> updateRestaurantMenuItem (@RequestBody RestaurantMenuItemDTO updatedRestaurant, @PathVariable("menu_item_id") Integer menu_item_id){
        RestaurantMenuItemDTO restaurantMenuItemDTO = restaurantMenuItemService.updateRestaurantMenuItem(updatedRestaurant, menu_item_id);
        return ResponseEntity.ok(restaurantMenuItemDTO);
    }

    @DeleteMapping("{menu_item_id}")
//    @PreAuthorize("hasAnyRole('CONTENT_MANAGER', 'MANAGER')")
    public ResponseEntity<Void> deleteRestaurantMenuItem( @PathVariable("menu_item_id") Integer menu_item_id) throws IdInvalidException {
        RestaurantMenuItemDTO currentRestaurantMenuItem = restaurantMenuItemService.getRestaurantMenuItemById(menu_item_id);
        restaurantMenuItemService.deleteRestaurantMenuItem(menu_item_id);
        return ResponseEntity.ok(null);
    }

//    @GetMapping("user/{user_id}")
//    public ResponseEntity<RestaurantDTO> findRestaurantByUserId(@PathVariable("user_id") int user_id) throws IdInvalidException {
//        RestaurantDTO restaurantDTO = restaurantService.findRestaurantByUserId(user_id);
//        return ResponseEntity.ok(restaurantDTO);
//    }
}
