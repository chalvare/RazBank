package com.razbank.razbank.responses.customer;

import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.responses.GenericResponse;
import com.razbank.razbank.utils.ResponseInfo;
import lombok.Data;

@Data
public class SaveCustomerResponse extends GenericResponse {
    Customer customer;

    /**
     * Method which builds the command response
     *  @param response object
     * @param customer object
     * @param message object
     */
    public void buildSaveCustomerResponse(SaveCustomerResponse response, Customer customer, String message) {
        response.setCustomer(customer);
        response.setResponseInfo(ResponseInfo.OK);
        response.setMessage(message);
    }
}
