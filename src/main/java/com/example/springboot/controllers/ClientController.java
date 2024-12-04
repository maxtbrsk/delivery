package com.example.springboot.controllers;

import com.example.springboot.models.ClientModel;
import com.example.springboot.repositories.ClientRepository;
import com.example.springboot.dtos.ClientRecordDto;
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
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @PostMapping
    public ResponseEntity<Object> saveClient(@RequestBody @Valid ClientRecordDto clientRecordDto){
        if (clientRepository.existsByCpf(clientRecordDto.cpf())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF já cadastrado");
        }
        if (clientRepository.existsByTelefone(clientRecordDto.telefone())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Telefone já cadastrado");
        }
        var clientModel = new ClientModel();
        BeanUtils.copyProperties(clientRecordDto, clientModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientRepository.save(clientModel));
    }

    @GetMapping
    public ResponseEntity<List<ClientModel>> getAllClients(){
        List<ClientModel> clientsList = clientRepository.findAll();
        if(!clientsList.isEmpty()){
            for(ClientModel client : clientsList){
                UUID id = client.getIdClient();
                client.add(linkTo(methodOn(ClientController.class).getOneClient(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(clientRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneClient(@PathVariable(value="id") UUID id){
        Optional<ClientModel> clientO = clientRepository.findById(id);
        if(clientO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }
        clientO.get().add(linkTo(methodOn(ClientController.class).getAllClients()).withRel("Lista de Clientes"));
        return ResponseEntity.status(HttpStatus.OK).body(clientO.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateClient(@PathVariable(value="id") UUID id, @RequestBody @Valid ClientRecordDto clientRecordDto) {
        Optional<ClientModel> clientO = clientRepository.findById(id);
        if(clientO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }
        var clientModel = clientO.get();
        BeanUtils.copyProperties(clientRecordDto, clientModel);
        return ResponseEntity.status(HttpStatus.OK).body(clientRepository.save(clientModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteClient(@PathVariable(value="id") UUID id){
        Optional<ClientModel> clientO = clientRepository.findById(id);
        if(clientO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }
        clientRepository.delete(clientO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Cliente deletado com sucesso");
    }
}
