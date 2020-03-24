package com.razbank.razbank.controllers.customer;

import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.exceptions.customer.CustomerNotFoundException;
import com.razbank.razbank.services.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustController {
/*
    private CustomerService customerService;

    @Autowired
    public CustController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/list")
    public String listCustomers(Model model){

        List<Customer>customers=customerService.findAll();
        System.out.println(customers.toString());
        model.addAttribute("customers",customers);

        return "/customers/list-customers";
    }

    @GetMapping("/customer/{customerId}")
    public String getCustomer(@PathVariable int customerId, Model model){
        Optional<Customer> customer = customerService.findById(customerId);
        customer.orElseThrow( ()->new CustomerNotFoundException("Customer id not found - "+customerId));
        System.out.println(customer.get());
        model.addAttribute("customer",customer.get());

        return "test";
    }



    @GetMapping("/showFormForAdd")
    public String showFormForAddCustomer(Model model){
        Customer customer = new Customer();

        model.addAttribute("customer", customer);

        return "/customers/customer-form";
    }

    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute Customer customer){
        //customer=new Customer("prueba","apellido","mail",new Date(Calendar.getInstance().getTime().getTime()).toString());
        customerService.save(customer);

        return "redirect:/customers/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdateCustomer(@RequestParam("customerId") int customerId, Model model){
        Optional<Customer> customer = customerService.findById(customerId);
        customer.orElseThrow( ()->new CustomerNotFoundException("Customer id not found - "+customerId));
        System.out.println(customer.get());
        model.addAttribute("customer",customer.get());

        return "/customers/customer-form";
    }

    @GetMapping("/delete")
    public String deleteCustomer(@RequestParam("customerId")int customerId){
        Optional<Customer> customer = customerService.findById(customerId);
        customer.orElseThrow( ()->new CustomerNotFoundException("Customer id not found - "+customerId));
        customerService.deleteById(customerId);
        return "redirect:/customers/list";
    }

    @GetMapping("/search")
    public String searchCustomer(@RequestParam("customerName") String name,
                         Model theModel) {

        // delete the employee
        List<Customer> customers = customerService.searchByName(name);

        // add to the spring model
        theModel.addAttribute("customers", customers);

        // send to /employees/list
        return "/customers/list-customers";

    }
*/
}
