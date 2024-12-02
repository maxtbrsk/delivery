package com.example.springboot.controllers;

import com.example.springboot.models.ItemModel;
import com.example.springboot.repositories.ItemRepository;
import com.example.springboot.dtos.ItemRecordDto;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    ItemRepository itemRepository;

    @PostMapping
    public ResponseEntity<ItemModel> saveItem(@RequestBody @Valid ItemRecordDto itemRecordDto){
        var itemModel = new ItemModel();
        BeanUtils.copyProperties(itemRecordDto, itemModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemRepository.save(itemModel));
    }

    @GetMapping
    public ResponseEntity<List<ItemModel>> getAllItems(){
        List<ItemModel> itemsList = itemRepository.findAll();
        if(!itemsList.isEmpty()){
            for(ItemModel item : itemsList){
                UUID id = item.getIdItem();
                item.add(linkTo(methodOn(ItemController.class).getOneItem(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(itemRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneItem(@PathVariable(value="id") UUID id){
        Optional<ItemModel> itemO = itemRepository.findById(id);
        if(itemO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item não encontrado");
        }
        itemO.get().add(linkTo(methodOn(ItemController.class).getAllItems()).withRel("Lista de Itens"));
        return ResponseEntity.status(HttpStatus.OK).body(itemO.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateItem(@PathVariable(value="id") UUID id, @RequestBody @Valid ItemRecordDto itemRecordDto) {
        Optional<ItemModel> itemO = itemRepository.findById(id);
        if(itemO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item não encontrado");
        }
        var itemModel = itemO.get();
        BeanUtils.copyProperties(itemRecordDto, itemModel);
        return ResponseEntity.status(HttpStatus.OK).body(itemRepository.save(itemModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteItem(@PathVariable(value="id") UUID id){
        Optional<ItemModel> itemO = itemRepository.findById(id);
        if(itemO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item não encontrado");
        }
        itemRepository.delete(itemO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Item deletado com sucesso");
    }
}