package com.razbank.razbank.command.customer;

import com.razbank.razbank.command.Command;
import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.requests.createCustomers.CreateCustomerRequest;

public interface SaveCustomerCommand extends Command {
    public void saveCustomer();
    public void setCustomerRequest(CreateCustomerRequest createCustomerRequest);
}
