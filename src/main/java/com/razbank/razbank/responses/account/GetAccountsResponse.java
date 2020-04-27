package com.razbank.razbank.responses.account;

import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.responses.GenericResponse;
import com.razbank.razbank.utils.ResponseInfo;
import lombok.Data;

import java.util.List;


@Data
public class GetAccountsResponse extends GenericResponse {
    private int customerId;
    private List<Account> accountList;

    /**
     * Method which builds the command response
     *  @param response object
     * @param customerId object
     * @param accountList object
     * @param message object
     */
    public void buildGetAccountsResponse(GetAccountsResponse response, int customerId, List<Account> accountList, String message) {
        response.setCustomerId(customerId);
        response.setAccountList(accountList);
        response.setResponseInfo(ResponseInfo.OK);
        response.setMessage(message);
    }


}
