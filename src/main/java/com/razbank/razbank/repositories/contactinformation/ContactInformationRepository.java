package com.razbank.razbank.repositories.contactinformation;

import com.razbank.razbank.entities.contactinformation.ContactInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactInformationRepository extends JpaRepository<ContactInformation,Integer> {
}
