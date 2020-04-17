package com.razbank.razbank.controllers.customer;

import com.razbank.razbank.dtos.customer.CustomerDTO;
import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.exceptions.customer.CreateCustomerException;
import com.razbank.razbank.exceptions.customer.CustomerNotFoundException;
import com.razbank.razbank.responses.customer.SaveCustomerResponse;
import com.razbank.razbank.services.customer.SaveCustomerService;
import com.razbank.razbank.services.customer.CustomerService;
import com.razbank.razbank.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/client")
public class CreateCustomerController {
    //TODO FALTAN RESPOSES
    //TODO FALTAN COMMENTS
    private static final Logger logger = LoggerFactory.getLogger(CreateCustomerController.class);
    private final SaveCustomerService saveCustomerService;
    private final CustomerService customerService;

    @Autowired
    public CreateCustomerController(SaveCustomerService saveCustomerService, CustomerService customerService) {
        this.saveCustomerService = saveCustomerService;
        this.customerService = customerService;
    }

    @GetMapping(value = "/client/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable @Valid int customerId) {
        Optional<Customer> customer = customerService.findById(customerId);
        CustomerDTO customerDTO = new CustomerDTO();
        customer.ifPresent(value -> BeanUtils.copyProperties(value, customerDTO));
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/client")
    public ResponseEntity<CustomerDTO> save(@RequestBody @Valid CustomerDTO customerDTO, HttpServletRequest request) {

        if (!Validator.validate(customerDTO)) {
            logger.error("INVALID CUSTOMER: {}", customerDTO);
            throw new CreateCustomerException("INVALID CUSTOMER: " + customerDTO.toString());
        }

        HttpSession session = request.getSession();

        SaveCustomerResponse response = saveCustomerService.save(customerDTO, session);
        if (response.getResponseInfo().getCode() != 0)
            throw new CreateCustomerException(response.getResponseInfo().getCode() + " ==> " + response.getMessage() + ": ERROR CREATING CUSTOMER: " + customerDTO.toString());

        Customer c = (Customer) request.getSession().getAttribute("SESSION");
        logger.info("CUSTOMER FROM SESSION: {}", c);

        Customer customer = response.getCustomer();
        BeanUtils.copyProperties(customer, customerDTO);

        return new ResponseEntity<>(customerDTO, HttpStatus.CREATED);
    }

    //No utilizar salvo para pruebas. Poner Cascade.ALL para borrar todos los registros
    @DeleteMapping(value = "/client/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable @Valid int customerId) {
        Optional<Customer> customer = customerService.findById(customerId);
        customerService.deleteById(customer.orElseThrow(() -> new CustomerNotFoundException("Customer id not found - " + customerId))
                .getId());
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @PostMapping("/invalidate/session")
    public void destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
    }

}
