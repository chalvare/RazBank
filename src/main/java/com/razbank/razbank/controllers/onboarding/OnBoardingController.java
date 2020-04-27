package com.razbank.razbank.controllers.onboarding;

import com.razbank.razbank.dtos.contactInformation.ContactInformationDTO;
import com.razbank.razbank.dtos.customer.CustomerDTO;
import com.razbank.razbank.entities.contactinformation.ContactInformation;
import org.springframework.beans.BeanUtils;
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

    @GetMapping("/personalData")
    public String showFormForPersonalData(Model model){
        model.addAttribute("customerDTO", new CustomerDTO());
        return "/customers/personalInformation";
    }

    @PostMapping("/savePersonalData")
    public String savePersonalData(@ModelAttribute("customerDTO") CustomerDTO customerDTO, HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        session.setAttribute(SESSION, customerDTO);
        model.addAttribute("contactInformationDTO", new ContactInformationDTO());
        return "/customers/contactInformation";
    }

    @PostMapping("/saveContactInformation")
    public String saveContactInformation(@ModelAttribute("contactInformationDTO") ContactInformationDTO contactInformationDTO, HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        CustomerDTO customerDTO= (CustomerDTO) session.getAttribute(SESSION);
        ContactInformation contactInformation = ContactInformation.builder().build();
        BeanUtils.copyProperties(contactInformationDTO, contactInformation);
        customerDTO.setContactInformation(contactInformation);
        session.setAttribute(SESSION, customerDTO);
        CustomerDTO customer3= (CustomerDTO) request.getSession().getAttribute(SESSION);
        model.addAttribute("customerDTO", customer3);
        return "/customers/resume";
    }

}
