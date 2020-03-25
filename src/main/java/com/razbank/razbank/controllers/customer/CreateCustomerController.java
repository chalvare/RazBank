package com.razbank.razbank.controllers.customer;

import com.razbank.razbank.dtos.customer.CustomerDTO;
import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.exceptions.customer.CreateCustomerException;
import com.razbank.razbank.exceptions.customer.CustomerNotFoundException;
import com.razbank.razbank.models.customer.CustomerModel;
import com.razbank.razbank.services.customer.CreateCustomerService;
import com.razbank.razbank.services.customer.CustomerService;
import com.razbank.razbank.utils.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/client")
public class CreateCustomerController {

    private CreateCustomerService createCustomerService;
    private CustomerService customerService;

    @Autowired
    public CreateCustomerController(CreateCustomerService createCustomerService,CustomerService customerService) {
        this.createCustomerService = createCustomerService;
        this.customerService = customerService;
    }

    @GetMapping(value="/client/{customerId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CustomerModel> getCustomer(@PathVariable int customerId){

        Optional<Customer> customer = customerService.findById(customerId);
        customer.orElseThrow( ()->new CustomerNotFoundException("Customer id not found - "+customerId));
        CustomerModel customerModel = new CustomerModel();
        BeanUtils.copyProperties(customer.get(), customerModel);
        return new ResponseEntity<>(customerModel, HttpStatus.OK);
    }

    @PostMapping(value="/client",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CustomerModel> create(@RequestBody CustomerModel customerModel,HttpServletRequest request){

        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customerModel, customerDTO);
        HttpSession session=request.getSession();

        Response response=createCustomerService.createCustomer(customerDTO, session);
        if(response.getCode()!=0)throw new CreateCustomerException(Response.ERROR.getMethod()+": ERROR CREATING CUSTOMER: "+customerDTO.toString());

        Customer c = (Customer) request.getSession().getAttribute("SESSION");
        BeanUtils.copyProperties(c, customerModel);

        return new ResponseEntity<>(customerModel, HttpStatus.CREATED);
    }

    @PostMapping("/invalidate/session")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "Destroy";
    }

}
