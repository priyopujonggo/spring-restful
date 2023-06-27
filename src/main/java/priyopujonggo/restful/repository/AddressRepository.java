package priyopujonggo.restful.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import priyopujonggo.restful.entity.Address;
import priyopujonggo.restful.entity.Contact;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
    
    Optional<Address> findFirstByContactAndId(Contact contact, String id);

    List<Address> findAllByContact(Contact contact);
}
