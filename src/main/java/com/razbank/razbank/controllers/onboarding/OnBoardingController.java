package com.razbank.razbank.controllers.onboarding;

import com.razbank.razbank.dtos.contactInformation.ContactInformationDTO;
import com.razbank.razbank.dtos.customer.CustomerDTO;
import com.razbank.razbank.dtos.otp.OtpDTO;
import com.razbank.razbank.entities.contactinformation.ContactInformation;
import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.entities.login.User;
import com.razbank.razbank.entities.otp.Otp;
import com.razbank.razbank.responses.customer.SaveCustomerResponse;
import com.razbank.razbank.responses.otp.SaveOtpResponse;
import com.razbank.razbank.responses.otp.VerifyOtpResponse;
import com.razbank.razbank.services.customer.SaveCustomerService;
import com.razbank.razbank.services.login.UserService;
import com.razbank.razbank.services.otp.OtpServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/onBoarding")
public class OnBoardingController {

    private static final String SESSION = "SESSION";
    private static final String USER = "USER";

    private UserService userService;
    private final SaveCustomerService saveCustomerService;
    private OtpServiceImpl otpServiceImpl;


    @Autowired
    public OnBoardingController(UserService userService, SaveCustomerService saveCustomerService, OtpServiceImpl otpServiceImpl) {
        this.userService = userService;
        this.saveCustomerService = saveCustomerService;
        this.otpServiceImpl = otpServiceImpl;
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
        model.addAttribute("otp", new Otp());//todo cambiar a dto
        OtpDTO otpDTO = new OtpDTO();
        otpDTO.setCustomerId(customerDTO.getId());
        otpDTO.setPhone("+34"+customerDTO.getContactInformation().getPhone());//TODO incluir el +34 con el uso de PhoneNumber
        SaveOtpResponse response = otpServiceImpl.save(otpDTO);
        if(response.getResponseInfo().getCode()==0){
            //todo save password/user
        }
        return "/customers/otp";
    }

    @PostMapping("/otp")
    public String otpVerify(@ModelAttribute("otp") Otp otp, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        CustomerDTO customerDTO = (CustomerDTO) session.getAttribute(SESSION);
        VerifyOtpResponse response = otpServiceImpl.verify(otp.getOtpCode(), customerDTO.getId());
        model.addAttribute("customerDTO", customerDTO);
        if (response.getResponseInfo().getCode() == 0) {
            return "/customers/resume";//todo el boton de ser cliente tendría que cambiar el cliente activo a 1 y enviar a login
        } else {
            return "KO";
        }
    }


}
