package springBootSmartContactManager.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import springBootSmartContactManager.entity.Contact;
import springBootSmartContactManager.entity.User;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

	@Query("from Contact as c where c.user.id=:userId")
	//currentPage=page
	//contact per page=5
	public Page<Contact> findcontactByUser(@Param("userId") int userId,Pageable pePageable );
	
	//search
	public List<Contact> findByNameContainingAndUser(String name,User user);
}
