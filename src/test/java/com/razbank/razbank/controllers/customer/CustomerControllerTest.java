package com.razbank.razbank.controllers.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.entities.contactinformation.ContactInformation;
import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.entities.restriction.Restriction;
import com.razbank.razbank.entities.restriction.RestrictionIdentity;
import com.razbank.razbank.services.customer.CustomerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.MimeTypeUtils;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    private static Customer customer1;
    private static Customer customer2;
    private static List<Customer> customerList;
    private static String NAME = "NAME";
    private static String LASTNAME = "LASTNAME";
    private static String EMAIL = "EMAIL";
    private static String CREATEDATE = "CREATEDATE";
    private static int TYPE_CUSTOMER = 0;
    private static String COUNTRY = "COUNTRY";
    private static String COUNTRY_CODE = "COUNTRY_CODE";
    private static String BIRTH_DATE = "BIRTH_DATE";
    private static String PLACE_OF_BIRTH = "PLACE_OF_BIRTH";

    private static int ID = 1;

    @Autowired
    private CustomerController controller;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerService service;

    @BeforeAll
    static void setup() {
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

         contactInformation2 = ContactInformation.builder()
                .addressType(ADDRESS_TYPE)
                .address(ADDRESS)
                .addressNumber(ADDRESS_NUMBER)
                .city(CITY)
                .postalCode(POSTAL_CODE)
                .country(COUNTRY)
                .phone(PHONE)
                .build();

         account2 = Account.builder()
                .accountNumber(ACCOUNT_NUMBER)
                .status(STATUS)
                .tin(TIN)
                .balance(BALANCE)
                .build();
         List<Account> accountList2 = new ArrayList<>();
         accountList2.add(account2);

         RestrictionIdentity restrictionIdentity2 = new RestrictionIdentity(ID, RESTRICTION);
         restriction2 = Restriction.builder()
                .restrictionIdentity(restrictionIdentity2)
                .build();
         List<Restriction> restrictionList2 = new ArrayList<>();
         restrictionList2.add(restriction2);

         customer2 = Customer.builder()
                .name(NAME)
                .lastName(LASTNAME)
                .email(EMAIL)
                .createDate(CREATEDATE)
                .typeCustomer(TYPE_CUSTOMER)
                .accounts(accountList2)
                .country(COUNTRY)
                .countryCode(COUNTRY_CODE)
                .birthDate(BIRTH_DATE)
                .placeOfBirth(PLACE_OF_BIRTH)
                .contactInformation(contactInformation2)
                .restrictions(restrictionList2)
                .build();

         customerList = new ArrayList<>();
         customerList.add(customer1);
         customerList.add(customer2);
    }

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void getAllCustomersTest() {
        Mockito.when(service.findAll()).thenReturn(customerList);
        List<Customer> list = service.findAll();
        assertNotNull(list);
        assertThat(list).hasSize(2);
        verify(service, times(1)).findAll();
    }


    @Test
    void getAllCustomersTest2() throws Exception {
        // Given
        // Real application context
        final int expectedSize = customerList.size();

        // When
        Mockito.when(service.findAll()).thenReturn(customerList);
        final ResultActions result=mockMvc.perform(MockMvcRequestBuilders.get("/api/customer")
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        // Then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.length()").value(expectedSize));
        result.andExpect(jsonPath("$[0].name",containsString(NAME)));
    }

    @Test
    void getCustomerTest() throws Exception {
        // Given
        // Real application context

        // When
        Mockito.when(service.findById(ID)).thenReturn(java.util.Optional.ofNullable(customer1));
        final ResultActions result=mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/{customerId}",ID)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        // Then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.name", Matchers.containsString(NAME)));
        result.andExpect(jsonPath("$.lastName",containsString(LASTNAME)));
        result.andExpect(jsonPath("$.email",containsString(EMAIL)));
        result.andExpect(jsonPath("$.createDate",containsString(CREATEDATE)));
        result.andExpect(jsonPath("$.typeCustomer", Matchers.is(TYPE_CUSTOMER)));
        result.andExpect(jsonPath("$.country",containsString(COUNTRY)));
        result.andExpect(jsonPath("$.countryCode",containsString(COUNTRY_CODE)));
        result.andExpect(jsonPath("$.birthDate",containsString(BIRTH_DATE)));
        result.andExpect(jsonPath("$.placeOfBirth",containsString(PLACE_OF_BIRTH)));

    }
    @Test
    void saveCustomerTest() throws Exception {
        // Given
        // Real application context

        // When
        // Then
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/customer")
                .content(asJsonString(customer1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists());

    }

    @Test
    void updateCustomerTest() throws Exception {
        // Given
        // Real application context

        // When
        // Then
        this.mockMvc.perform( MockMvcRequestBuilders
                .put("/api/customer")
                .content(asJsonString(customer2))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(LASTNAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(EMAIL));
    }

    @Test
    void deleteCustomerTest() throws Exception {
        // Given
        // Real application context

        // When
        // Then
        Mockito.when(service.findById(ID)).thenReturn(java.util.Optional.ofNullable(customer1));
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/customer/{customerId}", ID) )
                .andExpect(status().isOk());
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}