package com.razbank.razbank.services.customer;

import com.razbank.razbank.command.customer.SaveCustomerCommand;
import com.razbank.razbank.dtos.customer.CustomerDTO;
import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.exceptions.customer.CreateCustomerException;
import com.razbank.razbank.requests.createCustomers.CreateCustomerAdultRequestImpl;
import com.razbank.razbank.requests.createCustomers.CreateCustomerChildRequestImpl;
import com.razbank.razbank.requests.createCustomers.CreateCustomerRequest;
import com.razbank.razbank.requests.createCustomers.CreateCustomerSMERequestImpl;
import com.razbank.razbank.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class CreateCustomerServiceImpl implements CreateCustomerService {

    private static final String CLASSNAME = CreateCustomerServiceImpl.class.getSimpleName();
    private final SaveCustomerCommand saveCustomerCommand;
    private final HttpSession session;

   @Autowired
    public CreateCustomerServiceImpl(SaveCustomerCommand saveCustomerCommand,HttpSession session) {
        this.saveCustomerCommand = saveCustomerCommand;
        this.session = session;
    }

    @Override
    public Response createCustomer(CustomerDTO customerDTO, HttpSession session) {
        try {
            CreateCustomerRequest customerCreateInfoRequest = typeOfCustomer(customerDTO.getTypeCustomer());
            Customer customer = buildCustomer(customerDTO);
            customerCreateInfoRequest.setSession(this.session);
            customerCreateInfoRequest.buildCustomer(customer);

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

    private Customer buildCustomer(CustomerDTO customerDTO){
       return Customer.builder()
               .name(customerDTO.getName())
               .lastName(customerDTO.getLastName())
               .email(customerDTO.getEmail())
               .createDate(customerDTO.getCreateDate())
               .typeCustomer(customerDTO.getTypeCustomer())
               .accounts(customerDTO.getAccounts())
               .country(customerDTO.getCountry())
               .countryCode(customerDTO.getCountryCode())
               .birthDate(customerDTO.getBirthDate())
               .placeOfBirth(customerDTO.getPlaceOfBirth())
               .contactInformation(customerDTO.getContactInformation()).build();
    }
}
