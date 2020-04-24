package com.razbank.razbank.controllers.customer;

import com.razbank.razbank.dtos.customer.CustomerDTO;
import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.exceptions.generic.RazBankException;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

/**
 * <h1>Account Controller Implementation</h1>
 * Class which works as a controller accounts
 * <p>
 * <b>Note:</b> N/A
 *
 * @author Christian Álvarez
 * @version 1.0
 * @since 2020-04-22
 */
@Controller
@RequestMapping("/customer")
public class CreateCustomerController {
    //TODO FALTAN RESPOSES
    private static final Logger logger = LoggerFactory.getLogger(CreateCustomerController.class);
    private final SaveCustomerService saveCustomerService;
    private final CustomerService customerService;

    /**
     * Constructor
     *
     * @param saveCustomerService object
     * @param customerService object
     */
    @Autowired
    public CreateCustomerController(SaveCustomerService saveCustomerService, CustomerService customerService) {
        this.saveCustomerService = saveCustomerService;
        this.customerService = customerService;
    }

    /**
     * Gets customer with id
     *
     * @param customerId object
     * @return CustomerDTO
     */
    @GetMapping(value = "/customer/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable @Valid int customerId) {
        Optional<Customer> customer = customerService.findById(customerId);
        CustomerDTO customerDTO = new CustomerDTO();
        customer.ifPresent(value -> BeanUtils.copyProperties(value, customerDTO));
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    /**
     * Saves customer
     *
     * @param customerDTO object
     * @param request object
     * @return CustomerDTO
     */
    @PostMapping(value = "/customer")
    public ResponseEntity<CustomerDTO> save(@ModelAttribute("customerDTO") CustomerDTO customerDTO, HttpServletRequest request) {

        /*if (!Validator.validate(customerDTO)) {
            logger.error("INVALID CUSTOMER: {}", customerDTO);
            throw new RazBankException("INVALID CUSTOMER: " + customerDTO.toString());
        }*/

        HttpSession session = request.getSession();

        SaveCustomerResponse response = saveCustomerService.save(customerDTO, session);
        Customer c = (Customer) request.getSession().getAttribute("SESSION");
        logger.info("CUSTOMER FROM SESSION: {}", c);

        Customer customer = response.getCustomer();
        BeanUtils.copyProperties(customer, customerDTO);

        return new ResponseEntity<>(customerDTO, HttpStatus.CREATED);

    }

    /**
     * Deletes customer
     *
     * @param customerId object
     * @return String object
     */
    //No utilizar salvo para pruebas. Poner Cascade.ALL para borrar todos los registros
    @DeleteMapping(value = "/customer/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable @Valid int customerId) {
        Optional<Customer> customer = customerService.findById(customerId);
        customerService.deleteById(customer.orElseThrow(() -> new RazBankException("Customer id not found - " + customerId))
                .getId());
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }


    /**
     * Destroy session
     *
     * @param request object
     */
    @PostMapping("/invalidate/session")
    public void destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
    }



}
