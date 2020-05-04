package com.razbank.razbank.controllers.onboarding;

import com.razbank.razbank.dtos.contactInformation.ContactInformationDTO;
import com.razbank.razbank.dtos.customer.CustomerDTO;
import com.razbank.razbank.entities.contactinformation.ContactInformation;
import com.razbank.razbank.entities.login.User;
import com.razbank.razbank.responses.customer.SaveCustomerResponse;
import com.razbank.razbank.services.customer.SaveCustomerService;
import com.razbank.razbank.services.login.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/onBoarding")
public class OnBoardingController {

    private static final String SESSION = "SESSION";
    private static final String USER = "USER";

    private UserService userService;
    private final SaveCustomerService saveCustomerService;


    @Autowired
    public OnBoardingController(UserService userService, SaveCustomerService saveCustomerService) {
        this.userService = userService;
        this.saveCustomerService = saveCustomerService;
    }

    @GetMapping("/personalData")
    public String showFormForPersonalData(Model model) {
        model.addAttribute("customerDTO", new CustomerDTO());
        return "/customers/personalInformation";
    }

    @PostMapping("/savePersonalData")
    public String savePersonalData(@ModelAttribute("customerDTO") CustomerDTO customerDTO, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        session.setAttribute(SESSION, customerDTO);
        model.addAttribute("contactInformationDTO", new ContactInformationDTO());
        return "/customers/contactInformation";
    }

    @PostMapping("/saveContactInformation")
    public String saveContactInformation(@ModelAttribute("contactInformationDTO") ContactInformationDTO contactInformationDTO, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        CustomerDTO customerDTO = (CustomerDTO) session.getAttribute(SESSION);
        ContactInformation contactInformation = ContactInformation.builder().build();
        BeanUtils.copyProperties(contactInformationDTO, contactInformation);
        customerDTO.setContactInformation(contactInformation);
        SaveCustomerResponse saveCustomerResponse = saveCustomerService.save(customerDTO, session);
        BeanUtils.copyProperties(saveCustomerResponse.getCustomer(), customerDTO);
        session.setAttribute(SESSION, customerDTO);
        model.addAttribute("customerDTO", saveCustomerResponse.getCustomer());
        model.addAttribute("user", new User());//todo cambiar a dto
        return "/customers/password";
    }

    @PostMapping("/password")
    public String savePassword(@ModelAttribute("user") User user, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        CustomerDTO customerDTO = (CustomerDTO) session.getAttribute(SESSION);
        session.setAttribute(USER, userService.saveUser(user, customerDTO));
        model.addAttribute("customerDTO", customerDTO);
        model.addAttribute("user", user);//todo cambiar a dto
        return "/customers/resume";
    }

}
