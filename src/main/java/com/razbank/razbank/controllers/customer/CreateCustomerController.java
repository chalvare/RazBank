package com.razbank.razbank.controllers.customer;

import com.razbank.razbank.dtos.customer.CustomerDTO;
import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.exceptions.customer.CreateCustomerException;
import com.razbank.razbank.exceptions.customer.CustomerNotFoundException;
import com.razbank.razbank.models.customer.CustomerModel;
import com.razbank.razbank.services.customer.CreateCustomerService;
import com.razbank.razbank.services.customer.CustomerService;
import com.razbank.razbank.utils.Response;
import com.razbank.razbank.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/client")
public class CreateCustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CreateCustomerController.class);
    private final CreateCustomerService createCustomerService;
    private final CustomerService customerService;

    @Autowired
    public CreateCustomerController(CreateCustomerService createCustomerService,CustomerService customerService) {
        this.createCustomerService = createCustomerService;
        this.customerService = customerService;
    }

    @GetMapping(value="/client/{customerId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CustomerModel> getCustomer(@PathVariable @Valid int customerId){
        Optional<Customer> customer = customerService.findById(customerId);
        CustomerModel customerModel = new CustomerModel();
        BeanUtils.copyProperties(
                customer.orElseThrow( ()->new CustomerNotFoundException("Customer id not found - "+customerId)),
                customerModel);
        return new ResponseEntity<>(customerModel, HttpStatus.OK);
    }

    @PostMapping(value="/client",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CustomerModel> create(@RequestBody @Valid CustomerModel customerModel, HttpServletRequest request){

        if(!Validator.validate(customerModel)){
            logger.error("INVALID CUSTOMER: {}",customerModel);
            throw new CreateCustomerException("INVALID CUSTOMER: "+customerModel.toString());
        }

        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customerModel, customerDTO);
        HttpSession session=request.getSession();

        Response response=createCustomerService.createCustomer(customerDTO, session);
        if(response.getCode()!=0)throw new CreateCustomerException(Response.ERROR.getMethod()+": ERROR CREATING CUSTOMER: "+customerDTO.toString());

        Customer c = (Customer) request.getSession().getAttribute("SESSION");
        BeanUtils.copyProperties(c, customerModel);

        return new ResponseEntity<>(customerModel, HttpStatus.CREATED);
    }

    //No utilizar salvo para pruebas. Poner Cascade.ALL para borrar todos los registros
    @DeleteMapping(value="/client/{customerId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> deleteCustomer(@PathVariable @Valid int customerId){
        Optional<Customer> customer = customerService.findById(customerId);
        customerService.deleteById(customer.orElseThrow( ()->new CustomerNotFoundException("Customer id not found - "+customerId))
                                    .getId());
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @PostMapping("/invalidate/session")
    public void destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
    }

}
