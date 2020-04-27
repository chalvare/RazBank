package com.razbank.razbank.responses.account;

import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.responses.GenericResponse;
import com.razbank.razbank.utils.ResponseInfo;
import lombok.Data;

@Data
public class SaveAccountResponse extends GenericResponse {
    private Account account;

    /**
     * Method which builds the command response
     *  @param response object
     * @param account object
     * @param message object
     */
    public void buildSaveAccountResponse(SaveAccountResponse response, Account account, String message) {
        response.setAccount(account);
        response.setResponseInfo(ResponseInfo.OK);
        response.setMessage(message);
    }
}
