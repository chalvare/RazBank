package com.razbank.razbank.command.customer;

import com.razbank.razbank.command.Command;
import com.razbank.razbank.requests.createCustomers.CreateCustomerRequest;

public interface SaveCustomerCommand extends Command {
    void saveCustomer();
    void setCustomerRequest(CreateCustomerRequest createCustomerRequest);
}
