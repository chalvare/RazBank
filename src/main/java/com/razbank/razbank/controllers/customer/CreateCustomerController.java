package com.razbank.razbank.controllers.customer;

import com.razbank.razbank.dtos.customer.CustomerDTO;
import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.exceptions.customer.CreateCustomerException;
import com.razbank.razbank.models.customer.CustomerModel;
import com.razbank.razbank.services.customer.CreateCustomerService;
import com.razbank.razbank.utils.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/createaccount")
public class CreateCustomerController {

    private CreateCustomerService createCustomerService;

    @Autowired
    public CreateCustomerController(CreateCustomerService createCustomerService) {
        this.createCustomerService = createCustomerService;
    }

    @PostMapping(value="/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> create(@RequestBody CustomerModel customerModel){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customerModel, customerDTO);
        Response response=createCustomerService.createCustomer(customerDTO);
        if(response.getCode()!=0)throw new CreateCustomerException(Response.ERROR.getMethod()+": ERROR CREATING CUSTOMER: "+customerDTO.toString());
        return new ResponseEntity<>( "User Created: "+response, HttpStatus.CREATED);
    }
}
