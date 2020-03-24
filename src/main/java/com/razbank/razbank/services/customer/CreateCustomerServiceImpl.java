package com.razbank.razbank.services.customer;

import com.razbank.razbank.command.customer.SaveCustomerCommand;
import com.razbank.razbank.dtos.customer.CustomerDTO;
import com.razbank.razbank.exceptions.customer.CreateCustomerException;
import com.razbank.razbank.requests.createCustomers.CreateCustomerAdultRequestImpl;
import com.razbank.razbank.requests.createCustomers.CreateCustomerChildRequestImpl;
import com.razbank.razbank.requests.createCustomers.CreateCustomerRequest;
import com.razbank.razbank.requests.createCustomers.CreateCustomerSMERequestImpl;
import com.razbank.razbank.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCustomerServiceImpl implements CreateCustomerService {

    private static String CLASSNAME = CreateCustomerServiceImpl.class.getSimpleName();
    private SaveCustomerCommand saveCustomerCommand;

   @Autowired
    public CreateCustomerServiceImpl(SaveCustomerCommand saveCustomerCommand) {
        this.saveCustomerCommand = saveCustomerCommand;
    }

    @Override
    public Response createCustomer(CustomerDTO customerDTO) {
        try {
            CreateCustomerRequest customerCreateInfoRequest = typeOfCustomer(customerDTO.getTypeCustomer());
            customerCreateInfoRequest.buildCustomer(customerDTO);
            saveCustomerCommand.setCustomerRequest(customerCreateInfoRequest);
            saveCustomerCommand.execute();
        }catch(Exception e){
            Response.ERROR.setMethod(CLASSNAME+":"+new Exception().getStackTrace()[0].getMethodName());
            return Response.ERROR;
        }

        return Response.OK;
    }

    private CreateCustomerRequest typeOfCustomer(int typeOfCustomer){
        switch (typeOfCustomer){
            case 0:
                return new CreateCustomerAdultRequestImpl();
            case 1:
                return new CreateCustomerChildRequestImpl();
            case 2:
                return new CreateCustomerSMERequestImpl();
            default:
                throw new CreateCustomerException(Response.ERROR.getMethod()+": ERROR CREATING CUSTOMER: TYPE OF CUSTOMER INVALID.");
        }


    }

}
