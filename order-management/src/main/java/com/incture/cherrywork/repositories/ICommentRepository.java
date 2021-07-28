package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.CommentDo;

@Repository
public interface ICommentRepository extends JpaRepository<CommentDo, String>{

}
