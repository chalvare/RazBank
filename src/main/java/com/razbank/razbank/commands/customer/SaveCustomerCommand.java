package com.razbank.razbank.commands.customer;

import com.razbank.razbank.commands.Command;
import com.razbank.razbank.requests.createCustomers.CreateCustomerAdultRequestImpl;
import com.razbank.razbank.requests.createCustomers.CreateCustomerRequest;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public abstract class SaveCustomerCommand implements Command {

    private CreateCustomerAdultRequestImpl createCustomerAdultRequestImpl;

    public abstract void saveCustomer();
    public abstract void setCustomerRequest(CreateCustomerRequest createCustomerRequest);
}
