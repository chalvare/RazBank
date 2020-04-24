package com.razbank.razbank.controllers;

import com.razbank.razbank.dtos.contactInformation.ContactInformationDTO;
import com.razbank.razbank.dtos.customer.CustomerDTO;
import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.entities.contactinformation.ContactInformation;
import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.entities.restriction.Restriction;
import com.razbank.razbank.entities.restriction.RestrictionIdentity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/onBoarding")
public class OnBoardingController {

    @GetMapping("/personalData")
    public String showFormForPersonalData(Model model){
        model.addAttribute("customerDTO", new CustomerDTO());
        return "/customers/personalInformation";
    }

    @PostMapping("/savePersonalData")
    public String savePersonalData(@ModelAttribute("customerDTO") CustomerDTO customerDTO, HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        session.setAttribute("SESSION", customerDTO);
        model.addAttribute("contactInformationDTO", new ContactInformationDTO());
        return "/customers/contactInformation";
    }

    @PostMapping("/saveContactInformation")
    public String saveContactInformation(@ModelAttribute("contactInformationDTO") ContactInformationDTO contactInformationDTO, HttpServletRequest request, Model model){
        CustomerDTO customerDTO= (CustomerDTO) request.getSession().getAttribute("SESSION");
        ContactInformation contactInformation = new ContactInformation();
        BeanUtils.copyProperties(contactInformationDTO, contactInformation);
        customerDTO.setContactInformation(contactInformation);
        HttpSession session = request.getSession();
        session.setAttribute("SESSION", customerDTO);
        CustomerDTO customer3= (CustomerDTO) request.getSession().getAttribute("SESSION");
        model.addAttribute("customerDTO", customer3);
        return "/customers/resume";
    }
    CustomerDTO setup() {
         Customer customer1 =new Customer();

         List<Customer> customerList;
         String NAME = "NAME";
         String LASTNAME = "LASTNAME";
         String EMAIL = "EMAIL";
         String CREATEDATE = "CREATEDATE";
         int TYPE_CUSTOMER = 0;
         String COUNTRY = "COUNTRY";
         String COUNTRY_CODE = "COUNTRY_CODE";
         String BIRTH_DATE = "BIRTH_DATE";
         String PLACE_OF_BIRTH = "PLACE_OF_BIRTH";

         int ID = 1;

        ContactInformation contactInformation1;
        ContactInformation contactInformation2;
        Account account1;
        Account account2;
        Restriction restriction1;
        Restriction restriction2;
        int ACCOUNT_NUMBER = 123;
        int STATUS = 1;
        String TIN = "TIN";
        float BALANCE = 123.12f;
        int ADDRESS_TYPE = 1;
        String ADDRESS = "ADDRESS";
        String ADDRESS_NUMBER = "ADDRESS_NUMBER";
        String CITY = "CITY";
        String POSTAL_CODE = "POSTAL_CODE";
        String PHONE = "PHONE";
        String RESTRICTION = "RESTRICTION";
        contactInformation1 = ContactInformation.builder()
                .addressType(ADDRESS_TYPE)
                .address(ADDRESS)
                .addressNumber(ADDRESS_NUMBER)
                .city(CITY)
                .postalCode(POSTAL_CODE)
                .country(COUNTRY)
                .phone(PHONE)
                .build();

        account1 = Account.builder()
                .accountNumber(ACCOUNT_NUMBER)
                .status(STATUS)
                .tin(TIN)
                .balance(BALANCE)
                .customer(customer1)
                .build();
        List<Account> accountList = new ArrayList<>();
        accountList.add(account1);

        RestrictionIdentity restrictionIdentity = new RestrictionIdentity(ID, RESTRICTION);
        restriction1 = Restriction.builder()
                .restrictionIdentity(restrictionIdentity)
                .build();
        List<Restriction> restrictionList = new ArrayList<>();
        restrictionList.add(restriction1);

        customer1 = Customer.builder()
                .name(NAME)
                .lastName(LASTNAME)
                .email(EMAIL)
                .createDate(CREATEDATE)
                .typeCustomer(TYPE_CUSTOMER)
                .accounts(accountList)
                .country(COUNTRY)
                .countryCode(COUNTRY_CODE)
                .birthDate(BIRTH_DATE)
                .placeOfBirth(PLACE_OF_BIRTH)
                .contactInformation(contactInformation1)
                .restrictions(restrictionList)
                .build();

        customerList = new ArrayList<>();
        customerList.add(customer1);
        CustomerDTO customerDTO =new CustomerDTO();
        BeanUtils.copyProperties(customer1,customerDTO);
        return  customerDTO;
    }
}
