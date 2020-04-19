package com.razbank.razbank.services.customer;

import com.razbank.razbank.commands.customer.SaveCustomerAdultCommandImpl;
import com.razbank.razbank.dtos.customer.CustomerDTO;
import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.exceptions.generic.RazBankException;
import com.razbank.razbank.requests.customer.CreateCustomerAdultRequestImpl;
import com.razbank.razbank.requests.customer.CreateCustomerChildRequestImpl;
import com.razbank.razbank.requests.customer.CreateCustomerRequest;
import com.razbank.razbank.requests.customer.CreateCustomerSMERequestImpl;
import com.razbank.razbank.responses.customer.SaveCustomerResponse;
import com.razbank.razbank.utils.ResponseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * <h1>Save customer Service Implementation</h1>
 * Class which works as a service to save customer in database
 * <p>
 * <b>Note:</b> N/A
 *
 * @author Christian Álvarez
 * @version 1.0
 * @since 2020-04-18
 */
@Service
public class SaveCustomerServiceImpl implements SaveCustomerService {

    private static final String CLASSNAME = SaveCustomerServiceImpl.class.getSimpleName();
    private static final Logger logger = LoggerFactory.getLogger(SaveCustomerServiceImpl.class);

    private final SaveCustomerAdultCommandImpl saveCustomerCommand;
    private final HttpSession session;

    /**
     * Constructor
     *
     * @param saveCustomerCommand object
     * @param session object
     */
   @Autowired
    public SaveCustomerServiceImpl(SaveCustomerAdultCommandImpl saveCustomerCommand, HttpSession session) {
        this.saveCustomerCommand = saveCustomerCommand;
        this.session = session;
    }

    /**
     * Method which saves customer in database
     *
     * @param customerDTO object
     * @param session object
     * @return SaveCustomerResponse
     */
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
        }catch(RazBankException e){
            throw new RazBankException(e.getMessage(), e.getResponseInfo(), e.getWhere(), customer);
        }

        if (saveCustomerCommand.isSuccess()) {
            logger.info("SUCCESS");
            buildSaveCustomerResponse(response, customer, ResponseInfo.OK.getValue());
        }

        return response;
    }

    /**
     * Method which returns a kind of request depending of type of customer
     *
     * @param typeOfCustomer object
     * @return CreateCustomerRequest
     */
    private CreateCustomerRequest typeOfCustomer(int typeOfCustomer){
        switch (typeOfCustomer){
            case 0:
                return new CreateCustomerAdultRequestImpl();
            case 1:
                return new CreateCustomerChildRequestImpl();
            case 2:
                return new CreateCustomerSMERequestImpl();
            default:
                throw new RazBankException(CLASSNAME +": ERROR CREATING CUSTOMER: TYPE OF CUSTOMER INVALID.");
        }
    }

    /**
     * Method which builds the command response
     *  @param response object
     * @param customer object
     * @param message object
     */
    private void buildSaveCustomerResponse(SaveCustomerResponse response, Customer customer, String message) {
        response.setCustomer(customer);
        response.setResponseInfo(ResponseInfo.OK);
        response.setMessage(message);
    }


    /**
     * Method which builds an Customer from DTO
     *
     * @param customerDTO object
     * @return Customer
     */
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
               .contactInformation(customerDTO.getContactInformation())
               .restrictions(customerDTO.getRestrictions())
               .build();
    }
}
