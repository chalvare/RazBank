package com.razbank.razbank.controllers.customer;

import com.razbank.razbank.dtos.customer.CustomerDTO;
import com.razbank.razbank.exceptions.customer.CreateCustomerException;
import com.razbank.razbank.models.customer.CustomerModel;
import com.razbank.razbank.services.customer.CreateCustomerService;
import com.razbank.razbank.utils.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/createaccount")
public class CreateCustomerController {

    private CreateCustomerService createCustomerService;

    @PostMapping("/test")
    public String addNote(@RequestBody CustomerModel customerModel, HttpServletRequest request) {
        //get the notes from request session
        List<CustomerModel> notes = (List<CustomerModel>) request.getSession().getAttribute("SESSION");
        //check if notes is present in session or not
        if (notes == null) {
            notes = new ArrayList<>();
            // if notes object is not present in session, set notes in the request session
            request.getSession().setAttribute("SESSION", notes);
        }
        notes.add(customerModel);
        request.getSession().setAttribute("SESSION", notes);

        return null;
    }
    @GetMapping("/home")
    public String home(HttpSession session) {
        List<CustomerModel> customerModelList = (List<CustomerModel>) session.getAttribute("SESSION");
        return null;
    }
    @PostMapping("/invalidate/session")
    public String destroySession(HttpServletRequest request) {
        //invalidate the session , this will clear the data from configured database (Mysql/redis/hazelcast)
        request.getSession().invalidate();
        return null;
    }

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
