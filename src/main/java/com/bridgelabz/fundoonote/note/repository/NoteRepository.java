package com.bridgelabz.fundoonote.note.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonote.note.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

	Optional<Note> findByTitle(String title);

}