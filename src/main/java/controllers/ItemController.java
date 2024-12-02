package controllers;
import com.example.springboot.models.ItemModel;
import com.example.springboot.repositories.ItemRepository;
import dtos.ItemRecordDto;
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
public class ItemController {

    @Autowired
    ItemRepository ItemRepository;

    @PostMapping("/items")
    public ResponseEntity<ItemModel> saveItem(@RequestBody @Valid ItemRecordDto ItemRecordDto){
        var itemModel = new ItemModel();
        BeanUtils.copyProperties(ItemRecordDto, itemModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(ItemRepository.save(itemModel));
    }

    @GetMapping("/items")
    public ResponseEntity<List<ItemModel>> getAllItems(){
        List<ItemModel> itemsList = ItemRepository.findAll();
        if(!itemsList.isEmpty()){
            for(ItemModel item : itemsList){
                UUID id = item.getIdItem();
                item.add(linkTo(methodOn(ItemController.class).getOneItem(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(ItemRepository.findAll());
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<Object> getOneItem(@PathVariable(value="id") UUID id){
        Optional<ItemModel> itemO = ItemRepository.findById(id);
        if(itemO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item não encontrado");
        }
        itemO.get().add(linkTo(methodOn(ItemController.class).getAllItems()).withRel("Lista de Itens"));
        return ResponseEntity.status(HttpStatus.OK).body(itemO.get());
    }

    @PutMapping("/items/{id}")
        public ResponseEntity<Object> updateItem(@PathVariable(value="id") UUID id, @RequestBody @Valid ItemRecordDto ItemRecordDto) {
        Optional<ItemModel> itemO = ItemRepository.findById(id);
        if(itemO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item não encontrado");
        }
        var ItemModel = itemO.get();
        BeanUtils.copyProperties(ItemRecordDto, ItemModel);
        return ResponseEntity.status(HttpStatus.OK).body(ItemRepository.save(ItemModel));
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Object> deleteItem(@PathVariable(value="id") UUID id){
        Optional<ItemModel> itemO = ItemRepository.findById(id);
        if(itemO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item não encontrado");
        }
        ItemRepository.delete(itemO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Item deletado com sucesso");
    }
}
