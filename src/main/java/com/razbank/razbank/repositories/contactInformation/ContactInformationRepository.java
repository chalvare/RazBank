package com.razbank.razbank.repositories.contactInformation;

import com.razbank.razbank.entities.contactInformation.ContactInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactInformationRepository extends JpaRepository<ContactInformation,Integer> {
}
