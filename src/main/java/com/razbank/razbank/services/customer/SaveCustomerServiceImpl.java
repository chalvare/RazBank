package com.razbank.razbank.services.customer;

import com.razbank.razbank.commands.customer.SaveCustomerAdultCommandImpl;
import com.razbank.razbank.dtos.customer.CustomerDTO;
import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.exceptions.customer.CreateCustomerException;
import com.razbank.razbank.requests.createCustomers.CreateCustomerAdultRequestImpl;
import com.razbank.razbank.requests.createCustomers.CreateCustomerChildRequestImpl;
import com.razbank.razbank.requests.createCustomers.CreateCustomerRequest;
import com.razbank.razbank.requests.createCustomers.CreateCustomerSMERequestImpl;
import com.razbank.razbank.responses.customer.SaveCustomerResponse;
import com.razbank.razbank.utils.ResponseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class SaveCustomerServiceImpl implements SaveCustomerService {

    private static final String CLASSNAME = SaveCustomerServiceImpl.class.getSimpleName();
    private static final Logger logger = LoggerFactory.getLogger(SaveCustomerServiceImpl.class);

    private final SaveCustomerAdultCommandImpl saveCustomerCommand;
    private final HttpSession session;

   @Autowired
    public SaveCustomerServiceImpl(SaveCustomerAdultCommandImpl saveCustomerCommand, HttpSession session) {
        this.saveCustomerCommand = saveCustomerCommand;
        this.session = session;
    }

    @Override
    public SaveCustomerResponse save(CustomerDTO customerDTO, HttpSession session) {
        logger.info("SERVICE: {}", CLASSNAME);
        SaveCustomerResponse response = new SaveCustomerResponse();
        Customer customer = null;

        try {
            CreateCustomerRequest customerCreateInfoRequest = typeOfCustomer(customerDTO.getTypeCustomer());
            customer = buildCustomer(customerDTO);
            customerCreateInfoRequest.setSession(this.session);
            customerCreateInfoRequest.buildCustomer(customer);
            saveCustomerCommand.setCustomerRequest(customerCreateInfoRequest);
            saveCustomerCommand.execute();
        }catch(Exception e){
            response.setCustomer(customer);
            response.setResponseInfo(ResponseInfo.GENERIC_ERROR);
            response.setMessage(e.getMessage());
        }

        if (saveCustomerCommand.isSuccess()) {
            response.setCustomer(customer);
            response.setResponseInfo(ResponseInfo.OK);
        }

        return response;
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
                throw new CreateCustomerException(CLASSNAME +": ERROR CREATING CUSTOMER: TYPE OF CUSTOMER INVALID.");
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
