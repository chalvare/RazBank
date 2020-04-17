package com.razbank.razbank.commands.customer;

import com.razbank.razbank.commands.Command;
import com.razbank.razbank.requests.createCustomers.CreateCustomerRequest;

public interface SaveCustomerCommand extends Command {
    void saveCustomer();
    void setCustomerRequest(CreateCustomerRequest createCustomerRequest);
}
