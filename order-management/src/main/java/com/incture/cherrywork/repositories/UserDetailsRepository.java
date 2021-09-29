package com.incture.cherrywork.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.UsersDetailDo;



@Repository
public interface UserDetailsRepository extends JpaRepository<UsersDetailDo, String> {

	UsersDetailDo findByUserId(String userId);

	List<UsersDetailDo> findByUserGuidIn(List<String> userGuidList);

}
